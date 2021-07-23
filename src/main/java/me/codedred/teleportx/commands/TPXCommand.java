package me.codedred.teleportx.commands;

import me.codedred.teleportx.data.DataManager;
import me.codedred.teleportx.managers.TPManager;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class TPXCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        DataManager data = DataManager.getInstance();
        if (!sender.hasPermission("tpx.admin")) {
            sender.sendMessage(f(data.getConfig().getString("messages.no_permissions")));
            return true;
        }
        TPManager tpManager = TPManager.getInstance();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                data.reloadConfig();
                sender.sendMessage(f("&2&l[TeleportX] &aConfiguration Reloaded."));
                return true;
            }
        }
        else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("setdelay")) {
                if (!isNum(args[1])) {
                    sender.sendMessage(f("&4&l[TeleportX] &cDelay must be a number, in seconds."));
                    return true;
                }
                sender.sendMessage(f("&9&l[TeleportX] &3Delay changed from &l" + tpManager.getDelay() + "&3 to &l" + args[1] + "&3."));
                tpManager.setDelay(Integer.parseInt(args[1]));
                data.getConfig().set("teleportation_delay", Integer.parseInt(args[1]));
                data.saveConfig();
                return true;
            }

            else if (args[0].equalsIgnoreCase("setparticle")) {
                if (Arrays.stream(Particle.values()).noneMatch(s -> s.toString().contains(args[1].toUpperCase()))) {
                    sender.sendMessage(f("&4&l[TeleportX] &cIncorrect particle, try again."));
                    return true;
                }
                sender.sendMessage(f("&9&l[TeleportX] &3Particle changed from &l" + tpManager.getParticle() + "&3 to &l" + args[1].toUpperCase() + "&3."));
                tpManager.setParticle(Particle.valueOf(args[1].toUpperCase()));
                data.getConfig().set("particle_type", Particle.valueOf(args[1].toUpperCase()).name());
                data.saveConfig();
                return true;
            }
        }

        sender.sendMessage(f("&c&lTeleportX Usages:"));
        sender.sendMessage(f("&c/&7tpx setdelay <teleportation-delay-in-seconds>"));
        sender.sendMessage(f("&c/&7tpx setparticle <particle>"));
        sender.sendMessage(f("&c/&7tpx reload"));
        return true;
    }

    private String f(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private boolean isNum(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
