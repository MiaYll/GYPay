package me.wangcai.gypaybukkit.command;

import me.wangcai.gypaybukkit.GYPayBukkit;
import me.wangcai.gypaybukkit.config.Config;
import me.wangcai.gypaybukkit.enums.PayType;
import me.wangcai.gypaybukkit.model.CreateOrderParam;
import me.wangcai.gypaybukkit.model.Order;
import me.wangcai.gypaybukkit.service.IPayService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("gypay.admin")){
            return true;
        }
        IPayService payService = GYPayBukkit.getGyPayBukkit().getPayService();
        Config config = GYPayBukkit.getGyPayBukkit().getPluginConfig();
        if(args.length == 3 && args[0].equals("create")){
            Order order = null;
            int price = Integer.parseInt(args[2]);
            if(args[1].equalsIgnoreCase("wechat")){
                order = payService.createOrder(new CreateOrderParam(sender.getName(),price,"充值-" + price * config.SETTINGS_RATE + "点券","", PayType.WECHAT));
            }else if(args[1].equalsIgnoreCase("alipay")){
                order = payService.createOrder(new CreateOrderParam(sender.getName(),price,"充值-" + price * config.SETTINGS_RATE + "点券","", PayType.ALIPAY));
            }
            if(order == null) return true;
            final String orderId = order.getOrderId();
            new BukkitRunnable() {
                @Override
                public void run() {
                    payService.openOrderQRCode((Player) sender,orderId);
                }
            }.runTaskAsynchronously(GYPayBukkit.getGyPayBukkit());

        }
        return false;
    }

}
