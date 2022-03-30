package me.wangcai.gypaybukkit.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;


public class Message {
    public final String OPEN;
    public final String SUCCESS;
    public final String CLOSE;
    public final String PAYING;

    public Message(Plugin plugin) {
        File file = new File(plugin.getDataFolder() + "/message.yml");
        if (!file.exists()) plugin.saveResource("message.yml", false);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        OPEN = yaml.getString("open").replace("&","§");
        SUCCESS = yaml.getString("success").replace("&","§");
        CLOSE = yaml.getString("close").replace("&","§");
        PAYING = yaml.getString("paying" ).replace("&","§");

    }
}
