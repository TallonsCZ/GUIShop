package me.tallonscze.guishop.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemData {
    private double buy;
    private double sell;
    private int selled;
    private int buyed;
    private final Material material;

    public ItemStack getItem() {
        return new ItemStack(material);
    }
    public ItemStack getDisplayItem(){return this.item;}

    private final ItemStack item;

    ItemData(String sourceMaterial, int amount, String name, double buy, double sell){
        name = name.replace("&", "§");
        material = Material.getMaterial(sourceMaterial);
        item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        item.setItemMeta(itemMeta);
        setBuyed(0);
        setSelled(0);
        this.buy = buy;
        this.sell = sell;
    }

    public int getSelled() {
        return selled;
    }

    public void setSelled(int selled) {
        this.selled = selled;
    }

    public int getBuyed() {
        return buyed;
    }

    public void setBuyed(int buyed) {
        this.buyed = buyed;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

}
