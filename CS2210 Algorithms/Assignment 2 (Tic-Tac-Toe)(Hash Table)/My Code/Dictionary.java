/*
 * Title: Dictionary
 * Author: Ramtin sirjani
 * Version 1.0
 * Date: October 13th 2022
 * This class stores Nodes containing records for super tic-tac-toe in a hash table with separate chaining implementation. 
 */
public class Dictionary implements DictionaryADT{
    private Node[] hash_table; //An array of Nodes representing the hash table.
    private int size; //The size of the hash table.
    private int num_records; //How many records are stored in the hash table.

    public Dictionary(int size){ //Constructor.
        hash_table = new Node[size]; //Create the array of nodes. Size of array input as parameter.
        this.size = size;
        for(int i=0; i<size;i++){ //Initialize the array with empty Nodes.
            Node initialize_table = new Node();
            hash_table[i] = initialize_table;
        }
    }

    public int put(Record rec){ //Insert a record into the hash table.
        Node record = new Node(rec); //Create a node storing the record to be put.
        String key = rec.getKey(); //Acquire the key of the record.
        int location = hash_function(key); //Apply the hash function to determine where the record will be stored. (see below, hash function in private method)
        Node start = hash_table[location]; //Create a reference to the location in the hash table.
        if(in_table(start, key)){ //If record already in table, throw exception. (see below, in table private method))
            throw new DuplicatedKeyException("This record is already in the hash table.");
        }
        else{ //Insert the record, If there is a collision return 1, otherwise return 0. Increment num_records.
            int collisions = 0;
            while(start.getNext() != null){
                start = start.getNext();
                collisions = 1;
            }
            start.setNext(record);
            num_records += 1;
            return collisions;
        }
    }

    public void remove(String key){ //Remove a record from the table.
        int location = hash_function(key); //Apply the hash function to determine where the record is.
        Node start = hash_table[location]; //Create a reference to the location in the hash table.
        if(in_table(start, key)){ //If record in table, remove it. Decrement num_records.
            while (!start.getNext().getData().getKey().equals(key)){
                start = start.getNext();
            }
            if (start.getNext().getNext() != null){
                start.setNext(start.getNext().getNext());
                num_records -= 1;
            }
            else{
                start.setNext(null);
                num_records -= 1;
            }
        }
        else{ //Otherwise throw an exception.
            throw new InexistentKeyException("This record was not found in the hash table");
        }
    }

    public Record get(String key){ //Get a record that is stored in the hash table.
        int location = hash_function(key); //Apply the hash function to determine where the record is.
        Node start = hash_table[location]; //Create a reference to the location in the hash table.
        if(in_table(start, key)){ //If record in table, return it.
            start = start.getNext();
            while (!start.getData().getKey().equals(key)){
                start = start.getNext();
            }
            return start.getData();
        }
        else{ //Otherwise return null.
            return null;
        }
    }

    
    public int numRecords(){ //Return the number of records being stored in the hash table.
        return num_records;
    }

    private boolean in_table(Node start, String key){ //Check if record is in hash table.
        if(start.getNext() == null){
            return false;
        }
        else{
            start = start.getNext();
        }
        if (start.getData().getKey().equals(key)){
            return true;
        }
        else{
            return in_table(start, key);
        }
    }

    private int hash_function(String key){ //Hash function used to determine location of record based on key.
        int location = (int)(key.charAt(0))*33;
        for(int i=1; i < key.length()-1; i++){
            location = (location += (int)(key.charAt(i)))%this.size;
            location = location*33;
        }
        location = (location += (int)(key.charAt(key.length()-1)))%this.size;
        return location;
    }
}
