package me.tallonscze.guishop;

import me.tallonscze.guishop.commands.openInventoryCommand;
import me.tallonscze.guishop.commands.setItemToInventory;
import me.tallonscze.guishop.event.InventoryEvents;
import me.tallonscze.guishop.event.TimerEvent;
import me.tallonscze.guishop.utility.ConfigUtility;
import me.tallonscze.guishop.utility.InventoryUtils;
import me.tallonscze.guishop.utility.VaultUtility;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class GUIShop extends JavaPlugin {
    public static InventoryUtils invUtility;
    public static GUIShop INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }

        if(ConfigUtility.getConfig() == null){
            saveResource("config.yml", false);
        }
        invUtility = new InventoryUtils();

        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new TimerEvent(), this);

        getCommand("openinv").setExecutor(new openInventoryCommand());
        getCommand("setitem").setExecutor(new setItemToInventory());


        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();

    }

    @Override
    public void onDisable() {

    }

    public void reload(){
        invUtility = new InventoryUtils();
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
