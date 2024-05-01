package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.ItemData;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class DynamicPriceUtility {

    //Increase value
    public static void checkValue(ItemData iData, Inventory inventory, int slot) throws IOException {
        int avargeBuy = iData.getBuyed()/iData.getToChangePrice();
        int avargeSell = iData.getSelled()/iData.getToChangePrice();
        double inBuy = ConfigUtility.getConfig().getInt("dynamic_economy.increase_buy", 1)/100.0;
        double inSell = ConfigUtility.getConfig().getInt("dynamic_economy.decrease_sell", 1)/100.0;
        if(avargeBuy >= 1){
            //Increase buy price
            iData.setBuy(Math.round((iData.getBuy() * (1 + (avargeBuy*inBuy)))*100.0)/100.0);
            iData.setBuyed(0);
            inventory.close();
        }
        if (avargeSell >= 1) {
            //Decrease sell price
            iData.setSell(Math.round((iData.getSell() * (1 - (avargeSell*inSell)))*100.0)/100.0);
            iData.setSelled(0);
            inventory.close();
        }
        iData.reloadLore();
    }

    public static void increaseValue(ItemData iData, Inventory inventory, int slot) throws IOException{
        double percent = ConfigUtility.getConfig().getInt("dynamic_economy.needs_percent", 1)/100.0;
        long finalValue = Math.round(iData.getToChangePrice()*percent);
        if(iData.getLastPeriodBuy() <= finalValue && iData.getLastPeriodSell() <= finalValue){
            System.out.println(iData.getBuy() + " " + iData.getSell());
            System.out.println(iData.getBuy()-iData.getSell() + " " + iData.getBuy()*0.1);
            if(iData.getBuy()-iData.getSell() <= iData.getBuy()*0.1){
                System.out.println("Succes");
                increaseValueSell(iData, inventory, slot);
                increaseValueBuy(iData, inventory, slot);
            }else {
                System.out.println(iData.getLastPeriodBuy() + "   " + iData.getLastPeriodSell() + "   " + finalValue);
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
