/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;


public class Stack<T>
{
    private int size = 0;
    private final int MAX_SIZE = 5;
    private int top = -1;
    private final T[] values;
    
    public Stack()
    {
        values = (T[])java.lang.reflect.Array.newInstance(Object.class, MAX_SIZE);
    }
    
    public boolean isEmpty()
    {
        return (size == 0);
    }
    
    public void push(T val) throws Exception
    {
        if (size == MAX_SIZE)
        {
            throw new Exception("Size limit exceeded; can't push " + val);
        }
        
        size += 1;
        top += 1;
        values[top] = val;
    }
    
    public T pop() throws Exception
    {
        if (isEmpty())
        {
            throw new Exception("Stack is Empty; can't pop");
        }
        
        top -= 1;
        size -= 1;
        
        return values[top + 1];
    }
    
    public void print()
    {
        if (size == 0)
        {
            System.out.println("{empty}");
            return;
        }
        
        for (int i = 0; i < size; i++)
        {
            System.out.print(values[i] + " ");
        }
        
        System.out.println();
    }
}
