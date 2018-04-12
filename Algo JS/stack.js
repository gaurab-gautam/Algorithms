/***********************************************************************
Stack module
Comments: Stack implementation with array
************************************************************************/

module.exports = class Stack {
	constructor() {
		this._MAX_SIZE = 5;
		this._stack = [];
		this._size = 0;
		this._top = -1;
	}
	
	get length() {
		return this._size;
	}
	
	print() {
		let values = "[";
		
		for (let i = 0; i < this._size; i++) {
			values += this._stack[i];
			
			if (i != (this._size - 1)) {
				values += ", ";
			}
			else {
				values += "]";
			}
		}
		
		console.log(values);
	}
	
	push(val) {
		if (this._size == this._MAX_SIZE) {
			console.log("Stack is full!");
			return;
		}
		
		this._top += 1;
		this._size += 1;
		
		this._stack[this._top] = val;
	}
	
	pop() {
		if (this.isEmpty()) {
			console.log("Stack is empty!");
			return;
		}
		
		let val = this._stack[this._top];
		
		this._top -= 1;
		this._size -= 1;
		
		return val;
	}
	
	isEmpty() {
		return (this._size == 0);
	}
}



