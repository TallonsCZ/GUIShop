package me.tallonscze.guishop.event;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.utility.VaultUtility;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BuyEvent extends Event {
    private boolean cancelled;
    private boolean succes;
    double playerBalance;
    Economy econ = VaultUtility.getEconomy();
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public BuyEvent(Player player, double cost){
        this.playerBalance = econ.getBalance(player);
        if(playerBalance < cost){
            this.succes = false;
            player.sendMessage(GUIShop.INSTANCE.mess.not_enought_money);
        }else{
            econ.withdrawPlayer(player, cost);
            this.succes = true;
        }
    }

    public boolean isSucces(){
        return this.succes;
    }

    public HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }


    public boolean isCancelled(){
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }


}
