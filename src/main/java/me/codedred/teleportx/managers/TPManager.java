package me.codedred.teleportx.managers;

import me.codedred.teleportx.data.DataManager;
import org.bukkit.Particle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPManager {

    private static TPManager instance = null;

    public static TPManager getInstance() {
        if (instance == null)
            instance = new TPManager();
        return instance;
    }

    private Map<UUID, Boolean> activeParticles = new HashMap<>();

    private Particle particle;
    private int delay;

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setActive(UUID uuid, boolean value) {
        activeParticles.put(uuid, value);
    }

    public boolean isActive(UUID uuid) {
        if (activeParticles.containsKey(uuid))
            return activeParticles.get(uuid);
        return false;
    }

    public void register() {
        DataManager data = DataManager.getInstance();
        if (isParticle())
            setParticle(Particle.valueOf(data.getConfig().getString("particle_type").toUpperCase()));
        else
            setParticle(Particle.FIREWORKS_SPARK);

        setDelay(data.getConfig().getInt("teleportation_delay"));
    }

    private boolean isParticle() {
        DataManager data = DataManager.getInstance();
        try {
            Particle.valueOf(data.getConfig().getString("particle_type").toUpperCase());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
