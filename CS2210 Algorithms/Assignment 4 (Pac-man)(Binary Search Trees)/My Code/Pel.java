/*
 * Author: Ramtin Sirjani, 250680632
 * Date: Nov 19th 2022
 * Purpose: This object represents a pixel in a window. This pixel has a location and a colour.
 * Version: 1.0
 */

public class Pel {
    private Location p; //This location object is the location of the pixel. This is also the pel object's key attribute.
    private int color; //This integer represents the color of the pixel

    public Pel(Location p, int color){ //Constructor creates a new pixel objext with parameter inputs to set location and color 
        this.p = p;
        this.color = color;
    }

    public Location getLocus(){ //returns location of pixel
        return p;
    }

    public int getColor(){ //returns color of pixel
        return color;
    }
}
