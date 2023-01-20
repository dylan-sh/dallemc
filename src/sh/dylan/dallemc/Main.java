package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        System.out.println("Dalle MC ready to rumble...");
        //Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        SuggestionDB suggestionDB = new SuggestionDB();
        VoteRecords voteRecords = new VoteRecords();
        getCommand("suggest").setExecutor(new CommandSuggest(suggestionDB));
        getCommand("vote").setExecutor(new CommandVote(suggestionDB, voteRecords));
        getCommand("listsuggestions").setExecutor(new CommandListSuggestions(suggestionDB));


    }

    @Override

    public void onDisable(){
        System.out.println("Shutting down DalleMC...");
    }
}

//rip ChatListener(), iykyk


