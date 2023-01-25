package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Plugin instance = null;
    @Override
    public void onEnable(){
        System.out.println("Dalle MC ready to rumble...");
        instance = this;
        ConfigParser cfp = new ConfigParser("plugins/dallemc/config.txt");
        SuggestionDB suggestionDB = new SuggestionDB();
        VoteRecords voteRecords = new VoteRecords();
        getCommand("suggest").setExecutor(new CommandSuggest(suggestionDB));
        getCommand("vote").setExecutor(new CommandVote(suggestionDB, voteRecords));
        getCommand("listsuggestions").setExecutor(new CommandListSuggestions(suggestionDB));
        //timer section
        int delay = 0;
        int period = 2400;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Voting has started!");

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage("10 seconds left to vote!");

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.broadcastMessage("Voting has finished!");
                                String winner = suggestionDB.getWinningSuggestion();
                                if(winner == "")
                                {
                                    Bukkit.broadcastMessage("There are no suggestions. Nobody cares :(");
                                    if(Bukkit.getServer().getOnlinePlayers().size() == 0)
                                    {
                                        Bukkit.broadcastMessage("I will continue to be here... waiting... for someone to join... but for now I will cycle on a loop of voting forever and always until someone comes my way, an eternity of loneliness...");
                                    }
                                    return;
                                }
                                Bukkit.broadcastMessage("The winner is " + winner + " with " + suggestionDB.getVotes(winner) + " votes. It was suggested by " + suggestionDB.getSuggestor(winner).getDisplayName());
                                suggestionDB.clearEverything();
                                // Insert API call here
                            }
                        }, 200); // 20 ticks = 1 second

                    }
                }, 500);  // 500 ticks = 25 seconds
            }
        }, delay, period);
    }




    @Override

    public void onDisable(){
        instance = null; System.out.println("Shutting down DalleMC...");
    }
}

//rip ChatListener(), iykyk


