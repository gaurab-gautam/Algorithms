/**
 *
 * @author Gaurab R. Gautam
 */

package heaps;


public class MaxHeap<T> 
{
    public int heap_size;
    Integer NEGATIVE_INFINITY = Integer.MIN_VALUE;
    
    public MaxHeap(int size)
    {
        this.heap_size = size;
    }
    
    private void swap(T[] A, int first, int second)
    {
        T temp = A[first];
        A[first] = A[second];
        A[second] = temp;
    }
    
    public void buildHeap(T[] A)
    {
        for (int i = Math.floorDiv(A.length, 2) - 1; i >= 0; i--)
        {
            heapify(A, i);
        }
    }
    
    public void heapify(T[] A, int i)
    {
        Integer left = (2 * i + 1);
        Integer right = (2 * i + 1) + 1;
        Integer biggest = NEGATIVE_INFINITY;
        
        if (left < heap_size && (Integer)A[left] >= (Integer)A[i])
        {
            biggest = left;
        }
        else
        {
            biggest = i;
        }
        
        if (right < heap_size && (Integer)A[right] >= (Integer)A[biggest])
        {
            biggest = right;
        }
        
        if (biggest != i)
        {
            swap(A, biggest, i);
            heapify(A, biggest);
        }
    }
    
    public void insert(T[] A, T val)
    {
        heap_size += 1;
        A[heap_size - 1] = (T)NEGATIVE_INFINITY;
        increaseKey(A, heap_size - 1, val);
    }
    
    private void increaseKey(T[] A, int i, T key)
    {
        if ((Integer)key < (Integer)A[i])
        {
            System.err.print(key + " less that current key!");
            return;
        }
        
        A[i] = key;
        Integer parent = Math.floorDiv(i + 1, 2) - 1;
        
        while (i > 0 && (Integer)A[i] >= (Integer)A[parent])
        {
            swap(A, i, parent);
            i = parent;
            parent = Math.floorDiv(i + 1, 2) - 1;
        }
    }
    
    public T extractMax(T[] A)
    {
        if (heap_size <= 0)
        {
            System.err.println("Heap underflow!");
            return null;
        }
        
        T max = A[0];
        swap(A, 0, heap_size - 1);
        heap_size -= 1;
        heapify(A, 0);
        
        return max;
    }
    
    public T[] sort(T[] A)
    {
        buildHeap(A);
        
        for (int i = A.length - 1; i > 0; i--)
        {
            swap(A, 0, heap_size - 1);
            heap_size -= 1;
            heapify(A, 0);
        }
        
        return A;
    }
}
