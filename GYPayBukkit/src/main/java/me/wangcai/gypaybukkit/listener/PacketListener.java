package me.wangcai.gypaybukkit.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import java.util.List;
import java.util.UUID;

import me.wangcai.gypaybukkit.GYPayBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class PacketListener extends PacketAdapter {
    private final GYPayBukkit plugin;

    public PacketListener(GYPayBukkit plugin) {
        super(plugin, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Client.BLOCK_DIG });
        this.plugin = plugin;
    }


    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        List<UUID> cache = GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache();
        if(cache.remove(player.getUniqueId())){
            event.getPlayer().sendMessage(GYPayBukkit.getGyPayBukkit().getMessage().CLOSE);
        }
    }

    public void onPacketReceiving(PacketEvent event) {
        EnumWrappers.PlayerDigType digType = event.getPacket().getPlayerDigTypes().read(0);
        if (digType == EnumWrappers.PlayerDigType.DROP_ITEM || digType == EnumWrappers.PlayerDigType.DROP_ALL_ITEMS)
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> event.getPlayer().updateInventory(), 2L);
    }
}
