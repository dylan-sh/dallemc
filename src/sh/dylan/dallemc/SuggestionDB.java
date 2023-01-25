package sh.dylan.dallemc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SuggestionDB {

    private Map<String, Integer> suggestions;
    private Map<String, Player> playerSuggested;

    //bob the builder
    public SuggestionDB() {
        suggestions = new HashMap<String, Integer>();
        playerSuggested = new HashMap<String, Player>();
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

    public void clearEverything(){
        suggestions.clear();
        playerSuggested.clear();
    }

    //gets the winning suggestion. adds the suggestions with the most number of votes to an arraylist and picks from them at random.
    //normally this would just pick at random but the logic is designed for the event of a tie
    public String getWinningSuggestion(){
        int winSugCount = -1;
        ArrayList<String> winners = new ArrayList<>();
        ArrayList<String> suggestionList = new ArrayList<>(suggestions.keySet());
        for(String s : suggestionList){
            if(suggestions.get(s) > winSugCount){
                winSugCount = suggestions.get(s);
            }
        }
        for(String s : suggestionList){
            if(suggestions.get(s) == winSugCount){
                winners.add(s);
            }
        }
        if(winners.size()>1){
            Bukkit.broadcastMessage("There has been a tie. A suggestion will be chosen at random between the winners.");
        }
        Random rando = new Random();
        int randIndex = rando.nextInt(winners.size());
        String winner = winners.get(randIndex);
        return winner;
    }
}
