package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryUtils {

    private Map<String, InventoryData> inventories = new HashMap<>();

    public InventoryUtils(){
        File pathToInventory = new File(GUIShop.INSTANCE.getDataFolder() + "/inventories");
        File[] allFiles = pathToInventory.listFiles();
        if(allFiles == null){
            createDefaultInventory();
            allFiles = pathToInventory.listFiles();
        }
        int numberOfFiles = allFiles.length;
        for(int i = 0; i != numberOfFiles; i++){
            InventoryData data = new InventoryData(allFiles[i].getName());
            inventories.put(allFiles[i].getName(), data);
            System.out.println("[BurningCube] Loaded Inventory: " + i+1 + "/" + numberOfFiles + " Debug log: name: " + allFiles[i].getName());
        }
    }
    public InventoryData getInventory(Inventory inv){
        Optional<InventoryData> optionalInventoryData = inventories.values()
                .stream()
                .filter(value -> value.getInventory() == inv)
                .findAny();

        return optionalInventoryData.orElse(null);
    }

    public InventoryData[] getAllInventory(){
        return inventories.values().toArray(new InventoryData[0]);
    }

    public Inventory getInventory(String name){
        if(!inventories.containsKey(name)){
            return null;
        }
        return inventories.get(name).getInventory();
    }
    public void createDefaultInventory(){
        GUIShop.INSTANCE.saveResource("inventories/ExampleInventory.yml", false);
    }
}
