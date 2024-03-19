package me.tallonscze.guishop.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemData {

    private int slot;
    private double buy;
    private double sell;
    private int selled;
    private int buyed;
    private int lastPeriodSell;
    private int lastPeriodBuy;
    private ItemMeta iMeta;
    private final String name;
    private final Material material;
    private final int toChangePrice;

    public ItemStack getItem() {
        return new ItemStack(material);
    }
    public ItemStack getDisplayItem(){return this.item;}

    private final ItemStack item;
    private final String type;
    public ItemData(String sourceMaterial, int amount, String inputName, double buy, double sell, int buyed, int selled, int toChangePrice, int slot){
        name = inputName.replace("&", "§");
        this.buy = buy;
        this.toChangePrice = toChangePrice;
        this.sell = sell;
        this.buyed = buyed;
        this.selled = selled;
        type = sourceMaterial;
        material = Material.getMaterial(sourceMaterial);
        item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        setLore(itemMeta);
        iMeta = itemMeta;
        item.setItemMeta(itemMeta);

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
        if(selled == 0){
            this.selled = 0;
            return;
        }
        this.selled = getSelled() + selled;
    }
    public String getName() {
        return name;
    }

    public int getBuyed() {
        return buyed;
    }

    public void setBuyed(int buyed) {
        if(buyed == 0){
            this.buyed = 0;
        }else{
            this.buyed = getBuyed() + buyed;
        }

    }

    public int getToChangePrice() {
        return toChangePrice;
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

    public int getLastPeriodSell() {
        return lastPeriodSell;
    }

    public void setLastPeriodSell(int lastPeriodSell) {
        if (lastPeriodSell == 0){
            this.lastPeriodSell = 0;
        }else{
            this.lastPeriodSell = getLastPeriodSell() + lastPeriodSell;
        }
    }

    public int getLastPeriodBuy() {
        return lastPeriodBuy;
    }

    public void setLastPeriodBuy(int lastPeriodBuy) {
        if (lastPeriodBuy == 0){
            this.lastPeriodBuy = 0;
        }else{
            this.lastPeriodBuy = getLastPeriodBuy() + lastPeriodBuy;
        }
    }

    public int getSlot() {
        return slot;
    }

}
