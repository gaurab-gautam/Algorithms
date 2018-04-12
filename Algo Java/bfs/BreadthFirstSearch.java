/**
 *
 * @author Gaurab R. Gautam
 */
package bfs;

import graphs.Graph;
import graphs.Vertex;


public class BreadthFirstSearch 
{
    private static java.util.Queue<String> queue;
    
    public static void bfs(Graph g, String source)
    {
        Vertex parent = initializeSingleSource(g, source);
        queue = new java.util.LinkedList<>();
        queue.add(source);
        print(g, queue);
            
        while (!queue.isEmpty())
        {
            Vertex p = g.getAdjacencyList()[queue.remove().hashCode() % g.getSize()];
            for (String sink : p.getEdges())
            {
                Vertex v = g.getAdjacencyList()[sink.hashCode() % g.getSize()];
                
                if (v.isExplored() == Vertex.ExplorationState.UNEXPLORED)
                {
                    v.isExplored(Vertex.ExplorationState.EXPLORED);
                    v.setParent(p.getName());
                    v.setDistance(p.getDistance() + 1);
                    queue.add(v.getName());
                }
            }
            
            print(g, queue);
        }
    }

    private static Vertex initializeSingleSource(Graph g, String source) {
        Vertex vertex = g.getAdjacencyList()[(source.hashCode()% g.getSize())];
        
        vertex.setDistance(0);
        vertex.isExplored(Vertex.ExplorationState.EXPLORED);
        vertex.setParent(null);
        
        for (Vertex v : g.getAdjacencyList())
        {
            if (!v.equals(vertex))
            {
                v.setDistance(Integer.MAX_VALUE - 1);   // prevents overflow
                v.setParent(null);
                v.setDiscoveryTime(Integer.MAX_VALUE);
                v.setFinishTime(Integer.MAX_VALUE);
                v.isExplored(Vertex.ExplorationState.UNEXPLORED);
            }
        }
        
        return vertex;
    }

    private static void print(Graph g, java.util.Queue<String> q) 
    {
        System.out.println("Q: " + q);
        for (Vertex v : g.getAdjacencyList())
        {
            System.out.print("Node: " + v.getName());
            System.out.print("\tParent: " + 
                    (v.getParent() != null ? v.getParent() : "\u03D5"));
            System.out.print("\tIs explored?: " + String.format("%-10s", v.isExplored().toString()));
            System.out.println("\tDistance: " + 
                    (v.getDistance() != (Integer.MAX_VALUE - 1) ? v.getDistance() : "\u221E"));
        }
        
        System.out.println("\n\n");
    }
}
