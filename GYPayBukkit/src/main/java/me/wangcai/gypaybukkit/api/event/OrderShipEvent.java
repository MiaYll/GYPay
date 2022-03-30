package me.wangcai.gypaybukkit.api.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.wangcai.gypaybukkit.model.Order;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
@Data
public class OrderShipEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private Player player;
    private Order order;

    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
