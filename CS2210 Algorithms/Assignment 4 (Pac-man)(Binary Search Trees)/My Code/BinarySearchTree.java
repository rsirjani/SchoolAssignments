/*
 * Author: Ramtin Sirjani, 250680632
 * Date: Nov 19th 2022
 * Purpose: This class represents a binary search tree used to store all the pixels of an object used to play the game pac-man. 
 * will be implemented to determine collisions of pixels for different objects in the game
 * Version: 1.0
 */
public class BinarySearchTree implements BinarySearchTreeADT{
    private BNode r; //Nodes that make up the tree
    
    public BinarySearchTree(){  //Constructor that creates a new binary search tree using just one leaf.
        this.r = new BNode();
    }

    public BNode getRoot() { //Returns the root of a binary search tree of
        while(r.parent() != null){
            r = r.parent();
        }
        return r;
    }

    
    public Pel get(BNode r, Location key) { //If there is a pixel in the binary search tree with location key, return it. Otherwise return null.
        return getNode(r, key).getData();
    }

    
    public void put(BNode r, Pel data) throws DuplicatedKeyException { //Inserts the pixel (data) into the binary search tree in the proper location. If it's already in there throw exception. Rearranges tree to ensure it still has the properties of a binary search tree
        BNode destination = getNode(r,data.getLocus());
        if (!destination.isLeaf()){
            throw new DuplicatedKeyException("This pixel is already accounted for");
        }
        else{
            destination.setContent(data);
            destination.setLeftChild(new BNode());
            destination.leftChild().setParent(destination);
            destination.setRightChild(new BNode());
            destination.rightChild().setParent(destination);
        }
    }

    
    public void remove(BNode r, Location key) throws InexistentKeyException { //Removes the pixel with the key location (input as parameter) from the binary search tree. If the key is not in the tree already throws exception. Rearranges tree to ensure it still has the properties of a binary search tree
        BNode destination = getNode(r,key);
        if (destination.isLeaf()){ //case: If key is not in tree
            throw new InexistentKeyException("This pixel is already not in the tree");
        }
        else{ //case: when key is in the tree and one of the children is a leaf, remove the key and rearrange
            if(destination.leftChild().isLeaf()){
                BNode otherChild = destination.rightChild();
                BNode parent = destination.parent();
                if (parent != null){
                    if(parent.getData().getLocus().compareTo(destination.getData().getLocus()) == -1){
                        parent.setRightChild(otherChild);
                        otherChild.setParent(parent);
                    }
                    else{
                        parent.setLeftChild(otherChild);
                        otherChild.setParent(parent);
                    }
                }
                else{
                    this.r = otherChild;
                    otherChild.setParent(null);
                }
            }
            else if(destination.rightChild().isLeaf()){
                BNode otherChild = destination.leftChild();
                BNode parent = destination.parent();
                if (parent != null){
                    if(parent.getData().getLocus().compareTo(destination.getData().getLocus()) == -1){
                        parent.setRightChild(otherChild);
                        otherChild.setParent(parent);
                    }
                    else{
                        parent.setLeftChild(otherChild);
                        otherChild.setParent(parent);
                    }
                }
                else{
                    this.r = otherChild;
                    otherChild.setParent(null);
                }
            }
            else{ //case: when key is in the tree and neither of the children are leaves, remove the key and rearrange using successor.
                BNode smallest = new BNode();
                try {
                    smallest = smallestNode(destination.rightChild());
                } catch (EmptyTreeException e) {
                    e.printStackTrace();
                }
                destination.setContent(smallest.getData());
                remove(smallest, smallest.getData().getLocus());
            }
        }
        
    }

    
    public Pel successor(BNode r, Location key) { //Returns the node with location that succedes the key input as parameter (the key doesn't need to be in the tree). If no successor returns null.
        try {
            if (largestNode(r).getData().getLocus().compareTo(key) <= 0){
                return null;
            }
            else{
                BNode successorNode = getNode(r, key);
                if(successorNode.isLeaf()){
                    successorNode = successorNode.parent();
                    while(successorNode.getData().getLocus().compareTo(key) <= 0){
                        successorNode = successorNode.parent();
                    }
                    return successorNode.getData();
                }
                else{
                    if(successorNode.rightChild().isLeaf()){
                        while(successorNode.getData().getLocus().compareTo(key) <= 0){
                            successorNode = successorNode.parent();
                        }
                    }
                    else{
                        successorNode = smallestNode(successorNode.rightChild());
                    }
                    return successorNode.getData();
                }
            }
        } 
        catch (EmptyTreeException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public Pel predecessor(BNode r, Location key) { //Returns the node with location that precedes the key input as parameter (the key doesn't need to be in the tree). If no predecessor returns null.
        try {
            if (smallestNode(r).getData().getLocus().compareTo(key) >= 0){
                return null;
            }
            else{
                BNode predecessorNode = getNode(r, key);
                if(predecessorNode.isLeaf()){
                    predecessorNode = predecessorNode.parent();
                    while(predecessorNode.getData().getLocus().compareTo(key) >= 0){
                        predecessorNode = predecessorNode.parent();
                    }
                    return predecessorNode.getData();
                }
                else{
                    if(predecessorNode.leftChild().isLeaf()){
                        while(predecessorNode.getData().getLocus().compareTo(key) >= 0){
                            predecessorNode = predecessorNode.parent();
                        }
                }
                else{
                    predecessorNode = largestNode(predecessorNode.leftChild());
                }
                return predecessorNode.getData();
                }
            } 
        }
        catch (EmptyTreeException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public Pel smallest(BNode r) throws EmptyTreeException { //Return the smallest pixel value in the binary search tree. If no values in tree throws exception.
        return smallestNode(r).getData();
    }

    
    public Pel largest(BNode r) throws EmptyTreeException { //Return the largest pixel value in the binary search tree. If no values in tree throws exception.
        return largestNode(r).getData();
    }
    
    private BNode getNode(BNode r, Location key) { //Returns node containing the pixel with location (key).
        if(r.isLeaf()){
            return r;
        }
        else{
                if (key.compareTo(r.getData().getLocus()) == 0){
                    return r;
                }
                else if(key.compareTo(r.getData().getLocus()) == -1){
                    return getNode(r.leftChild(),key);
                }
                else{
                    return getNode(r.rightChild(),key);
                }
        }
    }  

    private BNode smallestNode(BNode r) throws EmptyTreeException{ //Return the node containing smallest pixel value in the binary search tree. If no values in tree throws exception.
        if (r.isLeaf()){
            throw new EmptyTreeException("There are no pixels stored");
        }
        else{
            while(!r.isLeaf()){
                r = r.leftChild();
            }
            return r.parent();
        }
    }
    
    private BNode largestNode(BNode r) throws EmptyTreeException{ //Return the node containing largest pixel value in the binary search tree. If no values in tree throws exception.
        if (r.isLeaf()){
            throw new EmptyTreeException("There are no pixels stored");
        }
        else{
            while(!r.isLeaf()){
                r = r.rightChild();
            }
            return r.parent();
        }
    }
}
