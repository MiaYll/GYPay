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
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;

public class PayServiceImpl implements IPayService {

    private HttpUtils httpUtils = new HttpUtils();
    private Gson gson = new Gson();
    private Map<UUID,byte[]> payingCache = new HashMap<>();
    private boolean downgrade = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].equals("v1_7_R4");


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
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.setScale(org.bukkit.map.MapView.Scale.FARTHEST);
        ItemStack item = ItemUtils.createItem(Material.MAP, "§a§l扫码地图", Arrays.asList("§7· 请扫描地图上的二维码"));
        item.setDurability(mapView.getId());
        ItemMeta itemMeta = item.getItemMeta();
        if(!downgrade) itemMeta.addItemFlags(ItemFlag.values());
        item.setItemMeta(itemMeta);
        PacketUtils.sendMapItemPacket(player,item,downgrade);
        payingCache.put(player.getUniqueId(),MapPalette.imageToBytes(qrCode));
        player.sendMap(mapView);
        return true;
    }

    @Override
    public Map<UUID,byte[]> getPayingCache() {
        return payingCache;
    }


}
