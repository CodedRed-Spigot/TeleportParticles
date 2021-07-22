package me.codedred.teleportx.listeners;

import me.codedred.teleportx.TeleportX;
import me.codedred.teleportx.data.DataManager;
import me.codedred.teleportx.enums.ParticleType;
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

        Particle particle = ParticleType.valueOf(data.getConfig().getString("particle.type").toUpperCase()).getParticle();
        int teleport_delay = data.getConfig().getInt("particle.teleport_delay");
        String teleportation_message = data.getConfig().getString("lang.teleportation");

        new BukkitRunnable() {
            final Location loc = player.getLocation();
            final Location tp_loc = event.getTo();
            double t = 0.0;
            int i = 0;

            public void run() {
                if (player.getLocation().distanceSquared(loc) > 0.0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', teleportation_message));
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
                    cancel();
                }
            }
        }.runTaskTimer(TeleportX.getPlugin(TeleportX.class), 0L, 1L);
    }
}
