/*
 * Title: Node
 * Author: Ramtin sirjani
 * Version 1.0
 * Date: October 13th 2022
 * This class stores records as data items, and a reference to another member of Node. 
 * This class functions as a linked list.
 */
public class Node {
    private Node next; //Stores an address to the the next Node in the list.
    private Record data; //Stores a record for the game super tic-tac-toe.
    
    public Node(Record data){ //Constructor.
        this.data = data;
    }

    public Node(){ //Constructor.
    }

    public void setNext(Node next){ //Sets the next Node in the list to the node given to the parameter.
        this.next = next; 
    }
    public Node getNext(){ //Returns the next Node in the list.
        return next;
    }
    public Record getData(){ //Returns the record stored in the Node.
        return data;
    }
}
