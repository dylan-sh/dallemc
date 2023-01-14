package sh.dylan.dallemc;

public class Suggestion {

    private String text = "";
    private int votes;

    public Suggestion(String text){
        this.text = text;
        this.votes = 1;
    }

    public void incrementVote(){
        this.votes += 1;
    }

    public void setText(){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public int getVotes(){
        return votes;
    }
}
