package me.tallonscze.guishop.language;

import me.tallonscze.guishop.GUIShop;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LanguageUtility {
    private final File dataFolderPath = GUIShop.INSTANCE.getDataFolder();

    public YamlConfiguration getLanguageConfiguration(){
        File file = new File(dataFolderPath, "language.yml");
        if(file.exists()){
            return YamlConfiguration.loadConfiguration(file);
        }else{
            return null;
        }
    }



}
