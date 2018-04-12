/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;


public class DoublyLinkedList<T>
{
    private class Data<T>
    {
        T value;
        Data<T> prev;
        Data<T> next;
        
        Data (T val)
        {
            this.value = val;
        }
    }
    
    private Data<T> head;
    private int size;
    
    public void insert(T val)
    {
        Data<T> data = new Data(val);
        
        if (this.isEmpty())
        {
            this.head = data;
        }
        else
        {
            Data<T> temp = this.head;
            this.head = data;
            data.next = temp;
            temp.prev = data;
        }
        
        size += 1;
    }
    
    public boolean delete (T val)
    {
        Data<T> data = find(val);
        
        if (data != null)
        {
            Data<T> parent = data.prev;
            Data<T> child = data.next;
           
            if (parent != null)
            {
                parent.next = child;
            }
            else
            {
                head = child;
            }
            
            if (child != null)
            {
                child.prev = parent;
            }
            
            data = null;
            size -= 1;
            
            return true;
        }
        else
        {
            System.out.println("Delete unsuccessful: " + val + " not found!");
            
            return false;
        }
    }
    
    public boolean search(T val)
    {
        return (find(val) != null);
    }
    
    private Data<T> find(T val)
    {
        for (Data<T> iter = head; iter != null; iter = iter.next)
        {
            if (iter.value.equals(val))
                return iter;
        }
        
        return null;
    }
    
    public boolean isEmpty()
    {
        return (this.size == 0);
    }
    
    public void print()
    {
        System.out.print("[");
        
        for (Data<T> iter = this.head; iter != null; iter = iter.next)
        {
            System.out.print(iter.value + ((iter.next != null) ? ", " : ""));
        }
        
        System.out.println("]");
    }
}
