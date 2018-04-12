/************************************************************************
 * BinarySearchTree class
 * @param {Array} A - input array
 ************************************************************************/

// import modules
const BinaryTree = require('./binarytree');

module.exports = class BinarySearchTree extends BinaryTree {
    constructor() {
        super();
    }
    
    build(array) {
        array.forEach( element => {
            this.insert(element);
        });
    }
    
    insert(val) {
        let node = this.root;
        let parent = null;
        let newNode = new BinaryTree.Node(val);
            
            
        // empty tree
        if (node === null) {
            this.root = newNode;
            //this.root.level = 0;
        }
        else {
            while (node !== null) {
                parent = node;
                    
                if (val < node.key) {
                    node = node.left;
                }
                else {
                    node = node.right;
                }
            }
            
            if (val < parent.key) {
                parent.left = newNode;
            }
            else {
                parent.right = newNode;
            }
            
            newNode.parent = parent;
            //newNode.level = parent.level + 1;
        }
        
        this.size += 1;
        
        return newNode;
    }
    
    delete(item) {
        let node = this.get(item);
        
        if (node === null) {
            return (item + " not found!");
        }
        
        this.deleteNode(node);
    }
    
    deleteNode(node) {
        // case 1: node.left = nil
        if (node.left === null) {
            this.transplant(node, node.right);
            //node.right.level = node.level;
            
            return (node.key + " deleted!");
        }
        
        // case 2: node.right = nil
        else if (node.right === null) {
            this.transplant(node, node.left);
            //node.left.level = node.level;
                        
            return (node.key + " deleted!");
        }
        else {
            let successor = this.successor(node);
            
            // case 3: node.right = successor of node
            if (successor.parent === node) {
                this.transplant(node, successor);
                successor.left = node.left;
                successor.left.parent = successor;
                //successor.level = node.level;
            }
            
            // case 4: node's successor is in right subtree of node.right
            // successor's left child is nill, so consider right child only
            else if (successor.parent !== node) {
                this.transplant(successor, successor.right);
                successor.right = node.right;
                successor.right.parent = successor;
                //successor.right.level = successor.level;
                
                this.transplant(node, successor);
                successor.left = node.left;
                successor.left.parent = successor;
                //successor.level = node.level;
            }
            
            this.size -= 1;
            
            return (node.key + " deleted!");
        }
    }
    
    // rearrange parent pointer of deleted node-node2
    transplant(node1, node2) {
        if (node1.hasParent()) {
            this.root = node2;
        }
        else if (node1.isLeftChild()) {
            node1.parent.left = node2;
        }
        else {
            node1.parent.right = node2;
        }
        
        if (node2 !== null) {
            node2.parent = node1.parent;
        }
    }
    
    treeMinimum(node) {
        while (node.left !== null) {
            node = node.left;
        }
        
        return node;
    }
    
    treeMaximum(node) {
        while (node.right !== null) {
            node = node.right;
        }
        
        return node;
    }
    
    successor(node) {
        if (node.hasRight()) {
            return this.treeMinimum(node.right);
        }
        
        while (node.hasParent()) {
            node = node.parent;
            
            if (node.hasParent() && node === node.parent.right) {
                return node;
            }
        }
        
        return null;
    }
    
    predecessor(node) {
        if (node.hasLeft()) {
            return this.treeMaximum(node.left);
        }
        else {
            let parent = node.parent;
            
            while (parent !== null && node.isLeftChild()) {
                node = parent;
                parent = node.parent;
            }
            
            return parent;
        }
    }
    
    search(node, val) {
        if (node === null || (node.key === val)) {
            return node;
        }
        
        if (val < node.key) {
            return this.search(node.left, val);
        }
        else {
            return this.search(node.right, val);
        }
    }
    
    inOrderTreeWalk(node) {
        if (node === null) {
            return;
        }
        
        this.inOrderTreeWalk(node.left);
        console.log(node.key + ", level = " + this.getLevel(node) + ", left: " +
                (!node.hasLeft() ? "\u03A6" : node.left.key) + ", right: " +
                (!node.hasRight() ? "\u03A6" : node.right.key) + ", parent: " +
                (!node.hasParent() ? "\u03A6" : node.parent.key));
        this.inOrderTreeWalk(node.right);
    }
    
    getLevel(node) {
        if (this.root === null || node === null) {
            return -1;
        }
        
        let iter = this.root;
        let level = 0;
        
        while (iter !== null && iter !== node) {
            if (node.key < iter.key) {
                iter = iter.left;
            }
            else {
                iter = iter.right;
            }
            
            level += 1;
        }
        
        return level;
    }
    
    get(val) {
        return this.search(this.root, val);
    }
    
    has(val) {
        return (this.search(this.root, val) !== null);
    }
};



