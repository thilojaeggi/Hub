package com.thilojaeggi.hub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ReplyCommand extends Command {
    Configuration lastreceivers;
    String message;
    public ReplyCommand() {
        super("reply", "hub.reply", "r");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        Plugin plugin = Main.plugin;
        message = Arrays.toString(args).replace("[", "").replace("]", "").replace(",", "");

        File LastReceivers = new File(plugin.getDataFolder(), "lastreceivers.yml");
        try {
            lastreceivers = ConfigurationProvider.getProvider(YamlConfiguration.class).load(LastReceivers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lastreceiver= Main.getInstance().getLastreceiver(sender.getName());
        ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(lastreceiver);
        if (targetPlayer != null) {
            targetPlayer.sendMessage("[" + ChatColor.AQUA.toString() + sender.getName() + ChatColor.WHITE.toString() + " -> " + ChatColor.AQUA.toString() + "dir" + ChatColor.WHITE.toString() + "] " + message);
            sender.sendMessage("[" + ChatColor.AQUA.toString() + "Du" + ChatColor.WHITE.toString() + " -> " + ChatColor.AQUA.toString() + targetPlayer.getName() + ChatColor.WHITE.toString() + "] " + message);
            lastreceivers.set(targetPlayer.getName().toLowerCase() + "_lastmsgfrom", sender.getName().toLowerCase());
            Main.getInstance().setLastreceivers(targetPlayer.getName(), sender.getName());
            Main.getInstance().saveConfig();
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "Spieler ist nicht online");
        }
    }
}
