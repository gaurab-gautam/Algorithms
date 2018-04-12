// Test module

module.exports.test = function() {
    //partition();
    //quickSort();
    //mergesort();
    
    //stack();
    //queue();
    //circularlinkedlist();
    //doublylinkedlist();
    //singlylinkedlist();
    
    //heap();
    //maxheap();
    //minheap();
    //heapsort();
    //maxpriorityqueue();
    //minpriorityqueue();
        
    //graph();
    //bfs();
    //dfs();
    //dijkstras();
    //binarysearchtree();
    redblacktree();
};

function redblacktree() {
    console.log("red black tree demo!");
    const RedBlackTree = require('./redblacktree');
    const BinaryTreePrinter = require('./binarytreeprinter');
    const Color = require('./color');
    
    let A = [1, 4, 3, 6, 13, 12, 7, 2, 17, 15, 18 , 9, 20];
    //A = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
    //A = [6, 4, 8, 2, 5, 7, 9];
    //A = [14, 7, 16, 4, 12, 15, 18, 5, 17];
    //A = [1, 5, 10, 100, 50];
    //A = [65, 50, 80, 10, 60, 70, 90, 62];
    //A = [80, 50, 10, 65, 90, 60, 70, 62];
    A = [36,34,39,33,35,38,40,32,37,41];
    
    
    process.stdout.write("- Creating array ");
    
    const SIZE = 10000000;
    for (let i = 0; i < SIZE; i++) {
        A[i] = i + 1;
    }
    
    console.log(' -- done');
    process.stdout.write("- Shuffling array ");
    
    // shuffle
    for (let i = 0; i < SIZE/100; i++) {
        let j = Math.floor(Math.random() * SIZE);
        let k = Math.floor(Math.random() * SIZE);
        
        let temp = A[j];
        A[j] = A[k];
        A[k] = temp;
    }
    
    console.log(' -- done ' + A.length);
    process.stdout.write("- Building RBT ");
    
    let rbt = new RedBlackTree(A.length);
    rbt.build(A);
    console.log(' -- done: ' + rbt.size);
    
    

    BinaryTreePrinter.printLeftToRight(rbt);
    console.log("-------------\n\n");
    
    
//    process.stdout.write("- Deleting some nodes ");
    
    // randomly delete elements
//    for (let i = 0; i < SIZE - 50; i++) {
//        //let j = Math.floor(Math.random() * rbt.size);
//        
//        rbt.delete(A[i]);
//    }
//    
//    console.log(' -- done');
//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("\n\n");

//    rbt.get(7).color = Color.RED;
//    rbt.get(16).color = Color.RED;
//    rbt.get(5).color = Color.RED;
//    rbt.get(17).color = Color.RED;

//    rbt.get(6).color = Color.BLACK;
//    rbt.get(4).color = Color.BLACK;
//    rbt.get(7).color = Color.BLACK;
//    rbt.get(2).color = Color.RED;
//    rbt.get(5).color = Color.RED;

    
//    console.log("deleting 90 ...");
//    rbt.delete(90);
//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("deleting 80 ...");
//    rbt.delete(80);
//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("deleting 70 ...");
//    rbt.delete(70);
        
//    console.log("deleting 9 ...");
//    rbt.delete(9);
//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("deleting 8 ...");
//    rbt.delete(8);
//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("deleting 7 ...");
//    rbt.delete(7);


//    console.log("deleting 12 ...");
//   rbt.delete(12);

//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("\n\n");
//    
//    rbt.delete(5);
//    
//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("\n\n");
//    
//    rbt.insert(5);

//    BinaryTreePrinter.printLeftToRight(rbt);
//    console.log("\n\n");
}

function binarysearchtree() {
    console.log("binary search tree demo!");
    const BinarySearchTree = require('./binarysearchtree');
    const BinaryTreePrinter = require('./binarytreeprinter');
    
    let A = [9, 4, 3, 6, 13, 12, 7, 2, 17, 15, 18 , 1, 20];
    //let A = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
    
    let bst = new BinarySearchTree(A.length);
    bst.build(A);
    BinaryTreePrinter.printLeftToRight(bst);
    console.log("\n\n");    
//    BinaryTreePrinter.printLinuxStyle(bst, BinaryTreePrinter.Legend.No);
    
    //bst.inOrderTreeWalk(bst.root);
    
//    console.log(bst.treeMinimum(bst.root.right.right.right.left).key);
//    console.log(bst.treeMaximum(bst.root.right.right.right.left).key);
//    console.log(bst.treeMaximum(bst.root).key);
//    //console.log(bst.search(bst.root, 13));
//    console.log(bst.search(bst.root, 12));
//    console.log(bst.successor(bst.get(13)));
//    console.log(bst.predecessor(bst.get(13)));
    
//      console.log(bst.treeMinimum(bst.get(13).right).key);
//      console.log(bst.successor(bst.get(13)).key);
    console.log(bst.remove(13));
    BinaryTreePrinter.printLeftToRight(bst);

}

