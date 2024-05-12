package me.tallonscze.guishop.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class StringToComponent {
    public static Component StringToMsg(String msg){
        Component nMsg = LegacyComponentSerializer.legacy('&').deserialize(msg);;
        return nMsg;
    }
}
