package me.tallonscze.guishop.commands;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import me.tallonscze.guishop.data.ItemData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class setItemToInventory implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){return true;}
        Player player = ((Player) sender).getPlayer();
        Inventory inv = GUIShop.INSTANCE.invUtility.getInventory(args[0] + ".yml");
        InventoryData invD = GUIShop.INSTANCE.invUtility.getInventory(inv);
        try {
            invD.addItemToInventory(new ItemData("STONE", 1, "Name"), 10);
            GUIShop.INSTANCE.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
