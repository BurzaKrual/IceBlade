package WandVariations;

import me.burzakrual.iceblade.LocationHelper;
import me.burzakrual.iceblade.Main;
import me.burzakrual.iceblade.Wand;
import me.burzakrual.iceblade.ParticleEmiter;
import java.util.Random;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class IceWand extends Wand {
    int range = 8;

    int duration = 2;

    public IceWand(Main main, String name, String rarity, int cost) {
        super(main, name, rarity, cost);
    }

    public void runAction(Player player) {
        Location playerLocation = player.getLocation();
        Random rdm = new Random();
        for (int x = -this.range; x < this.range; x++) {
            for (int y = -this.range; y < this.range; y++) {
                for (int z = -this.range; z < this.range; z++) {
                    final Location blockLocation = LocationHelper.offsetLocation(playerLocation, new Vector(x, y, z));
                    Location blockGroundLocation = LocationHelper.offsetLocation(blockLocation, new Vector(0, -1, 0));
                    if (blockLocation.getBlock().getType().toString().contains("AIR") &&
                            !blockLocation.getBlock().getType().toString().contains("STAIR") &&
                            blockGroundLocation.getBlock().getType().isSolid() &&
                            blockLocation.distance(playerLocation) <= this.range) {
                        blockLocation.getBlock().setType(Material.SNOW);
                        ParticleEmiter.emitParticlesContinuously(blockLocation, Particle.CLOUD, 1, 0.05D, new Vector(1, 1, 1), this.main, rdm.nextInt(100), 100, 200);
                        BukkitRunnable runnable = new BukkitRunnable() {
                            public void run() {
                                if (blockLocation.getBlock().getType() == Material.SNOW)
                                    blockLocation.getBlock().setType(Material.AIR);
                            }
                        };
                        runnable.runTaskLater((Plugin)this.main, (rdm.nextInt(40) + 20 * this.duration));
                    }
                }
            }
        }
        for (Entity entity : player.getNearbyEntities(this.range, this.range, this.range)) {
            if (entity != player &&
                    entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                player.sendMessage(ChatColor.BLUE.BOLD +"You Froze » "+ entity.getName());
                PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 20 * this.duration, 10);
                livingEntity.addPotionEffect(slowness);
            }
        }
        player.getWorld().playSound(playerLocation, Sound.BLOCK_SNOW_PLACE, 1.0F, 1.0F);
    }
}
