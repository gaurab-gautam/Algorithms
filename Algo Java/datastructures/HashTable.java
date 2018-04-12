/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;



public class HashTable<E>
{
    private final int SIZE = 5;
    private final LinkedList<E>[] table;
    
    public HashTable()
    {
        @SuppressWarnings("unchecked")
        final LinkedList<E>[] a = (LinkedList<E>[])java.lang.reflect.Array.newInstance(LinkedList.class, SIZE);
        
        // create initial chain
        for (int i = 0; i < this.SIZE; i++)
        {
            a[i] = (LinkedList<E>)new LinkedList<>();
        }
        
        this.table = a;
    }
    
    public LinkedList<E>[] getTable()
    {
        return this.table;
    }
    
    public int size()
    {
        return this.SIZE;
    }
    
    public void put(E obj)
    {
        int index = obj.hashCode() % this.SIZE;
        
        this.table[index].insert(obj);
    }
    
    public boolean get(E obj)
    {
        return this.table[hashfunction(obj)].search(obj);
    }
    
    public int getIndex(E obj)
    {
        return hashfunction(obj);
    }
    
    private int hashfunction(E obj)
    {
        return obj.hashCode() % this.SIZE;
    }
}
