package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.ItemData;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class DynamicPriceUtility {

    private static boolean isEnabled = ConfigUtility.getConfig().getBoolean("dynamic_economy.enabled", true);

    //Increase value
    public static void checkValue(ItemData iData, Inventory inventory, int slot) throws IOException {
        if(!isEnabled){
            return;
        }
        int avargeBuy = iData.getBuyed()/iData.getToChangePrice();
        int avargeSell = iData.getSelled()/iData.getToChangePrice();
        if(avargeBuy >= 1){
            increaseValueBuy(iData, inventory, slot);
            inventory.close();
        }
        if (avargeSell >= 1) {
            decreaseValueSell(iData, inventory, slot);
            inventory.close();
        }
        iData.reloadLore();
    }

    public static void increaseValue(ItemData iData, Inventory inventory, int slot) throws IOException{
        if(!isEnabled){
            return;
        }
        double percent = ConfigUtility.getConfig().getInt("dynamic_economy.needs_percent", 1)/100.0;
        long finalValue = Math.round(iData.getToChangePrice()*percent);
        if(iData.getLastPeriodBuy() <= finalValue && iData.getLastPeriodSell() <= finalValue){
            if(iData.getBuy()-iData.getSell() <= iData.getBuy()*0.1){
                increaseValueSell(iData, inventory, slot);
                increaseValueBuy(iData, inventory, slot);
            }else {
                decreaseValueBuy(iData, inventory, slot);
                increaseValueSell(iData, inventory, slot);
            }
            while(iData.getBuy() < iData.getSell()){
                iData.setSell(iData.getSell()-(iData.getBuy()*0.1));
            }
            iData.reloadLore();
            inventory.close();
        }
        iData.setLastPeriodBuy(0);
        iData.setLastPeriodSell(0);
    }

    public static void increaseValueBuy(ItemData iData, Inventory inventory, int slot) throws IOException {
        double inBuy = ConfigUtility.getConfig().getInt("dynamic_economy.increase_buy", 1)/100.0;
        iData.setBuy(Math.round((iData.getBuy() * (1 + (inBuy)))*100.0)/100.0);
        iData.setBuyed(0);
        iData.reloadLore();
    }
    public static void decreaseValueBuy(ItemData iData, Inventory inventory, int slot) throws IOException {
        double inBuy = ConfigUtility.getConfig().getInt("dynamic_economy.decrease_buy", 1)/100.0;
        iData.setBuy(Math.round((iData.getBuy() * (1 - (inBuy)))*100.0)/100.0);
        iData.setBuyed(0);
        iData.reloadLore();
    }
    public static void increaseValueSell(ItemData iData, Inventory inventory, int slot) throws IOException {
        double inSell = ConfigUtility.getConfig().getInt("dynamic_economy.increase_sell", 1)/100.0;
        iData.setSell(Math.round((iData.getSell() * (1 + (inSell)))*100.0)/100.0);
        iData.setSelled(0);
        iData.reloadLore();
    }
    public static void decreaseValueSell(ItemData iData, Inventory inventory, int slot) throws IOException {
        double inSell = ConfigUtility.getConfig().getInt("dynamic_economy.decrease_sell", 1)/100.0;
        iData.setSell(Math.round((iData.getSell() * (1 - (inSell)))*100.0)/100.0);
        iData.setSelled(0);
        iData.reloadLore();
    }
}
