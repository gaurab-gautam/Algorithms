/**
 *
 * @author Gaurab R. Gautam
 */


package binarysearchtrees;

public class Node<T>
{
    public static enum Color 
    {
        NONE("none"), 
        RED("red"), 
        BLACK("black"),
        DOUBLEBLACK("double black");

        private final String name;

        Color(String color)
        {
            name = color;
        }

        @Override
        public String toString()
        {
            return name;
        }
    };

    protected T key;   // data field
    protected Node<T> left;
    protected Node<T> right;
    protected Node<T> parent;
    protected Color color = Color.NONE;

    Node (T val)
    {
        this.key = val;
    }

//    void printDetails()
//    {
//        System.out.println((this != null ? this.key + 
//                (this.parent != null ? (this.parent.left == this ? "[" + this.parent.key + "->L(" + this.color + ")]" : 
//                        "[" + this.parent.key +"->R(" + this.color + ")]") : "[p=\u03D5(" + this.color + ")]")
//                        : "[\u03D5]") + "\t");
//    }
//
//    void print()
//    {
//        System.out.printf("%-5s", (this != null ? this.key : "[\u03D5] "));
//    }

    T getKey()
    {
        return this.key;
    }
    
    void setKey(T val) 
    {
        this.key = val;
    }
    
    Node<T> getParent() 
    {
        return this.parent;
    }
    
    void setParent(Node<T> val)
    {
        this.parent = val;
    }
    
    Node<T> getLeft() 
    {
        return this.left;
    }
    
    void setLeft(Node<T> val)
    {
        this.left = val;
    }
    
    Node<T> getRight() 
    {
        return this.right;
    }
    
    void setRight(Node<T> val)
    {
        this.right = val;
    }
    
    Color getColor() 
    {
        return this.color;
    }
    
    void setParent(Color val)
    {
        this.color = val;
    }
    
    boolean hasLeft() 
    {
        return this.left != null;
    }
            
    boolean hasRight() {
        return this.right != null;
    }

    boolean hasParent() {
        return this.parent != null;
    }

    boolean isRoot() {
        return this.parent == null;
    }

    boolean isLeaf() {
        return (this.left == null) && (this.right == null);
    }

    boolean isRightChild() {
        return (this.parent != null && this.parent.right == this);
    }

    boolean isLeftChild() {
        return (this.parent != null && this.parent.left == this);
    }
    
}
