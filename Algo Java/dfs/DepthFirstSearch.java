/**
 *
 * @author Gaurab R. Gautam
 */
package dfs;

import graphs.Graph;
import graphs.Vertex;


public class DepthFirstSearch 
{
    private static int time = 0;
    private static final StringBuilder printText = new StringBuilder();
    
    public static void dfs(Graph g, String source)
    {
        dfsVisit(g, source);    // first start from source
        
        // cover all the nodes that form a cross edge
        for (int i = 0; i < g.getSize(); i++)
        {
            if (g.getAdjacencyList()[i].isExplored() == Vertex.ExplorationState.UNEXPLORED)
            {
                dfsVisit(g, g.getAdjacencyList()[i].getName());
            }
        }
        
        System.out.println(printText.toString());
    }

    private static void dfsVisit(Graph g, String vertex)
    {
        System.out.println(vertex);
        Vertex parent = g.getAdjacencyList()[vertex.hashCode() % g.getSize()];
        time += 1;
        parent.setDiscoveryTime(time);
        parent.isExplored(Vertex.ExplorationState.EXPLORED);
        
        String txt = parent.getName() + " : discovered @ " + time + "; finished @ \u221E";
        
        
        // offset of indentation for printout
        int offset = 0;
        if (parent.getParent() ==  null)
        {
            offset = (4 * parent.getDiscoveryTime() + 35);
        }
        else 
        {
            offset = (4 * (g.getAdjacencyList()[parent.getParent().hashCode() % g.getSize()].getDiscoveryTime() + 1) + 35);
        }
        
        String format = "%" + offset + "s\n";
        printText.append(String.format(format, txt));
        
        for (String sink : g.getAdjacencyList()[vertex.hashCode() % g.getSize()].getEdges())
        {
            Vertex v = g.getAdjacencyList()[sink.hashCode()%g.getSize()];
            
            if (v.isExplored() == Vertex.ExplorationState.UNEXPLORED)
            {
                v.setParent(parent.getName());
                
                dfsVisit(g, v.getName());
            }
        }
        
        time += 1;
        parent.setFinishTime(time);
        
        format = "%" + (offset + 1) + "s\n";
        txt =  parent.getName() + " : discovered @ " + parent.getDiscoveryTime() + "; finished @ " + parent.getFinishTime();
        printText.append(String.format(format, txt));
    }
}
