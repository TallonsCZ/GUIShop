package me.tallonscze.guishop.data;

import me.tallonscze.guishop.GUIShop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.NULL;

public class InventoryData {
    private final String fileName;
    private final String name;
    private final String icon;
    private final Inventory inventory;
    private final File pathToInventory;
    private YamlConfiguration invConfig;
    private int inventorySize;
    private Map<Integer, ItemData> items = new HashMap<>();

    public InventoryData(String name){
        pathToInventory = new File(GUIShop.INSTANCE.getDataFolder() + "/inventories/" + name);
        this.fileName = name;
        invConfig = YamlConfiguration.loadConfiguration(pathToInventory);
        inventorySize =  invConfig.getInt("global.size", 27);
        if(inventorySize != 9 && inventorySize != 18 && inventorySize != 27 && inventorySize != 36 && inventorySize != 45){
            inventorySize = 45;
        }
        inventory = Bukkit.createInventory(null, inventorySize+9, Component.text(invConfig.getString("global.name", "not found..")).decoration(TextDecoration.ITALIC, false));
        this.name = invConfig.getString("global.name", "not found...");
        this.icon = invConfig.getString("global.icon", "stone");
        loadItemsToMap();
        loadAllItemsToInventory();
    }

    public Inventory getInventory(){
        return this.inventory;
    }

    public ItemData getItem(int slot){
        return items.get(slot);
    }

    /*
    public void saveInventoryToConfig(Inventory inv){
        ItemStack[] items = inv.getStorageContents();
        for (ItemStack item: items) {
        }
    }
     */
    public Map<Integer, ItemData> getAllItemData(){
        return items;
    }

    public void addItemToInventory(ItemData item, int slot) throws IOException {
        items.put(slot, item);
        reloadAllItemsToInventory();
        String section = "items."+slot;
        invConfig.createSection(section);
        invConfig.set(section+ ".to_change_price", item.getToChangePrice());
        invConfig.set(section+ ".name", item.getName());
        invConfig.set(section+ ".item", item.getTypeString());
        invConfig.set(section+ ".sell", item.getSell());
        invConfig.set(section+ ".buy", item.getBuy());
        invConfig.set(section+ ".amount", item.getDisplayItem().getAmount());
        invConfig.set(section+ ".position", slot);
        invConfig.save(pathToInventory);
    }

    private void loadAllItemsToInventory(){
        inventory.clear();
        items.forEach((key, value) -> inventory.setItem(key, value.getDisplayItem()));
    }

    public void reloadAllItemsToInventory(){
        loadAllItemsToInventory();
    }
    public void setItemToMap(ItemData data, int slot){
        items.put(slot, data);
        loadAllItemsToInventory();
    }

    public void saveInventoryToFile() throws IOException{
        items.forEach((key, value) -> {
            try {
                saveItemToFile(value, key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void saveItemToFile(ItemData data,int slot) throws IOException{
        if(slot >= inventorySize){
            return;
        }
        String section = "items."+slot;
        invConfig.set(section+".buy", data.getBuy());
        invConfig.set(section+".sell", data.getSell());
        invConfig.set(section+".selled", data.getSelled());
        invConfig.set(section+".buyed", data.getBuyed());
        invConfig.set(section+".last_buy", data.getLastPeriodBuy());
        invConfig.set(section+".last_sell", data.getLastPeriodSell());
        invConfig.save(pathToInventory);
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    private void loadItemsToMap(){
        ConfigurationSection section = invConfig.getConfigurationSection("items");
        if(section != null){
            for(String key: section.getKeys(false)){
                if(Integer.parseInt(key) >= inventorySize){
                    break;
                }
                ConfigurationSection itemData = section.getConfigurationSection(key);
                String name = itemData.getString("name", "none");
                String item = itemData.getString("item", "stone").toUpperCase();
                if(Material.matchMaterial(item) == null){
                    return;
                }
                double sell = itemData.getDouble("sell", 1);
                double buy = itemData.getDouble("buy", 1);
                int buyed = itemData.getInt("buyed", 0);
                int selled = itemData.getInt("selled", 0);
                int toChangePrice = itemData.getInt("to_change_price", 1);
                ItemData itemToInventory = new ItemData(item, itemData.getInt("amount"), name);
                itemToInventory.setSell(sell);
                itemToInventory.setBuy(buy);
                itemToInventory.setBuyed(buyed);
                itemToInventory.setSelled(selled);
                itemToInventory.setToChangePrice(toChangePrice);
                itemToInventory.setSlot(Integer.parseInt(key));
                int slot = itemData.getInt("position", NULL);
                if(slot != NULL){
                    items.put(slot, itemToInventory);
                }

            }
            items.put(inventorySize, GUIShop.INSTANCE.navData.getBackItem());
        }
    }
    public String getFileName() {
        return fileName;
    }
}
