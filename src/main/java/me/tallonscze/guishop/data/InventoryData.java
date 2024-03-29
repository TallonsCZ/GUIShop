package me.tallonscze.guishop.data;

import me.tallonscze.guishop.GUIShop;
import net.kyori.adventure.text.Component;
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
        items.clear();
        loadAllItemsToInventory();
    }
    public void setItemToMap(ItemData data, int slot){
        items.put(slot, data);
        loadAllItemsToInventory();
    }

    public void setSell(double sell, int slot) throws IOException{
        String section = "items."+slot;
        invConfig.set(section+".sell", sell);
        invConfig.save(pathToInventory);
    }

    public void setBuy(double buy, int slot) throws IOException{
        String section = "items."+slot;
        invConfig.set(section+".buy", buy);
        invConfig.save(pathToInventory);
    }

    public void setSelled(int selled, int slot) throws IOException {
        String section = "items."+slot;
        invConfig.set(section+ ".selled", selled);
        invConfig.save(pathToInventory);
    }

    public void setBuyed(int buyed, int slot) throws IOException {
        String section = "items."+slot;
        invConfig.set(section+ ".buyed", buyed);
        invConfig.save(pathToInventory);
    }

    public void setLastBuy(int buy,int slot) throws IOException{
        String section = "items."+slot;
        invConfig.set(section+ ".last_buy", buy);
        invConfig.save(pathToInventory);
    }

    public void setLastSell(int sell,int slot) throws IOException{
        String section = "items."+slot;
        invConfig.set(section+ ".last_sell", sell);
        invConfig.save(pathToInventory);
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
                double sell = itemData.getDouble("sell", 1);
                double buy = itemData.getDouble("buy", 1);
                int buyed = itemData.getInt("buyed", 0);
                int selled = itemData.getInt("selled", 0);
                int toChangePrice = itemData.getInt("to_change_price", 1);
                ItemData itemToInventory = new ItemData(item, itemData.getInt("amount"), name, buy, sell, buyed, selled, toChangePrice, Integer.parseInt(key));
                int slot = itemData.getInt("position", NULL);
                if(slot != NULL){
                    items.put(slot, itemToInventory);
                }

            }
        }
    }
}
