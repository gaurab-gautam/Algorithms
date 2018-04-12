/**
 *
 * @author Gaurab R. Gautam
 */

package heaps;

public class MinHeap<T>
{
    public int heap_size;
    Integer POSITIVE_INFINITY = Integer.MAX_VALUE;
    
    public MinHeap(int size)
    {
        this.heap_size = size;
    }
    
    public T[] buildHeap(T[] a)
    {
        for (int i = Math.floorDiv(a.length, 2) - 1; i >= 0; i--)
        {
            heapify(a, i);
        }
        
        return a;
    }
    
    private void swap(T[] a, int first, int second)
    {
        T temp = a[first];
        a[first] = a[second];
        a[second] = temp;
    }
    
    private void heapify(T[] a, int i)
    {
        int left = 2 * i + 1;
        int right = (2 * i + 1) + 1;
        int smallest = POSITIVE_INFINITY;
        
        if (left < heap_size && ((Integer)a[left]) <= (Integer)a[i])
        {
            smallest = left;
        }
        else
        {
            smallest = i;
        }
        
        if (right < heap_size && (Integer)a[right] <= (Integer)a[smallest])
        {
            smallest = right;
        }
        
        if (smallest != i)
        {
            swap(a, smallest, i);
            
            heapify(a, smallest);
        }
    }
    
    public void insert(T[] a, T val)
    {
        heap_size += 1;
        a[heap_size - 1] = (T)POSITIVE_INFINITY;
        decreaseKey(a, heap_size - 1, val);
    }
    
    private void decreaseKey(T[] a, int i, T key)
    {
        if ((Integer)key > (Integer)a[i])
        {
            System.err.println("Key: " + key + " greater than current value!");
            return;
        }
        
        a[i] = key;
        
        while (i > 0 && (Integer)a[i] < (Integer)a[Math.floorDiv(i + 1, 2) - 1])
        {
            swap(a, i, Math.floorDiv(i + 1, 2) - 1);
            i = Math.floorDiv(i + 1, 2) - 1;
        }
    }
    
    public T minimum(T[] a)
    {
        return a[0];
    }
    
    public T extractMin(T[] a)
    {
        if (heap_size <= 0)
        {
            System.err.println("Heap underflow!");
            return null;
        }
        
        T min = a[0];
        swap(a, 0, heap_size - 1);
        heap_size -= 1;
        
        heapify(a, 0);
        
        return min;
    }
    
    public T[] reverseSort(T[] A)
    {
        buildHeap(A);
        
        for (int i = 0; i < A.length; i++)
        {
            extractMin(A);
        }
        
        return A;
    }
}
