/*********************************************************
 partition module
 A: Array to be partitioned
 startIndex: Index of the first element of the array to be 
			 partitioned
 length: length of the array to be partitioned
*********************************************************/

exports.partition = function (A, startIndex, endIndex) {
	var p = A[startIndex];
	var i = startIndex + 1;
	
	for (var j = i; j <= endIndex; j++) {
		if (A[j] < p) {
			swap (A, i, j);
			i++;
		}
	}
	
	swap (A, i - 1, startIndex);
	
	return i - 1;	// return pivot index
}


/*********************************************************
 swap function
 A: Array whose elements are being swapped
 i: Index of the first element to be swapped
 j: Index of the second element to be swapped
*********************************************************/
var swap = function swap(A, i, j) {
	var temp = A[i];
	A[i] = A[j];
	A[j] = temp;
	
	return A;
}