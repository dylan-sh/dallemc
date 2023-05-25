package sh.dylan.dallemc;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteRecords {
    private Map<String, List<Player>> voteRecord;

    //i realize after the fact that this probably could've been done in the suggestionDB class. too late now though it's already written lol
    public VoteRecords()
    {
        voteRecord = new HashMap<String, List<Player>>();
        System.out.println("VotedDB construction successful.");
    }

    //adds the player to the map containing the suggestion and the players. if suggestion not present it will add it and the voter.
    public void addVote(String suggestion, Player p){
        if(voteRecord.containsKey(suggestion)) {
            voteRecord.computeIfAbsent(suggestion, k -> new ArrayList<>()).add(p);
        }else{
            //don't ask me how those curly braces work at the end but they do
            voteRecord.put(suggestion, new ArrayList<Player>(){{add(p);}});
        }
    }

    public boolean hasVoted(Player player){
        if(isVoteRecordEmpty()){
            return false;
        }

        ArrayList<List<Player>> superList = new ArrayList<>();
        superList.addAll(voteRecord.values());
        for(List<Player> l : superList){
            for(Player p: l){
                if(p.equals(player)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isVoteRecordEmpty(){
        if(voteRecord.isEmpty()){
            return true;
        }
        return false;
    }

    public void clearEverything(){
        System.out.println("clearing vote records");
        voteRecord.clear();
    }
}
