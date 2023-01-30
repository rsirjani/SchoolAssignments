/*
 * Author: Ramtin Sirjani 250680632
 * Date: December 7th 2022
 * Version: 1.0
 * Title: MyMap
 * Purpose: Reads in data from input file for constructing a roadmap (represented by a graph data structure). Includes an algorithm to solve the maze (path).
 */

import java.io.*;
import java.util.Stack;
import java.util.Iterator;

public class MyMap {
    private Graph roadmap; //The graph with nodes representing intersections or dead ends and edges representing roads
    private int startNode; //Beginning node in maze
    private int endNode; //Destination node for maze
    private int maxPrivate; //The total number of private roads that can be taken to solve the maze.
    private int maxConstruction; //The total number of construction roads that can be taken to solve the maze.
    private int currentPrivate = 0; //The current number of private roads taken
    private int currentConstruction = 0; //The current number of construction roads taken
    private int mapWidth; //width * length determines number of nodes in roadmap.
    private int mapLength;
    private Stack<Node> path = new Stack<>(); //Data structure to store nodes to visit to solve the maze in order.


    public MyMap(String inputFile) throws MapException{ //Constructor reads input file and initializes most instance variables. Also constructs roadmap.
        try{
            File input = new File(inputFile);
            FileReader fr = new FileReader(input);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int i = 1; 
            boolean horizontal = false; //determines if line being read stores information for horizontal of vertical edges
            int c = 0; //starting node for horizontal edges
            int d = 0; //starting node for vertical edges
            while((line=br.readLine())!=null){
                if (i == 2){
                    startNode = Integer.parseInt(line);
                }
                else if (i == 3){
                    endNode = Integer.parseInt(line);
                }
                else if (i == 4){
                    mapWidth = Integer.parseInt(line);
                }
                else if (i == 5){
                    mapLength = Integer.parseInt(line);
                }
                else if (i == 6){
                    roadmap = new Graph(mapWidth * mapLength);
                    maxPrivate = Integer.parseInt(line);
                }
                else if (i == 7){
                    maxConstruction = Integer.parseInt(line);
                }
                else if (i >= 8){ //Construct roadmap based on formatted input file
                    if(!line.trim().isEmpty()){    
                        int row = i - 7;
                        if(row % 2 == 0){
                            horizontal = false;
                        }
                        else{
                            horizontal = true;
                        }
                        for(int j=0 ; j < line.length() ; j++){
                            if(horizontal){
                                if(j % 2 != 0){
                                    if (line.charAt(j) == 'P'){
                                        roadmap.addEdge(roadmap.getNode(c), roadmap.getNode(c + 1), "public");
                                    }
                                    if (line.charAt(j) == 'V'){
                                        roadmap.addEdge(roadmap.getNode(c), roadmap.getNode(c + 1), "private");
                                    }
                                    if (line.charAt(j) == 'C'){
                                        roadmap.addEdge(roadmap.getNode(c), roadmap.getNode(c + 1), "construction");
                                    }
                                    c++;
                                }
                                if(j == line.length() - 1){
                                    c++;
                                }
                            }
                            else{
                                if(j % 2 == 0){
                                    if (line.charAt(j) == 'P'){
                                        roadmap.addEdge(roadmap.getNode(d), roadmap.getNode(d + (int)Math.ceil(line.length()/2) + 1), "public");
                                    }
                                    if (line.charAt(j) == 'V'){
                                        roadmap.addEdge(roadmap.getNode(d), roadmap.getNode(d + (int)Math.ceil(line.length()/2) + 1), "private");
                                    }
                                    if (line.charAt(j) == 'C'){
                                        roadmap.addEdge(roadmap.getNode(d), roadmap.getNode(d + (int)Math.ceil(line.length()/2) + 1), "construction");
                                    }
                                    d++;
                                    }
                            }
                        }
                    }
                }
                i++;
            }    
            br.close();
            fr.close();
        }
        catch(IOException error){
            throw new MapException();
        }
        catch(GraphException error){
            throw new MapException();
        }
    }

    public Graph getGraph(){ //Methods to return instance variables
        return roadmap;
    }

    public int getStartingNode(){
        return startNode;
    }

    public int getDestinationNode(){
        return endNode;
    }

    public int maxPrivateRoads(){
        return maxPrivate;
    }

    public int maxConstructionRoads(){
        return maxConstruction;
    }


    public Iterator<Node> findPath(int start, int destination, int maxPrivate, int maxConstruction){ //Solves the maze using a modified depth first search algorithm. 
        //First mark node and push on Stack. If at destination you're done.
        try{
            Node currentNode = roadmap.getNode(start); 
            currentNode.markNode(true);
            path.push(currentNode);
            if (currentNode.getId() == destination){
                return path.iterator();
            }
            else{
                //Check all edges and if the node at the end of the edge isnt marked visit it (taking into account maximum construction and private road count)
                Iterator<Edge> incidentEdges = roadmap.incidentEdges(currentNode);
                while(incidentEdges.hasNext()){
                    Edge incidentEdge = incidentEdges.next();
                    incidentEdges.remove();
                    String type = incidentEdge.getType();
                    Node nextNode = incidentEdge.secondNode();
                    if (nextNode.getMark() == false){
                        Iterator<Node> result;
                        if(type.equals("private") && currentPrivate < maxPrivate){
                            currentPrivate = currentPrivate + 1;
                            result = findPath(nextNode.getId(), destination, maxPrivate, maxConstruction);
                            if(result != null){
                                return result;
                            }
                            else{
                                currentPrivate = currentPrivate -1;
                            }
                        }
                        if(type.equals("construction") && currentConstruction < maxConstruction){
                            currentConstruction = currentConstruction + 1;
                            result = findPath(nextNode.getId(), destination, maxPrivate, maxConstruction);
                            if(result != null){
                                return result;
                            }
                            else{
                                currentConstruction = currentConstruction -1;
                            }
                        }
                        if(type.equals("public")){
                            result = findPath(nextNode.getId(), destination, maxPrivate, maxConstruction);
                            if(result != null){
                                return result;
                            }
                        }
                    }
                }
                //If stuck move back and try another route
                currentNode.markNode(false);
                path.pop();
                return null;
            }
        }
        catch(GraphException error){
        }
        return null;
    }
}
