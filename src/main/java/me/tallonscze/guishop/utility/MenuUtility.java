package me.tallonscze.guishop.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class MenuUtility {

    private static Inventory inv;

    public static Inventory getInv(){
        return inv;
    }

    MenuUtility(){
        inv = Bukkit.createInventory(null, 27, Component.text(ConfigUtility.getMenuConfig().getString("global.name", "Shops menu")));
    }
}
