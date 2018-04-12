/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;


public class LinkedList<T> extends CircularLinkedList
{
    public LinkedList(int size)
    {
        super(size);
    }
    
    public LinkedList() {}
    
    public LinkedList<T> copyFrom(LinkedList<T> source)
    {
        this.clear();
        
        for (int i = 0; i < source.size(); i++)
        {
            // copy only non-null for efficiency
            if (source.get(i) != null)
                this.insert(source.get(i));
        }
        
        return this;
    }
}
