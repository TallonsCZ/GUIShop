package me.tallonscze.guishop.utility;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultUtility {
    private static Economy econ = null;
    private static Permission perms = null;


    public static Economy getEconomy() {
        return econ;
    }

    public static void setEcon(Economy economy){
        VaultUtility.econ = economy;
    }

    public static void setPerms(Permission permssion){
        VaultUtility.perms = permssion;
    }

    public static Permission getPermissions() {
        return perms;
    }

}
