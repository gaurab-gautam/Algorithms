/************************************************************************
 * dijkstrasShortestPath module
 * @param {String} filepath - path to the file containing graph information 
 ************************************************************************/

// import modules
const fs = require('fs');
const readline = require('readline');
const Graph = require('./graph');

module.exports.run = function (filepath) {
    
    class Heap {
        // constructor
        constructor (array) {
            if (new.target === Heap) {
                throw new TypeError("Can't instantiate an abstract class!");
            }

            if (this._buildHeap === undefined) {
                throw new TypeError("Must override buildHeap!");
            }

            if (this.heapify === undefined) {
                throw new TypeError("Must override heapify!");
            }

            this._heap = array;
            this._heapSize = array.length;
        }

        indexOf(item) {
            for (let i = 0; i < this.heapSize; i++)
            {
                let vertex = this.heap[i];

                if (vertex.name === item.name) {

                    return i;
                }
            }

            return -1;  // item not found
        }

        get heapSize() {
            return this._heapSize;
        }

        set heapSize(value) {
            this._heapSize = value;
        }

        get heap() {
            return this._heap;
        }

        isEmpty() {
            return this._heapSize === 0;
        }

        swap(i, j) {
            let temp = this.heap[i];
            this.heap[i] = this.heap[j];
            this.heap[j] = temp;
        }

        print() {
            let stringBuilder = "[";

            for (let i = 0; i < this._heapSize; i++) {
                stringBuilder += " ";
                stringBuilder += this.heap[i];
                stringBuilder += ",";
            }

            stringBuilder = stringBuilder.replace(/(,)$/, " ]");

            console.log(stringBuilder);
        }
    }
    
    class MinHeap extends Heap{
        //constructor
        constructor(array) {
            super(array);

            if ((new.target !== MinHeap) && (new.target !== Heap)) {
                // subclass priority queue must override these methods
                if (this.insert === undefined) {
                    throw new TypeError("Must override insert!");
                }

                if (this.extractMin === undefined) {
                    throw new TypeError("Must override extractMin!");
                }

                if (this.minimum === undefined) {
                    throw new TypeError("Must override minimum!");
                }

                if (this.decreaseKey === undefined) {
                    throw new TypeError("Must override decreaseKey!");
                }
            }

            this._buildHeap();
        }

        heapify(i){
            let smallest = Number.MAX_VALUE;
            let left = 2 * i + 1;
            let right = left + 1;

            if ((left < this.heapSize) && (this.heap[left].distance < this.heap[i].distance)) {
                smallest = left;
            }
            else {
                smallest = i;
            }

            if ((right < this.heapSize) && (this.heap[right].distance < this.heap[smallest].distance)) {
                smallest = right;
            }

            if (smallest !== i) {
                this.swap(smallest, i);
                this.heapify(smallest);
            }
        }


        // start from parrent of last leaf up to root
        _buildHeap() {
            for (let i = Math.floor(this.heapSize/2) - 1; i >= 0; i--) {
                this.heapify(i);
            }
        }
    }
    
    class MinPriorityQueue extends MinHeap {
        constructor(A) {
            super(A);
        }

        minimum() {
            if (this.heapSize < 1) {
                console.log("Heap underflow!");
                return;
            }

            return this.heap[0];
        }

        extractMin() {
            if (this.heapSize < 1) {
                console.log("Heap underflow!");
                return;
            }

            let min = this.heap[0];
            //this.swap(0, this.heapSize - 1);
            this.heap[0] = this.heap[this.heapSize - 1];
            this.heap.pop();
            this.heapSize -= 1;
            this.heapify(0);

            return min;
        }

        insert(val) {
            this.heapSize += 1;
            this.heap[this.heapSize - 1] = Number.MAX_VALUE;
            this.decreaseKey(this.heapSize - 1, val);
        }

        decreaseKey(i, newkey) {
            if (this.heap[i] < newkey) {
                console.log("new key is greater than current key!");
                return;
            }

            this.heap[i].distance = newkey;
            let child = i;
            let parent = Math.ceil(child/2) - 1;


            // bubble up until min heap property has been restored
            while ((parent >= 0) && (this.heap[child].distance < this.heap[parent].distance)) {
                this.swap(child, parent);
                child = parent;
                parent = Math.ceil(child/2) - 1;
            }

        }
    }
    
    
    let graph = new Graph();
    let nodeIndex = 0;
    
    let lineReader = readline.createInterface({
        input: fs.createReadStream(filepath)
    });
    
    // read lines from the file
    lineReader.on('line', (line) => {
        graph.addVertex(line, nodeIndex);
        nodeIndex += 1;
    });
    
    lineReader.on('close', () => {
        // get array representation of graph's adjacency list
        const adjList = graph.toArray().slice(0, graph.length);
        
        // s is source vertex
        let source = "s";
        
        initializeSingleSource(adjList, source);
        dijkstras(adjList);    
        
    });
    
    function dijkstras(g) {
        let queue = new MinPriorityQueue(g);    // min priority queue
        let iteration = 1;
        
        
        while (!queue.isEmpty()) {
            let parent = queue.extractMin();
            print(graph, iteration);
            
            parent.edges.forEach( (edge) => {
                let child = graph.adjacencyList.get(edge.name);
                
                relax(queue, parent, child, parseInt(edge.weight, 10));
            });
            
            iteration += 1;
        }
        
    }
    
    function relax(queue, parent, child, weight) {
        if (child.distance > (parent.distance + weight)) {
            child.parent = parent.name;
            
            //child.distance = parent.distance + weight;
            let index = queue.indexOf(child);
            queue.decreaseKey(index, parent.distance + weight);
        }
    }
    
    // initialize source vertex distance to be 0
    function initializeSingleSource(g, source) {
        g.forEach( (vertex) => {
            if (vertex.name === source) {
                vertex.distance = 0;
                return;
            }
        });
    }
    
    function print(g, iteration) {
        console.log("Iteration " + iteration + ":");
        console.log("-----------------------------------------");
        
        
        g.adjacencyList.forEach( (v, key, mapObj) =>
        {
            let stringBuilder = "";
            stringBuilder += ("Node: " + v.name).padEnd(12);
            stringBuilder += ("%14s", "Parent: " + 
                    (v.parent !== null ? v.parent : "\u03A6")).padEnd(14);
            stringBuilder += ("Distance: " + 
                    (v.distance !== Number.MAX_VALUE ? v.distance : "\u221E"));
            
        
            console.log(stringBuilder);
        });
        
        
        console.log();
        console.log();
        
    } 
};