function dijkstras() {
    console.log("dijkstras demo!");
    const dijkstras = require('./dijkstrasshortestpath');
    dijkstras.run(".././inputs/dijkstras.txt");
}

function minpriorityqueue()
{
    console.log("min priority queue demo!");
    const PriorityQueue = require('./minpriorityqueue');
    //let A = [2, 8, 4, 7, 3, 1, 5];
    let A = [16, 4, 10, 14, 7, 9, 3, 2, 8, 1];
    console.log(A);
    let queue = new PriorityQueue(A);
    queue.print();
    
    console.log("minimum: " + queue.minimum());
    let max = queue.extractMin();
    console.log("extract min: " + max);
    queue.print();
    console.log("insert: " + max);
    queue.insert(max);
    queue.print();
    console.log("derease key at index 3 to 3");
    queue.decreaseKey(3, 3);
    queue.print();
}

function maxpriorityqueue()
{
    console.log("max priority queue demo!");
    const PriorityQueue = require('./maxpriorityqueue');
    //let A = [2, 8, 4, 7, 3, 1, 5];
    let A = [16, 4, 10, 14, 7, 9, 3, 2, 8, 1];
    console.log(A);
    let queue = new PriorityQueue(A);
    queue.print();
    
    console.log("maximum: " + queue.maximum());
    let max = queue.extractMax();
    console.log("extract max: " + max);
    queue.print();
    console.log("insert: " + max);
    queue.insert(max);
    queue.print();
    console.log("increase key at index 6 to 13");
    queue.increaseKey(6, 13);
    queue.print();
}

function heapsort()
{
    console.log("heapsort demo!");
    const heapsort = require('./heapsort');
    //let A = [2, 8, 4, 7, 3, 1, 5];
    let A = [16, 4, 10, 14, 7, 9, 3, 2, 8, 1];
    console.log(A);
    
    heapsort.sort(A, false);
    console.log(A);
}

function minheap()
{
    console.log("minheap demo!");
    const Heap = require('./minheap');
    //let A = [2, 8, 4, 7, 3, 1, 5];
    let A = [16, 4, 10, 14, 7, 9, 3, 2, 8, 1];
    console.log(A);
    let myHeap = new Heap(A);
    myHeap.print();
}

function maxheap()
{
    console.log("maxheap demo!");
    const Heap = require('./maxheap');
    //let A = [2, 8, 4, 7, 3, 1, 5];
    let A = [16, 4, 10, 14, 7, 9, 3, 2, 8, 1];
    console.log(A);
    let myHeap = new Heap(A);
    myHeap.print();
}

function heap()
{
    console.log("heap demo!");
    const Heap = require('./heap');
    let A = [2, 8, 4, 7, 3, 1, 5];
    let myHeap = new Heap(A);
}

function dfs()
{
    console.log("dfs demo");
    // demo
    const dfs = require('./dfs');
    dfs.run('.././inputs/dfs.txt');
}

function bfs()
{
    console.log("bfs demo");
    const bfs = require('./bfs');
    bfs.run('.././inputs/bfs.txt');   
}

function graph()
{
    console.log("graph demo");
    //DEMO 
    let Graph = require("./graph");
    let Color = require('./color');

    let input = [];
    input[0] = "s t,10 y,5";
    input[1] = "t x,1 y,2";
    input[2] = "x z,4";
    input[3] = "y t,3 x,9 z,2";
    input[4] = "z s,7 x,6"; 

    //input[0] = "s t y";
    //input[1] = "t x y";
    //input[2] = "x z";
    //input[3] = "y t x z";
    //input[4] = "z s x"; 

    let g = new Graph();

    for (let i = 0; i < input.length; i++) {
            g.addVertex(input[i], i);
    }

    g.adjacencyList[4].color = Color.WHITE;
    g.adjacencyList[4].edges[0].weight = 100;
    console.log(g.adjacencyList[4].edges[0].weight);

    //g.print();
}

