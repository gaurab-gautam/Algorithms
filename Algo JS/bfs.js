/***********************************************************************
 * bfs module
 * @param {String} filepath - input file containing nodes, edges and/or weights
 ************************************************************************/

// import modules
const fs = require('fs');
const readline = require('readline');
const Graph = require('./graph');
const Color = require('./color');
const VisitedState = require('./visitedstate');
const Queue = require('./queue');


module.exports.run = function (filepath) 
{
    // declare graph object
    let graph = new Graph();
    let nodeIndex = 0;

    let lineReader = readline.createInterface({
        input: fs.createReadStream(filepath)
    });

    // read lines of input file
    lineReader.on('line', (line) => {
        graph.addVertex(line, nodeIndex);
        nodeIndex += 1;
    });

    // wait until file read is completed?
    lineReader.on('close', () => {
        search(graph);
    });
    
    
    function search(graph) 
    {
        let queue = new Queue();

        // let first vertex be the source vertex
        let current = "r";
        let distance = 0;

        // initialize source
        let source = graph.adjacencyList.get(current);
        source.distance = distance;
        source.parent = null;
        source.visited = VisitedState.VISITED;

        // put the source vetex in the queue
        queue.enqueue(current);

        while (!queue.isEmpty()) 
        {
            print(graph, queue);

            // take the current vertex out of queue
            current = queue.dequeue();

            // edges of the vertex
            let edges = graph.adjacencyList.get(current).edges;

            // add edges to the queue;
            edges.forEach(edge => {
                //console.log("edge.name: " + edge.name);
                let v = graph.adjacencyList.get(edge.name);

                if (v.visited === VisitedState.NOT_VISITED) 
                {
                    v.visited = VisitedState.VISITED;
                    queue.enqueue(v.name);
                
                    // set up other variables
                    v.parent = current;
                    v.distance = graph.adjacencyList.get(v.parent).distance + 1; 
                }   
            });
        }
    }   
};


    
function print(graph, queue) {
    queue.print();
    console.log("----------------------------------------------------------------");
    
    graph.adjacencyList.forEach( vertex => {
        let stringBuilder = "";
        
        stringBuilder += ("Vertex: " + vertex.name).padEnd(12);
        stringBuilder += ("Parent: " + (vertex.parent === null ? "\u03D5" : vertex.parent)).padEnd(12);
        stringBuilder += ("Is visited? " + vertex.visited).padEnd(27);
        stringBuilder += "Distance: " + (vertex.distance === Number.MAX_VALUE ? "\u221E" : vertex.distance);
        
        console.log(stringBuilder);
    });
    
    console.log("\r\n");
}


