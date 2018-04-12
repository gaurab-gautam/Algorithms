/************************************************************************
 * Heap class
 ************************************************************************/

module.exports = class Heap {
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
};