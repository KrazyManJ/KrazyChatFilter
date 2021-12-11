package me.KrazyManJ.KrazyChatFilter.Listeners;

import me.KrazyManJ.KrazyChatFilter.Censore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
    @EventHandler (priority = EventPriority.NORMAL)
    public void on(AsyncPlayerChatEvent event){
       event.setMessage(Censore.Message(event.getMessage()));
    }
}
