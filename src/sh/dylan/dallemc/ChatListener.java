package sh.dylan.dallemc;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

//ArrayList<string> currentSuggestions

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(event.getMessage().substring(0,8).contains("/suggest")){
            Suggestion sug = new Suggestion(event.getMessage().substring(8));
            event.getPlayer().sendMessage(ChatColor.RED + sug.getText());
        }

        /**
        if(event.getMessage().substring(0,5).contains("/vote")){
            Suggestion sug = new Suggestion(event.getMessage().substring(8));
            event.getPlayer().sendMessage(ChatColor.RED + sug.getText());
        }
        **/
    }


}
