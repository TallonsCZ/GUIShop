package me.tallonscze.guishop.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemData {
    private boolean isBack = false;
    private int slot;
    private double buy;
    private double sell;
    private int selled;
    private int buyed;
    private int lastPeriodSell;
    private int lastPeriodBuy;
    private ItemMeta iMeta;
    private final String name;
    private Material material;
    private int toChangePrice;
    private final ItemStack item;
    private final String type;
    public ItemData(String sourceMaterial, int amount, String inputName){
        name = inputName.replace("&", "§");
        type = sourceMaterial;
        material = Material.getMaterial(sourceMaterial);
        if(material == null){
            material = Material.STONE;
        }
        item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        setLore(itemMeta);
        iMeta = itemMeta;
        item.setItemMeta(itemMeta);
    }

    public void setBack(boolean back) {
        if(back){
            ItemMeta meta = getiMeta();
            if(meta.hasLore()){
                List<Component> lore = meta.lore();
                lore.clear();
                meta.lore(lore);
                setiMeta(meta);
            }
        }
        isBack = back;

    }
    private void setLore(ItemMeta data){
        if(isBack()){
            if(data.hasLore()){
                List<Component> lore = data.lore();
                lore.clear();
                data.lore(lore);
                setiMeta(data);
            }
            return;
        }
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

    public void setLastPeriodBuy(int lastPeriodBuy) {
        if (lastPeriodBuy == 0){
            this.lastPeriodBuy = 0;
        }else{
            this.lastPeriodBuy = getLastPeriodBuy() + lastPeriodBuy;
        }
    }

    public void setLastPeriodSell(int lastPeriodSell) {
        if (lastPeriodSell == 0){
            this.lastPeriodSell = 0;
        }else{
            this.lastPeriodSell = getLastPeriodSell() + lastPeriodSell;
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

    public int getLastPeriodBuy() {
        return lastPeriodBuy;
    }

    private ItemMeta getiMeta(){
        return item.getItemMeta();
    }
    private void setiMeta(ItemMeta meta){
        this.item.setItemMeta(meta);
    }
    public int getSlot() {
        return slot;
    }
    public boolean isBack() {
        return isBack;
    }
    public ItemStack getItem() {
        return new ItemStack(material);
    }
    public ItemStack getDisplayItem(){return this.item;}

    public void setSlot(int slot){
        this.slot = slot;
    }
    public void setToChangePrice(int price){
        this.toChangePrice = price;
    }

}
