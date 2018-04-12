/************************************************************************
 * MaxHeap class
 ************************************************************************/

const Heap = require('./heap');
const NEGATIVE_INFINITY = Number.MIN_VALUE;
    
module.exports = class MaxHeap extends Heap {
    //constructor
    constructor(array) {
        super(array);
        
        // subclass priority queue must override these methods
        if ((new.target !== MaxHeap) && (new.target !== Heap)) {
            if (this.insert === undefined) {
                throw new TypeError("Must override insert!");
            }

            if (this.extractMax === undefined) {
                throw new TypeError("Must override extractMax!");
            }

            if (this.maximum === undefined) {
                throw new TypeError("Must override maximum!");
            }

            if (this.increaseKey === undefined) {
                throw new TypeError("Must override increaseKey!");
            }
        }
        
        this._buildHeap();
    }
    
    heapify(i){
        let largest = NEGATIVE_INFINITY;
        let left = 2 * i + 1;
        let right = left + 1;
        
        if ((left < this.heapSize) && (this.heap[left] > this.heap[i])) {
            largest = left;
        }
        else {
            largest = i;
        }
        
        if ((right < this.heapSize) && (this.heap[right] > this.heap[largest])) {
            largest = right;
        }
        
        if (largest !== i) {
            this.swap(largest, i);
            this.heapify(largest);
        }
    }
    
    
    // start from parent of the last leaf node
    _buildHeap() {
        for (let i = Math.floor(this.heapSize/2) - 1; i >= 0; i--) {
            this.heapify(i);
        }
    }
    
};


