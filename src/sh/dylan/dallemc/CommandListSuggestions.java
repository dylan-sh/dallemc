package sh.dylan.dallemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandListSuggestions implements CommandExecutor {
    private CommandSuggest suggestCommand = new CommandSuggest();
    private CommandVote voteCommand = new CommandVote();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;
        Map<String,Integer> suggestions = suggestCommand.getSuggestions();
        if (suggestions.isEmpty()) {
            player.sendMessage("No strings have been suggested yet.");
            return true;
        }
        player.sendMessage("List of Suggestions: ");
        for (Map.Entry<String, Integer> entry : suggestions.entrySet()) {
            String suggestedString = entry.getKey();
            int votes = entry.getValue();
            player.sendMessage("- " + suggestedString + ": " + votes + " votes.");
        }
        return true;
    }
}

