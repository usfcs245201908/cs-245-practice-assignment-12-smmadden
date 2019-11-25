import java.util.*;

public class Hashtable <k, v> {

    // does this implementation need k and v, or is it just string?
    // if this implementation is just string for both key and value then replace all k and vs with string

    private ArrayList<HashNode> buckets;
    public final double LOAD_THRESHOLD = 0.5; // 0.3, whatever
    private int entries = 0;


    // constructor
    public Hashtable(){
        buckets = new ArrayList<>();
    }

    class HashNode{
        private k key;
        private v value;
        private HashNode next;

        // constructor

        public HashNode(k key, v value){
            this.key = key;
            this.value = value;
            next = null;
        }

        // getters and setters:

        public k getKey(){
            return key;
        }

        public v getValue(){
            return value;
        }

        public HashNode getNext(){
            return next;
        }

        public void setKey(k newKey){
            key = newKey;
        }

        public void setValue(v newValue){
            value = newValue;
        }

        public void setKey(HashNode newNext){
            next = newNext;
        }
    }

    public void put(String key, String value){
        // Adds the key/value pair into the Hashtable instance. If there is an existing key/value pair, the Hashtable instance replaces the stored value with the argument value

        // need to use the hashCode() function
    }

    public String get(String key){
        // Returns the value associated with the key which is passed as an argument; returns ​null​ if no key/value pair is contained by the Hashtable instance


        return "";
    }

    public v find(k key){
        // what if the key doesn't exist in the hashtable - not a problem, should just return a null
        // first need to find the bucket

        HashNode head = buckets.get(getHash(key)); // will give us the head of the linked list
        // does the node at that head have the key we want? keep looking until run out of nodes to look into
        // if head == null, return null (don't need to check that, put it in the loop), if not, check if its the key,
        while(head != null){
            if(head.key == key){
                return head.value;
            }
            head = head.next;
        }
        // gotten to end of the list without finding a hashnode with the key so just return null
        return null;

    }

    public boolean containsKey(String key){
        // Returns “true” if a key/value object pair (with the key matching the argument and any value)

        return false;
    }

    public String remove(String key){
        // Removes the key/value pair from the Hashtable instance and returns the value associated with the key to the caller. Throws an Exception instance if the key is not present in the Hashtable instance.

        // DELETED flags
        return "";
    }

    private int getHash(k key){
        return Math.abs(key.hashCode() % buckets.size()); // or something
    }


    // need to think about the collision policy - what to do in the case of multiple key value pairs with the same input


}
