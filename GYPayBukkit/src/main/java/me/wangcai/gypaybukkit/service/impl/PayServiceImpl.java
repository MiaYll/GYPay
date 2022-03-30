package me.wangcai.gypaybukkit.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import me.wangcai.gypaybukkit.GYPayBukkit;
import me.wangcai.gypaybukkit.model.CreateOrderParam;
import me.wangcai.gypaybukkit.model.Order;
import me.wangcai.gypaybukkit.model.ResponseInfo;
import me.wangcai.gypaybukkit.service.IPayService;
import me.wangcai.gypaybukkit.utils.HttpUtils;
import me.wangcai.gypaybukkit.utils.ItemUtils;
import me.wangcai.gypaybukkit.utils.PacketUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PayServiceImpl implements IPayService {

    private HttpUtils httpUtils = new HttpUtils();
    private Gson gson = new Gson();
    private List<UUID> payingCache = new ArrayList<>();


    @Override
    public boolean ship(String orderId) {
        String result = httpUtils.get(GYPayBukkit.getGyPayBukkit().getPluginConfig().SETTINGS_SERVER + "/order/ship/" + orderId);
        ResponseInfo<Order> responseInfo = gson.fromJson(result,new TypeToken<ResponseInfo<Order>>(){}.getType());
        return responseInfo.getCode() == 200;
    }

    @Override
    public List<Order> getUnShipOrder() {
        String result = httpUtils.get(GYPayBukkit.getGyPayBukkit().getPluginConfig().SETTINGS_SERVER + "/order/unShipOrder");
        ResponseInfo<List<Order>> responseInfo = gson.fromJson(result,new TypeToken<ResponseInfo<List<Order>>>(){}.getType());
        return responseInfo.getObj();
    }

    @Override
    public Order createOrder(CreateOrderParam createOrderParam) {
        String result = httpUtils.postJson(GYPayBukkit.getGyPayBukkit().getPluginConfig().SETTINGS_SERVER + "/order/create",gson.toJson(createOrderParam));
        ResponseInfo<Order> responseInfo = gson.fromJson(result,new TypeToken<ResponseInfo<Order>>(){}.getType());
        return responseInfo.getObj();
    }

    @SneakyThrows
    @Override
    public boolean openOrderQRCode(Player player, String orderId) {
        BufferedImage qrCode = httpUtils.getImage(GYPayBukkit.getGyPayBukkit().getPluginConfig().SETTINGS_SERVER + "/order/qrcode/" + orderId);
        PacketUtils.sendMapViewPacket(player, qrCode);
        ItemStack item = ItemUtils.createItem(Material.MAP, "§a§l扫码地图", Arrays.asList("§7· 请扫描地图上的二维码"));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        item.setItemMeta(itemMeta);
        PacketUtils.sendMapItemPacket(player,item);
        payingCache.add(player.getUniqueId());
        return true;
    }

    @Override
    public List<UUID> getPayingCache() {
        return payingCache;
    }


}
