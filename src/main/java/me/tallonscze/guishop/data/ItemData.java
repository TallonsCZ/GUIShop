package me.tallonscze.guishop.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemData {
    private double buy;
    private double sell;
    private int selled;
    private int buyed;

    private ItemMeta iMeta;
    private final String name;
    private final Material material;

    public ItemStack getItem() {
        return new ItemStack(material);
    }
    public ItemStack getDisplayItem(){return this.item;}

    private final ItemStack item;
    private final String type;
    public ItemData(String sourceMaterial, int amount, String inputName, double buy, double sell){
        name = inputName.replace("&", "§");
        this.buy = buy;
        this.sell = sell;
        type = sourceMaterial;
        material = Material.getMaterial(sourceMaterial);
        item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        setLore(itemMeta);
        iMeta = itemMeta;
        item.setItemMeta(itemMeta);
        setBuyed(0);
        setSelled(0);
    }

    private void setLore(ItemMeta data){
        List<Component> list = new ArrayList<>();
        Component fLine = Component.text("Buy for: " + getBuy());
        Component sLine = Component.text("Sell for: " + getSell());
        list.add(fLine);
        list.add(sLine);
        data.lore(list);
    }

    public void reloadLore(){
        setLore(this.iMeta);
        item.setItemMeta(iMeta);
    }

    public String getTypeString(){
        return this.type;
    }
    public int getSelled() {
        return selled;
    }

    public void setSelled(int selled) {
        this.selled = getSelled() + selled;
    }
    public String getName() {
        return name;
    }

    public int getBuyed() {
        return buyed;
    }

    public void setBuyed(int buyed) {
        this.buyed = getBuyed() + buyed;
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
