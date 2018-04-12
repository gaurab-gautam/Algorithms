/***********************************************************************
Node class
comment: 
************************************************************************/
const VisitedState = require('./visitedstate');
let Color = require('./color');

module.exports = class Vertex {	
	// constructor for Node class
	constructor(name, index) 
        {
            this._name = name;
            this._visited = VisitedState.NOT_VISITED;
            this._discoveryTime = Number.MAX_VALUE - 1000; // prevent overflow 
            this._finishTime = Number.MAX_VALUE - 1000;
            this._color = Color.DEFAULT_COLOR;
            this._parent = null;
            this._index = index;
            this._distance = Number.MAX_VALUE - 1000;   // prevent overflow when incremented
            this._edges = [];
	}
        
        
	//Getters:
	get index() {
		return this._index;
	}
	
	get name() {
		return this._name;
	}
	
	get visited() {
		return this._visited;
	}
	
	get discoveryTime() {
		return this._discoveryTime;
	}
	
	get finishTime() {
		return this._finishTime;
	}
	
	get color() {
		return this._color;
	}
	
	get parent() {
		return this._parent;
	}
	
	get distance() {
		return this._distance;
	}
	
	get edges() {
		return this._edges;
	}
	
	//Setters:
	set visited(value) {
		this._visited = value;
	}
	
	set discoveryTime(value) {
		this._discoveryTime = value;
	}
	
	set finishTime(value) {
		this._finishTime = value;
	}
	
	set color(value) {
		this._color = value;
	}
	
	set parent(value) {
		this._parent = value;
	}
	
	set distance(value) {
		this._distance = value;
	}
	
	addEdge(name, weight) {
		// create an edge JSON object
		let edge = {'name' : name, 'weight' : weight};
		this._edges.push(edge);
	}
};
