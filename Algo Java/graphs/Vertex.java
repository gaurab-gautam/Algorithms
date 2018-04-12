/**
 *
 * @author Gaurab R. Gautam
 */
package graphs;

import java.util.List;
import java.util.Map;


public class Vertex 
{
    public static final int INFINITY = 1000000;
    
    public enum ExplorationState 
    {
        EXPLORED("explored"), 
        UNEXPLORED("unexplored");
        
        private final String name;
        
        private ExplorationState(String state)
        {
            this.name = state;
        }
    
        @Override
        public String toString()
        {
            return name;
        }
    };
    
    private String name = null;
    private int distance = INFINITY;
    private ExplorationState isExplored = ExplorationState.UNEXPLORED;
    private String parent = null;
    private int discoveryTime = Integer.MAX_VALUE - 1;
    private int finishTime = Integer.MAX_VALUE - 1;
    private List<String> edges;
    private List<String> reversedEdges;
    private Map<String, Integer> weights;
    
    public String path;
    
    
    
    // Constructor
    public Vertex ()
    {
        this.edges = new java.util.LinkedList();
        this.reversedEdges = new java.util.LinkedList();
        this.weights = new java.util.HashMap();
    }
    
    // Copy Constructor
    public Vertex (String name)
    {
        this.name = name;
        this.edges = new java.util.LinkedList();
        this.reversedEdges = new java.util.LinkedList();
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
    
    // Getters
    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public ExplorationState isExplored() {
        return isExplored;
    }

    public Map<String, Integer> getWeights() {
        return weights;
    }

    public String getParent() {
        return parent;
    }
    
    public int getDiscoveryTime() {
        return discoveryTime;
    }

    public int getFinishTime() {
        return finishTime;
    }
    
    public List<String> getEdges() {
        return edges;
    }
    
    public List<String> getReveresdEdges() {
        return reversedEdges;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void isExplored(ExplorationState state) {
        this.isExplored = state;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    
    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
