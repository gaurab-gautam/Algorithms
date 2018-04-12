/************************************************************************
 * MaxPriorityQueue class
 ************************************************************************/

const MaxHeap = require('./maxheap');

module.exports = class MaxPriorityQueue extends MaxHeap {
    constructor(A) {
        super(A);
    }
    
    
    maximum() {
        if (this.heapSize < 1) {
            console.log("Heap underflow!");
            return;
        }
        
        return this.heap[0];
    }
    
    extractMax() {
        if (this.heapSize < 1) {
            console.log("Heap underflow!");
            return;
        }
        
        let max = this.heap[0];
        //this.swap(0, this.heapSize - 1);
        this.heap[0] = this.heap[this.heapSize - 1];
        this.heap.pop();
        this.heapSize -= 1;
        this.heapify(0);
        
        return max;
    }
    
    insert(value) {
        this.heapSize += 1;
        this.heap[this.heapSize - 1] = Number.MIN_VALUE;
        this.increaseKey(this.heapSize - 1, value);        
    }
    
    increaseKey(i, newkey) {
        if (newkey < this.heap[i]) {
            console.log("New key is smaller than current key!");
            return;
        }
        
        this.heap[i] = newkey;
        
        let child = i;
        let parent = Math.ceil(child/2) - 1;
        
        // bubble up until heap property has been restored
        while ( (parent >= 0) && (this.heap[child] > this.heap[parent]) ) {
            this.swap(child, parent);
            child = parent;
            parent = Math.ceil(child/2) - 1;
        }
    }
};

