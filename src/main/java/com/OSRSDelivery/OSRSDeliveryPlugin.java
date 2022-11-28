package com.OSRSDelivery;

import com.OSRSDelivery.Area.AreaIds;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Delivery Service"
)
public class OSRSDeliveryPlugin extends Plugin {
	@Inject
	private Client client;
	@Inject
	private OSRSDeliveryConfig config;
	@Inject
	private ClientToolbar clientToolbar;

	private Boolean loggedIn = false;
	private AreaIds location;
	private OSRSDeliveryPanel deliveryPanel;
	private NavigationButton navButton;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Delivery Service Plugin started!");
		deliveryPanel = injector.getInstance(OSRSDeliveryPanel.class);
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/panel.png");

		navButton = NavigationButton.builder()
				.tooltip("Resource packs hub")
				.icon(icon)
				.priority(10)
				.panel(deliveryPanel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Delivery Service Plugin stopped!");
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			loggedIn = true;
			location = AreaIds.fromRegion(client.getLocalPlayer().getWorldLocation().getRegionID());
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick) {
		if (loggedIn) {
			AreaIds newLocation = AreaIds.fromRegion(client.getLocalPlayer().getWorldLocation().getRegionID());
			if (!newLocation.getAreaName().equals(location.getAreaName())) {
				location = newLocation;
				client.addChatMessage(ChatMessageType.GAMEMESSAGE,
						"", "Now Entering " + newLocation.getAreaName(), null);
			}
		}
	}

	@Provides
	OSRSDeliveryConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OSRSDeliveryConfig.class);
	}
}
