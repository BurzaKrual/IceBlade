package me.burzakrual.iceblade;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Wand implements Listener {
    protected Main main;

    protected String name;

    protected String rarity;

    protected int cooldown;


    protected List<String> playersOnCooldown = new ArrayList<>();

    public Wand(Main main, String name, String rarity, int cooldown) {
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
        this.main = main;
        this.name = name;
        this.rarity = rarity;
        this.cooldown = cooldown;
    }

    public ItemStack createWandItem() {
        ItemStack wandItem = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta wandMeta = wandItem.getItemMeta();
        wandMeta.setDisplayName(this.name);
        wandMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Left click to use this " + this.rarity + ChatColor.GRAY + " wand");
        lore.add(ChatColor.GRAY + "This wand has a cooldown of " + ChatColor.GREEN + this.cooldown + ChatColor.GRAY + " seconds");
        wandMeta.setLore(lore);
        wandItem.setItemMeta(wandMeta);
        return wandItem;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.isSneaking()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null &&
                    item.getItemMeta() != null &&
                    item.getItemMeta().getDisplayName() != null)
                if (item.getItemMeta().getDisplayName().equals(this.name)) {
                    if (this.playersOnCooldown.contains(player.getName())) {
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                        return;
                    }
                    runAction(player);
                    this.playersOnCooldown.add(player.getName());
                    BukkitRunnable runnable = new BukkitRunnable() {
                        public void run() {
                            Wand.this.playersOnCooldown.remove(player.getName());
                        }
                    };
                    runnable.runTaskLater((Plugin)this.main, (20 * this.cooldown));
                }
        }
    }

    public abstract void runAction(Player paramPlayer);
}