/************************************************************************
 * RedBlackTree class
 * @param {Number} - array size
 ************************************************************************/

// import modules
const BinarySearchTree = require('./binarysearchtree');
const Color = require('./color');
const BinaryTreePrinter = require('./binarytreeprinter');
const Debug = require('./debug');
const DEBUG = Debug.NO;     // don't print debug printouts

module.exports = class RedBlackTree extends BinarySearchTree {
    constructor() {
        super();
    }
    
    build(array) {
        for (let i = 0; i < array.length; i++) {
            this.insert(array[i]);
        }
    }
    
    
    validateRedProperty(node) {
        if (node.isRoot()) {
            node.color = Color.BLACK;
            return;
        }
        
        // case 1: if node's parent is black, it doesn't violate red property
        if (node.parent.color === Color.BLACK) {
            return;
        }
        
        // case 2: node's parent is red, four subcases follows
        else {
            let parent = node.parent;
            let grandparent = parent.parent;
            let uncle = null;
            
            if (parent.isLeftChild()) {
                uncle = grandparent.right;
            }
            else {
                uncle = grandparent.left;
            }
            
            // case 2a: node's uncle is black or nil
            // four subcases follow depending on position of parent and node
            if (uncle === null || uncle.color === Color.BLACK) {
                // case 2a.1: parent is left child
                if (parent.isLeftChild()) {
                    // case 2a.1.1: node is left child
                    // recolor parent & grandparent and rotate right
                    if (node.isLeftChild()) {
                        parent.color = Color.BLACK;
                        grandparent.color = Color.RED;
                    }
                    // case 2a.1.2: node is right child
                    else if (node.isRightChild()) {
                        // recolor node and parent and rotate right
                        node.color = Color.BLACK;
                        grandparent.color = Color.RED;
                        
                        // promote node to take parent's place
                        this.rotateLeft(parent);
                    }
                    
                    this.rotateRight(grandparent);
                }
                // case 2a.2: parent is right child
                else if (parent.isRightChild()) {
                    // case 2a.2.1: node is right child
                    // recolor parent & grandparent and rotate left
                    if (node.isRightChild()) {
                        parent.color = Color.BLACK;
                        grandparent.color = Color.RED;
                    }
                    // case 2a.2.2: node is left child
                    else if (node.isLeftChild()) {
                        // recolor and rotate left
                        node.color = Color.BLACK;
                        grandparent.color = Color.RED;
                        
                        // promote left child
                        this.rotateRight(parent);
                    }
                    
                    this.rotateLeft(grandparent);
                }
            }
            
            // case 2b: node's uncle is red 
            // recolor and recursively validate red property until root or
            // until if grandparent's parent is black
            else if (uncle.color === Color.RED) {
                parent.color = Color.BLACK;
                uncle.color = Color.BLACK;
                grandparent.color = Color.RED;
                
                this.validateRedProperty(grandparent);
            }          
                    
            //if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
        }
        
    }
     
    // To get rid of double black node, find the closest red sibling and change
    // colors {double black, red} to {black, black}
    fixDoubleBlackNode(node) {
        let sibling = null;
        
        if (node.isRoot()) {
            node.color = Color.BLACK;
            return;
        }
        else if (node.isLeftChild()) {
            sibling = node.parent.right;
        }
        else {
            sibling = node.parent.left;
        }
        
        // case: black sibling
        if (sibling.color === Color.BLACK) 
        {
            let leftNephew = sibling === null ? null : sibling.left;
            let rightNephew = sibling === null ? null : sibling.right;

            // case 1: black sibling with a right red child; left won't matter
            // restructure and recolor:
            if (rightNephew !== null && rightNephew.color === Color.RED) 
            {
                print("---------- case 1: black sibling with a right red child ----------");
                print("[restructure and recolor]:"); 
                
                // case 1a: left node with a right black sibling with a right red child
                //          sibling's right child is red; sibling's left child won't matter
                //          do left rotation on parent
                //          recolor sibling's right child (right nephew) black
                //          recolor node black (extra black absorbed)
                //          recolor sibling to parent color
                if (node.isLeftChild()) {
                    print(" rotate parent [" + node.parent.key + "] left");
                    print(" recolor right nephew, parent and node black;");
                    print(" recolor sibling with parent's previous color");
                    
                    this.rotateLeft(node.parent);
                    rightNephew.color = Color.BLACK;
                    sibling.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    node.color = Color.BLACK;    // absorbs double black by parent
                }
                
                // case 1b: node is right child
                //          double rotatation: rotate nephew left and parent right
                //          recolor right newphew with parent's color
                //          recolor parent black; recolor node black
                else if (node.isRightChild()) {
                    print(" rotate sibling [" + sibling.key + "] left");
                    print(" rotate parent [" + node.parent.key + "] right");
                    print(" recolor right  nephew with parent's color");
                    print(" recolor parent black, node black");
                    
                    this.rotateLeft(sibling);
                    this.rotateRight(node.parent);
                    rightNephew.color = node.parent.color;
                    node.parent.color = Color.BLACK;    // parent absorbs extra black
                    node.color = Color.BLACK;
                }
                
                if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
            }
            // case 2: black sibling with left child red (right child won't matter)
            // restructure and recolor: 
            else if (leftNephew !== null && leftNephew.color === Color.RED) 
            {
                print("---------- case 2: black sibling with left child red AND right child black/red  ----------");
                print("[restructure and recolor]:");
                    
                // case 2a: double black node is left child
                //          double rotation: rotate sibling's left child right 
                //                 then rotate parent left
                //          recolor sibling's left child to parent color
                //          recolor parent and node black
                if (node.isLeftChild()) {
                    print("rotate sibling's left child (left nephew) [" + sibling.key + "] right\n" + 
                                " rotate parent left;" +
                                " recolor left nephew with parent's color;" + 
                                " recolor parent black and node black");
                        
                    this.rotateRight(sibling);
                    this.rotateLeft(node.parent);
                    leftNephew.color = node.parent.color;
                    node.color = Color.BLACK;   // absorb extra black by parent
                    node.parent.color = Color.BLACK;

                    if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
                }
                
                // case 2b: double black node is right child
                //          rotate parent right
                //          recolor sibling with parent's color
                //          recolor node black; recolor left nephew black
                //          recolor parent black
                else 
                {
                    print(" rotate parent [" + node.parent.key + "] right");
                    print(" recolor right nephew, parent and node black;");
                    print(" recolor sibling with parent's previous color");
                    
                    this.rotateRight(node.parent);
                    sibling.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    leftNephew.color = Color.BLACK;
                    node.color = Color.BLACK;
                } 
            }
            
            // case 3: black sibling with black children [null is also considered black]
            //         recolor sibling red and parent black
            //         if parent is red, it absorbs black and terminates; otherwise,
            //         parent becomes double black
            //         if so, other cases applies
            //         terminates if parent is red, otherwise propogates upwards
            else if ((leftNephew === null || leftNephew.color === Color.BLACK) &&
                     (rightNephew === null || rightNephew.color === Color.BLACK))
             {
                print("---------- case 3: black sibling with black children ----------");
                print(" recolor sibling [" + sibling.key + "] red and parent [" +  node.parent.key + "] black;\n");
                    
                sibling.color = Color.RED;
                node.color = Color.BLACK;   // absorb extra black by parent
                
                if (node.parent.color === Color.BLACK) 
                {
                    node.parent.color = Color.DOUBLEBLACK;
                    
                    if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
                    
                    this.fixDoubleBlackNode(node.parent);
                }
                else  // terminates as parent absorbs extra black
                {
                    print(" absorb extra black by parent; terminates!");
                    node.parent.color = Color.BLACK;
                    
                    if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
                }
            }
        }

        // case 4: red sibling
        //         restructure so sibling color is black and use case 1, 2 or 3
        //         to restructure left child: rotate left
        //         or to restructure right child, rotate right
        else if (sibling.color === Color.RED) {
            print("---------- case 4: red sibling ----------\n" + 
                        " recolor sibling [" + sibling.key + "] black and parent [" + node.parent.key + "] red");
                
            if (node.isLeftChild()) {
                print("rotate parent left");
                this.rotateLeft(node.parent);
            }
            else if (node.isRightChild()) {
                print("rotate parent right");
                this.rotateRight(node.parent);
            }
            
            sibling.color = Color.BLACK;
            node.parent.color = Color.RED;
            
            if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
            
            this.fixDoubleBlackNode(node);
        }
    }
    
    
    delete(item) {
        let node = this.get(item);
        
        // item not found
        if (node === null) {
            print(item + " not found!");
            return false;
        }
        
        print("--- delete " + item + " --->");
        
        // node is leaf, delete it
        if (node.isLeaf()) {
            this.deleteSingleNode(node);
            
            return;
        }
        // node has one child
        else if (!(node.hasLeft() && node.hasRight())) {
            this.deleteSingleNode(node);
            
            return;
        }
        
        let inOrderPredecessor = this.predecessor(node);
        
        // swap node's key with its inorder predecessor and delete its predecessor
        // if node is black and predecessor is red, it just 
        // absorbs the predecessor
        let key = node.key;
        node.key = inOrderPredecessor.key;
        inOrderPredecessor.key = key;
        
        // delete predecessor
        this.deleteSingleNode(inOrderPredecessor);
        
        return true;
    }
    
    // Node is always a leaf node or a node with a single child
    deleteSingleNode(node) {
        if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
        // node is red, done after deletion!
        if (node.color === Color.RED) {
            print("Deleting red node: [" + node.key + "]; done!");
            super.deleteNode(node);
            
            if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
        }
        // node is  black
        else if (node.color === Color.BLACK) {
            if (node.isLeaf()) {
                // node now represents double black null node
                node.color = Color.DOUBLEBLACK;
                //node.key = "\u03A6";

                if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);

                // fix double black node
                this.fixDoubleBlackNode(node);

                // delete node
                super.deleteNode(node);
                
            }
            else if (node.hasLeft()) {
                let leftChild = node.left;
                
                // delete node
                super.deleteNode(node);
                
                if (leftChild.color === Color.BLACK) {
                    leftChild.color = Color.DOUBLEBLACK;
            
                    if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
                    
                    // fix double black node
                    this.fixDoubleBlackNode(leftChild);
                }
                else {  // color is red
                    // absorb extra blackness
                    leftChild.color = Color.BLACK;
                }
            }
            // child node is right node
            else if (node.hasRight()){
                let rightChild = node.right;
                
                // delete node
                super.deleteNode(node);
                
                if (rightChild.color === Color.BLACK) {
                    rightChild.color = Color.DOUBLEBLACK;
            
                    if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
                    
                    // fix double black node
                    this.fixDoubleBlackNode(rightChild);
                }
                else {  // color is red
                    // absorb extra blackness
                    rightChild.color = Color.BLACK;
                }
            }
            
            if (DEBUG === Debug.YES) BinaryTreePrinter.printLeftToRight(this);
        }
    }
    
    rotateLeft(node) {
        let rightChild = node.right;
        node.right = rightChild.left;
        
        if (rightChild.hasLeft()) {
            rightChild.left.parent = node;
        }
        
        rightChild.parent = node.parent;
        
        if (node.isRoot()) {
            this.root = rightChild;
        }
        else if (node.isLeftChild()) {
            node.parent.left = rightChild;
        }
        else {
            node.parent.right = rightChild;
        }
        
        rightChild.left = node;
        node.parent = rightChild;
    }
    
    rotateRight(node) {
        let leftChild = node.left;
        node.left = leftChild.right;
        
        if (leftChild.hasRight()) {
            leftChild.right.parent = node;
        }
        
        leftChild.parent = node.parent;
        
        if (node.isRoot()) {
            this.root = leftChild;
        }
        else if (node.isLeftChild()) {
            node.parent.left = leftChild;
        }
        else {
            node.parent.right = leftChild;
        }
        
        leftChild.right = node;
        node.parent = leftChild;
    }
    
    insert(val) {
        let node = super.insert(val);
        
        node.color = Color.RED;
        this.validateRedProperty(node);
    }
};

function print(text) {
    if (DEBUG === Debug.YES) {
        console.log(text);
    }
}


