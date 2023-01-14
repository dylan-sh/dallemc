package sh.dylan.dallemc;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(event.getMessage().equalsIgnoreCase("test")){
            event.getPlayer().sendMessage(ChatColor.RED + "it worked bro");
        }

    }


}
