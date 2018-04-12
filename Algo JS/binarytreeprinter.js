/***********************************************************************
 * Binary Tree Printer class - prints binary tree 
 ***********************************************************************/

// import modules
const Color = require('./color');
const colors = require('colors');
const ConsoleProgressBar = require('./consoleprogressbar');

module.exports = class BinaryTreePrinter
{
    static get Legend() {
        return Legend;
    }
    
    static printTopToBottom(tree) {
        const LEFT_BRANCH = "┌";
        const RIGHT_BRANCH = "┐";
        const BRANCH = "|";
        const DEFAULT_SPACING = 4;
        
        throw Error("unsupported function!");
        
    }
    
    static printLeftToRight(tree) {
        const RIGHT_BRANCH = "┌── ";
        const LEFT_BRANCH = "└── ";
        const DEFAULT_SPACING = 4;
        
        let _printBar = [];
        const _tree = tree;
        let numNodesProcessed = 0;
        let progressBar = null;
        
        for (let index = 0; index < tree.height; index++) {
            _printBar[index] = false;
        }
        
        _print(tree);
        
        function _print(tree) {
            if (tree.isEmpty()) {
                console.log("[ EMPTY TREE ]");  
                return;
            }
            
            let source = tree.root;
            let stringBuilder = "\n\n";
            
            //progressBar = new ConsoleProgressBar();
            progressBar = new ConsoleProgressBar(tree.size);
            progressBar.start();

            stringBuilder = _printNode(source, stringBuilder);
            
            progressBar.stop();
            
            console.log(stringBuilder);
            console.log("\n\n");
        }
        
        
        // inorder tree walk -- right child first
        function _printNode(node, stringBuilder) {
            if (node !== null) 
            {
                numNodesProcessed += 1;
                progressBar.update(numNodesProcessed);
                
                // print right child first
                stringBuilder = _printNode(node.right, stringBuilder);

                // then print node
                if (node.isRoot()) {
                    stringBuilder += ("" + node.key).black.bold;
                    stringBuilder += "──|\n";
                }
                
                else {
                    stringBuilder = _pad(node, stringBuilder);
                    
                    if (node.color === Color.DOUBLEBLACK) {
                        stringBuilder += ("" + node.key).inverse;
                    }
                    else if (node.color === Color.RED) {
                        stringBuilder += ("" + node.key).red.bold;
                    }
                    else {
                        stringBuilder += ("" + node.key).black.bold;
                    }
                    
                    stringBuilder += "\n";
                }

                // keep track where to print bar | while drawing tree
                if (node.parent !== null && node.isRightChild()) {
                    let parentLevel = _tree.getLevel(node.parent);
                    _printBar[parentLevel] = true;
                }
                
                else if (node.parent !== null && node.isLeftChild()) {
                    let parentLevel = _tree.getLevel(node.parent);
                    _printBar[parentLevel] = false;
                }
                
                if (!node.hasLeft()) {
                    _printBar[_tree.getLevel(node)] = false;
                }
                
                // print left child last
                stringBuilder = _printNode(node.left, stringBuilder);
                
            }
            
            return stringBuilder;
        }
        
        function _pad(node, stringBuilder) {
            let level = _tree.getLevel(node);
            
            for (let i = 0; i < level; i++) {
                stringBuilder += "".padStart(DEFAULT_SPACING); 
                
                if (_printBar[i] === true) 
                {
                    // don't need a bar before └── 
                    if ((node.isLeftChild()) && (i === (level-1))) {
                        stringBuilder += "";
                    }
                    else {
                        // replace last space with |
                        stringBuilder = stringBuilder.slice(0, stringBuilder.length - 1);
                        stringBuilder += "|";
                    }
                }
            }
            
            if (node.hasParent()) 
            {
                // remove last space to accomodate └── or ┌── 
                //if (stringBuilder[stringBuilder.length - 1] !== "\n") 
                {
                    stringBuilder = stringBuilder.slice(0, stringBuilder.length - 1);
                }
                        
                if (node.isLeftChild()) {
                    stringBuilder += LEFT_BRANCH;
                }
                else if (node.isRightChild()) {
                    stringBuilder += RIGHT_BRANCH;
                }
            }  
                
            return stringBuilder;
        }
    }
    
    static printLinuxStyle(tree, legend = Legend.NO) {
        
        const HALF_BRANCH = "└── ";
        const FULL_BRANCH = "├── ";
        const DEFAULT_SPACING = 4;
        let _printBar = [];
        const _tree = tree;
        
        for (let index = 0; index < tree.height; index++) {
            _printBar[index] = true;
        }
        
        _print(tree);
        
        // prints binary tree from root to leaf, left to right
        function _print(tree) {
            if (tree.root === null) {
                console.log("[ EMPTY TREE ]");
                return;
            }

            let stringBuilder = "\n";
            let source = tree.root;
            
            if (legend === Legend.YES) {
                stringBuilder = _printLegend(stringBuilder);
            }
            
            stringBuilder += HALF_BRANCH;
            stringBuilder = _printNode(source, stringBuilder);

            console.log(stringBuilder.toString());
        }
        
        // recursively print node -- preorder tree walk
        function _printNode(node, stringBuilder) {

            // print the node
            if (node.color === Color.DOUBLEBLACK) {
                stringBuilder += ("" + node.key).black.bold;
            }
            else if (node.color === Color.RED) {
                stringBuilder += ("" + node.key).red.bold;
            }
            else  {
                stringBuilder += ("" + node.key).black.bold;
            }
            
            stringBuilder += "\n";

            
            if (node.parent !== null && node.isRightChild()) {
                let parentLevel = _tree.getLevel(node.parent);
                
                _printBar[parentLevel] = false;
            }
            else if (node.parent !== null && node.isLeftChild()) {
                let parentLevel = _tree.getLevel(node.parent);
                
                _printBar[parentLevel] = true;
            }

            if (node.hasLeft() || node.hasRight()) {
                stringBuilder = _pad(node, stringBuilder);
            }

            if (node.hasLeft() && node.hasRight()) {
                stringBuilder += FULL_BRANCH;
                stringBuilder = _printNode(node.left, stringBuilder);


                stringBuilder = _pad(node, stringBuilder);
                stringBuilder += "|\n";
                stringBuilder = _pad(node, stringBuilder);
                
                stringBuilder += HALF_BRANCH;
                stringBuilder = _printNode(node.right, stringBuilder);
            }
            else {
                if (node.hasLeft()) {
                    stringBuilder += HALF_BRANCH;
                    stringBuilder = _printNode(node.left, stringBuilder);
                }
                else if (node.hasRight()) {
                    stringBuilder += "|";
                    stringBuilder += "\n";
                    stringBuilder = _pad(node, stringBuilder);
                    stringBuilder += "|";
                    stringBuilder += "\n";
                    stringBuilder = _pad(node, stringBuilder);
                    stringBuilder += HALF_BRANCH;
                    stringBuilder = _printNode(node.right, stringBuilder);
                }
            }

            return stringBuilder;
        }

        function _pad(node, stringBuilder) {
            let level = _tree.getLevel(node);
            
            stringBuilder += "".padStart(DEFAULT_SPACING);
            
            for (let i = 0; i < level; i++) {
                if (_printBar[i] === true) 
                {
                    stringBuilder += "|";
                    stringBuilder += "".padStart(DEFAULT_SPACING - 1);
                }
                else {
                    stringBuilder += "".padStart(DEFAULT_SPACING);
                }
            }

            return stringBuilder;
        }
        
        function _printLegend(stringBuilder) {
            stringBuilder += "LEGENDS: \n";
            stringBuilder += ("        " + HALF_BRANCH + "node\n");
            stringBuilder += ("                " + FULL_BRANCH + "left.child\n");
            stringBuilder += ("                |             |\n");
            stringBuilder += ("                |             |\n");
            stringBuilder += ("                |             " + HALF_BRANCH + "right.child\n");
            stringBuilder += "                |\n";
            stringBuilder += "                |\n";
            stringBuilder += ("                " + HALF_BRANCH + "right.child\n");
            stringBuilder += ("                               " + HALF_BRANCH + "left.child\n");
            stringBuilder += "\n\n";
            
            return stringBuilder;
        }
    }
    
};

const Legend = Object.freeze({
    YES : "YES",
    NO : "NO"
});

