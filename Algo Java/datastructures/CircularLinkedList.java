/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;

/**
 *
 * @author Anonymous
 * @param <T>
 */
public class CircularLinkedList<T> 
{
    private Node<T> head = null;
    private int size = 0;
    
    public CircularLinkedList()
    {
        this.head = new Node();
        this.head.value = null;
    }
    
    public CircularLinkedList(int iniSize)
    {
        this.head = new Node();
        this.head.value = null;
        T val = null;
        
        for (int i = 0; i < iniSize; i++)
        {
            Node<T> node = new Node(null);
        
            if (size == 0)
            {
                this.head.next = node;
                this.head.previous = node;
                node.previous = head;
                node.next = head;
            }
            else
            {
                Node<T> first = this.head.next;
                this.head.next = node;
                node.previous = this.head;
                first.previous = node;
                node.next = first;
            }
            
            this.size += 1;
        }
        //System.out.println("size = " + size + " : " + this);
    }
    
    public int size()
    {
        return this.size;
    }
    
    public void clear()
    {
        for (int i = 0; i < this.size; i++)
        {
            delete(get(i));
        }
        
        this.head = new Node();
        this.size = 0;
    }
    
    public T get(int index)
    {
        int i = 0;
        Node iter = head.next;
        
        while (i != index && i < this.size)
        {
            iter = iter.next;
            i += 1;
        }
        
        return (T)iter.value;
    }
    
    public void set(int index, T val)
    {
        int i = 0;
        Node iter = head.next;
        
        while (i != index && i < this.size)
        {
            iter = iter.next;
            i += 1;
        }
        
        iter.value = val;
    }
    
    
    public boolean isEmpty()
    {
        return (this.size == 0);
    }
    
    public void insert(T val)
    {
        Node<T> node = new Node(val);
        
        if (this.isEmpty())
        {
            this.head.next = node;
            this.head.previous = node;
            node.previous = head;
            node.next = head;
        }
        else
        {
            Node<T> first = this.head.next;
            this.head.next = node;
            node.previous = this.head;
            first.previous = node;
            node.next = first;
        }
            
        this.size += 1;
    }
    
    public void insert(T val, int index)
    {
        int i = 0;
        Node iter = head.next;
        
        while (i != index && i < this.size)
        {
            iter = iter.next;
            i += 1;
        }
        
        iter.value = val;
    }
    
    public boolean search (T val)
    {
        return (find(val) != null);
    }
    
    private Node<T> find (T val)
    {
        Node<T> iter = this.head.next;
        
        while (iter != this.head && !iter.value.equals(val))
        {
            iter = iter.next;
        }
        
        if (iter == this.head)
        {
            //System.out.println(val + " not found!");
            iter = null;
        }
        
        return iter;
    }
    
    public boolean delete(T val)
    {
        Node<T> node = find(val);
        
        if (node != null)
        {
            Node<T> parent = node.previous;
            Node<T> child = node.next;
            
            parent.next = node.next;
            child.previous = node.previous;
            
            node = null;
            this.size -= 1;
        }
        
        return false;
    }
    
    public void print()
    {
        System.out.println(toString());
    }
    
    @Override
    public String toString()
    {
       StringBuilder str = new StringBuilder();
       
        Node<T> iter = this.head.next;
        str.append("[");
        
        while (iter != this.head)
        {
            str.append(iter.value).append(iter.next != head ? ", " : "");
            iter =  iter.next;
        }
        
        str.append("]");
        
        return str.toString();
    }
}
