package me.tallonscze.guishop.event;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import me.tallonscze.guishop.utility.ConfigUtility;
import me.tallonscze.guishop.utility.DynamicPriceUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

public class TimerEvent implements Listener {
    int TickCount = 0;
    int SaveInterval = ConfigUtility.getConfig().getInt("dynamic_economy.timer",30)*20;

    @EventHandler
    public void Timer(ServerTickEndEvent event) throws IOException {
        TickCount++;
        if(TickCount <= SaveInterval){
            return;
        }
        TickCount = 0;
        checkAllInventories();
        saveAllInventories();
    }

    public static void checkAllInventories() throws IOException{
        GUIShop.INSTANCE.getLogger().info("Starting price update..");
        InventoryData[] inventories = GUIShop.INSTANCE.invUtility.getAllInventory();
        for (InventoryData iData: inventories) {
            iData.getAllItemData().forEach((key, value) -> {
                try {
                    DynamicPriceUtility.increaseValue(value, iData.getInventory(), key);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            iData.reloadAllItemsToInventory();
        }
        GUIShop.INSTANCE.getLogger().info("Price update stoped..");
    }

    public void saveAllInventories(){
        InventoryData[] inventories = GUIShop.INSTANCE.invUtility.getAllInventory();
        for (InventoryData inv: inventories) {
            try {
                inv.saveInventoryToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
