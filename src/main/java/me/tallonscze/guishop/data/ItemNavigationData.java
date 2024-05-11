package me.tallonscze.guishop.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemNavigationData {
    private ItemData backItem;
    public ItemNavigationData(){
        createBackItem();
    }

    private void createBackItem(){
        backItem = new ItemData("diamond", 1, "Back to inventory");
        backItem.setBack(true);
    }

    public ItemData getBackItem(){
        return backItem;
    }
}
