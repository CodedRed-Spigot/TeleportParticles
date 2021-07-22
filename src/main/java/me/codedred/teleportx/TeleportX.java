package me.codedred.teleportx;

import me.codedred.teleportx.listeners.PlayerTeleport;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportX extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();

    }

    @Override
    public void onDisable() {

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerTeleport(), this);
    }
}
