package com.thilojaeggi.hub;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public final class Main extends Plugin {
    public static Plugin plugin;
    private static Main instance;
    Configuration Config, lastreceivers;
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
        getProxy().getPluginManager().registerCommand(this, new MsgCommand());
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand());

        instance = this;
        plugin = this;
        createFolder();

        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                ProxyServer.getInstance().broadcast("["+ChatColor.AQUA.toString()+"INFO"+ChatColor.WHITE.toString() + "] " +ChatColor.WHITE.toString() + "Binärzahlen sind " + ChatColor.BOLD.toString() + 1 + ChatColor.WHITE.toString() +  " und " + ChatColor.BOLD.toString() +2);
            }
        };
        timer.schedule(myTask, 600000, 600000);
    }
    private void createFolder() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File ConfigFile = new File(getDataFolder(), "config.yml");
        File LastReceivers = new File(getDataFolder(), "lastreceivers.yml");
        if (!LastReceivers.exists()){
            try {
                LastReceivers.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!ConfigFile.exists()) {
            try {
                ConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loaderConfiguration();
    }

    private void loaderConfiguration() {
        try {
            lastreceivers = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "lastreceivers.yml"));
            Config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            if (!Config.contains("hub")){
                Config.set("hub", "lobby");
                saveConfig();
            }
            if (!Config.contains("alert")){
                Config.set("alert", "lobby");
                saveConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * method "getConfig" you will get the configuration files depends on a string
     * in this case there are two "Config" and "Lang"
     */

    public Configuration getConfig(String file) {

        if (file.equalsIgnoreCase("Config")) {
            if (Config == null) {
                reloadConfig();
            }
            return Config;
        }
        return null;
    }
    public void setLastreceivers(String receiver,String sender){
        lastreceivers.set(receiver + "_lastmsgfrom", sender);
    }
    public String getLastreceiver(String receiver){
        return lastreceivers.get(receiver+"_lastmsgfrom").toString();
    }
    public void saveConfig() {
        if ((Config != null)) {
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(lastreceivers, new File(getDataFolder(), "lastreceivers.yml"));
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(Config, new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static Main getInstance(){
        return instance;
    }
    public void reloadConfig() {
        loaderConfiguration();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
