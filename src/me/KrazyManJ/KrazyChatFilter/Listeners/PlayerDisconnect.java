package me.KrazyManJ.KrazyChatFilter.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {
    @EventHandler
    public void on(PlayerQuitEvent event){ PlayerChat.removeFromLastTyped(event.getPlayer()); }
}
