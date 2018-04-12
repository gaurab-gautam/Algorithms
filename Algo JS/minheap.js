/************************************************************************
 * MinHeap class
 ************************************************************************/

const Heap = require('./heap');
const POSITIVE_INFINITY = Number.MAX_VALUE;

module.exports = class MinHeap extends Heap{
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
        let smallest = POSITIVE_INFINITY;
        let left = 2 * i + 1;
        let right = left + 1;
        
        if ((left < this.heapSize) && (this.heap[left] < this.heap[i])) {
            smallest = left;
        }
        else {
            smallest = i;
        }
        
        if ((right < this.heapSize) && (this.heap[right] < this.heap[smallest])) {
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
};


