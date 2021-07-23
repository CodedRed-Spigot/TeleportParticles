package me.codedred.teleportx;

import me.codedred.teleportx.commands.TPXCommand;
import me.codedred.teleportx.commands.TPXCompleter;
import me.codedred.teleportx.data.DataManager;
import me.codedred.teleportx.listeners.PlayerTeleport;
import me.codedred.teleportx.managers.TPManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportX extends JavaPlugin {

    @Override
    public void onEnable() {
        TPManager.getInstance().register();

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("tpx").setExecutor(new TPXCommand());
        getCommand("tpx").setTabCompleter(new TPXCompleter());
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerTeleport(), this);
    }
}
