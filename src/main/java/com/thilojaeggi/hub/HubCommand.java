package com.thilojaeggi.hub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HubCommand extends Command {
    Configuration configuration, lastreceivers;
    public HubCommand() {
        super("hub", "hub.use", "lobby");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Plugin plugin = Main.plugin;
        File Config = new File(plugin.getDataFolder(), "config.yml");
        File LastReceivers = new File(plugin.getDataFolder(), "lastreceivers.yml");
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Config);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.getServer().toString().equals(configuration.getString("hub"))){
            ServerInfo target = ProxyServer.getInstance().getServerInfo(configuration.getString("hub"));
            player.connect(target);
        } else {
            player.sendMessage("Du bist bereits auf dem Lobby Server");
        }
    }
}