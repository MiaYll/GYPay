package me.wangcai.gypaybukkit;

import me.wangcai.gypaybukkit.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class GYPayBukkit extends JavaPlugin {

    private Config config;
    private static GYPayBukkit gyPayBukkit;

    @Override
    public void onEnable() {
        gyPayBukkit = this;
        config = new Config(this);
    }

    public Config getPluginConfig(){
        return config;
    }

    public static GYPayBukkit getGyPayBukkit() {
        return gyPayBukkit;
    }
}
