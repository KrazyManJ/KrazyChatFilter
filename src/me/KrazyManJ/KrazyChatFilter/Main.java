package me.KrazyManJ.KrazyChatFilter;

import me.KrazyManJ.KrazyChatFilter.Listeners.PlayerChat;
import me.KrazyManJ.KrazyChatFilter.Listeners.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommand(), this);
        saveDefaultConfig();
        instance = this;
        Censore.reloadRegex();
    }

    public static Main getInstance(){
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("krazychatfilter") && sender.hasPermission("krazychatfilter.admin")){
            reloadConfig();
            Censore.reloadRegex();
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lKrazyChatFilter&7] &ePlugin was successfully reloaded!"));
            }
            getLogger().log(Level.INFO, "[KrazyChatFilter] Plugin was successfully reloaded!");
            return true;
        }
        return false;
    }
}
