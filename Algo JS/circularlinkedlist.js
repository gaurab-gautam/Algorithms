/***********************************************************************
 * LinkedList class - Circular linkedlist, adds item to the head of list
************************************************************************/
const Node = require('./node');

// colors for console error printout
const BACKGROUND_COLOR =    '\x1b[41m';            // red
const FOREGROUND_COLOR =    '\x1b[37m';            // white
const RESET =               '\x1b[0m';             // reset color

module.exports = class LinkedList {
    constructor()
    {
        this._head = new Node();
        
        // circular in nature
        this._head.previous = this._head;
        this._head.next = this._head;
        
        this._length = 0;
    }
    
    get length()
    {
        return this._length;
    }
    
    isEmpty()
    {
        return (this.length === 0);
    }
    
    has(value)
    {
        let iter = this._head.next;
        
        while (iter !== this._head)
        {
            if (iter.data === value)
            {
                return true;
            }
            
            iter = iter.next;
        }
        
        return false;
    }
    
    clear()
    {
        let iter = this._head.next;
        
        // iterate through the list
        while (iter !== this._head)
        {
            let node = iter;
            
            iter = iter.next;
            node = null;
            this._length -= 1;
        }
        
        // point to oneself
        this._head.previous = this._head;
        this._head.next = this._head;
    }
    
    _find(value)
    {
        let iter = this._head.next;
        
        while (iter !== this._head)
        {
            // item found
            if (iter.data === value)
            {
                return iter;
            }
            
            iter = iter.next;
        }
        
        // item not found
        return null;
    }
    
    get(index)
    {
        let iter = this._head.next;
        let i = 0;
        
        while (iter !== this._head)
        {
            if (i === index)
            {
                return iter.data;
            }
            
            i += 1;
            iter = iter.next;
        }
        
        return null;
    }
    
    set(index, value)
    {
        let iter = this._head.next;
        let i = 0;
        
        while (iter !== this._head)
        {
            if (i === index)
            {
                iter.data = value;
                return true;
            }
            
            i += 1;
            iter = iter.next;
        }
        
        return false;
    }
    
    insert(index, value)
    {
        // bound check
        if ((index < 0) || (index >= this._length)) 
        {
            console.log(BACKGROUND_COLOR, FOREGROUND_COLOR, "Index out of bounds!!!", RESET);
            
            return false;
        }
        
        if (index === 0)
        {
            add(value);
            return true;
        }
        // if index > 0 in an empty list, add item at the head
        else if ((index !== 0) && (this.isEmpty()))
        {
            add(value);
            return true;;
        }
        
        let iter = this._head.next;
        let i = 0;
        
        while (iter !== this._head)
        {
            if (i === index)
            {
                let parent = iter.previous;
                
                let node = new Node(value);
                
                node.next = iter;
                node.previous = parent;
                iter.previous = node;
                parent.next = node;
                
                this._length += 1;
                
                return true;
            }
            
            i += 1;
            iter = iter.next;
        }
        
        return false;
    }
    
    remove(value)
    {
        let iter = this._find(value);
        
        // item found
        if (iter !== null)
        {
            let child = iter.next;
            let parent = iter.previous;
            
            parent.next = child;
            child.previous = parent;
            iter = null;
            
            this._length -= 1;
            
            return true;
        }
        
        return false;
    }
    
    add(value)
    {
        let node = new Node(value);
            
        if (this.isEmpty())
        {
            this._head.next = node;
            this._head.previous = node;
            node.previous = this._head;
            node.next = this._head;
        }
        else
        {
            let head = this._head.next;
            
            this._head.next = node;
            node.next = head;
            head.previous = node;
            node.previous = this._head;
        }
        
        this._length += 1;
    }
    
    addRange(collection)
    {
        collection.forEach( item => {
            this.add(item);
        });
    }
    
    
    print()
    {
        // empty list
        if (this.isEmpty())
        {
            console.log("[ ]");
            return;
        }
        
        let iter = this._head.next;
        let stringBuilder = "[";
        
        while (iter !== this._head) 
        {
            stringBuilder += " ";
            stringBuilder += iter.data;
            stringBuilder += ",";
            iter = iter.next;
        }
        
        let regex = /,([^,]*)$/;
        stringBuilder = stringBuilder.replace(regex, " ]");
        
        
        console.log(stringBuilder);
    }
};