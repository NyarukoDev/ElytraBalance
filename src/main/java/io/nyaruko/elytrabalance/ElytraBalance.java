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

import org.bukkit.plugin.java.JavaPlugin;

public class ElytraBalance extends JavaPlugin {
    private static Config config;

    final String version = "1.2.1";
    //Update this whenever the config is altered
    final int configVersion = 2;

    @Override
    public void onEnable() {
        initConfig();
        registerEvents();

        if (isEnabled()) {
            getLogger().info("ElytraBalance v" + version + " successfully loaded.");
        } else {
            getLogger().severe("ElytraBalance v" + version + " failed to load.");
        }
    }

    private void initConfig() {
        //Ensure plugin data folder exists
        if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
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
            if (config.configVersion < configVersion) {
                config.configVersion = configVersion;
                saveConfig(configFile);
            }
        } else {
            //Create config file if not exists
            try {
                if (configFile.createNewFile()) {
                    config = new Config(configVersion);
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
    }

    private void saveConfig(File configFile) {
        try (Writer writer = Files.newBufferedWriter(configFile.toPath())) {
            Gson file = new GsonBuilder().setPrettyPrinting().create();
            file.toJson(config, writer);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save plugin config.");
            this.setEnabled(false);
        }
    }

    private void registerEvents() {
        if (!config.canConsumeFoodInFlight) {
            getServer().getPluginManager().registerEvents(new EatListener(), this);
        }
        if (config.removeElytraOnBreak) {
            getServer().getPluginManager().registerEvents(new BreakListener(), this);
        }
        getServer().getPluginManager().registerEvents(new BoostListener(), this);
        getServer().getPluginManager().registerEvents(new FixListener(), this);
    }

    public static Config getConfigModel() {
        return config;
    }
}
