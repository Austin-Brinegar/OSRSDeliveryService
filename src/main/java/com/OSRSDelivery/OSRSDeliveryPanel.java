package com.OSRSDelivery;

import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import javax.inject.Singleton;
import javax.swing.*;

@Singleton
public class OSRSDeliveryPanel extends PluginPanel {

    private final IconTextField searchBar;

    OSRSDeliveryPanel(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        searchBar = new IconTextField();
    }

}
