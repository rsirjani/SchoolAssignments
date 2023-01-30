/*
 * Author: Ramtin Sirjani 250680632
 * Date: December 7th 2022
 * Version: 1.0
 * Title: Edge
 * Purpose: Connects two nodes in a graph
 */
public class Edge {
    private Node u;
    private Node v;
    private String type;

    public Edge(Node u, Node v, String type){ //Constructor sets starting and ending node. Also sets the type of the edge(rules of maze).
        this.u = u;
        this.v = v;
        this.type = type;
    }

    public Node firstNode(){ //Returns the starting Node.
        return u;
    }

    public Node secondNode(){ //Returns the ending Node.
        return v;
    }

    public String getType(){ //Returns the type.
        return type;
    }
}
