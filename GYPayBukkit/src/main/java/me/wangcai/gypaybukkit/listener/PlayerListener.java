package me.wangcai.gypaybukkit.listener;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;


import me.wangcai.gypaybukkit.GYPayBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerListener implements Listener {
    private final GYPayBukkit plugin;

    public PlayerListener(GYPayBukkit plugin) {
        this.plugin = plugin;
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketListener(plugin));
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        HumanEntity clicked = event.getWhoClicked();
        if (clicked instanceof Player &&
                GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache().contains(clicked.getUniqueId())) {
            ((Player)clicked).updateInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player &&
                GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache().contains(entity.getUniqueId())) {
            ((Player)entity).updateInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache().contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().updateInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onToggleHand(PlayerChangedMainHandEvent event) {
        if (GYPayBukkit.getGyPayBukkit().getPayService().getPayingCache().contains(event.getPlayer().getUniqueId()))
            event.getPlayer().updateInventory();
    }
}
