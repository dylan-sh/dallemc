package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        System.out.println("Dalle MC ready to rumble...");
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        getCommand("vote").setExecutor(new CommandVote());
        getCommand("suggest").setExecutor(new CommandSuggest());


    }

    @Override

    public void onDisable(){
        System.out.println("Shutting down...");
    }
}


