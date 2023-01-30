/*
 * Title: Record
 * Author: Ramtin sirjani
 * Version 1.0
 * Date: October 13th 2022
 * This class stores a record for the game super tic-tac-toe.
 */
public class Record {
    private String key; //Stores the key for the the record.
    private int score; //Stores the score for the the record. The score indicates the game state. A higher score favours the commputer.
    private int level; //Stores the level for the the record. The level indicates the difficulty of the game.
    
    public Record(String key, int score, int level){ //constructor
        this.key = key;
        this.score = score;
        this.level = level;
    }

    public String getKey(){ //returns key
        return key;
    }
    public int getScore(){ //returns score
        return score;
    }
    public int getLevel(){ //returns level
        return level;
    }
}
