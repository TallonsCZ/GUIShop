package me.tallonscze.guishop.commands;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.language.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class openInventoryCommand implements CommandExecutor {

    public YamlConfiguration lang = GUIShop.INSTANCE.getLanguage().getLanguageConfiguration();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Inventory inv = GUIShop.INSTANCE.invUtility.getInventory(args[0] + ".yml");
            if(inv != null){
                player.openInventory(inv);
            }else{
                player.sendMessage(GUIShop.INSTANCE.mess.inventory_not_found);
            }

            return true;
        } else{
            GUIShop.INSTANCE.getLogger().warning("Command can be only execute by Player..");
            return true;
        }
    }
}
