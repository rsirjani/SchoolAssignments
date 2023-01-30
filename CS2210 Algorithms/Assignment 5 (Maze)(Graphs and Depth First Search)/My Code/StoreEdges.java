/*
 * Author: Ramtin Sirjani 250680632
 * Date: December 7th 2022
 * Version: 1.0
 * Title: StoreEdges
 * Purpose: allows the storage of edge objects in a linked list for an adjacency list.
 */

public class StoreEdges {
    private Edge edge; //Edge being stored
    private StoreEdges next; //reference to next container

    public StoreEdges(){ //Constructor (allows initialization of empty array).
        this.edge = null;
        this.next = null;
    }

    public StoreEdges(Edge edge){ //Overloaded constructor that stores the edge passed to the parameter.
        this.edge = edge;
        this.next = null;
    }

    public StoreEdges getNext(){ //Return the next container in the list.
        return next;
    }

    public void setNext(StoreEdges next){ //Sets the next container in the list.
        this.next = next;
    }


    public Edge getEdge(){ //Returns the edge being stored in the container.
        return edge;
    }

    public void setEdge(Edge edge){ //Sets the edge being stored in the container.
        this.edge = edge;
    }
}
