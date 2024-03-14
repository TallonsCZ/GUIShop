package me.tallonscze.guishop.data;

import me.tallonscze.guishop.GUIShop;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.NULL;

public class InventoryData {
    private final Inventory inventory;
    private final File pathToInventory;
    private YamlConfiguration invConfig;

    private Map<Integer, ItemData> items = new HashMap<>();

    public InventoryData(String name){
        pathToInventory = new File(GUIShop.INSTANCE.getDataFolder() + "/inventories/" + name);
        invConfig = YamlConfiguration.loadConfiguration(pathToInventory);
        inventory = Bukkit.createInventory(null, invConfig.getInt("global.size", 27), Component.text(invConfig.getString("global.name", "not found..")));
        loadItemsToMap();
        loadAllItemsToInventory();
    }

    public Inventory getInventory(){
        return this.inventory;
    }

    public ItemData getItem(int slot){
        return items.get(slot);
    }
    private void loadAllItemsToInventory(){
        items.forEach((key, value) -> inventory.setItem(key, value.getDisplayItem()));
    }

    private void loadItemsToMap(){
        ConfigurationSection section = invConfig.getConfigurationSection("items");
        if(section != null){
            for(String key: section.getKeys(false)){
                ConfigurationSection itemData = section.getConfigurationSection(key);
                String name = itemData.getString("name", "none");
                String item = itemData.getString("item", "stone").toUpperCase();
                if(Material.matchMaterial(item) == null){
                    return;
                }
                Double sell = itemData.getDouble("sell", 1);
                Double buy = itemData.getDouble("buy", 1);
                ItemData itemToInventory = new ItemData(item, itemData.getInt("amount"), name, buy, sell);
                int slot = itemData.getInt("position", NULL);
                if(slot != NULL){
                    items.put(slot, itemToInventory);
                }

            }
        }
    }
}
