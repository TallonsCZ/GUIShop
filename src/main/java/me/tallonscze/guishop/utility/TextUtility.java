package me.tallonscze.guishop.utility;

import net.kyori.adventure.text.Component;

public class TextUtility {
    public Component stringToComponent(String msg){
        Component mess = Component.text(msg);
        return mess;
    }
}
