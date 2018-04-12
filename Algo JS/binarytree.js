/***********************************************************************
 * BinaryTree class - binary tree 
 ***********************************************************************/

// import module
const Color = require('./color');
const Queue = require('./queue');
const BinaryTreePrinter = require('./binarytreeprinter');

module.exports = class BinaryTree {
    constructor() {
        if (new.target === BinaryTree) {
            throw new TypeError("Can't instantiate an abstract class!");
        }
        
        if (this.build === undefined) {
            throw new TypeError("Must override build!");
        }
        
        this._root = null;
        this._size = 0;
        this._height = 0;
    }
    
    get root() {
        return this._root;
    }
    
    set root(val) {
        this._root = val;
    }
    
    get size() {
        return this._size;
    }
    
    set size(val) {
        this._size = val;
    }
    
    get height() {
        //this._height = Math.floor(Math.log2(this._size));
        return this._height;
    }
    
    isEmpty() {
        return this._size === 0;
    }
    
    
    // builds balanced binary tree from input array regardless of key value
    // note: this is not a binary search tree
    buildBalancedBinaryTree(A) {
        let queue = new Queue();
        this._root = new BinaryTree.Node(A[0]);
        this._root.level = 0;
        this._root.index = 0;
        
        queue.enqueue(this._root);
        
        
        for (let i = 1; i < A.length; i++) {
            let parent = queue.dequeue();
            let left = null;
            let right = null;
            
            if (A[i] !== null) {
                left = new BinaryTree.Node(A[i]);
                left.index = i;
                left.level = parent.level + 1;
                parent.left = left;
                left.parent = parent;
                queue.enqueue(left);
            }
            
            if ((i + 1) < A.length) {
                i += 1;
               
                if (A[i] !== null) {
                   right = new BinaryTree.Node(A[i]);
                    right.index = i;
                    right.level = parent.level + 1;
                    parent.right = right;
                    right.parent = parent;
                    queue.enqueue(right); 
                }
            }
        }
    }
    
     _print() {
        // prints binary tree from root to leaf -- preorder tree walk
        BinaryTreePrinter.printLinuxStyle(this, BinaryTreePrinter.Legend.YES);
        console.log("\n\n\n");
        // inorder tree walk -- right child first
        BinaryTreePrinter.printLeftToRight(this);
    }
    
    
    /***********************************************************************
     * Node class - inner class for tree node
     ***********************************************************************/
    static get Node() {
        return class Node {
            constructor(data) {
                this._key = data;
                this._parent = null;
                this._left = null;
                this._right = null;
                this._color = Color.NOT_SET;
            }

            get key() {
                return this._key;
            }
            
            set key(val) {
                this._key = val;
            }

            get parent() {
                return this._parent;
            }

            get left() {
                return this._left;
            }

            get right() {
                return this._right;
            }

            get color() {
                return this._color;
            }
            
            set parent(val) {
                this._parent = val;
            }

            set left(val) {
                this._left = val;
            }

            set right(val) {
                this._right = val;
            }

            set color(val) {
                this._color = val;
            }
            
            hasLeft() {
                return this._left !== null;
            }
            
            hasRight() {
                return this._right !== null;
            }
            
            hasParent() {
                return this._parent !== null;
            }
            
            isRoot() {
                return this._parent === null;
            }
            
            isLeaf() {
                return (this._left === null) && (this._right === null);
            }
            
            isRightChild() {
                return (this._parent !== null && this._parent.right === this);
            }
            
            isLeftChild() {
                return (this._parent !== null && this._parent.left === this);
            }
        };
    }
};

