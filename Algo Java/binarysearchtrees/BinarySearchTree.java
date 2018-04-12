/**
 *
 * @author Gaurab R. Gautam
 */
package binarysearchtrees;

public class BinarySearchTree<T extends Comparable<? super T>> 
{
    Node<T> root = null;
    private int size = 0;
    private int height = 0;
    
    public BinarySearchTree(T[] vals)
    {
        buildTree(vals);
    }
    
    public BinarySearchTree()
    {
        
    }
    
    public Node<T> getroot() {
        return this.root;
    }
    
    public void setRoot(Node<T> val) {
        this.root = val;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public void setSize(int val) 
    {
        this.size = val;
    }
    
    // upper bound of height of red black tree
    public int getHeight() {
        this.height = 2 * (int)Math.floor(Math.log(this.size + 1)/Math.log(2));
        return this.height;
    }
    
    public boolean isEmpty() 
    {
        return this.size == 0;
    }
    
    public Node<T> getRoot()
    {
        return this.root;
    }
    
    private void buildTree(T[] vals)
    {
        for (int i = 0; i < vals.length; i++)
        {
            insert(new Node<>(vals[i]));
        }
    }
    
    public void inorderTreeWalk(Node<T> node)
    {
        if (node != null)
        {
            inorderTreeWalk(node.left);
            System.out.print(node.key + " ");
            inorderTreeWalk(node.right);
        }
    }
    
    public Node<T> search(Node<T> node, T key)
    {
        if (node == null || node.key.equals(key))
        {
            return node;
        }
        else if (key.compareTo(node.key) < 0)
        {
            return search(node.left, key);
        }
        else
        {
            return search(node.right, key);
        }
        
    }
    
    public Node<T> minimum(Node<T> node)
    {
        while (node.left != null)
        {
            node = node.left;
        }
        
        return node;
    }
    
    public Node<T> maximum(Node<T> node)
    {
        while (node.right != null)
        {
            node = node.right;
        }
        
        return node;
    }
    
    public Node<T> successor(Node<T> node)
    {
        if (node.hasRight())
        {
            return minimum(node.right);
        }
        else
        {
            Node<T> parent = node.parent;
            
            while (parent != null && node.isRightChild())
            {
                node = parent;
                parent = node.parent;
            }
            
            return parent;
        }
    }
    
    public Node<T> predecessor(Node<T> node)
    {
        if (node.hasLeft())
        {
            return maximum(node.left);
        }
        else
        {
            Node<T> parent = node.parent;
            
            while (parent != null && node.isLeftChild())
            {
                node = parent;
                parent = node.parent;
            }
            
            return parent;
        }
    }
    
    private void transplant (Node<T> u, Node<T> v)
    {
        if (u.parent == null)
        {
            root = v;
        }
        else if (u.isLeftChild())
        {
            u.parent.left = v;
        }
        else
        {
            u.parent.right = v;
        }
        
        if (v != null)
        {
            v.parent = u.parent;
        }
    }
    
    public boolean delete(T key)
    {
        Node<T> node = get(key);
        
        if (node == null)
        {
            System.out.println(key + " not found!");
            return false;
        }
        
        return delete(node);
    }
    
    public boolean delete(Node<T> node)
    {
        boolean deleted = false;
        
        if (!node.hasLeft())
        {
            transplant(node, node.right);
            node = null;
            this.size -= 1;
            
            deleted = true;
        }
        else if (!node.hasRight())
        {
            transplant(node, node.left);
            node = null;
            this.size -= 1;
            deleted = true;
        }
        else if (node.hasLeft() && node.hasRight())
        {
            Node<T> successor = minimum(node.right);
            
            if (successor.parent != node)
            {
                transplant(successor, successor.right);
                successor.right = node.right;
                successor.right.parent = successor;
            }
            
            transplant(node, successor);
            successor.left = node.left;
            node.left.parent = successor;
            
            node = null;
            this.size -= 1;
            
            deleted = true;
        }
        
        return deleted;
    }
    
    public Node<T> insert(T val)
    {
        Node<T> newNode = new Node(val);
        insert(newNode);
        
        return newNode;
    }
    
    private void insert(Node<T> newNode)
    {
        Node<T> parent = null;
        Node<T> node = root;
        
        while (node != null)
        { 
            parent = node;
            
            if (newNode.key.compareTo(node.key) < 0)
            {
                node = node.left;
            }
            else
            {
                node = node.right;
            }
        }
        
        newNode.parent = parent;
        
        if (parent == null)
        {
            root = newNode;
        }
        else if (newNode.key.compareTo(parent.key) < 0)
        {
            parent.left = newNode;
        }
        else
        {
            parent.right = newNode;
        }
        
        this.size += 1;
    }
    
    public int getLevel(Node<T>node) {
        if (this.root == null || node == null) {
            return -1;
        }
        
        Node<T> iter = this.root;
        int level = 0;
        
        while (iter != null && iter != node) {
            if (node.key.compareTo(iter.key) < 0) {
                iter = iter.left;
                level += 1;
            }
            else {
                iter = iter.right;
                level += 1;
            }
        }
        
        return level;
    }
    
    public Node<T> get(T val) {
        return this.search(this.root, val);
    }
    
    public boolean has(T val) {
        return (this.search(this.root, val) != null);
    }
    
}
