package sh.dylan.dallemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Map;

public class CommandSuggest implements CommandExecutor {

    //bunch of var bc i'm a chimp
    private String suggestedString;

    SuggestionDB suggestionDB = new SuggestionDB();
    VoteRecords voteRecords = new VoteRecords();

    public CommandSuggest(SuggestionDB suggestionDB) {
        this.suggestionDB = suggestionDB;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only the player can run this command");
            return true;
        }
        Player player = (Player) sender;
        if (suggestionDB.checkIfPlayerSuggested(player)) {
            player.sendMessage("You have already suggested something. Use /vote <string> to vote.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("You must provide a suggestion.");
            return true;
        }
        String suggestion = args[0];
        if (suggestionDB.suggestionPresent(suggestion)) {
            player.sendMessage("This has already been suggested by " + suggestionDB.getSuggestor(suggestion).getDisplayName() + ". Use /vote <string> to vote for it.");
        } else {
            suggestionDB.addSuggestion(suggestion, player);
            voteRecords.addVote(suggestion, player);
            player.sendMessage("Suggestion successful!");
        }
        return true;
    }
}