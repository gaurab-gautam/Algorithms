/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;

import java.util.Arrays;

/**
 * Implements chaining
 * 
 */
public class Map<K, V>
{
    // class to store dictionary key value informaion
    class Entry<K, V>
    {
        K key;
        V value;
        
        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public String toString()
        {
            return (this.key + " -> " + this.value);
        }
    }
    
    private int SIZE = 3;
    private LinkedList<Entry>[] buckets;
    final private int[] primes = {
                                  5, 11, 23, 47, 71, 101, 149, 173, 199, 281,
//                                  499, 1009, 1499, 1999, 2503, 3001, 3499, 
//                                  4001, 4507, 4999, 5501, 5501, 6007, 6491, 
//                                  7001, 7499, 7919, 
                                  20011, 
//                                  30011, 
                                  40009, 
//                                  49999, 59999, 70001, 
                                  79999, 
//                                  90001, 100003, 104529,105211, 106427, 110917, 
                                  125243, 
//                                  134153, 
                                  137341, 290623, 400009,7690843, 14000071
                                 };
    
    private int primeIndex = 0;
    public int numEntries = 0;
    //private int numFilledBuckets = 0;
    
    // constructor
    public Map()
    {
        this.buckets = (LinkedList<Entry>[])java.lang.reflect.Array.newInstance(LinkedList.class, SIZE);
        
        for (int i = 0; i < SIZE; i++)
        {
            this.buckets[i] = new LinkedList<>();
        }
    }
    
    public int size()
    {
        return this.SIZE;
    }
    
    public void print()
    {
        int index = 0;
        
        for (LinkedList<Entry> bucket : this.buckets)
        {
            System.out.println(index++ + " : "  + bucket);
        }
        
        System.out.println();
    }
    
    public V get(K key)
    {
        if (key == null)
            return null;
        
        int index = hashfunction(key);
        
        if (this.buckets[index].isEmpty())
            return null;
        
        // get the value from the chain
        for (int j = 0; j < this.buckets[index].size(); j++)
        {
            Entry e = (Entry)this.buckets[index].get(j);
            
            if (e.key.equals(key))
            {
                return (V)e.value;
            }
        }
        
        return null;
    }
    
    private Entry<K,V> getEntry(K key)
    {
        if (key == null)
            return null;
        
        int index = hashfunction(key);
        
        if (this.buckets[index].isEmpty())
            return null;
        
        // get entry from the chain
        for (int j = 0; j < this.buckets[index].size(); j++)
        {
            Entry e = (Entry)this.buckets[index].get(j);
            
            if (e.key.equals(key))
            {
                return e;
            }
        }
        
        // not found
        return null;
    }
    
    public String delete(K key)
    {
        if (key == null)
            return null;
        
        int index = hashfunction(key);
        Entry<K,V> e = getEntry(key);
        
        if (e != null)   // found
        {
            this.buckets[index].delete(e);
            this.numEntries -= 1;

//            if (this.buckets[index].isEmpty())
//            {
//                this.numFilledBuckets -= 1;
//            }
            
            return "(" + key + ", " + (V)e.value + ")";
        }
        else
        {
            return null;
        }
    }
    
    public void put(K key, V value)
    {
        //System.out.println("inserting " + key + ":");
        
        // increase bucket size if load(a) is .75 or more
        if (this.getLoad() >= 0.75)
        {
            this.SIZE = getNewSize();
            increaseBucketSize();
        }        
        
        int index = hashfunction(key);
        Entry<K,V> entry = getEntry(key);
        
        if (entry == null)  //new entry
        {
            // add an entry to the chain of hashed index
            Entry e = new Entry<>(key, value);
            
//            if (this.buckets[index].isEmpty())
//            {
//                this.numFilledBuckets += 1;
//            }
            
            this.buckets[index].insert(e);
            this.numEntries += 1;
        }
        else    //edit
        {
            entry.value = value;
        }
        //print();
    }
    
    private int hashfunction(K key)
    {
        return (key.hashCode() % this.SIZE);
    }
    
    private float getLoad()
    {
        return (this.numEntries + 1)/(float)this.SIZE;
    }
    
    private int getNewSize()
    {
        this.primeIndex += 1;
        
        return this.primes[primeIndex];
    }
    
    private void increaseBucketSize()
    {
        //System.out.println("increasing bucket size ...");
        this.numEntries = 0;
        LinkedList<Entry>[] tmp = Arrays.copyOf(this.buckets, this.buckets.length);
        this.buckets = (LinkedList<Entry>[])java.lang.reflect.Array.newInstance(LinkedList.class, SIZE);
        
        for (int i = 0; i < SIZE; i++)
        {
            this.buckets[i] = new LinkedList<>();
        }
        
        copyBuckets(tmp);
    }
    
    private void copyBuckets(LinkedList<Entry>[] source)
    {
        for (LinkedList<Entry> entries : source)
        {
            if (!entries.isEmpty())
            {
                for (int i = 0; i < entries.size(); i++)
                {
                    Entry e = (Entry)entries.get(i);
                    put((K)e.key, (V)e.value);
                }
            }
        }
    }
}
