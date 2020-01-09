package io.nyaruko.elytrabalance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.nyaruko.elytrabalance.listeners.BoostListener;
import io.nyaruko.elytrabalance.listeners.BreakListener;
import io.nyaruko.elytrabalance.listeners.EatListener;
import io.nyaruko.elytrabalance.listeners.FixListener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ElytraBalance extends JavaPlugin {
    /**
     * Update this whenever the config is altered
     */
    private static final int CONFIG_VERSION = 3;
    private static final String VERSION = "1.3.0";
    private static Config config;

    @Override
    public void onEnable() {
        initConfig();
        registerEvents();

        if(isEnabled()) {
            getLogger().log(Level.INFO, "ElytraBalance v{0} successfully loaded.", VERSION);
        } else {
            getLogger().log(Level.SEVERE, "ElytraBalance v{0} failed to load.", VERSION);
        }
    }

    private void initConfig() {
        //Ensure plugin data folder exists
        if(!getDataFolder().exists() && !getDataFolder().mkdir()) {
            getLogger().log(Level.SEVERE, "Failed to create data folder.");
            this.setEnabled(false);
        }

        File configFile = new File(getDataFolder() + "/config.json");

        if(configFile.exists()) {
            //Load config if one exists
            try (Reader reader = new FileReader(configFile)){
                config = new Gson().fromJson(reader, Config.class);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, e.getMessage());
                this.setEnabled(false);
                return;
            }

            //Update config to latest version
            //Re-saves the config file as the latest version but with existing user values
            if(config.configVersion < CONFIG_VERSION) {
                config.configVersion = CONFIG_VERSION;
                saveConfig(configFile);
            }
            return;
        }

        //Create config file if not exists
        try {
            if(configFile.createNewFile()) {
                config = new Config(CONFIG_VERSION);
                saveConfig(configFile);
            } else {
                getLogger().log(Level.SEVERE, "Failed to create plugin config.");
                this.setEnabled(false);
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, e.getMessage());
            this.setEnabled(false);
        }
    }

    private void saveConfig(File configFile) {
        try (Writer writer = Files.newBufferedWriter(configFile.toPath())) {
            Gson file = new GsonBuilder().setPrettyPrinting().create();
            file.toJson(config, writer);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save plugin config: {0}", e.getMessage());
            this.setEnabled(false);
        }
    }

    private void registerEvents() {
        if(!config.canConsumeFoodInFlight) {
            getServer().getPluginManager().registerEvents(new EatListener(), this);
        }
        if(config.removeElytraOnBreak) {
            getServer().getPluginManager().registerEvents(new BreakListener(), this);
        }
        getServer().getPluginManager().registerEvents(new BoostListener(), this);
        getServer().getPluginManager().registerEvents(new FixListener(), this);
    }

    public static void sendConfigMessage(Player player, String configMessage) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', configMessage));
    }

    public static Config getConfigModel() {
        return config;
    }
}
