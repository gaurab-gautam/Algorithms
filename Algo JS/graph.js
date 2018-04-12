/***********************************************************************
    Graph class
    comment: used for creating unweighted/weighted, directed/undirected graphs
************************************************************************/
	
// import modules
const Vertex = require('./vertex');
const Color = require('./color');
const VisitedState = require('./visitedstate');

module.exports = class Graph 
{
    // default constructor
    constructor() 
    {
        this._adjacencyList = new Map();
    }

    // Getter
    get adjacencyList() 
    {
        return this._adjacencyList;
    }

    // function to create graph from graph data
    /* graphData is a string containing vertex, edges/weight information;
       weight may be null for unweighted graph;
       graph may be directed or undirected depending on the context; for instance,
       the following example can be undirected graph with u-w edge or a directed
       graph with u<->w edge
        e.g.    u   v,3  w,1
                v   u,3
                w   u,1
            edges from node u:  {u, v} with weight 3, 
            {u, w} with weight 1,  and so on ...
    */
    addVertex(graphData, index) 
    {
        let splits = graphData.split(/\s+/);
        let source = splits[0].trim();

        var vertex = new Vertex(source, index);

        // iterate through edges to extract data
        for (let j = 1; j < splits.length; j++) 
        {
            // no weights
            if (!splits[j].includes(",")) 
            { 
                vertex.addEdge(splits[j].trim(), null);
                continue;
            }

            let edgeSplits = splits[j].trim().split(/\,/);
            let sink = edgeSplits[0];
            let weight = edgeSplits[1];

            vertex.addEdge(sink, weight);

        }

        this._adjacencyList.set(source, vertex);
    }
    
    
    // Array representation of adjacency list
    toArray() {
        let adjArray = [];
        
        this._adjacencyList.forEach( (vertex, key, mapObj) => {
            adjArray.push(vertex);
        });
        
        return adjArray;
    }

    // prints graph
    print() 
    {
        for (let value of this._adjacencyList) {
            console.log(value);
        }
    }
};
