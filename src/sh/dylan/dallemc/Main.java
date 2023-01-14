package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        System.out.println("Starting up...");
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

    }

    @Override

    public void onDisable(){
        System.out.println("Shutting down...");
    }
}


