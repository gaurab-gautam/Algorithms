/**
 *
 * @author Gaurab R. Gautam
 */
package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Graph 
{
    protected final Vertex[] adjList;     // array of Vertices reflecting adjacency list
    protected final int SIZE;
    
    public Graph(int size)
    {
        this.SIZE = size;
        adjList = new Vertex[SIZE];
        
        for (int i = 0; i < this.SIZE; i++)
        {
            adjList[i] = new Vertex();
        }
    }
    
    public int getSize()
    {
        return this.SIZE;
    }
    
    public Vertex[] getAdjacencyList()
    {
        return this.adjList;
    }
    
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
                        String edge = tokens[i].trim();
                        int reversedIndex = (edge.hashCode()) % this.SIZE;
                        
                        adjList[nodeIndex].getEdges().add(edge);
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
    
    public void print()
    {
        for (Vertex v : this.adjList)
        {
            System.out.println(v.getName() + " : " + v.getEdges() + "\t" + v.getReveresdEdges());
        }
        
        System.out.println();
    }
    
}
