import java.util.*;

public class Hashtable{

    // does this implementation need k and v, or is it just string?
    // if this implementation is just string for both key and value then replace all k and vs with string

    private HashNode[] buckets;
    public final double LOAD_THRESHOLD = 0.3; // 0.5, whatever
    private int entries = 0;


    // constructor
    public Hashtable(){
        buckets = new HashNode[9]; // does this need to start at some length to add in stuff?
        //System.out.println(buckets.size());
        //buckets.add(null);
        //System.out.println(buckets.get(0));
    }

    class HashNode{
        private String key;
        private String value;
        private HashNode next;

        // constructor

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }

        // getters and setters:

        public String getKey(){
            return key;
        }

        public String getValue(){
            return value;
        }

        public HashNode getNext(){
            return next;
        }

        public void setKey(String newKey){
            key = newKey;
        }

        public void setValue(String newValue){
            value = newValue;
        }

        public void setKey(HashNode newNext){
            next = newNext;
        }
    }

    public void put(String key, String value) throws Exception{
        // Adds the key/value pair into the Hashtable instance. If there is an existing key/value pair, the Hashtable instance replaces the stored value with the argument value


        // problem == duplicates
        // essentially have to find the thing to make sure there's no duplicates
        // if we find the key, just change the value
        // if the key is not there then just add in the key value pair
        HashNode head = buckets[getHash(key)];
        // head of the linked list - what are the two possible values for the head of the linked list
        // null and not null
        // if head is null
        if(head == null){
            // can do set
            buckets[getHash(key)] = new HashNode(key, value);
            //buckets.set(getHash(key), new HashNode(key, value));
        } else {
            // find if there is a key equal to the key we have
            // recycle from find function
            while(head != null){
                if(head.key.equals(key)){
                    head.value = value; // just changing its value
                    return; // we're done here - makes it more efficient - REASON we can return = not changing the number of entries so don't need to mess with threshold
                }
                head = head.next;
                // this is going to be slightly inefficient but
                // make more efficient!!! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            }
            // key is not found - we need to add it
            // want to add to the head of the linked list
            // create a new node

            HashNode node = new HashNode(key, value);
            node.next = buckets[getHash(key)];
            //node.next = buckets.get(getHash(key)); // cant do head because head is guaranteed to be null at this point
            // one last thing to do - if this is now the head, need to do a bucket set
            buckets[0] = node; //.set(0, node);
        }

        ++entries;
        // calculate lambda
        if(((entries * 1.0) / buckets.length) >= LOAD_THRESHOLD){
            int originalSize = buckets.length;
            buckets = new HashNode[originalSize*2]; // don't need to copy everything over because we are rehashing everything

//            for(int i=0; i<10; i++){ // multiplying the size by 2 each time??
//                buckets.add(null); // created a memory issue
//            }

            // could do a while loop, like while the above equation is like it is, but then will stop right when its the perf load, not with plenty space after - what's more efficient?

            // will it be a double for loop? like for the size of buckets and the size of each linked list?

            // remove node then add again, cycle through entire original size
            for(int i=0; i<originalSize; i++){
                HashNode node = buckets[i];
                //HashNode node = buckets.get(i); // this gets the head of the bucket at index i

                while(node != null){
                    remove(node.key);
                    put(node.key, node.value);
                    node = node.next;
                }
            }


            // need to remove then add again

            // need to increase the number of buckets
            // left up to us
            // rehash each item in the table <<<<<<<<<<<<<<
        }


    }

    public String get(String key){
        // Returns the value associated with the key which is passed as an argument; returns ​null​ if no key/value pair is contained by the Hashtable instance

        HashNode head = buckets[getHash(key)];
        //HashNode head = buckets.get(getHash(key)); // will give us the head of the linked list
        // does the node at that head have the key we want? keep looking until run out of nodes to look into
        // if head == null, return null (don't need to check that, put it in the loop), if not, check if its the key,
        while(head != null){
            if(head.key.equals(key)){
                return head.value;
            }
            head = head.next;
        }
        // gotten to end of the list without finding a hashnode with the key so just return null
        return null;
    }

    public boolean containsKey(String key){
        // Returns “true” if a key/value object pair (with the key matching the argument and any value)

        // sounds like it is essentially find except it returns boolean

        HashNode head = buckets[getHash(key)];
        //HashNode head = buckets.get(getHash(key)); // will give us the head of the linked list
        // does the node at that head have the key we want? keep looking until run out of nodes to look into
        // if head == null, return null (don't need to check that, put it in the loop), if not, check if its the key,
        while(head != null){
            if(head.key.equals(key)){
                return true;
            }
            head = head.next;
        }
        // gotten to end of the list without finding a hashnode with the key so return false
        return false;
    }

    public String remove(String key) throws Exception{
        // Removes the key/value pair from the Hashtable instance and returns the value associated with the key to the caller. Throws an Exception instance if the key is not present in the Hashtable instance.

        HashNode head = buckets[getHash(key)];
        //HashNode head = buckets.get(getHash(key)); // getHash is the index of head of the linked list of key value pairs

        // if key not found then throw exception <<<<<<

        // if the head is null then we're done
        if(head != null){
            if(head.key == key){
                buckets[getHash(key)] = head.next; // was 0 before, doesn't make sense because the index is the hash
                //buckets.set(0, head.next); // what am I trying to do here? << trying to set the head
                --entries;
                return head.value;
            } else {
                // need to find the one previous to the one with the key and link the prev to the next of the key
                HashNode prev = head;
                HashNode curr = null;
                while(prev.next != null){
                    curr = prev.next;
                    if(curr.key.equals(key)){
                        // this is the one we want to delete
                        prev.next = curr.next;
                        --entries;
                        return curr.value;
                    }
                    // this is where we stopped - keeps going
                    prev = prev.next;
                }

            }
            // if we have the head and the head is not null and the key is what we want then we can just set the head to the next node

        }
        // if we have made it this far then the key does not exist (head is null or have cycled through entire list) - throw exception
        throw new Exception();

    }

    private int getHash(String key){
        return Math.abs(key.hashCode() % buckets.length); // or something
    }


    // need to think about the collision policy - what to do in the case of multiple key value pairs with the same input


}
