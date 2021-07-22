package me.codedred.teleportx.data;

import org.bukkit.configuration.file.FileConfiguration;

public class DataManager {

    private static DataManager instance = null;

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    private final CustomFile config = new CustomFile("config.yml");

    public FileConfiguration getConfig() {
        return config.getConfig();
    }

    public void reloadConfig() {
        config.reloadConfig();
    }
}
