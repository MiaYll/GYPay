package me.wangcai.gypaybukkit.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.wangcai.gypaybukkit.GYPayBukkit;
import me.wangcai.gypaybukkit.utils.PacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class PacketListener extends PacketAdapter {
    private final GYPayBukkit plugin;
    private boolean downgrade = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].equals("v1_7_R4");

    public PacketListener(GYPayBukkit plugin) {
        super(plugin, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Client.BLOCK_DIG,PacketType.Play.Server.MAP });
        this.plugin = plugin;
    }


    public void onPacketSending(PacketEvent event) {
        Map<UUID, byte[]> cache = GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache();
        if(cache.containsKey(event.getPlayer().getUniqueId()) && event.getPacket().getType() == PacketType.Play.Server.MAP){
            event.setPacket(PacketUtils.editMapViewPacket(event.getPacket(), cache.get(event.getPlayer().getUniqueId()), downgrade));
            return;
        }
        Player player = event.getPlayer();
        if(cache.remove(player.getUniqueId()) != null){
            event.getPlayer().sendMessage(GYPayBukkit.getGyPayBukkit().getMessage().CLOSE);
        }
    }

    public void onPacketReceiving(PacketEvent event) {
        if (GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache().containsKey(event.getPlayer().getUniqueId()))
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> event.getPlayer().updateInventory(), 2L);
    }
}
