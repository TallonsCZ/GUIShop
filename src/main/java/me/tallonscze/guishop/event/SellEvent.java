package me.tallonscze.guishop.event;

import me.tallonscze.guishop.utility.VaultUtility;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class SellEvent extends Event{

    private boolean cancelled;
    private boolean succes;
    double playerBalance;
    Economy econ = VaultUtility.getEconomy();
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public SellEvent(Player player, double cost){
        this.playerBalance = econ.getBalance(player);
        Inventory pInv = player.getInventory();
            econ.depositPlayer(player, cost);

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
