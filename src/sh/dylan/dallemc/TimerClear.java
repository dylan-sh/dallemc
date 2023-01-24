package sh.dylan.dallemc;

public class TimerClear implements Runnable {

    SuggestionDB suggestionDB;
    VoteRecords voteRecords;

    public TimerClear(SuggestionDB suggestionDB, VoteRecords voteRecords){
        this.suggestionDB = suggestionDB;
        this.voteRecords = voteRecords;
    }

    @Override
    public void run(){
        clearVotes();
    }

    public void clearVotes(){
        suggestionDB.clearEverything();
        voteRecords.clearEverything();
    }


}
