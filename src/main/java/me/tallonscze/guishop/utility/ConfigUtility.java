package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtility {

    private static File dataFolderPath = GUIShop.INSTANCE.getDataFolder();
    public static YamlConfiguration getConfig(){
        File file = new File(dataFolderPath, "config.yml");
        if(file.exists()){
            return YamlConfiguration.loadConfiguration(file);
        }else{
            return null;
        }
    }

    public static YamlConfiguration getMenuConfig(){
        File file = new File(dataFolderPath, "menu.yml");
        if(file.exists()){
            return YamlConfiguration.loadConfiguration(file);
        }else {
            return null;
        }
    }

}
