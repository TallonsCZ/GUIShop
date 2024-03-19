package me.tallonscze.guishop.event;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import me.tallonscze.guishop.data.ItemData;
import me.tallonscze.guishop.utility.DynamicPriceUtility;
import me.tallonscze.guishop.utility.VaultUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class InventoryEvents implements Listener {

    @EventHandler
    private void onPlayerClickInventoryEvent(InventoryClickEvent event) throws IOException {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
        InventoryData data = GUIShop.invUtility.getInventory(clickedInv);
        if(data == null || clickedInv == null || !clickedInv.equals(data.getInventory())){
            return;
        }
        event.setCancelled(true);
        int slot = event.getSlot();
        if (data.getItem(slot) == null) {
            return;
        }
        ItemData iData = data.getItem(slot);
        if (event.getClick().isCreativeAction() && VaultUtility.getPermissions().playerHas(player, "guishop.admin.canedit")){
            return;
        } else if (event.getClick().isCreativeAction()) {
            player.sendMessage(Component.text("You dont use shop while have Creative mode!"));
        }
        ItemStack item = data.getItem(slot).getItem();
        Material material = item.getType();
        if (event.getClick().isLeftClick() && event.getClick().isMouseClick()){
            BuyEvent buyEvent = new BuyEvent(player ,data.getItem(slot).getBuy());
            if(buyEvent.isSucces()){
                player.getInventory().addItem(item);
                iData.setBuyed(1);
                data.setBuyed(iData.getBuyed(), slot);
                iData.setLastPeriodBuy(1);
                data.setLastBuy(iData.getLastPeriodBuy(), slot);
            }
        } else if (event.getClick().isRightClick()) {
            if(player.getInventory().contains(material)){
                SellEvent sellEvent = new SellEvent(player, data.getItem(slot).getSell());
                iData.setSelled(1);
                data.setSelled(iData.getSelled(), slot);
                sell(player, material, 1);
                iData.setLastPeriodSell(1);
                data.setLastSell(iData.getLastPeriodSell(), slot);

            }else{
                player.sendMessage(Component.text("[BurningCube] You dont have material in your inventory.."));
            }

        }
        ItemData newData = DynamicPriceUtility.checkValue(iData, clickedInv, slot);
        if(newData != null){
            data.setItemToMap(newData, slot);
            player.openInventory(clickedInv);
        }

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
