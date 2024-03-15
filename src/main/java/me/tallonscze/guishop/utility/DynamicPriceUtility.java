package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.data.ItemData;
import org.bukkit.inventory.Inventory;

public class DynamicPriceUtility {
    public static void checkValue(ItemData iData, Inventory inventory){
        int avargeBuy = iData.getBuyed()/10;
        int avargeSell = iData.getSelled()/10;
        if(avargeBuy >= 1){
            iData.setBuy(iData.getBuyed() * avargeBuy);
            iData.setBuyed(0);
            inventory.close();
        } else if (avargeSell >= 1) {
            iData.setSell(iData.getSell() * 1 - (avargeSell*0.1));
            iData.setSelled(0);
            inventory.close();
        } else {
            return;
        }
        iData.reloadLore();
    }
}
