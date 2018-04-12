/***********************************************************************
dfs module
filepath: input file containing nodes, edges and/or weights
************************************************************************/

// import modules
const fs = require('fs');
const readline = require('readline');
const Graph = require('./graph');
const VisitedState = require('./visitedstate');
const Color = require('./color');

// variable for dfs
let time = 0;

// colors for console printout
const BACKGROUND_COLOR =    '\x1b[47m';            // white
const FOREGROUND_COLOR =    '\x1b[30m';            // black
const RESET  =              '\x1b[0m';             // reset color

module.exports.run = function (filepath) 
{	
    // declare graph object
    let graph = new Graph();
    let nodeIndex = 0;

    let lineReader = readline.createInterface( { 
            input: fs.createReadStream(filepath)	
    });

    // read lines of input file
    lineReader.on('line', (line) => {
            graph.addVertex(line, nodeIndex);
            nodeIndex += 1;
    });

    // wait until file read is completed?
    lineReader.on('close', () => {
        dfs(graph, "u");
    });
    
    
    
    function dfs(graph, source) {
        let current = graph.adjacencyList.get(source);

        dfsVisit(graph, current);

        // Process all the cross edges
        graph.adjacencyList.forEach( vertex => {
            if (vertex.visited === VisitedState.NOT_VISITED) 
            {
                dfsVisit(graph, vertex);
            }
        });

        console.log("End of algorithm");
        print(graph, null);
    }

    function dfsVisit(graph, current) 
    {
        time += 1;

        // update vertex
        current.visited = VisitedState.VISITED;
        current.discoveryTime = time;

        console.log(BACKGROUND_COLOR, FOREGROUND_COLOR, "Current vertex: " + current.name, RESET);
        print(graph, current.index);

        current.edges.forEach( edge => {
            let child = graph.adjacencyList.get(edge.name);

            if (child.visited === VisitedState.NOT_VISITED) 
            {
                child.parent = current.name;

                dfsVisit(graph, child);
            }
        });

        time += 1;

        // update vertex
        current.finishTime = time;
    }
};

function print(graph, index) {
    console.log("---------------------------------------------------------------------------------------")
    let i = 0;
    
    graph.adjacencyList.forEach( vertex => {
        let stringBuilder = ("Vertex: " + vertex.name).padEnd(12);
        stringBuilder += ("Parent: " + (vertex.parent === null ? "\u03D5" : vertex.parent)).padEnd(12);
        stringBuilder += ("Is visited? " + vertex.visited).padEnd(27);
        stringBuilder += ("Discovery time: " + (vertex.discoveryTime === Number.MAX_VALUE ? "\u221E" : vertex.discoveryTime)).padEnd(20);
        stringBuilder += ("Finish time: " + (vertex.finishTime === Number.MAX_VALUE ? "\u221E" : vertex.finishTime));
        
        if (i === index) 
        {
            console.log(BACKGROUND_COLOR, FOREGROUND_COLOR, stringBuilder, RESET);
        }
        else 
        {
            console.log(stringBuilder);
        }
            
        i += 1;
    });
    
    console.log("\r\n");
}


