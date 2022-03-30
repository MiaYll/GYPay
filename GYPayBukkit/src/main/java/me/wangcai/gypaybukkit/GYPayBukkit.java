package me.wangcai.gypaybukkit;

import me.wangcai.gypaybukkit.command.Commands;
import me.wangcai.gypaybukkit.config.Config;
import me.wangcai.gypaybukkit.config.Message;
import me.wangcai.gypaybukkit.listener.PlayerListener;
import me.wangcai.gypaybukkit.service.IPayService;
import me.wangcai.gypaybukkit.service.impl.PayServiceImpl;
import me.wangcai.gypaybukkit.task.CheckShipTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GYPayBukkit extends JavaPlugin {

    private Config config;
    private Message message;
    private static GYPayBukkit gyPayBukkit;
    private IPayService payService;
    private CheckShipTask checkShipTask;

    @Override
    public void onEnable() {
        gyPayBukkit = this;
        config = new Config(this);
        message = new Message(this);
        payService = new PayServiceImpl();
        checkShipTask = new CheckShipTask(this);
        checkShipTask.runTaskTimerAsynchronously(this,100,100);
        Bukkit.getPluginCommand("gypay").setExecutor(new Commands());
        new PlayerListener(this);
    }

    public Config getPluginConfig(){
        return config;
    }

    public static GYPayBukkit getGyPayBukkit() {
        return gyPayBukkit;
    }

    public IPayService getPayService() {
        return payService;
    }

    public CheckShipTask getCheckShipTask() {
        return checkShipTask;
    }

    public Message getMessage() {
        return message;
    }
}
