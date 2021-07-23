package me.codedred.teleportx.commands;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPXCompleter implements TabCompleter {

    private static final List<String> PARTICLE_LIST = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (PARTICLE_LIST.isEmpty()) {
            Arrays.stream(Particle.values()).forEach(particle -> PARTICLE_LIST.add(particle.name()));
        }
        List<String> finalList = new ArrayList<>();

        if (args.length ==1) {
            ArrayList<String> length0 = new ArrayList<>(Arrays.asList(
                    "setDelay",
                    "setParticle",
                    "reload"));
            for (String s : length0) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase()))
                    finalList.add(s);
            }
            return finalList;
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("setDelay")) {
            ArrayList<String> defaultSeconds = new ArrayList<>(Arrays.asList(
                    "10",
                    "5",
                    "3",
                    "1"));
            for (String s : defaultSeconds) {
                if (s.startsWith(args[1]))
                    finalList.add(s);
            }
            return finalList;
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("setParticle")) {
            for (String s : PARTICLE_LIST) {
                if (s.startsWith(args[1].toUpperCase()))
                    finalList.add(s);
            }
            return finalList;
        }
        return null;
    }
}
