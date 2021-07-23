package me.codedred.teleportx.listeners;

import me.codedred.teleportx.TeleportX;
import me.codedred.teleportx.data.DataManager;
import me.codedred.teleportx.managers.TPManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerTeleport implements Listener {

    @EventHandler
    public void onTp(PlayerTeleportEvent event) {
        if (!event.getPlayer().hasPermission("tpx.show"))
            return;
        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND))
            return;
        event.setCancelled(true);

        Player player = event.getPlayer();
        DataManager data = DataManager.getInstance();
        TPManager tp = TPManager.getInstance();

        if (tp.isActive(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', data.getConfig().getString("messages.already-teleporting")));
            return;
        }

        tp.setActive(player.getUniqueId(), true);
        Particle particle = tp.getParticle();
        int teleport_delay = tp.getDelay();
        if (data.getConfig().getBoolean("messages.send_teleportation_msg"))
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    data.getConfig().getString("messages.teleportation_msg").replace("%delay%", Integer.toString(teleport_delay))));
        String teleportation_canceled = data.getConfig().getString("messages.teleportation_canceled").replace("%delay%", Integer.toString(teleport_delay));

        new BukkitRunnable() {
            final Location loc = player.getLocation();
            final Location tp_loc = event.getTo();
            double t = 0.0;
            int i = 0;

            public void run() {
                if (player.getLocation().distanceSquared(loc) > 0.0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', teleportation_canceled));
                    tp.setActive(player.getUniqueId(), false);
                    cancel();
                    return;
                }
                t += 0.39269908169872414;
                for (int j = 0; j < 4; ++j) {
                    player.getWorld().spawnParticle(particle, loc.clone().add(Math.cos(t + 1.5707963267948966 * j),
                            0.4 * t, Math.sin(t + 1.5707963267948966 * j)), 0);
                    player.getWorld().spawnParticle(particle, tp_loc.clone().add(Math.cos(t + 1.5707963267948966 * j),
                            0.4 * t, Math.sin(t + 1.5707963267948966 * j)), 0);
                }
                if (t > 6.5) {
                    t = 0.0;
                    ++i;
                }
                if (i * 6.5 > 20 * teleport_delay * 0.39269908169872414) {
                    Vector direction = player.getEyeLocation().getDirection();
                    Location destination = event.getTo();
                    destination.setDirection(direction);
                    player.teleport(destination);
                    tp.setActive(player.getUniqueId(), false);
                    cancel();
                }
            }
        }.runTaskTimer(TeleportX.getPlugin(TeleportX.class), 0L, 1L);
    }
}
