package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.ItemData;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class DynamicPriceUtility {

    //Increase value
    public static ItemData checkValue(ItemData iData, Inventory inventory, int slot) throws IOException {
        int avargeBuy = iData.getBuyed()/iData.getToChangePrice();
        int avargeSell = iData.getSelled()/iData.getToChangePrice();
        double inBuy = ConfigUtility.getConfig().getInt("dynamic_economy.increase_buy", 1)/100.0;
        double inSell = ConfigUtility.getConfig().getInt("dynamic_economy.decrease_sell", 1)/100.0;
        if(avargeBuy >= 1){
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

    public static ItemData increaseValue(ItemData iData, Inventory inventory, int slot) throws IOException{
        double percent = ConfigUtility.getConfig().getInt("dynamic_economy.needs_percent", 1)/100.0;
        long finalValue = Math.round(iData.getToChangePrice()*percent);
        if(iData.getLastPeriodBuy() <= finalValue && iData.getLastPeriodSell() <= finalValue){
            System.out.println(iData.getLastPeriodBuy() + "   " + iData.getLastPeriodSell() + "   " + finalValue);
            double inBuy = ConfigUtility.getConfig().getInt("dynamic_economy.decrease_buy", 1)/100.0;
            double inSell = ConfigUtility.getConfig().getInt("dynamic_economy.increase_sell", 1)/100.0;
            if(iData.getLastPeriodBuy() <= finalValue || iData.getLastPeriodSell() <= finalValue){
                iData.setBuy(Math.round((iData.getBuy() * (1 - (inBuy)))*100.0)/100.0);
                iData.setBuyed(0);
                GUIShop.invUtility.getInventory(inventory).setBuyed(0, slot);
                GUIShop.invUtility.getInventory(inventory).setBuy(iData.getBuy(), slot);
                iData.setSell(Math.round((iData.getSell() * (1 + (inSell)))*100.0)/100.0);
                GUIShop.invUtility.getInventory(inventory).setSelled(0, slot);
                GUIShop.invUtility.getInventory(inventory).setSell(iData.getSell(), slot);
                iData.setSelled(0);
                iData.reloadLore();
                inventory.close();
            }
            iData.setLastPeriodBuy(0);
            iData.setLastPeriodSell(0);
            GUIShop.invUtility.getInventory(inventory).setLastSell(0, slot);
            GUIShop.invUtility.getInventory(inventory).setLastBuy(0, slot);
        }

        return iData;
    }
}
