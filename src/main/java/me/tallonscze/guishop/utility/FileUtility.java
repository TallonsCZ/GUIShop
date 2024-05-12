package me.tallonscze.guishop.utility;

import me.tallonscze.guishop.GUIShop;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileUtility {

    File dataFolder = GUIShop.INSTANCE.getDataFolder();

    public FileUtility(){
        int menuVersion = 1;
        int version = 2;


        if(!dataFolder.exists()){
            dataFolder.mkdirs();
        }

        File menu = new File(dataFolder, "menu.yml");
        if(!menu.exists()){
            GUIShop.INSTANCE.saveResource("menu.yml", true);
            GUIShop.INSTANCE.getLogger().info("Menu.yml created");
        }else{
            YamlConfiguration menuConf = YamlConfiguration.loadConfiguration(menu);
            int menuLocalVersion = menuConf.getInt("version");

            if(menuLocalVersion != menuVersion){
                GUIShop.INSTANCE.saveResource("menu.yml", true);
                GUIShop.INSTANCE.getLogger().info("Menu.yml created");
            }
        }


        if(ConfigUtility.getConfig() == null){
            GUIShop.INSTANCE.saveResource("config.yml", true);
            GUIShop.INSTANCE.getLogger().info("Config.yml created");
        } else if (ConfigUtility.getConfig().getInt("version", 0) != version) {
            GUIShop.INSTANCE.saveResource("config.yml", true);
            GUIShop.INSTANCE.getLogger().info("Config.yml replaced");
        }
    }
}
