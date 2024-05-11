package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class MenuUtility {

    private Inventory inv;
    private int countInventory;

    private Map<Integer, InventoryData> inventories= new HashMap<>();
    public Inventory getInv(){
        return inv;
    }

    public MenuUtility(int numInv){
        this.countInventory = numInv;
        inv = Bukkit.createInventory(null, 54, Component.text(ConfigUtility.getMenuConfig().getString("global.name", "Shops menu")));
        int numOfInsertInv = 0;
        int placed = 0;
        int finalSlot = 10;
        for (InventoryData invData: GUIShop.INSTANCE.invUtility.getAllInventory()) {
            numOfInsertInv++;
            if(numOfInsertInv > 28){
                numOfInsertInv = 0;
                //next inventory or done
            }
            Material icon;
            icon = Material.matchMaterial(invData.getIcon());
            if(icon == null){
                icon = Material.STONE;
            }
            ItemStack invItem = new ItemStack(icon);
            ItemMeta invMeta = invItem.getItemMeta();
            invMeta.displayName(Component.text(invData.getName()));
            invItem.setItemMeta(invMeta);
            inv.setItem(finalSlot+placed, invItem);
            inventories.put(finalSlot+placed, invData);
            placed++;
            if(placed == 7){
                finalSlot = finalSlot+placed+2;
                placed = 0;
            }

        };
    }
    public Map<Integer, InventoryData> getMap(){
        return inventories;
    }
}
