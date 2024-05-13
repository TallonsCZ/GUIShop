package me.tallonscze.guishop.tabPrediction;

import me.tallonscze.guishop.GUIShop;
import me.tallonscze.guishop.data.InventoryData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Inventories implements TabCompleter {

    List<String> list = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(list.isEmpty()){
            for (InventoryData data: GUIShop.INSTANCE.invUtility.getAllInventory()) {
                list.add(data.getFileName().substring(0, data.getFileName().length()-4));
            }
        }


        if(sender instanceof Player){
            if (command.getName().equalsIgnoreCase("openinv")){
                if(args.length == 1){
                    return list;
                }
            }
        }
        return null;
    }
}
