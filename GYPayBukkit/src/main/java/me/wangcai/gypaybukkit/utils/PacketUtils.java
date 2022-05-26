package me.wangcai.gypaybukkit.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapPalette;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PacketUtils {

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    @SneakyThrows
    public static PacketContainer editMapViewPacket(PacketContainer packet,byte[] bytes,boolean downgrade) {
        if(downgrade){
            byte[] origin = packet.getByteArrays().read(0);
            for (int y = 0; y < 128; ++y) {
                origin[y + 3] = bytes[y * 128 + origin[1]];
            }
            packet.getByteArrays().write(0,origin);
        }else{
            packet.getIntegers().write(1, Integer.valueOf(0));
            packet.getIntegers().write(2, Integer.valueOf(0));
            packet.getIntegers().write(3, Integer.valueOf(128));
            packet.getIntegers().write(4, Integer.valueOf(128));
            packet.getBytes().write(0, Byte.valueOf((byte)0));
            packet.getByteArrays().write(0, bytes);
            packet.getBooleans().write(0, Boolean.valueOf(true));
        }
        return packet;
    }

    @SneakyThrows
    public static void sendMapItemPacket(Player player, ItemStack map,boolean downgrade) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.WINDOW_ITEMS);
        ArrayList<ItemStack> items = new ArrayList<>(46);
        int slot = 36 + player.getInventory().getHeldItemSlot();
        for (int i = 0; i < 45; i++)
            items.add(new ItemStack(Material.AIR));
        items.set(slot, map);
        packet.getIntegers().write(0, Integer.valueOf(0));
        if(downgrade){
            packet.getItemArrayModifier().write(0, items.toArray(new ItemStack[items.size()]));
        }else{
            packet.getItemListModifier().write(0, items);
        }
        protocolManager.sendServerPacket(player, packet, false);
    }
}
