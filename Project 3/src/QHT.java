public class QHT<K, V> {

    public class KVPair<K, V> {

    /*
    Generic key-value pair class
    */

        K k;
        V v;

        KVPair(K key, V val) {
            k = key;
            v = val;
        }

        public K key() {
            return k;
        }

        public V value() {
            return v;
        }
    }

  /*
    instance variables.
    DO NOT CHANGE, ADD OR REMOVE INSTANCE VARIABLES
  */

    KVPair[] htable;                                  //The Hash table which is an array of KVPairs
    int size;                                         //Number of elements in the hash table
    int initCap;                                      //Initial capacity of the hash table
    static final int DEFAULT_EXP = 2;                 //Default exponent if it's not specified
    final KVPair TOMBSTONE = new KVPair(null, null);  //The Tombstone to be used when deleting an element

    QHT() {
    /*
      ***TO-DO***
      Default constructor
      should initialize the hash table with default capacity
    */
        htable = new KVPair[4];
        size = 0;
        initCap = 4;


    }

    QHT(int exp) {
    /*
      ***TO-DO***
      Single-parameter constructor. The capacity of the hash table
      should be 2^exp. if exp < 2, use default exponent.
      initialize size and initCap accordingly
    */

        if (exp < 3) {
            htable = new KVPair[4];
            size = 0;
            initCap = 4;
        }

        else {

            initCap = 2;

            for (int i = 1; i < exp; i++) {
                initCap = initCap * 2;
            }

            htable = new KVPair[initCap];
            size = 0;

        }
    }

    public int size() {
    /*
      ***TO-DO***
      return the number of elements currently stored in the
      hash table. Shouldn't include TOMBSTONES
      Should run in O(1)
    */

        return size;
    }

    public int capacity() {
    /*
      ***TO-DO***
      return the capacity of the hash table
      Should run in O(1)
    */

        return htable.length;
    }

    public boolean isEmpty() {
    /*
      ***TO-DO***
      return true if hash table is empty,
      false otherwise
      Should run in O(1)
    */

        if (size == 0) {
            return true;
        }

        return false;

    }

    public double loadFactor() {
    /*
      ***TO-DO***
      return the load factor of this hash table.
      load factor is the ratio of size to capacity
      Should run in O(1). Note that the return type is double.
    */

        return (double)size / (double)htable.length;

    }

    private int h(K k) {
    /*
      The hash function. returns an integer for an arbitrary key
      Should run in O(1)
    */

        return (k.hashCode() + capacity()) % capacity() ;
    }

    private int p(K k, int i) {
    /*
      The probe function. returns an integer. i is
      the number of collisions seen so far for the key
      Should run in O(1)
    */

        return i/2 + (i*i)/2 + (i%2);
    }

    public void insert(K k, V v) {
    /*
      ***TO-DO***
      should insert the given key and value as a
      KVPair in the hash table.
      if load factor > 0.5, increase capacity by a factor of 2
    */

        KVPair newKV = new KVPair(k, v);

        if (size == htable.length) {
            throw new IllegalStateException();
        }

        if (!isEmpty()){
            if (findKey(k)) {
                return;
            }
        }

        if (get(h(k)) == null || get(h(k)).value() == null) { //null or tombstone

            htable[h(k)] = newKV;
            size++;
        }

        else {

            int i = 1;

            while (find(k) == null) {

                if (htable[(p(k, i) + h(k))%capacity()] == null || htable[(p(k, i) + h(k))%capacity()].value() == null) { //if null or tombstone

                    htable[(p(k, i) + h(k))%capacity()] = newKV;

                    break;
                }

                i++;
            }

            size++;
        }

        if (loadFactor() > 0.5) {
            resizeUp();
        }

    }

    private void resizeUp() {

        KVPair[] subhtable = htable.clone();

        htable = new KVPair[capacity() * 2];

        size = 0;

        for (int i = 0; i < subhtable.length; i++) {

            if (subhtable[i] != null && subhtable[i].value() != null) {

                if (isEmpty()) {

                    htable[h((K)subhtable[i].key())] = subhtable[i];
                    size++;
                }

                if (htable[h((K)subhtable[i].key())] == null) {

                    htable[h((K)subhtable[i].key())] = subhtable[i];
                    size++;
                }

                else {

                    int j = 1;

                    while (find((K)subhtable[i].key()) == null) {

                        if (htable[(p((K)subhtable[i].key(), j) + h((K)subhtable[i].key()))%capacity()] == null) { //if null

                            htable[(p((K)subhtable[i].key(), j) + h((K)subhtable[i].key()))%capacity()] = subhtable[i];

                            size++;

                            break;
                        }

                        j++;

                    }

                }

            }
        }

    }

    public V remove(K k) {

    /*
      ***TO-DO***
      if k is found in the hash table, remove KVPair
      and return the value. Otherwise, return null.
      if load factor < 0.25 then reduce capacity in half.
    */

        if (isEmpty()) {
            return null;
        }

        if (find(k) == null) {
            return null;
        }

        V byeValue = null;

        if (htable[h(k)].key() == k) {

            byeValue = (V)htable[h(k)].value();

            htable[h(k)] = TOMBSTONE;

            size--;
        }

        else {

            for(int i = 1; i < capacity(); i++) {

                if (htable[(p(k, i) + h(k))%capacity()].key() == k) {

                    byeValue = (V)htable[(p(k, i) + h(k))%capacity()].value();

                    htable[(p(k, i) + h(k))%capacity()] = TOMBSTONE;

                    size--;

                    break;
                }
            }

        }

        if (loadFactor() < 0.25 && capacity() != initCap) {

            resizeDown();
        }

        return byeValue;

    }

    private void resizeDown() {

        KVPair[] subhtable = htable.clone();

        htable = new KVPair[capacity()/2];

        size = 0;

        for (int i = 0; i < subhtable.length; i++) {

            if (subhtable[i] != null && subhtable[i].value() != null) {

                if (isEmpty()) {

                    htable[h((K)subhtable[i].key())] = subhtable[i];

                    size++;
                }

                if (htable[h((K)subhtable[i].key())] == null) {

                    htable[h((K)subhtable[i].key())] = subhtable[i];

                    size++;
                }

                else {

                    int j = 1;

                    while (find((K)subhtable[i].key()) == null) {

                        if (htable[(p((K)subhtable[i].key(), j) + h((K)subhtable[i].key()))%capacity()] == null) { //if null

                            htable[(p((K)subhtable[i].key(), j) + h((K)subhtable[i].key()))%capacity()] = subhtable[i];

                            size++;

                            break;
                        }

                        j++;
                    }
                }

            }
        }
    }

    public V find(K k) {
    /*
      ***TO-DO***
      if k is found in the hash table, return the value.
      Otherwise, return null.
    */

        if (isEmpty()) {
            return null;
        }

        if (htable[h(k)] != null && htable[h(k)].key() == k) {
            return (V)htable[h(k)].value();
        }

        for (int i = 1; i < capacity(); i++) {

            if (htable[(p(k, i) + h(k))%capacity()] != null && htable[(p(k, i) + h(k))%capacity()].key() == k) {
                return (V)htable[(p(k, i) + h(k))%capacity()].value();
            }
        }

        return null;

    }

    public boolean findKey(K k) {

        if (isEmpty()) {
            return false;
        }

        if (htable[h(k)] != null && htable[h(k)].key() == k) {
            return true;
        }

        for (int i = 1; i < capacity(); i++) {

            if(htable[(p(k, i) + h(k))%capacity()] != null && htable[(p(k, i) + h(k))%capacity()].key() == k) {
                return true;
            }
        }

        return false;
    }

    public KVPair get(int i) {
    /*
      return the KVPair at index i of the hash table
    */

        if (i >= capacity())
            return null;

        return htable[i];
    }

    public String toString() {
    /*
      return a string representation of the hash table.
    */

        String ret = "\n\n";

        for (int i = 0; i < capacity(); i++) {
            if (get(i) != null) {
                if (get(i).key() != null)
                    ret += i + "\t" + get(i).key() + "\t->\t" + get(i).value() + "\n";
                else
                    ret += i + "\tTOMBSTONE\n";
            }
            else {
                ret += i + "\tnull\n";
            }
        }

        return ret;
    }
}
