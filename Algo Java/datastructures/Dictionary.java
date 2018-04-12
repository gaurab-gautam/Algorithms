/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;

import java.util.Arrays;
import java.util.Comparator;


public class Dictionary<K, V>
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
    }
    
    private int SIZE = 6491;
    private Entry[] buckets;
    final private int[] primes = {
//                                  5, 11, 23, 47, 71, 101, 149, 173, 199, 281,
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
    
    public Dictionary()
    {
        this.buckets = (Entry[])java.lang.reflect.Array.newInstance(Entry.class, SIZE);
    }
    
    public int size()
    {
        return this.SIZE;
    }
    
    public void printSorted()
    {
        Entry[] tmp = copyNoneNull(this.buckets);
        Arrays.sort(tmp, new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2)
            {
                if (e1 == null)
                {
                    if (e2 == null)
                    { 
                        return 0;
                    }
                    else
                    {
                        return 1;
                    }
                }
                else if (e2 == null)
                {
                    return -1;
                }
                else
                {
                    return ((Integer)e1.key).compareTo((Integer)e2.key);
                }
            }
        });

        for (int i = 0; i < tmp.length; i++)
        {
            System.out.println(i + " : " + tmp[i]);
        }
        
        System.out.println();
    }
    
    public void print()
    {
        int index = 0;
        
        for (Entry bucket : this.buckets)
        {
            System.out.println(index++ + " : " + 
                    (bucket == null ? null : bucket.key + " -> " + bucket.value));
        }
        
        System.out.println();
    }
    
    public boolean contains(K key)
    {
        return get(key) == null;
    }
    
    private int getIndex(K key)
    {
        if (key == null)
            return -1;
        
        int index = hashfunction(key);
        int i = index;
        
        // linearly probe for the right key
        while ((i != (index - 1)) && 
                (this.buckets[i] != null && !this.buckets[i].key.equals(key)))
        {
            i += 1;

            if (i == this.buckets.length)
            {
                i = 0;
            }
        }

        if (this.buckets[i] != null && this.buckets[i].key.equals(key))
        {
              return i;
        }
        else
        {
              return -1;
        }
    }
    
    public V get(K key)
    {
        if (key == null)
            return null;
        
        int index = hashfunction(key);
        int i = index;
        
        // linearly probe the right key
        while ((i != (index - 1)) && 
                (this.buckets[i] == null || !this.buckets[i].key.equals(key)))
        {
            i += 1;

            if (i == this.buckets.length)
            {
                i = 0;
            }
        }

        if (this.buckets[i] != null && this.buckets[i].key.equals(key))
        {
              return (V)this.buckets[i].value;
        }
        else
        {
              return null;
        }
    }
    
    public V delete(K key)
    {
        int index = hashfunction(key);
        int i = index;
        
        // linearly probe the right key
        while ((i != (index - 1)) && 
               (this.buckets[i] == null || !this.buckets[i].key.equals(key)))
        {
            i += 1;

            if (i == this.buckets.length)
            {
                i = 0;
            }
        }
        
        index = i;
        if (this.buckets[i] != null && this.buckets[i].key.equals(key))
        {
            V val = (V)this.buckets[index].value;

            this.buckets[index] = null;
            this.numEntries -= 1;

            return val;
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
        
        // check if the bucket is occupied
        // if occupied do linear probing (or double hashing)
        // if same key found, just edit the value
        int i = getIndex(key);
        
        if (i != -1)    // duplicate entry, just edit
        {
            this.buckets[i].value = value;
        }
        else
        {
            index = linearProbing(index);

            this.buckets[index] = new Entry<>(key, value);
            this.numEntries += 1;
        }
        //print();
    }
    
    private int hashfunction(K key)
    {
        return (key.hashCode() % this.SIZE);
    }

    private int hashfunction1(K key)
    {
        return (key.hashCode() * key.hashCode() + (int)Math.pow(Math.sqrt((double)key.hashCode()), 2)) % this.SIZE;
    }
    
    private float getLoad()
    {
        //int size = getNumItems();
        //return (size + 1)/(float)this.SIZE;
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
        Entry[] tmp = Arrays.copyOf(this.buckets, this.buckets.length);
        this.buckets = (Entry[])java.lang.reflect.Array.newInstance(Entry.class, SIZE);
        copyBuckets(tmp);
    }
    
    private void copyBuckets(Entry[] source)
    {
        for (Entry e : source)
        {
            if (e != null)
            {
                put((K)e.key, (V)e.value);
            }
        }
    }
    
    private int linearProbing(int index)
    {
        int i = index;
        
        // linearly probe to find the empty bucket
        while (this.buckets[i] != null && (i != (index - 1)))
        {
            
            i += 1;

            if (i == this.buckets.length)
            {
                i = 0;
            }
        }
        
        return i;  // always finds an empty spot because buckets are never filled
    }
    
    private Entry[] copyNoneNull(Entry[] buckets)
    {
        Entry[] tmp = (Entry[])java.lang.reflect.Array.newInstance(Entry.class, SIZE);

        int index = 0;
        
        for (Entry bucket : buckets)
        {
            if (bucket != null)
            {
                tmp[index++] = bucket;
            }
        }
        
        return tmp;
    }
}
