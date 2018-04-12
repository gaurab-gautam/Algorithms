/**
 *
 * @author Gaurab R. Gautam
 */
package algorithms;

import java.util.Arrays;
import java.util.Random;


public class LinearTimeSelection 
{
    public static int run(int[] A, int ithStatistics)
    {
        if (ithStatistics > A.length)
        {
            System.out.println(ithStatistics + "th order statistics > # elements"
                    + "[" + A.length + "]" + " in the array?");
            return -1;
        }
        
        return rSelect(A, 0, A.length - 1, ithStatistics);
    }
    
    private static int rSelect(int[] A, int start, int end, int i)
    {
        if (start >= end)
        {
            return A[i - 1];
        }
        
        Random r = new Random();
        int j = start + r.nextInt(end - start + 1);  // start <= j <= end
        j = partitioning(A, start, end, j);

        if ((j + 1) == i)
        {
            return A[j];
        }
        else if (i < (j + 1))
        {
            return rSelect(A, start, j - 1, i);
        }
        else
        {
            return rSelect(A, j + 1, end, i);
        }
    }
    
    private static int rSelect(int[] A, int n, int i)
    {
        if (n == 1)
        {
            return A[0];
        }
        
        Random r = new Random();
        int j = r.nextInt(n);  // 0 <= j < n
        j = partitioning(A, 0, n - 1, j);
        
        if ((j + 1) == i)
        {
            return A[j];
        }
        else if ((j + 1) > i)
        {
            int[] tmp = Arrays.copyOfRange(A, 0, j);
            return rSelect(tmp, tmp.length, i);
        }
        else
        {
            int[] tmp = Arrays.copyOfRange(A, j + 1, A.length);
            return rSelect(tmp, tmp.length, i - (j + 1));
        }
    }
    
    private static int partitioning(int[] A, int start, int end, int pivotIndex)
    {
        int i = start;
        
        swap(A, start, pivotIndex);
        pivotIndex = start;
        int pivot = A[pivotIndex];
        
        for (int j = start + 1; j <= end; j++)
        {
            if (A[j] <= pivot)
            {
                i += 1;
                swap(A, i, j);
            }
        }
        
        swap(A, pivotIndex, i);
        
        return i;
    }
    
    private static void swap(int[] A, int first, int second)
    {
        int temp = A[first];
        A[first] = A[second];
        A[second] = temp;
    }
}
