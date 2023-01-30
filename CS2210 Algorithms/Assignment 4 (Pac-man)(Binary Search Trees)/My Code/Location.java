/*
 * Author: Ramtin Sirjani, 250680632
 * Date: Nov 19th 2022
 * Purpose: Allow the storage and comparison of locations (x and y coordinates) in a window
 * Version: 1.0
 */

public class Location { 
    private int x; //x-coordinate
    private int y; //y-coordinate

    public Location(int x, int y){ //constructor for a location object, parameters determine coordinates of the object.
        this.x = x;
        this.y = y;
    }

    public int getx(){ //returns the X-coordinate
        return x;
    }

    public int gety(){ //returns the Y-coordinate
        return y;
    }

    public int compareTo(Location p){ // Compares two location objects. If the location of this object has a higher y-coordinate value (closer to bottom of window) returns 1, 
        if(y == p.gety() && x > p.getx()){ //if locations have the same y coordinate but this one has the higher x (closer to right end) returns 1, if they have the same x and y returns 0, in the remaining scenarios returns -1.
            return 1;
        }
        if(y == p.gety() && x == p.getx()){
            return 0;
        }
        if(y == p.gety() && x < p.getx()){
            return -1;
        }
        if(y > p.gety()){
            return 1;
        }
        else{
            return -1;
        }
    }
}
