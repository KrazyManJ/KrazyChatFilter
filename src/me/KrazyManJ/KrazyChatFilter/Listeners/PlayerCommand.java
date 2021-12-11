package me.KrazyManJ.KrazyChatFilter.Listeners;

import me.KrazyManJ.KrazyChatFilter.Censore;
import me.KrazyManJ.KrazyChatFilter.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class PlayerCommand implements Listener {
    @EventHandler
    public void on(PlayerCommandPreprocessEvent event){
        if (event.getMessage().contains(" ")){
            List<String> commands = Main.getInstance().getConfig().getStringList("commands");
            String command = event.getMessage().substring(1).split(" ", 2)[0];
            if (commands.contains(command)){
                String args = String.join("", event.getMessage().substring(1).split(" ", 2)[1]);
                event.setMessage("/"+command+" "+ Censore.Message(args));
            }
        }
    }
}
