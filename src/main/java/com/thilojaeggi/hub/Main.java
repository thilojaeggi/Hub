package com.thilojaeggi.hub;

import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public final class Main extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
