//Here are example uses of a range object
var r1 = new Range3(1,3);

var r2 = new Range3(1,4);
console.log("r1.equals(r2) : " + r1.equals(r2));//false;
console.log("r1.compareTo(r2) : " + r1.compareTo(r2));// <0

//create an array of range
var ranges = [];
ranges.push(new Range3(1,3));
ranges.push(new Range3(1,4));
ranges.push(new Range3(2,2));

ranges.sort(function(a,b){return a.compareTo(b)});
console.log(ranges);