/**
 *
 * @author Gaurab R. Gautam
 */
package algorithms;

import heaps.MaxHeap;
import heaps.MinHeap;

public class HeapSort
{
    public Integer[] sort(Integer[] a)
    {
        MaxHeap<Integer> maxHeap = new MaxHeap(a.length);
        
        return maxHeap.sort(a);
    }
    
    public Integer[] reverseSort(Integer[] a)
    {
        MinHeap<Integer> minHeap = new MinHeap(a.length);
        
        return minHeap.reverseSort(a);
    }
}
