package me.burzakrual.iceblade;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler {
    public static void handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return;
        Player player = (Player) sender;
        if (label.equals("iceblade")) {
            if (args.length == 0) {
                player.sendMessage("Wands v" + Main.version + " by 'pyzn'. Use /wands help to get a list of commands");
                return;
            }
            if (args.length == 1 && args[0].equals("help")) {
                player.sendMessage("Here is a list of all valid wands commands:\n/wands help\n/wands give <name>");
                return;
            }
            if (args.length == 2 && args[0].equals("give")) {
                if (!player.hasPermission("wands.give") &&
                        !player.isOp()) {
                    player.sendMessage("You do not seem to have the right permissions to perform this command");
                    return;
                }
                for (Wand wand : Main.wandVariations) {
                    if (wand.name.toLowerCase().contains(args[1].toLowerCase())) {
                        ItemStack wandItem = wand.createWandItem();
                        player.getInventory().addItem(new ItemStack[]{wandItem});
                        player.sendMessage("You have recieved a " + wand.name);
                        return;
                    }
                    }
                    player.sendMessage("Invalid command usage. Use /wands help to get a list of commands");
                }
            }
        }
    }