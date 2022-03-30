package me.wangcai.gypaybukkit.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PacketUtils {

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    @SneakyThrows
    public static void sendMapViewPacket(Player player, BufferedImage bufferedImage) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.MAP);
        byte[] bytes = com.glazed7.glazedpay.bukkit.utils.MapColor.getByte(bufferedImage);
        packet.getIntegers().write(0, Integer.valueOf(0));
        packet.getIntegers().write(1, Integer.valueOf(0));
        packet.getIntegers().write(2, Integer.valueOf(0));
        packet.getIntegers().write(3, Integer.valueOf(128));
        packet.getIntegers().write(4, Integer.valueOf(128));
        packet.getBytes().write(0, Byte.valueOf((byte)0));
        packet.getByteArrays().write(0, bytes);
        packet.getBooleans().write(0, Boolean.valueOf(true));
        protocolManager.sendServerPacket(player, packet);
    }

    @SneakyThrows
    public static void sendMapItemPacket(Player player, ItemStack map) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.WINDOW_ITEMS);
        ArrayList<ItemStack> items = new ArrayList<>(46);
        int slot = 36 + player.getInventory().getHeldItemSlot();
        for (int i = 0; i < 46; i++)
            items.add(new ItemStack(Material.AIR));
        items.set(slot, map);
        packet.getIntegers().write(0, Integer.valueOf(0));
        packet.getItemListModifier().write(0, items);
        protocolManager.sendServerPacket(player, packet, false);
    }
}
