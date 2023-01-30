/*
 * Author: Ramtin Sirjani, 250680632
 * Date: Nov 19th 2022
 * Purpose: This class represents a Node to be stored in a binary search tree. Each node has a reference to a parent, two children.
 * contains a pixel object as the value. The location of the pixel object is the key attribute for sorting pel objects in a tree.
 * Version: 1.0
 */

public class BNode {
    private Pel value; //Pel object stored in node
    private BNode left; //reference to left child
    private BNode right; //reference to right child
    private BNode parent; //reference to parent

    public BNode(Pel value, BNode left, BNode right, BNode parent){ //constructor creates a new BNode object and uses parameters to set all instance variables.
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public BNode(){ //overloaded constructor creates a new BNode object and sets all instance variables to null. This node represents a leaf (or the root when creating a new tree).
        value = null;
        left = null;
        right = null;
        parent = null;
    }

    public BNode parent(){ //returns the parent of this node
        return parent;
    }

    public void setParent(BNode newParent){ //sets the parent of this node
        parent = newParent;
    }

    public void setLeftChild(BNode p){ //sets the left child of this node
        left = p;
    }

    public void setRightChild(BNode p){ //sets the right child of this node
        right = p;
    }

    public void setContent(Pel value){ //sets the value to a new pixel object
        this.value = value;
    }

    public boolean isLeaf(){ //checks the node to see if it is a leaf
        if (this.value == null){
            return true;
        }
        else{
            return false;
        }
    }

    public Pel getData(){ //Returns the pixel object stored in this node
        return value;
    }

    public BNode leftChild(){ //return the left child of this node
        return left;
    }

    public BNode rightChild(){ //return the right child of this node
        return right;
    }
}
