/***********************************************************************
LinkedList class
comment: Singly linkedlist; adds item to the end of the list
         Can also be implemented without tail if item is added 
         at the head (See linkedlist.js)
************************************************************************/
const Node = require('./node');

module.exports = class LinkedList 
{
    constructor() {
        // head of this list
        this._head = null;

        // points to the tail of this list
        this._tail = null;

        // length of list
        this._length = 0;
    }

    get length() 
    {
        return this._length;
    }

    isEmpty() 
    {
        return (this._length === 0);
    }
	
    add(value) 
    {
        if (this.isEmpty()) 
        {
            this._head = new Node(value);
            this._tail = this._head;
            this._length += 1;
        }
        else 
        {
            let node = new Node(value);

            // last node's next is new node
            this._tail.next = node;

            // tail of linked list is new node
            this._tail = node;

            this._length += 1;
        }
    }
	
    addRange(collection) 
    {
        collection.forEach( (value) => {
                this.add(value);
        });
    }

    remove(value) 
    {
        // node is head
        if (this._head.data === value)
        {
            let node = this._head;
            this._head = node.next;
            
            // node is also tail
            if (this._tail === node)
            {
                this._tail = null;
            }
                
            // destroy node
            node = null;
            this._length -= 1;
            
            return true;
        }
        // node is not head
        else 
        {
            // node is not head so its parent exists
            let parent = this._findParent(value);
            
            // node doesn't exist
            if (parent === null)
            {
                return false;
            }
            
            // get the node
            let node = parent.next;
            
            parent.next = node.next;
                                
            // node is tail of list
            if (this._tail === node)
            {
                this._tail = parent;
            }
                        
            // destroy node
            node = null;
            this._length -= 1;
            
            return true;
        }
    }

    has(value) 
    {
        let iter = this._head;

        while (iter !== null)
        {
            // search successful if condition holds
            if (value === iter.data)
            {
                return true;
            }
            
            iter = iter.next;
        }
        
        // search unsuccessful
        return false;
    }
    
    // returns parent of found value
    // null if not found or is head of the list
    _findParent(value) 
    {
        let iter = this._head;
        let parent = null;
        
        while (iter !== null)
        {
            // search successful if condition holds
            if (value === iter.data)
            {
                // returns parent
                return parent;
            }
            
            // save parent node
            parent = iter;
            iter = iter.next;
        }
        
        // search unsuccessful
        return null;
    }
	
    find(value)
    {
        // node is head
        if (this._head.data === value)
        {
           return this._head;
        }
        // node is tail
        else if ( this._tail.data === value)
        {
            return this._tail;
        }
        else
        {
            return this._findParent(value).next;
        }
    }
    
    print()
    {
        let iter = this._head;
        let stringBuilder = "[ ";

        while (iter !== null) 
        { 
            stringBuilder += iter.data;

            if (iter.next !== null) {
                 stringBuilder += ", ";
            }

            iter = iter.next;
        }

        stringBuilder += " ]";
        console.log(stringBuilder);
    }
};




