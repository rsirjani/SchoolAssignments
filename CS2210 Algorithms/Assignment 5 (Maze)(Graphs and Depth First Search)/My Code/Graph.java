/*
 * Author: Ramtin Sirjani 250680632
 * Date: December 7th 2022
 * Version: 1.0
 * Title: Graph
 * Purpose: Instructions to create and modify a Graph data structure. Adjacency list implementation.
 */

import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT{
    private Node[] nodeArray; //An array storing the Nodes of the graph (Nodes stored in the index coinciding with their node IDs).
    private StoreEdges[] edgeArray; //An array storing edges of the graph (each edge stored twice, once for each Node it connects).
    
    public Graph(int n){ //Constructor, builds a graph with n nodes. Adjacency list implementation.
        nodeArray = new Node[n];
        edgeArray = new StoreEdges[n];
        for (int i=0; i < n ; i++){
            nodeArray[i] = new Node(i);
            edgeArray[i] = new StoreEdges();
        }
    }

    public void addEdge(Node nodeu, Node nodev, String edgeType) throws GraphException { //Inserts an edge between two nodes in the graph.
        try{
            Node inGraphU = getNode(nodeu.getId());
            Node inGraphV = getNode(nodev.getId());
            placeEdge(inGraphU, inGraphV, edgeType);
            placeEdge(inGraphV, inGraphU, edgeType);
        }
        catch(GraphException error){ //If the edge is already in the graph throw an exception.
            throw error;
        }
    }

    private void placeEdge(Node u, Node v, String edgeType) throws GraphException{ //Helper method for addEdge. Since two edges need to be added to the adjacency list every time a new edge is added to the graph.
        Edge newEdge = new Edge(u, v, edgeType);
        StoreEdges newEdgeNode = new StoreEdges(newEdge);
        StoreEdges locationToStore = edgeArray[u.getId()];
        while(locationToStore.getNext() != null){
            locationToStore = locationToStore.getNext();
            if(locationToStore.getEdge() != null){
                if(locationToStore.getEdge().firstNode() == u && locationToStore.getEdge().secondNode() == v){
                    throw new GraphException();
                }
            }
        }
        locationToStore.setNext(newEdgeNode);
    }

    public Node getNode(int id) throws GraphException {  //Return a Node in the graph.
        try{
            return nodeArray[id];
        }
        catch(ArrayIndexOutOfBoundsException error){ //If the node doesnt exist throw an exception.
            throw new GraphException();
        }
    }

    
    public Iterator<Edge> incidentEdges(Node u) throws GraphException {  //Returns an iterator storing all the edges incident to node u.
        try{
            Stack<Edge> edgeStack = new Stack<>();
            Node inGraphU = getNode(u.getId());
            StoreEdges edgeNode = edgeArray[inGraphU.getId()];
            
            if(edgeNode.getNext() != null){ //Determines which edges are adjacent to u by iterating through the adjacency list.
                edgeNode = edgeNode.getNext();
                while(edgeNode != null){
                    edgeStack.push(edgeNode.getEdge());
                    edgeNode = edgeNode.getNext();
                }
            }
            if(edgeStack.empty()){
                return null;
            }
            else{
                return edgeStack.iterator();
            }
        }
        catch(ArrayIndexOutOfBoundsException error){
            throw new GraphException();
        }
    }

    
    public Edge getEdge(Node u, Node v) throws GraphException { //Returns the edge with starting node u and ending node v.
        try{
            Node inGraphU = getNode(u.getId());
            StoreEdges edgeNode = edgeArray[inGraphU.getId()];
            if(edgeNode.getNext() != null){
                edgeNode = edgeNode.getNext();
                while(edgeNode != null){
                    if(edgeNode.getEdge().secondNode() == v){
                        return edgeNode.getEdge();
                    }
                    edgeNode = edgeNode.getNext();
                }
            }
            else{
                throw new GraphException();
            }
        }
        catch(ArrayIndexOutOfBoundsException error){
            throw new GraphException();
        }
        catch(GraphException error){
            throw error;
        }
        throw new GraphException();
    }

    
    public boolean areAdjacent(Node u, Node v) throws GraphException { //Determines if an edge exists between u an v. True if it does and false if it does not.
        try{
            Node inGraphU = getNode(u.getId());
            Node inGraphV = getNode(v.getId());
            StoreEdges location = edgeArray[u.getId()];
            while(location.getNext() != null){
                location = location.getNext();
                if(location.getEdge() != null){
                    if(location.getEdge().firstNode() == inGraphU && location.getEdge().secondNode() == inGraphV){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        catch(GraphException error){
            throw error;
        }
        return false;
    }
    
}
