package me.wangcai.gypaybukkit.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static ItemStack createItem(Material material, String name, List<String> lorries) {
        return createItem(material, name, lorries, new HashMap<>(0));
    }

    public static ItemStack createItem(Material material, String name, List<String> lorries, Map<String, Object> replace) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(parse(name, replace));
        itemMeta.setLore(parse(lorries, replace));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static List<String> parse(List<String> list, Map<String, Object> replace) {
        return parse(list, replace, (Player)null);
    }

    public static List<String> parse(List<String> list, Map<String, Object> replace, Player player) {
        List<String> newList = new ArrayList<>();
        list.forEach(e -> newList.add(parse(e, replace, player)));
        return newList;
    }

    public static String parse(String string, Map<String, Object> replace) {
        return parse(string, replace, (Player)null);
    }

    public static String parse(String string, Map<String, Object> replace, Player player) {
        if (!replace.isEmpty())
            for (Map.Entry<String, Object> entry : replace.entrySet())
                string = string.replace(entry.getKey(), entry.getValue().toString());
        if (player != null)
            string = PlaceholderAPI.setPlaceholders(player, string);
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
