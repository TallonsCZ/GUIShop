package me.tallonscze.guishop.commands;

import me.tallonscze.guishop.utility.MenuUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class openMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if(player == null){
                System.out.println("Player is null");
                return true;
            } else if (MenuUtility.getInv() == null) {
                System.out.println("Menu is null");
                return true;
            }
            player.openInventory(MenuUtility.getInv());
            return true;
        } else{
            System.out.println("Command can be only execute by Player..");
            return true;
        }
    }
}