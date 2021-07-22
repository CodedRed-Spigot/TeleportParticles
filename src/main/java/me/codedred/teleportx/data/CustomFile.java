package me.codedred.teleportx.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import me.codedred.teleportx.TeleportX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomFile {
    private final TeleportX plugin = TeleportX.getPlugin(TeleportX.class);
    private FileConfiguration dataConfig = null;
    private File dataConfigFile = null;
    private final String name;

    public CustomFile(String name) {
        this.name = name;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (dataConfigFile == null) {
            dataConfigFile = new File(plugin.getDataFolder(),
                    name);
        }

        dataConfig = YamlConfiguration
                .loadConfiguration(dataConfigFile);

        InputStream defConfigStream = plugin.getResource(name);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration
                    .loadConfiguration(new InputStreamReader(defConfigStream));
            dataConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    public void saveConfig() {
        if ((dataConfig == null) || (dataConfigFile == null)) {
            return;
        }
        try {
            getConfig().save(dataConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "
                    + dataConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (dataConfigFile == null) {
            dataConfigFile = new File(plugin.getDataFolder(),
                    name);
        }
        if (!dataConfigFile.exists()) {
            plugin.saveResource(name, false);
        }
    }

}