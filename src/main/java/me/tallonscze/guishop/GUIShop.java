package me.tallonscze.guishop;

import me.tallonscze.guishop.commands.openInventoryCommand;
import me.tallonscze.guishop.commands.openMenuCommand;
import me.tallonscze.guishop.commands.setItemToInventory;
import me.tallonscze.guishop.event.InventoryEvents;
import me.tallonscze.guishop.event.TimerEvent;
import me.tallonscze.guishop.utility.ConfigUtility;
import me.tallonscze.guishop.utility.InventoryUtils;
import me.tallonscze.guishop.utility.VaultUtility;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public final class GUIShop extends JavaPlugin {
    public static InventoryUtils invUtility;
    public static GUIShop INSTANCE;
    private final int version = 1;
    private final int menuVersion = 1;

    @Override
    public void onEnable() {
        INSTANCE = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }

        File menu = new File(getDataFolder(), "menu.yml");
        if(!menu.exists()){
            saveResource("menu.yml", true);
            getLogger().info("Menu.yml created");
        }else{
            YamlConfiguration menuConf = YamlConfiguration.loadConfiguration(menu);
            int menuLocalVersion = menuConf.getInt("version");
            if(menuLocalVersion != menuVersion){
                saveResource("menu.yml", true);
                getLogger().info("Menu.yml created");
            }
        }

        if(ConfigUtility.getConfig() == null){
            saveResource("config.yml", true);
            getLogger().info("Config.yml created");
        } else if (ConfigUtility.getConfig().getInt("version", 0) == version) {
            saveResource("config.yml", true);
            getLogger().info("Config.yml replaced");
        }
        invUtility = new InventoryUtils();



        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new TimerEvent(), this);

        getCommand("openinv").setExecutor(new openInventoryCommand());
        getCommand("setitem").setExecutor(new setItemToInventory());
        getCommand("menu").setExecutor(new openMenuCommand());


        if (!setupEconomy() ) {
            getLogger().severe(String.format("Disabled due to no Vault dependency found!", getDescription().getName()));
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
