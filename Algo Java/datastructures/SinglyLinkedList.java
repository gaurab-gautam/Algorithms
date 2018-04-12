/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;



// Singly Linked List
public class SinglyLinkedList
{
    private final int INFINITY = Integer.MAX_VALUE;
    
    private class Data
    {
        int data;
        Data next;
        
        Data (int val)
        {
            this.data = val;
        }
        
        Data ()
        {
            // used by head pointer
            data = +-INFINITY;
        }
    }
    
    private Data head = null;
    public int size = 0;
    
    public SinglyLinkedList()
    {
        this.head = new Data();
    }
    
    public void insert(int val)
    {
        Data data = new Data(val);
        
        if (this.head.next != null)
        {
            data.next = this.head.next;
        }
        
        this.head.next = data;
        size += 1;
    }
    
    public boolean delete(int val)
    {
        Data data = find(val);
        
        if (data != null)
        {
            if (data == head)
            {
                Data temp = this.head.next.next;
                this.head.next = null;
                this.head.next = temp;
            }
            else    // parent pointer would be returned by search
            {
                Data temp = data.next;
                data.next = temp.next;
                temp = null;
            }
            
            size -= 1;
            
            return true;
        }
        else
        {
            System.out.println(val + " not found!");
            
            return false;
        }
    }
    
    /**
     * 
     * @param val       value to be searched
     * @return          returns true if value is found; returns false otherwise
     */
    public boolean search(int val)
    {
        return (find(val) != null);
    }
    
    /**
     * 
     * @param val       value to be searched
     * @return          returns node if value is found, otherwise returns null
     *                  Data type return is either first node for list with a
     *                  single element, otherwise returns parent node of node with
     *                  searched value
     */
    private Data find(int val)
    {
        for (Data iter = head; iter != null; iter = iter.next)
        {
            if (iter == head && iter.data == val)
            {
                return head;
            }
            else if (iter.next == null)
            {
                return null;
            }
            else if (iter.next.data == val)
            {
                return iter;
            }
        }
        
        return null;
    }
    
    public void print()
    {
        System.out.print("[");
        
        for (Data iter = this.head.next; iter != null; iter = iter.next)
        {
            System.out.print(iter.data + (iter.next != null ? ", " : ""));
        }
        
        System.out.println("]");
    }
}