package me.tallonscze.guishop.language;

import me.tallonscze.guishop.GUIShop;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
    YamlConfiguration lang = GUIShop.INSTANCE.getLanguage().getLanguageConfiguration();
    public Component prefix;
    public Component inventory_not_found;
    public Component not_enought_money;
    public Component dont_have_resources;

    public Messages(){
        prefix = toComp(lang.getString("prefix", "[GUIShop]"));
        inventory_not_found = prefix.append(toComp(lang.getString("inventory_not_found", "Inventory not found.")));
        not_enought_money = prefix.append(toComp(lang.getString("not_enought_money", "You dont have enought money.")));
        dont_have_resources = prefix.append(toComp(lang.getString("dont_have_resources", "You dont have items in your inventory.")));
    }

    private Component toComp(String msg){
        return StringToComponent.StringToMsg(msg);
    }
}
