package me.wangcai.gypaybukkit.service;

import me.wangcai.gypaybukkit.model.CreateOrderParam;
import me.wangcai.gypaybukkit.model.Order;
import me.wangcai.gypaybukkit.model.Record;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IPayService {
    boolean ship(String orderId);

    List<Order> getUnShipOrder();

    Order createOrder(CreateOrderParam createOrderParam);

    boolean openOrderQRCode(Player player, String orderId);

    Map<UUID,byte[]> getPayingCache();
}