function circularlinkedlist()
{
    console.log("circular linked list demo");
    /* Demo */
    let LinkedList = require('./circularlinkedlist');
    let list = new LinkedList();
    let A = [2, 8, 4, 7, 3, 1, 5];
    console.log(A);
    list.addRange(A);
    list.print();

    
    console.log("has 8?: " + list.has(8));
    console.log("has 13?: " + list.has(13));
    console.log("remove 3?: " + list.remove(3));
    list.print();
    console.log("remove 12? " + list.remove(12));
    list.print();

    console.log("length: " + list.length);

    console.log("remove 2?: " + list.remove(2));
    list.print();
    console.log("remove 8?: " + list.remove(8));
    list.print();

    console.log("remove 7?: " + list.remove(7));
    list.print();

    console.log("clear");
    list.clear();
    list.print();

    console.log("adding items again...");
    list.addRange(A);
    list.print();
    
    console.log("insert item 6 at index 3 (index 10 insert 6)");
    list.insert(10, 6);
    list.print();
    
    console.log("set item at index 3 to 11");
    list.set(3, 11);
    list.print();
    
    console.log("get item at index 3");
    console.log(list.get(3));
}

function doublylinkedlist()
{
    console.log("doubly linked list demo");
    /* Demo */
    let LinkedList = require('./doublylinkedlist');
    let list = new LinkedList();
    let A = [2, 8, 4, 7, 3, 1, 5];
    console.log(A);
    list.addRange(A);
    list.print();

    console.log("has 8?: " + list.has(8));
    console.log("find 4?: " + list.find(4));
    console.log("has 13?: " + list.has(13));

    console.log("remove 3?: " + list.remove(3));
    list.print();
    console.log("remove 12? " + list.remove(12));
    list.print();

    console.log("length: " + list.length);

    console.log("remove 2?: " + list.remove(2));
    list.print();
    console.log("remove 8?: " + list.remove(8));
    list.print();

    console.log("remove 7?: " + list.remove(7));
    list.print();

    console.log("remove 4?: " + list.remove(4));
    list.print();

    console.log("remove 1?: " + list.remove(1));
    list.print();

    console.log("remove 5?: " + list.remove(5));
    list.print();

    console.log("adding items again...");
    list.addRange(A);
    list.print();
}

function singlylinkedlist()
{
    console.log("singly linked list demo");
    /* Demo */
    let LinkedList = require('./singlylinkedlist');
    let list = new LinkedList();
    let A = [2, 8, 4, 7, 3, 1, 5];
    console.log(A);
    list.addRange(A);
    list.print();

    console.log("has 13?: " + list.has(13));
    console.log("remove 3?: " + list.remove(3));
    list.print();
    console.log("remove 12? " + list.remove(12));
    list.print();
    console.log("length: " + list.length);

    console.log("remove 2?: " + list.remove(2));
    list.print();
    console.log("remove 8?: " + list.remove(8));
    list.print();

    console.log("remove 7?: " + list.remove(7));
    list.print();

    console.log("remove 4?: " + list.remove(4));
    list.print();

    console.log("remove 1?: " + list.remove(1));
    list.print();

    console.log("remove 5?: " + list.remove(5));
    list.print();
}

function stack()
{
    console.log("stack demo");
    /* Demo */
    let Stack = require("./stack");

    let stack = new Stack();

    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);
    stack.push(6);
    stack.pop();
    stack.pop();
    console.log(stack);
    stack.print();
    console.log(stack.length); 
}

function queue()
{
    console.log("queue demo");
    const Queue = require('./queue');

    let queue = new Queue();

    console.log ("enqueue 1...5");
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    queue.enqueue(5);
    console.log(queue);
    queue.print();
    console.log("enqueue 6");
    queue.enqueue(6);
    console.log("dequeue");
    queue.dequeue();
    console.log(queue);

    console.log("enqueue 6");
    queue.enqueue(6);
    console.log(queue);

    console.log("dequeue x 3");
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    console.log(queue);

    console.log ("enqueue 7...10");
    queue.enqueue(7);
    queue.enqueue(8);
    queue.enqueue(9);
    queue.enqueue(10);
    console.log(queue);

    console.log("dequeue");
    queue.dequeue();
    console.log(queue);

    console.log("enqueue 10");
    queue.enqueue(10);
    console.log(queue);

    console.log("dequeue x 5");
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    console.log(queue);

    console.log("enqueue 0");
    queue.enqueue(0);
    console.log(queue);

    console.log ("enqueue 1...5");
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    queue.enqueue(5);
    console.log(queue);
}

function mergesort()
{
    console.log("merge sort demo: [empty]");
}

function partition()
{
    console.log("partition demo: [empty]");
}

function quickSort()
{
    console.log("quick sort demo: [empty]");
}
