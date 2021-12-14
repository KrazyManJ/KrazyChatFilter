package me.KrazyManJ.KrazyChatFilter.Listeners;

import me.KrazyManJ.KrazyChatFilter.Censore;
import me.KrazyManJ.KrazyChatFilter.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class PlayerChat implements Listener {
    private static final HashMap<Player, String> lastTyped = new HashMap<>();

    public static void removeFromLastTyped(Player player){ lastTyped.remove(player); }

    @EventHandler (priority = EventPriority.NORMAL)
    public void on(AsyncPlayerChatEvent event){
        if (Main.getInstance().getConfig().getBoolean("antispam")){
            if (lastTyped.containsKey(event.getPlayer()) && lastTyped.get(event.getPlayer()).equalsIgnoreCase(event.getMessage())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cYou are not allowed to spam!");
                return;
            }
            lastTyped.put(event.getPlayer(), event.getMessage());
        }
        event.setMessage(Censore.Message(event.getMessage()));
    }
}
