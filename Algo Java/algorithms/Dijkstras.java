/**
 *
 * @author Gaurab R. Gautam
 */
package algorithms;

import graphs.Graph;
import graphs.Vertex;
import java.util.Arrays;

public class Dijkstras 
{
    private static class PriorityQueue
    {
        static int heap_size;
        
        static Vertex[] buildHeap(Vertex[] graph)
        {
            heap_size = graph.length;
            
            for (int i = Math.floorDiv(heap_size, 2) - 1; i >= 0; i--)
            {
                heapify(graph, i);
            }
            
            return graph;
        }

        static private void swap(Vertex[] v, int i, int j)
        {
            Vertex temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
        
        static private void heapify(Vertex[] v, int i) 
        {
            int l = (2 * i + 1);
            int r = (2 * i + 1) + 1;
            int smallest = Integer.MAX_VALUE;
            
            if (l < heap_size && v[l].getDistance() < v[i].getDistance())
            {
                smallest = l;
            }
            else
            {
                smallest = i;
            }
            
            if (r < heap_size && v[r].getDistance() < v[i].getDistance())
            {
                smallest = r;
            }
            
            if (smallest != i)
            {
                swap(v, smallest, i);
                heapify(v, smallest);
            }
        }
        
        static Vertex extractMin(Vertex[] v)
        {
            Vertex min = v[0];
            
            swap(v, 0, heap_size - 1);
            heap_size -= 1;

            return min;
        }
        
        static boolean isEmpty()
        {
            return heap_size == 0;
        }
        
        static void decreaseKey(Vertex[] v, int i, int dist)
        {
            if (dist > v[i].getDistance())
            {
                System.err.println(v[i] + ":  Distatnce: " + dist + " greater than current value " + v[i].getDistance() + "!");
                return;
            }
            
            v[i].setDistance(dist);
            
            while (i > 0 && (v[i].getDistance() < v[Math.floorDiv(i + 1, 2) - 1].getDistance()))
            {
                int j = Math.floorDiv(i + 1, 2) - 1;
                swap(v, i, j);
                
                i = j;
            }
        }
        
        protected static int heapGetIndex(Vertex[] A, Vertex v) {
            for (int i = 0; i < A.length; i++)
            {
                if (A[i].equals(v))
                {
                    return i;
                }
            }
            
            return -1;
        }
    }
    
    private static Vertex[] g;
    private static int size;
    
    public static void run(Graph g1, String source)
    {
        g = g1.getAdjacencyList();
        size = g1.getSize();
        
        //initialize single source
        int rootIndex = (source.hashCode()%size);
        g[rootIndex].setDistance(0);
        g[rootIndex].path = source;
        
        dijkstras();
    }

    private static void dijkstras() {
        Vertex[] queue = PriorityQueue.buildHeap(Arrays.copyOf(g, g.length - 3));   // disregard empty buckets
        int iteration = 0;
        
        //printHeap(queue);
        
        while (!PriorityQueue.isEmpty())
        {
            Vertex u = PriorityQueue.extractMin(queue);
            //System.out.println(u + " : " + u.getEdges());
            //printHeap(queue);
            print(g, iteration, u);
            
            for (String edge : u.getEdges())
            {
                int i = edge.hashCode() % size;
                Vertex v = g[i];
                
                //System.out.println(i + " => " + v.getName());
                
                int w = u.getWeights().get(v.getName());
                
                if (v.getDistance() > (u.getDistance() + w))
                {
                    v.setParent(u.getName());
                    PriorityQueue.decreaseKey(queue, PriorityQueue.heapGetIndex(queue, v), u.getDistance() + w);
                    v.path = (u.path + "->" + v.getName());
                    
                    //printHeap(queue);
                }
            }
            
            iteration += 1;
            
        }
    }
    
    private static void print(Vertex[] g, int iteration, Vertex u) 
    {
        System.out.println("Iteration #: " + iteration + "\t" + u);
        System.out.println("------------------------------------");
            
        for (Vertex v : g)
        {
            System.out.print(String.format("%-14s", "Node: " + 
                    (v.getName() != null ? v.getName() : "\u03D5")));
            System.out.print(String.format("%-14s", "Parent: " + 
                    (v.getParent() != null ? v.getParent() : "\u03D5")));
            System.out.print(String.format("%-22s", "Distance: " + 
                    (v.getDistance() != Vertex.INFINITY ? v.getDistance() : "\u221E")));
            System.out.println(v.path != null ? v.path : "\u03D5");
        }
        
        System.out.println("\n");
    }
    
    private static void printHeap(Vertex[] q) 
    {
        System.out.println("----------------HEAP----------------------");
        
        for (int i = 0; i < PriorityQueue.heap_size; i++)
        {
            Vertex v = q[i];
            System.out.print("Node: " + v.getName());
            System.out.print("\tParent: " + 
                    (v.getParent() != null ? v.getParent() : "\u03D5"));
            System.out.println("\tDistance: " + 
                    (v.getDistance() != Vertex.INFINITY ? v.getDistance() : "\u221E"));
        }
        
        System.out.println("---------------END HEAP-------------------\n");
    }
}
