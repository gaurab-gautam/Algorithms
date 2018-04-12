/* 
 * heapSort module
 */

let MinHeap = require('./minheap');
let MaxHeap = require('./maxheap');


/*
 * @param {Object} A - array to sort
 * @param {boolean} [isReversed] - true for reverse sort, optional/false otherwise
 */
module.exports.sort = function (A, isReversed = false) {
    
    if (!isReversed) {
        let heap = new MaxHeap(A);
        heapsort(heap);
    }
    else {
        let heap = new MinHeap(A);
        heapsort(heap);
    }
    
    /*
    * @param {Object} heap - heapified array
    * @description sorts the given array
    */
    function heapsort(heap) {
        for (let i = heap.heapSize - 1; i > 0; i--) {
            heap.swap(i, 0);
            heap.heapSize -= 1;
            heap.heapify(0);
        }
    }
    
};


