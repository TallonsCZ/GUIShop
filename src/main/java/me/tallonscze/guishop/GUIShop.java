package me.tallonscze.guishop;

import me.tallonscze.guishop.commands.openInventoryCommand;
import me.tallonscze.guishop.commands.openMenuCommand;
import me.tallonscze.guishop.commands.setItemToInventory;
import me.tallonscze.guishop.data.InventoryData;
import me.tallonscze.guishop.data.ItemNavigationData;
import me.tallonscze.guishop.event.InventoryEvents;
import me.tallonscze.guishop.event.MenuEvent;
import me.tallonscze.guishop.event.TimerEvent;
import me.tallonscze.guishop.language.LanguageUtility;
import me.tallonscze.guishop.language.Messages;
import me.tallonscze.guishop.utility.*;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class GUIShop extends JavaPlugin {
    private int numInv;
    public ItemNavigationData navData;
    private MenuUtility inventoryMenu;
    private LanguageUtility languageUtility;
    public Messages mess;
    public InventoryUtils invUtility;
    public static GUIShop INSTANCE;


    @Override
    public void onEnable() {
        INSTANCE = this;
        FileUtility fileUt = new FileUtility();
        navData = new ItemNavigationData();
        invUtility = new InventoryUtils();
        languageUtility = new LanguageUtility();
        mess = new Messages();

        //Register Events
        getServer().getPluginManager().registerEvents(new MenuEvent(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new TimerEvent(), this);

        //Register Commands
        getCommand("openinv").setExecutor(new openInventoryCommand());
        getCommand("setitem").setExecutor(new setItemToInventory());
        getCommand("menu").setExecutor(new openMenuCommand());


        //create inventory
        createMenu();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        try {
            TimerEvent.checkAllInventories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        InventoryData[] inventories = GUIShop.INSTANCE.invUtility.getAllInventory();
        for (InventoryData inv: inventories) {
            try {
                inv.saveInventoryToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createMenu(){
        inventoryMenu = new MenuUtility(this.numInv);
    }

    public void reload(){
        invUtility = new InventoryUtils();
        createMenu();
    }

    public MenuUtility getMenu(){
        return this.inventoryMenu;
    }
    public LanguageUtility getLanguage(){
        return this.languageUtility;
    }

    private boolean setupEconomy() {
        if (GUIShop.INSTANCE.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = GUIShop.INSTANCE.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        VaultUtility.setEcon(rsp.getProvider());
        return VaultUtility.getEconomy() != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = GUIShop.INSTANCE.getServer().getServicesManager().getRegistration(Permission.class);
        VaultUtility.setPerms(rsp.getProvider());
        return VaultUtility.getPermissions() != null;
    }
}
