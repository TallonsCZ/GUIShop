package me.tallonscze.guishop.event;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {

    @EventHandler
    private void onPlayerClickInventoryEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
        InventoryData data = GUIShop.invUtility.getInventory(clickedInv);
        if (data == null){
            return;
        }
        int slot = event.getSlot();
        if (data.getItem(slot) == null) {
            return;
        }
        System.out.println(clickedInv);
        if(clickedInv == null || !clickedInv.equals(data.getInventory())){
            return;
        }
        ItemStack item = data.getItem(slot).getItem();
        Material material = item.getType();
        if (event.getClick().isLeftClick() && event.getClick().isMouseClick()){
            BuyEvent buyEvent = new BuyEvent(player ,data.getItem(slot).getBuy());
            if(buyEvent.isSucces()){
                player.getInventory().addItem(item);
            }
        } else if (event.getClick().isRightClick()) {
            if(player.getInventory().contains(material)){
                SellEvent sellEvent = new SellEvent(player, data.getItem(slot).getSell());
                sell(player, material, 1);
            }else{
                player.sendMessage(Component.text("[BurningCube] You dont have material in your inventory.."));
                System.out.println(item + " " + player.getInventory().contains(item) + player.getInventory().getItem(0) + player.getInventory().getItem(1));
            }
        }
        event.setCancelled(true);
    }

    public void sell(Player player, Material item, int amount){
        Inventory pInv = player.getInventory();

        ItemStack[] items;
        if(!pInv.contains(item, amount)){
            return;
        }
        items = pInv.getStorageContents();
        for (ItemStack itemToCheck: items) {
            if(itemToCheck == null){
                continue;
            }
            if(itemToCheck.getType().equals(item)){
                itemToCheck.setAmount(itemToCheck.getAmount()-1);
                break;
            }
        }
    }


}
