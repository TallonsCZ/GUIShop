package me.tallonscze.guishop.commands;

import me.tallonscze.guishop.GUIShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class openInventoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Inventory inv = GUIShop.invUtility.getInventory(args[0] + ".yml");
            if(inv != null){
                player.openInventory(inv);
            }else{
                player.sendMessage("Inventar nenalezen..");
            }

            return true;
        } else{
            System.out.println("Command can be only execute by Player..");
            return true;
        }
    }
}
