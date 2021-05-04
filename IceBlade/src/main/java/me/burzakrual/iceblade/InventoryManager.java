package me.burzakrual.iceblade;


import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryManager {
    public static ItemStack createWandItem(String name) {
        ItemStack wandItem = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta wandMeta = wandItem.getItemMeta();
        wandMeta.setDisplayName(name);
        wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Press Shift to use wand");
        wandMeta.setLore(lore);
        wandItem.setItemMeta(wandMeta);
        return wandItem;
    }
    //consumes gunpowder
    public static void giveWandToPlayer(Player player, String name) {
        ItemStack wandItem = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta wandMeta = wandItem.getItemMeta();
        wandMeta.setDisplayName(name);
        wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Press Shift to use wand");
        lore.add(ChatColor.GRAY + "Uses Gunpower as ammunition");
        wandMeta.setLore(lore);
        wandItem.setItemMeta(wandMeta);
        player.getInventory().addItem(new ItemStack[] { wandItem });
    }

    public static boolean removeGunpowderFromPlayerInventory(Player player, int amount) {
        if (amount == 0 ||
                !Main.costEnabled)
            return true;
        PlayerInventory playerInventory = player.getInventory();
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = playerInventory.getContents()).length, b = 0; b < i; ) {
            ItemStack item = arrayOfItemStack[b];
            if (item != null &&
                    item.getType() == Material.GUNPOWDER) {
                if (item.getAmount() > amount) {
                    item.setAmount(item.getAmount() - amount);
                    return true;
                }
                if (item.getAmount() == amount) {
                    playerInventory.remove(item);
                    return true;
                }
            }
            b++;
        }
        return false;
    }
}
