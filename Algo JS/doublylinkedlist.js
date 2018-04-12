/***********************************************************************
LinkedList class
comment: Doubly linkedlist, adds item to the head of list
         For easier implentation, see circular linkedlist
************************************************************************/
const Node = require('./node');

module.exports = class LinkedList 
{
    constructor() {
        // head of this list
        this._head = null;

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
            this._length += 1;
        }
        else 
        {
            let node = new Node(value);
            let child = this._head;
            
            node.next = child;
            child.previous = node;
            this._head = node;
            
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
        let node = this.find(value);
        
        // node found
        if (node !== null)
        {
            // get the parent and child
            let parent = node.previous;
            let child = node.next;
            
            // node is not head
            if (parent !== null)
            {
                parent.next = child;
            }
            else
            {
                this._head = child;
            }
            
            // if child exists
            if (child !== null)
            {
                child.previous = parent;
            }
                        
            // destroy node
            node = null;
            this._length -= 1;
            
            return true;
        }
        
        return false;
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
	
    find(value)
    {
        let iter = this._head;
        
        while (iter !== null)
        {
            if (iter.data === value)
            {
                return iter;
            }
            
            iter = iter.next;
        }
        
        return null;
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




