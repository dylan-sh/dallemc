package sh.dylan.dallemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandVote implements CommandExecutor {

    boolean voted = false;
    SuggestionDB suggestionDB = new SuggestionDB();
    VoteRecords voteRecords = new VoteRecords();

    public CommandVote(SuggestionDB suggestionDB, VoteRecords voteRecords) {
        this.suggestionDB = suggestionDB;
        this.voteRecords = new VoteRecords();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //checks if player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only the player can run this command");
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("You have to have something to vote for silly!");
            return true;
        }
        String playerName = player.getName();
        if (voteRecords.hasVoted(player)) {
            player.sendMessage("You have already voted!");
            return true;
        }
        String suggestion = args[0];

        if (!suggestionDB.suggestionPresent(suggestion)) {
            player.sendMessage("This isn't a current suggestion. You should try /suggest 'ing it!");
            return true;
        }else {
            suggestionDB.incrementVote(suggestion);
            voteRecords.addVote(suggestion, player);
            player.sendMessage("You voted for " + suggestion);
            return true;
        }
    }
}
