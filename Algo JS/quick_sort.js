/***********************************************************************
quicksort module
A: Array to be sorted
startIndex: starting index of the array/subarray
length: length of the array
************************************************************************/

// import the partition module
var partition = require('./partition');

exports.sort = function (A) {
	quickSort(A, 0, A.length);
}

function quickSort(A, startIndex, length) {
	// base case
	if (startIndex >= (length - 1)) {
		return A;
	}
	
	var pivotIndex = choosePivotIndex(startIndex, length - 1);
	
	swap (A, startIndex, pivotIndex);
	pivotIndex = partition.partition(A, startIndex, length - 1);	
	
	quickSort(A, startIndex, pivotIndex);	// pass pivot index as last argument is length of the array
	quickSort(A, pivotIndex + 1, length);	
	
	return A;
}

/***********************************************************************
swap function
A: Source 
i: index of an item to be swapped
j: index of another item to be swapped with
************************************************************************/
function swap(A, i, j) {
	temp = A[i];
	A[i] = A[j];
	A[j] = temp;
	
	return A;
}

/***********************************************************************
randomNumber function
min: minimum seed
max: maximum seed
comment: returns random number between min and max, inclusively
************************************************************************/
function randomNumber(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
  
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

/***********************************************************************
choosePivotIndex function
i: starting index of the subarray
j: ending index of the subarray
comment: returns the pivot index that is randomly choosen between the
		 provided interval [i, j]
************************************************************************/
function choosePivotIndex(i, j) {
	return randomNumber(i, j);
}
