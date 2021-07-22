package me.codedred.teleportx.enums;

import org.bukkit.Particle;

public enum ParticleType {

    FIREWORK(Particle.FIREWORKS_SPARK),
    CRIT(Particle.CRIT_MAGIC),
    DRAGON(Particle.DRAGON_BREATH),
    FLAME(Particle.FLAME),
    ENCHANT(Particle.ENCHANTMENT_TABLE);

    Particle particle;

    ParticleType(Particle particle) {
        this.particle = particle;
    }

    public Particle getParticle() {
        return particle;
    }

}
