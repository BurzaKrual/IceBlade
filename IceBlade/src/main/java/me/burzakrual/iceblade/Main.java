package me.burzakrual.iceblade;

import java.util.ArrayList;
import java.util.List;

import WandVariations.IceWand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static double version = 1.3D;

    public static boolean costEnabled = true;

    public static List<Wand> wandVariations = new ArrayList<>();

    public static FileConfiguration config;

    public void onEnable() {
        loadConfig();
        initiateWands();
    }

    void loadConfig() {
        config = getConfig();
        config.addDefault("Wands.Ice.Cooldown", Integer.valueOf(20));

        config.options().copyDefaults(true);
        saveConfig();
    }

    void initiateWands() {

        wandVariations.add(new IceWand(this, ChatColor.BLUE + "[Ice Blade] ", ChatColor.BLUE + "Freeze your enemy for 2s", config.getInt("Wands.Ice.Cooldown")));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHandler.handleCommand(sender, cmd, label, args);
        return true;
    }
}
