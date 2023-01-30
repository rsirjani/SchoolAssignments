/*
 * Author: Ramtin Sirjani 250680632
 * Date: December 7th 2022
 * Version: 1.0
 * Title: Node
 * Purpose: A node in a graph.
 */

public class Node { 
    private int id; //Node id
    private boolean mark = false; //Allows graph traversal to determine if node has been visited.

    public Node(int id){ //Constructor
        this.id = id;
    }

    public void markNode(boolean mark){ //Sets mark to parameter
        this.mark = mark;
    }

    public boolean getMark(){ //Returns mark
        return mark;
    }

    public int getId(){ //Returns ID
        return id;
    }
}
