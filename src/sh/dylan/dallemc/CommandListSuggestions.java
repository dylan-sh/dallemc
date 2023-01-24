package sh.dylan.dallemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandListSuggestions implements CommandExecutor {

    SuggestionDB suggestionDB;
    public CommandListSuggestions(SuggestionDB suggestionDB){
        this.suggestionDB = suggestionDB;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;
        if (suggestionDB.isSuggestionsEmpty()) {
            player.sendMessage("No strings have been suggested yet. Suggested one using /suggest <string> !");
            return true;
        }
        ArrayList<String> suggestions = suggestionDB.getSuggestions();
        player.sendMessage("List of Suggestions: ");
        for (String suggestion: suggestions) {
            String suggestedString = suggestion;
            int votes = suggestionDB.getVotes(suggestedString);
            player.sendMessage("- " + suggestedString + ": " + votes + " votes.");
        }
        return true;
    }
}

