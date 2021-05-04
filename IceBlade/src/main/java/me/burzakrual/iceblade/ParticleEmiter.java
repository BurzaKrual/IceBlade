package me.burzakrual.iceblade;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleEmiter {
    public static void emitParticlesContinuously(final Entity entity, final Particle particle, final int amount, final double speed, final Vector spread, Main main, int delay, final int period, final int duration) {
        BukkitRunnable runnable = new BukkitRunnable() {
            int counter = 0;

            public void run() {
                ParticleEmiter.emitParticles(entity.getLocation(), particle, amount, speed, spread);
                this.counter += period;
                if (this.counter >= duration)
                    cancel();
                if (entity.isDead())
                    cancel();
            }
        };
        runnable.runTaskTimer((Plugin)main, delay, period);
    }

    public static void emitParticlesContinuously(final Location location, final Particle particle, final int amount, final double speed, final Vector spread, Main main, int delay, final int period, final int duration) {
        BukkitRunnable runnable = new BukkitRunnable() {
            int counter = 0;

            public void run() {
                ParticleEmiter.emitParticles(location, particle, amount, speed, spread);
                this.counter += period;
                if (this.counter >= duration)
                    cancel();
            }
        };
        runnable.runTaskTimer((Plugin)main, delay, period);
    }

    public static void emitParticles(Location location, Particle particle, int amount, double speed, Vector spread) {
        location.getWorld().spawnParticle(
                particle,
                location.getX(),
                location.getY(),
                location.getZ(),
                amount,
                spread.getX(),
                spread.getY(),
                spread.getZ(),
                speed);
    }
}

