package com.thilojaeggi.hub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {
    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.getServer().toString().equals("lobby")){
            ServerInfo target = ProxyServer.getInstance().getServerInfo("lobby");
            player.connect(target);
        } else {
            player.sendMessage("Du bist bereits auf dem Lobby Server");
        }
    }
}