/***********************************************************************
Queue module
Comments: Circular queue implementation with array
************************************************************************/

module.exports = class Queue {
	constructor() {
		this._MAX_SIZE = 10;
		this._head = 0;
		this._tail = 0;
		this._queue = [];
		this._size = 0;
	}
	
	isEmpty() {
		return (this._size === 0);
	}
	
	get length() {
		return this._size;
	}
        
        get items() {
            return this._queue;
        }
        
        peek() {
            return this._queue[this._head];
        }
	
	enqueue(val) {
//            console.log("before enqueue");
//            console.log(this);
            if (this._size === this._MAX_SIZE) {
                console.log("Queue is full!");
                return;
            }

            if (!this.isEmpty()) {
                this._tail += 1;

                if (this._tail === this._MAX_SIZE) {
                        this._tail = 0;
                }
            }	

            this._queue[this._tail] = val;
            this._size += 1;
            
//            console.log("after:");
//            console.log(this);
	}
	
	dequeue() {
            if (this.isEmpty()) {
                console.log("Queue is empty!");
                return;
            }

//                console.log(this);
//                console.log("dequeing:");

            let val = this._queue[this._head];
            this._queue[this._head] = undefined;
            this._head += 1;
            this._size -= 1;

            if (this._head === this._MAX_SIZE) {   
                    this._head = 0;
            }

            // update tail and head if queue is empty
            if (this.isEmpty()) {
                this._tail = 0;
                this._head = 0;
            }

//                console.log(val);

            return val;
	}
	
	print() 
        {
            let values = "[";
            let sizecount = 0;
            
            for (let i = 0; i < this._MAX_SIZE; i++) 
            {
                if (this._queue[i] !== undefined)
                {
                    values += this._queue[i];
                    sizecount += 1;
                    
                    if (sizecount === this._size) 
                    {
                        values += "]";
                        break;
                    }
                    else {
                            values += ", ";
                    }
                }
            }
            
            console.log(values);
	}
};
