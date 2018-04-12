/**
 *
 * @author Gaurab R. Gautam
 */
package binarysearchtrees;

import utility.ConsoleProgressBar;
import utility.OSDetector;



public class BinaryTreePrinter {
    static final String RIGHT_BRANCH = "┌── ";
    static final String LEFT_BRANCH = "└── ";
    static final String VERTICAL_LINE = "\u2502";
    static final int DEFAULT_SPACING = 6; 
    static int numNodesProcessed = 1;
    static ConsoleProgressBar progressBar;
        
    private static String red(String text)
    {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        
//        if (OSDetector.getOS() == OSDetector.OS.WINDOWS)
//        {
//            return text;
//        }
//        else
//        {
//            return ANSI_RED + text + ANSI_RESET;
//        }
        
        return ANSI_RED + text + ANSI_RESET;
    }
    
    private static String black(String text)
    {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        
//        if (OSDetector.getOS() == OSDetector.OS.WINDOWS)
//        {
//            return text;
//        }
//        else
//        {
//            return ANSI_BLACK + text + ANSI_RESET;
//        }
        
        return ANSI_BLACK + text + ANSI_RESET;
    }
    
    private static String doubleBlack(String text)
    {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        final String ANSI_BOLD = "\u001B[1m";
        final String ANSI_BLACK = "\u001B[30m";
        
//        if (OSDetector.getOS() == OSDetector.OS.WINDOWS)
//        {
//            return text;
//        }
//        else
//        {
//            return ANSI_BOLD + ANSI_WHITE_BACKGROUND + ANSI_BLACK  + text + ANSI_RESET;  
//        }

        return ANSI_BOLD + ANSI_WHITE_BACKGROUND + ANSI_BLACK  + text + ANSI_RESET;  
    }   
    
    public static void printLeftToRight(BinarySearchTree tree) {
        
        boolean[] _printBar = new boolean [tree.getHeight()];
        
        for (int index = 0; index < tree.getHeight(); index++) {
            _printBar[index] = false;
            
        }
        
        print(tree, _printBar);
        
    }
        
    private static void print(BinarySearchTree tree, boolean[] _printBar) 
    {
        if (tree.isEmpty()) {
            System.out.println("[ EMPTY TREE ]");  
            return;
        }

        Node source = tree.root;
        String stringBuilder = "\n\n";
        
        progressBar = new ConsoleProgressBar(tree.getSize());
        progressBar.setHeader("Printing");
        progressBar.setProgressBarIndicator(ConsoleProgressBar.ProgressBarIndicator.HALF_BLOCK);
        progressBar.start();
        
        
        stringBuilder = printNode(tree, source, stringBuilder, _printBar);
        
        progressBar.stop();
        System.out.println(stringBuilder);
        System.out.println("\n\n");
    }
        
    // inorder tree walk -- right child first
    private static String printNode(BinarySearchTree tree, Node node, 
                                        String stringBuilder, boolean[] _printBar) 
    {
        if (node != null) 
        {
            // print right child first
            stringBuilder = printNode(tree, node.right, stringBuilder, _printBar);

            // then print node
            if (node.isRoot()) {
                stringBuilder += ("" + node.key);
                stringBuilder += "──|\n";
            }

            else {
                stringBuilder = _pad(tree, node, stringBuilder, _printBar);
                
                if (node.color == null)
                {
                    stringBuilder += black(("" + node.key));
                }
                else 
                {
                    switch (node.color) 
                    {
                        case DOUBLEBLACK:
                            stringBuilder += doubleBlack(("" + node.key));
                            break;
                        case RED:
                            stringBuilder += red(("" + node.key));
                            break;
                        case BLACK:
                            stringBuilder += black(("" + node.key));
                            break;
                    }
                }

                stringBuilder += "\n";
            }
            
            // update progress of node processed
            numNodesProcessed += 1;
            progressBar.update(numNodesProcessed);
            

            // keep track where to print bar | while drawing tree
            if (node.parent != null && node.isRightChild()) {
                int parentLevel = tree.getLevel(node.parent);
                _printBar[parentLevel] = true;
            }

            else if (node.parent != null && node.isLeftChild()) {
                int parentLevel = tree.getLevel(node.parent);
                _printBar[parentLevel] = false;
            }

            if (!node.hasLeft()) {
                _printBar[tree.getLevel(node)] = false;
            }

            // print left child last
            stringBuilder = printNode(tree, node.left, stringBuilder, _printBar);

        }

        return stringBuilder;
    }
        
    private static String _pad(BinarySearchTree tree, Node node, String stringBuilder, boolean[] _printBar) {
        int level = tree.getLevel(node);
        
        for (int i = 0; i < level; i++) {
            
            stringBuilder += String.format("%" + DEFAULT_SPACING + "s", ""); 

            if (_printBar[i] == true) 
            {
                // don't need a bar before └── 
                if ((node.isLeftChild()) && (i == (level-1))) {
                    stringBuilder += "";
                }
                else {
                    // replace last space with |
                    stringBuilder = stringBuilder.substring(0, stringBuilder.length() - 1);
                    stringBuilder += VERTICAL_LINE;
                }
            }
        }

        if (node.hasParent()) 
        {
            // remove last space to accomodate └── or ┌── 
            //if (stringBuilder[stringBuilder.length - 1] !== "\n") 
            {
                stringBuilder = stringBuilder.substring(0, stringBuilder.length() - 1);
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
