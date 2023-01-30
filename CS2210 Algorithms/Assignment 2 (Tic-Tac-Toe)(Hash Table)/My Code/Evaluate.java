/*
 * Title: Evaluate
 * Author: Ramtin sirjani
 * Version 1.0
 * Date: October 13th 2022
 * This class evaluates the state of a super tic-tac-toe game. 
 */
public class Evaluate { 
    private int size; //Stores the size of the super tic-tac-toe board.
    private int tilesToWin; //the required number of adjacent tiles needed to become a super tic-tac-toe champion.
    private int maxLevel; //unused
    char[][] gameBoard; //A 2-D array of characters representing the game board.

    public Evaluate(int size, int tilesToWin, int maxLevels){ //Constructor.
        this.size = size;
        this.tilesToWin = tilesToWin;
        this.maxLevel = maxLevels;
        gameBoard = new char[size][size];
        for (int col=0; col<size; col++){ //Initialize a new game board to be empty.
            for (int row=0; row<size; row++){
                gameBoard[row][col] = 'e';
            }
        }
    }

    public Dictionary createDictionary(){ //Create a hash table to store game records.
        Dictionary dict = new Dictionary(6007);
        return dict;
    }

    public Record repeatedState(Dictionary dict){ //Checks if there is already a record representing the current game-state in the dictionary.
        StringBuilder game = new StringBuilder();
        for (int col=0; col<size; col++){
            for (int row=0; row<size; row++){
                game.append(gameBoard[row][col]);
            }
        }
        String game_string = game.toString();
        return dict.get(game_string);
    }

    public void insertState(Dictionary dict, int score, int level){ //Stores the current game-state in the dictionary.
        StringBuilder game = new StringBuilder();
        for (int col=0; col<size; col++){
            for (int row=0; row<size; row++){
                game.append(gameBoard[row][col]);
            }
        }
        String game_string = game.toString();
        Record record = new Record(game_string, score, level);
        dict.put(record);
    }

    public void storePlay(int row, int col, char symbol){ //Stores symbol in the specified cell of the game board.
        gameBoard[row][col] = symbol;
    }

    public boolean squareIsEmpty(int row, int col){ //Returns true if the square is empty.
        if (gameBoard[row][col] == 'e'){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean tileOfComputer(int row, int col){ //Returns true if the square is 'c'.
        if (gameBoard[row][col] == 'c'){
            return true;
        }
        else{
            return false;
        }
    }
 
    public boolean tileOfHuman(int row, int col){ //Returns true if the square is 'h'.
        if (gameBoard[row][col] == 'h'){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean wins(char symbol){ //Iterates through the entire game board to check if the game has been won.
        int adjacent = 0;
        //Tests if there is vertical adjacency.
        for (int col=0; col<size; col++){
            for (int row=0; row<size; row++){
                if (gameBoard[row][col] == symbol){
                    adjacent ++;
                    if(adjacent==tilesToWin){
                        return true;
                    }
                }
                else{
                     adjacent=0;
                }
                if (row == size - 1){
                    adjacent=0;
                }
            }
        }
        //Tests if there is horizontal adjacency.
        for (int row=0; row<size; row++){
            for (int col=0; col<size; col++){
                if (gameBoard[row][col] == symbol){
                    adjacent ++;
                    if(adjacent==tilesToWin){
                        return true;
                    }
                }
                else{
                     adjacent=0;
                }
                if (col == size - 1){
                    adjacent=0;
                }
            }
        }
        //Tests if there is diagonal adjacency one way.
        for (int row= size -1; row>-1; row--){
            for (int col=0, i = row; i<size; col++, i++){
                if (gameBoard[i][col] == symbol){
                    adjacent ++;
                    if(adjacent==tilesToWin){
                        return true;
                    }
                }
                else{
                     adjacent=0;
                }
                if (i == size - 1){
                    adjacent=0;
                }
            }
        }
        for (int col= 1; col<size; col++){
            for (int row=0, i = col; i<size; row++, i++){
                if (gameBoard[row][i] == symbol){
                    adjacent ++;
                    if(adjacent==tilesToWin){
                        return true;
                    }
                }
                else{
                     adjacent=0;
                }
                if (i == size - 1){
                    adjacent=0;
                }
            }
        }
        //Tests if there is diagonal adjacency the other way.
        for (int row=size -1; row>-1; row--){
            for (int col=size -1, i = row; i<size; col--, i++){
                if (gameBoard[i][col] == symbol){
                    adjacent ++;
                    if(adjacent==tilesToWin){
                        return true;
                    }
                }
                else{
                     adjacent=0;
                }
                if (i == size - 1){
                    adjacent=0;
                }
            }
        }
        for (int col=size -1; col>-1; col--){
            for (int row=0, i = col; i>-1; i--, row++){
                if (gameBoard[row][i] == symbol){
                    adjacent ++;
                    if(adjacent==tilesToWin){
                        return true;
                    }
                }
                else{
                     adjacent=0;
                }
                if (i == 0){
                    adjacent=0;
                }
            }
        }
        return false;
    }

            
                
    public boolean isDraw(){ //Returns true if the game is a draw.
        for (int col=0; col<size; col++){
            for (int row=0; row<size; row++){
                if (squareIsEmpty(row,col)){
                    return false;
                }
            }
        }
        return true;
    }

    public int evalBoard(){ //Checks the current state of the game.
        if (wins('c')){
            return 3;
        }
        if (wins('h')){
            return 0;
        }
        if (isDraw()){
            return 2;
        }
        else{
            return 1;
        }
    }
}
