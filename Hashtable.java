public class Hashtable{
    private HashNode[] buckets;
    public final double LOAD_THRESHOLD = 0.3; // could also be 0.5
    private int entries = 0;

    // constructor
    public Hashtable(){
        buckets = new HashNode[10];
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

        public void setNext(HashNode newNext){
            next = newNext;
        }
    }

    public void put(String key, String value){
        // adds the key/value pair into the Hashtable instance.

        HashNode head = buckets[getHash(key)];
        if(head == null){
            // if the head is null, can just set the head to a new node
            buckets[getHash(key)] = new HashNode(key, value);
        } else {
            // need to find if there is a key equal to the key we have
            while(head != null){
                if(head.key.equals(key)){
                    head.value = value; // just changing its value
                    return; // we're done (more efficient to return here)
                }
                head = head.next;
            }
            // end of loop, so key is not found - we need to add it
            // add to the head of the linked list
            HashNode node = new HashNode(key, value);
            node.next = buckets[getHash(key)];
            buckets[getHash(key)] = node;
        }
        ++entries;

        // calculate lambda
        if(((entries * 1.0) / buckets.length) >= LOAD_THRESHOLD){
            HashNode[] old_buckets = buckets;
            buckets = new HashNode[old_buckets.length*2]; // don't need to copy everything over because we are rehashing everything

            // remove node then add again, cycle through entire original array and rehash into new array
            for(int i=0; i<old_buckets.length; i++){
                HashNode node = old_buckets[i]; // node is from the old buckets array, need to place into the updated buckets array
                while(node != null){
                    put(node.key, node.value);
                    node = node.next;
                }
            }
        }


    }

    public String get(String key){
        // returns the value associated with the key
        HashNode head = buckets[getHash(key)];
        // loop through linked list to find the key
        while(head != null){
            if(head.key.equals(key)){
                return head.value;
            }
            head = head.next;
        }
        // gotten to end of the list without finding a hash node with the key, so returns null
        return null;
    }

    public boolean containsKey(String key){
        // returns “true” if there is a key/value object pair
        HashNode head = buckets[getHash(key)];
        // loops through linked list to find the key
        while(head != null){
            if(head.key.equals(key)){
                return true;
            }
            head = head.next;
        }
        // gotten to end of the list without finding a hash node with the key, so returns false
        return false;
    }

    public String remove(String key) throws Exception{
        // removes the key/value pair from the Hashtable
        HashNode head = buckets[getHash(key)];
        if(head != null){
            if(head.key.equals(key)){ // special case if we are removing the head
                buckets[getHash(key)] = head.next;
                --entries;
                return head.value;
            } else {
                // removing from a linked list - need to find the previous node
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
                    prev = prev.next;
                }

            }

        }
        // if we have made it this far then the key does not exist (head is null or have cycled through entire list) - throw exception
        throw new Exception();
    }

    private int getHash(String key){
        return Math.abs(key.hashCode() % buckets.length);
    }


}
