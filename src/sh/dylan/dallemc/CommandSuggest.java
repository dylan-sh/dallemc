package sh.dylan.dallemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSuggest implements CommandExecutor {
    private CommandVote voteCommand = new CommandVote();
    private String suggestedString;
    private boolean suggested = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only the player can run this command");
            return true;
        }
        Player player = (Player) sender;
        if (suggested) {
            player.sendMessage("You have already suggested something. Use /vote <string> to vote.");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("You must provide a suggestion.");
            return true;
        }
        suggestedString = args[0];
        suggested = true;
        player.sendMessage("Suggested: " + suggestedString + ". Use /vote <string> to vote.");
        return true;
    }
    public void vote(Player player,String[] args){
        voteCommand.onCommand(player,null,"vote",args);
    }
}
