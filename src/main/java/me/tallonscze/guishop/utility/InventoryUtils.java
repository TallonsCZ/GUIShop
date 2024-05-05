package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryUtils {

    private final File pathToInventory = new File(GUIShop.INSTANCE.getDataFolder() + "/inventories");
    private Map<String, InventoryData> inventories = new HashMap<>();

    public InventoryUtils(){
        int numberOfFiles;
        if(getAllFiles() == null){
            createDefaultInventory();
            numberOfFiles = 1;
        }else{
            numberOfFiles = getAllFiles().length;
        }
        File[] allFile = getAllFiles();
        for(int i = 0; i != numberOfFiles; i++){
            InventoryData data = new InventoryData(allFile[i].getName());
            inventories.put(allFile[i].getName(), data);
            System.out.println("[BurningCube] Loaded Inventory: " + i+1 + "/" + numberOfFiles + " Debug log: name: " + allFile[i].getName());
        }
    }

    private File[] getAllFiles(){
        return pathToInventory.listFiles();
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
