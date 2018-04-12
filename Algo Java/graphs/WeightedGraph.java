/**
 *
 * @author Gaurab R. Gautam
 */

package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WeightedGraph extends Graph
{
    public WeightedGraph(int size)
    {
        super(size);
    }
    
    @Override
    public void loadGraphFromFile(String filename)
    {
        BufferedReader reader = null;
        
        try 
        {
            reader = new BufferedReader(new FileReader(filename));
            String line = null;
            
            try 
            {
                while ((line = reader.readLine()) != null)
                {
                    // ignore empty lines
                    if (line.trim().isEmpty())
                    {
                        continue;
                    }
                    
                    String[] tokens = line.split(" |\t");
                    String vertex = tokens[0].trim();
                    int nodeIndex = (vertex.hashCode() % this.SIZE);
                    adjList[nodeIndex].setName(vertex);
                    
                    //System.out.println(vertex + " : " + vertex.hashCode() + " : " + nodeIndex);
                    
                    for (int i = 1; i < tokens.length; i++)
                    {
                        String edge = tokens[i].trim().substring(0, tokens[i].trim().indexOf(","));
                        int weight = Integer.parseInt(tokens[i].trim().substring(tokens[i].trim().indexOf(",") + 1));
                        int reversedIndex = (edge.hashCode()) % this.SIZE;
                        
                        adjList[nodeIndex].getEdges().add(edge);
                        adjList[nodeIndex].getWeights().put(edge, weight);
                        adjList[reversedIndex].getReveresdEdges().add(vertex);
                    }
                }
            } 
            catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
            }
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            try 
            {
                if (reader != null)
                {
                    reader.close();
                }
            } 
            catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @Override
    public void print()
    {
        for (Vertex v : this.adjList)
        {
            System.out.print(v.getName() + " : ");
            
            for (String e : v.getEdges())
            {
                System.out.print(e + "(" + v.getWeights().get(e) + ");");
            }
            
            System.out.println("  " + v.getReveresdEdges());
        }
        
        System.out.println();
    }
}
