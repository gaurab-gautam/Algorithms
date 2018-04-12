/************************************************************************
 * MinPriorityQueue class
 ************************************************************************/

const MinHeap = require('./minheap');

module.exports = class MinPriorityQueue extends MinHeap {
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
        
        this.heap[i] = newkey;
        let child = i;
        let parent = Math.ceil(child/2) - 1;
        
        
        // bubble up until min heap property has been restored
        while ((parent >= 0) && (this.heap[child] < this.heap[parent])) {
            this.swap(child, parent);
            child = parent;
            parent = Math.ceil(child/2) - 1;
        }
        
    }
};

