package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLOutput;

public class Main extends JavaPlugin {
    public static Plugin instance = null;
    @Override
    public void onEnable(){
        System.out.println("Dalle MC Initializing...");
        instance = this;
        ConfigParser cfp = new ConfigParser("plugins/dallemc/","config.txt");
        System.out.println("API KEY received:" + cfp.getAPIKey());
        SuggestionDB suggestionDB = new SuggestionDB();
        VoteRecords voteRecords = new VoteRecords();
        getCommand("suggest").setExecutor(new CommandSuggest(suggestionDB));
        getCommand("vote").setExecutor(new CommandVote(suggestionDB, voteRecords));
        getCommand("listsuggestions").setExecutor(new CommandListSuggestions(suggestionDB));
        System.out.println("\033[32m" + "Dalle MC is ready to rumble!" + "\033[0m");
        //timer section
        int delay = 0;
        int period = 1200;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage("10 seconds left to vote!");

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                            @Override
                            public void run(){
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
                                voteRecords.clearEverything();
                                ImageGeneration imageGenerator = new ImageGeneration(cfp.getAPIKey(), winner);
                                try {
                                    String filename = imageGenerator.generateImage();
                                    if(filename.contains("Error")){
                                        Bukkit.broadcastMessage("Error in generation, probably due to NSFW suggestion or network error.");
                                    }else {
                                        CommandSender console = Bukkit.getServer().getConsoleSender();
                                        filename = filename.substring(0, filename.length() - 4);
                                        Bukkit.dispatchCommand(console, "pixelator " + filename + " world -528 -60 252");
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, 200); // 20 ticks = 1 second

                    }
                }, 250);  // 500 ticks = 25 seconds
            }
        }, delay, period);
    }




    @Override

    public void onDisable(){
        instance = null; System.out.println("Shutting down DalleMC...");
    }
}

//rip ChatListener(), iykyk


