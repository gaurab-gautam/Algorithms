/**
 *
 * @author Gaurab R. Gautam
 */
package datastructures;

// circular node
public class Node<T>
{
    T value;
    Node<T> previous = this;
    Node<T> next = this;

    Node(T val)
    {
        this.value = val;
    }
    
    Node()
    {
        // copy constructor
    }
}
