package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        System.out.println("Dalle MC ready to rumble...");
        //Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        SuggestionDB suggestionDB = new SuggestionDB();
        VoteRecords voteRecords = new VoteRecords();
        TimerClear vst = new TimerClear(suggestionDB, voteRecords);
        getCommand("suggest").setExecutor(new CommandSuggest(suggestionDB));
        getCommand("vote").setExecutor(new CommandVote(suggestionDB, voteRecords));
        getCommand("listsuggestions").setExecutor(new CommandListSuggestions(suggestionDB));
        //timer section
        int delay = 0;
        int period = 2400;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Voting has started!");

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage("10 seconds left to vote!");

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.broadcastMessage("Voting has finished!");
                                // Insert API call here
                            }
                        }, 20); // 20 ticks = 1 second

                    }
                }, 500);  // 500 ticks = 25 seconds
            }
        }, delay, period);
    }




    @Override

    public void onDisable(){
        System.out.println("Shutting down DalleMC...");
    }
}

//rip ChatListener(), iykyk


