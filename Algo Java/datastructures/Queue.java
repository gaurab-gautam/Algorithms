/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;


public class Queue<T>
{
    private final int MAX_SIZE = 5;
    private final T[] values;
    private int size = 0;
    private int head = -1;
    private int tail = 0;
    private final int INFINITY = Integer.MAX_VALUE;
    
    public Queue()
    {
        this.values = (T[])java.lang.reflect.Array.newInstance(Object.class, MAX_SIZE);
    }
    
    public boolean isEmpty()
    {
        return (size == 0);
    }
    
    public void enqueue(T val) throws Exception
    {
        if (size == MAX_SIZE)
        {
            throw new Exception("Size limit exceeded; can't enqueue " + val);
        }
        else if (head == -1)
        {
            head = 0;
        }        
        
        values[tail] = val;
        size += 1;
        tail += 1;
        
        if (tail == MAX_SIZE)
        {
            tail = 0;
        }
    }
    
    public T dequeue() throws Exception
    {
        if (size == 0)
        {
            throw new Exception("Queue is empty! Can't dequeue.");
        }
        
        T val = values[head];
        size -= 1;
        head += 1;
        
        if (head == MAX_SIZE)
        {
            head = 0;
        }
        
        return val;
    }
    
    public void print()
    {
        for (int i = 0; i < MAX_SIZE; i++)
        {
            System.out.print(
                              ((size == 0) ? "*" : 
                               ( ((head <= tail) && (i >= head) && (i < tail )) || 
                                 ((head >= tail) && ((i >= head) || (i < tail ))) )
                                    ? values[i] : "*") + " ");
        }
        
        System.out.println();
    }
}
