package com.OSRSDelivery;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class OSRSDeliveryTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(OSRSDeliveryPlugin.class);
		RuneLite.main(args);
	}
}