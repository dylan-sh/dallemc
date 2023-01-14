package sh.dylan.dallemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandVote implements CommandExecutor {
    private Set<String> votedPlayers = new HashSet<>();

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
        if (votedPlayers.contains(playerName)) {
            player.sendMessage("You have already voted!");
            return true;
        }
        // Perform the vote logic
        String voteArgument = args[0];
        player.sendMessage("You voted for" + voteArgument + "!");
        votedPlayers.add(playerName);
        return true;
    }
}
