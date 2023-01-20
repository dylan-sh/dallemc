package sh.dylan.dallemc;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class SuggestionDB {

    private Map<String, Integer> suggestions;
    private Map<String, Player> playerSuggested;

    //bob the builder
    public SuggestionDB() {
        System.out.println("SuggestionDB construction successful.");
    }

    //adds the suggestion to the maps
    public void addSuggestion(String text, Player p){
        suggestions.put(text, 1);
        playerSuggested.put(text, p);
    }

    //returns true if present
    public boolean suggestionPresent(String text){
        if(suggestions.containsKey(text)) {
            return true;
        }
        return false;
    }

    //checks the number of votes if exists
    public int getVotes(String text)
    {
        if(suggestionPresent(text)){
            return suggestions.get(text);
        }
        return -1;
    }

    //increments the value of the suggestions value pair
    public void incrementVote(String text)
    {
        if(suggestionPresent(text))
        {
            suggestions.merge(text, 1, Integer::sum);
        }
    }

    public Player getSuggestor(String suggestion){
        return playerSuggested.get(suggestion);
    }

    public ArrayList getSuggestions(){
        if(isSuggestionsEmpty()) {
            return null;
        }

        ArrayList<String> suggestionsList = new ArrayList<>();
        suggestionsList.addAll(suggestions.keySet());
        return suggestionsList;
    }

    public boolean checkIfPlayerSuggested(Player p){
        if(isSuggestionsEmpty()){
            return false;
        }

        if(playerSuggested.containsValue(p)){
            return true;
        }
        return false;
    }

    public boolean isSuggestionsEmpty(){
        if(suggestions.isEmpty()){
            return true;
        }
        return false;
    }
}
