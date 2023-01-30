/*
 * Author: Ramtin Sirjani, 250680632
 * Date: Nov 19th 2022
 * Purpose: This class represents an object in the game. it contains information about the pixels of that object including its location.
 * This class allows the detection of when two objects intersect. Also contains information of the box enclosing the object. The purpose of the box is 
 * to allow the location of the pixels of that object to be updated nore efficiently when the object moves.
 * Version: 1.0
 */
public class MyObject implements MyObjectADT{
    private int id; //The ID of the object. Allowing objects to be unique
    private int width; //The width of the box enclosing the objext 
    private int height; //The height of the box enclosing the object
    private String type; //The type of object in the game
    private Location pos; //The location of the top left of the box enclosing the object
    private BinarySearchTree objectTree; //The binary tree storing an ordered list of pixels composing this object

    public MyObject(int id, int width, int height, String type, Location pos){ //constructor initializing all instance variables for a new MyObject object.
        this.id = id;
        this.width = width;
        this.height = height;
        this.type = type;
        this.pos = pos;
        objectTree = new BinarySearchTree();
    }

    public void setType(String type){ //Sets type
        this.type = type;
    }

    public int getWidth(){ //Returns width
        return width;
    }

    public int getHeight(){ //Returns height
        return height;
    }

    public String getType(){ //Returns type
        return type;
    }

    public int getId(){ //Return ID
        return id;
    }

    public Location getLocus(){ //Returns the location of the onclosing box
        return pos;
    }

    public void setLocus(Location value){ //Sets the location of the enclosing box
        pos = value;
    }

    public void addPel(Pel pix) throws DuplicatedKeyException{ //Adds a pixel to the binary tree storing pixels for the object
        objectTree.put(objectTree.getRoot(), pix);
    }

    public boolean intersects(MyObject otherObject){ //Checks if any pixels of two objects intersect
        if (OverlapBoxes(otherObject) == false){
            return false;
        }
        else{
            try {
                Pel pixel = objectTree.smallest(objectTree.getRoot());
                while(pixel != null){
                    int overlappingX = pixel.getLocus().getx() + pos.getx() - otherObject.getLocus().getx();
                    int overlappingY = pixel.getLocus().gety() + pos.gety() - otherObject.getLocus().gety();
                    Location overlappingKey = new Location(overlappingX, overlappingY);
                    pixel = objectTree.successor(objectTree.getRoot(), pixel.getLocus());
                    if(otherObject.findPel(overlappingKey) == true){
                        return true;
                    }
                }
            }
            catch (EmptyTreeException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean findPel(Location p){ //Checks if a pixel at a specific location (p) is in the object
        if(objectTree.get(objectTree.getRoot(), p) != null){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean OverlapBoxes(MyObject otherObject){ //Checks if two boxes overlap one another
        int rightBorder =  this.pos.getx() + width;
        int leftBorder = this.pos.getx();
        int topBorder = this.pos.gety();
        int bottomBorder = this.pos.gety() + height;
        Location topRight = new Location(this.pos.getx() + width, this.pos.gety());
        Location topLeft = new Location(this.pos.getx(), this.pos.gety());
        Location bottomRight = new Location(this.pos.getx() + width, this.pos.gety() + height);
        Location bottomLeft =  new Location(this.pos.getx(), this.pos.gety() + height);
        int otherRightBorder = otherObject.getLocus().getx() + otherObject.getWidth();
        int otherLeftBorder = otherObject.getLocus().getx();
        int otherTopBorder = otherObject.getLocus().gety();
        int otherBottomBorder = otherObject.getLocus().gety() + otherObject.getHeight();
        Location otherTopRight = new Location(otherObject.getLocus().getx() + otherObject.getWidth(), otherObject.getLocus().gety());
        Location otherTopLeft = new Location(otherObject.getLocus().getx(), otherObject.getLocus().gety());
        Location otherBottomRight = new Location(otherObject.getLocus().getx() + otherObject.getWidth(), otherObject.getLocus().gety() + otherObject.getHeight());
        Location otherBottomLeft =  new Location(otherObject.getLocus().getx(), otherObject.getLocus().gety() + otherObject.getHeight());
        if(otherTopRight.getx() <= rightBorder && otherTopRight.getx() >= leftBorder && otherTopRight.gety() <= bottomBorder && otherTopRight.gety() >= topBorder){
            return true;
        }
        else if(otherTopLeft.getx() <= rightBorder && otherTopLeft.getx() >= leftBorder && otherTopLeft.gety() <= bottomBorder && otherTopLeft.gety() >= topBorder){
            return true;
        }
        else if(otherBottomRight.getx() <= rightBorder && otherBottomRight.getx() >= leftBorder && otherBottomRight.gety() <= bottomBorder && otherBottomRight.gety() >= topBorder){
            return true;
        }
        else if(otherBottomLeft.getx() <= rightBorder && otherBottomLeft.getx() >= leftBorder && otherBottomLeft.gety() <= bottomBorder && otherBottomLeft.gety() >= topBorder){
            return true;
        }
        else if(topRight.getx() <= otherRightBorder && topRight.getx() >= otherLeftBorder && topRight.gety() <= otherBottomBorder && topRight.gety() >= otherTopBorder){
            return true;
        }
        else if(topLeft.getx() <= otherRightBorder && topLeft.getx() >= otherLeftBorder && topLeft.gety() <= otherBottomBorder && topLeft.gety() >= otherTopBorder){
            return true;
        }
        else if(bottomRight.getx() <= otherRightBorder && bottomRight.getx() >= otherLeftBorder && bottomRight.gety() <= otherBottomBorder && bottomRight.gety() >= otherTopBorder){
            return true;
        }
        else if(bottomLeft.getx() <= otherRightBorder && bottomLeft.getx() >= otherLeftBorder && bottomLeft.gety() <= otherBottomBorder && bottomLeft.gety() >= otherTopBorder){
            return true;
        }
        else{
        return false;
        }
    }
}
