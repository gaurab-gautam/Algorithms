/***********************************************************************
Node class
comment: Node of linked list
************************************************************************/

module.exports = class Node 
{
    constructor(data) 
    {
        this._data = data;
        this._previous = null;
        this._next = null;
    }
	
    // Getters:
    get data() 
    {
        return this._data;
    }

    get previous() 
    {
        return this._previous;
    }

    get next() 
    {
        return this._next;
    }

    // Setters:
    set data(value)
    {
        this._data = value;
    }
    
    set previous(value) 
    {
        this._previous = value;
    }
	
    set next(value) 
    {
        this._next = value;
    }

    set value(data) 
    {
        this._value = data;
    }
};