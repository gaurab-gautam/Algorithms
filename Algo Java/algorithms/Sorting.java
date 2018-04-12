/**
 *
 * @author Gaurab R. Gautam
 */

package algorithms;

public class Sorting {
    private static int getRandomIndex(int min, int max) {
  
	return (int)Math.floor(Math.random() * (max - min + 1)) + min;
    }
    
    private static int[] swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        
        return A;
    }
    
    private static int partition(int[] A, int startIndex, int endIndex) {
        int i = startIndex + 1; 
        int pivot = A[startIndex];
        
        for (int j = i; j <= endIndex; j++) {
            if (A[j] < pivot) {
                swap(A, j, i);
                i++;
            }
        }
        
        swap (A, i - 1, startIndex);
        
        return (i - 1);
    }
    
    private static int[] insertionSort(int[] A)
    {
        for (int j = 1; j < A.length; j++)
        {
            int key = A[j];
            int i = j - 1;
            while (i >= 0 && A[i] > key)
            {
                A[i + 1] = A[i];
                i--;
            }
            
            A[i + 1] = key;
        }
        
        return A;
    }
    
    private static int[] quickSort(int[] A, int startIndex, int length) {
        if (startIndex < (length - 1)) 
        {
            //randomize pivot index
            int pivotIndex = getRandomIndex(startIndex, length - 1);
            
            swap(A, startIndex, pivotIndex);     // move pivot to the front of array
            pivotIndex = partition(A, startIndex, length - 1);

            quickSort(A, startIndex, pivotIndex);
            quickSort(A, pivotIndex + 1, length);
        }
        
        return A;
    }
    
    private static int[] mergeSort(int[] A){
        if (A.length == 1) {
            return A;
        }
        
        int[] leftArr = new int[((int)Math.floorDiv(A.length, 2))];
        int[] rightArr = new int[A.length - leftArr.length];
        
        System.arraycopy(A, 0, leftArr, 0, leftArr.length);
        System.arraycopy(A, leftArr.length, rightArr, 0, rightArr.length);
        
        leftArr = mergeSort(leftArr);
        rightArr = mergeSort(rightArr);
       
        return merge(leftArr, rightArr);
    }
    
    private static int[] merge(int[]A, int[]B){
        int i = 0;
        int j = 0;
        int[] C = new int[A.length + B.length];
        
        for (int k = 0; k < C.length; k++) {
            if ((j >= B.length) || (i < A.length) && (A[i] < B[j])) {
                C[k] = A[i];
                i++;
            }
            else if ((k < C.length) && (j < B.length)) {
                C[k] = B[j];
                j++;
            }
        }
        
        return C;
    }
}
