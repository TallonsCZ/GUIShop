package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import me.tallonscze.guishop.data.ItemData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class DynamicPriceUtility {

    //Increase value
    public static ItemData checkValue(ItemData iData, Inventory inventory, int slot) throws IOException {
        int avargeBuy = iData.getBuyed()/iData.getToChangePrice();
        int avargeSell = iData.getSelled()/iData.getToChangePrice();
        double inBuy = ConfigUtility.getConfig().getInt("dynamic_economy.increase_buy", 1)/100.0;
        double inSell = ConfigUtility.getConfig().getInt("dynamic_economy.increase_sell", 1)/100.0;
        if(avargeBuy >= 1){
            System.out.println(Math.round((iData.getBuy() * (1 + (avargeBuy*inBuy)))*100.0)/100.0);
            System.out.println("inBuy is: " + inBuy);
            System.out.println(((((1 + (avargeBuy*inBuy)))*100.0)/100.0));
            iData.setBuy(Math.round((iData.getBuy() * (1 + (avargeBuy*inBuy)))*100.0)/100.0);
            iData.setBuyed(0);
            GUIShop.invUtility.getInventory(inventory).setBuyed(0, slot);
            GUIShop.invUtility.getInventory(inventory).setBuy(iData.getBuy(), slot);
            inventory.close();
        } else if (avargeSell >= 1) {
            iData.setSell(Math.round((iData.getSell() * (1 - (avargeSell*inSell)))*100.0)/100.0);
            GUIShop.invUtility.getInventory(inventory).setSelled(0, slot);
            GUIShop.invUtility.getInventory(inventory).setSell(iData.getSell(), slot);
            iData.setSelled(0);
            inventory.close();
        } else {
            return null;
        }
        iData.reloadLore();
        return iData;
    }
}
