package me.tallonscze.guishop.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.tallonscze.guishop.data.ItemData;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class GuiExpansion extends PlaceholderExpansion {

    @Override
    @NotNull
    public String getAuthor() {
        return "TallonsCZ";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "GUIShop";
    }

    @Override
    @NotNull
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("buy_price")) {
            return "text1";
        }

        if (params.equalsIgnoreCase("placeholder2")) {
            return "text2";
        }

        return null; //
    }
}