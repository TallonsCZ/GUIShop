package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtility {

    private static File dataFolderPath = GUIShop.INSTANCE.getDataFolder();

    public static FileConfiguration getConfig(){
        File file = new File(dataFolderPath, "config.yml");
        if(file.exists()){
            return YamlConfiguration.loadConfiguration(file);
        }else{
            return null;
        }
    }
}
