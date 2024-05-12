package me.tallonscze.guishop.language;

import me.tallonscze.guishop.GUIShop;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
    YamlConfiguration lang = GUIShop.INSTANCE.getLanguage().getLanguageConfiguration();
    public Component inventory_not_found;

    public Messages(){
        inventory_not_found = StringToComponent.StringToMsg(lang.getString("inventory_not_found", "Inventory not found."));
    }
}
