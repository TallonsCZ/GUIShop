package me.tallonscze.guishop.event;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuEvent implements Listener {

    @EventHandler
    public void invEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
        if(clickedInv == null || !clickedInv.equals(GUIShop.INSTANCE.getMenu().getInv())){
            return;
        }
        event.setCancelled(true);
        int slot = event.getSlot();
        InventoryData data = GUIShop.INSTANCE.getMenu().getMap().get(slot);
        if(data == null){
            return;
        }
        player.closeInventory();
        player.openInventory(data.getInventory());
    }
}
