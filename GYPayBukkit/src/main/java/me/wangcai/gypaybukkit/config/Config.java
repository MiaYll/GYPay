package me.wangcai.gypaybukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.List;

public class Config{
    public final String SETTINGS_SERVER;
    public final String SETTINGS_NAME;
    public final String SETTINGS_PASSWORD;
    public final int SETTINGS_RATE;
    public final List<String> COMMANDS;

    public Config(Plugin plugin){
        File file = new File(plugin.getDataFolder() + "/config.yml");
        if(!file.exists()) plugin.saveResource("config.yml",false);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        SETTINGS_SERVER = yaml.getString("settings.server");
        SETTINGS_NAME = yaml.getString("settings.name");
        SETTINGS_PASSWORD = yaml.getString("settings.password");
        SETTINGS_RATE = yaml.getInt("settings.rate");
        COMMANDS = yaml.getStringList("commands");
    }
}
