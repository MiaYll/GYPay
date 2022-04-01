package me.wangcai.gypaybukkit.task;

import me.wangcai.gypaybukkit.GYPayBukkit;
import me.wangcai.gypaybukkit.api.event.OrderCreateEvent;
import me.wangcai.gypaybukkit.model.Order;
import me.wangcai.gypaybukkit.service.IPayService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckShipTask extends BukkitRunnable {

    private GYPayBukkit plugin;

    public CheckShipTask(GYPayBukkit plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        IPayService payService = GYPayBukkit.getGyPayBukkit().getPayService();
        for (Order order : payService.getUnShipOrder()) {
            Player player = Bukkit.getPlayer(order.getUsername());
            if(player == null || !player.isOnline()) continue;
            if(payService.ship(order.getOrderId())){
                player.updateInventory();
                OrderCreateEvent event = new OrderCreateEvent(player, order, false);
                Bukkit.getPluginManager().callEvent(event);
                if(event.isCancelled()) continue;
                player.sendMessage(GYPayBukkit.getGyPayBukkit().getMessage().SUCCESS
                        .replace("%money%",order.getPrice() + "")
                        .replace("%points%",order.getPrice() * GYPayBukkit.getGyPayBukkit().getPluginConfig().SETTINGS_RATE + "")
                        .replace("%order%",order.getOrderId()));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (String command : GYPayBukkit.getGyPayBukkit().getPluginConfig().COMMANDS) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    command.replace("%player%",player.getName())
                                            .replace("%points%", String.valueOf((int)(GYPayBukkit.getGyPayBukkit().getPluginConfig().SETTINGS_RATE * order.getPrice())))
                            );
                        }
                    }
                }.runTask(plugin);
            }
        }
    }
}
