//include Set.js
var set1 = new Set(1,2,3);

//test standard methods
console.log("set1.toString(): " + set1.toString());
console.log("set1.toLocaleString(): " + set1.toLocaleString());
console.log("set1.toArray(): " + set1.toArray());
console.log("JSON.stringify(set1): " + JSON.stringify(set1));//[1,2,3,null]

//test standard conversion methods
var set2 = new Set(1,2,3);
console.log("set2 contain 1: " + set2.contains(1));
console.log("set1.equals(set2): " + set1.equals(set2)); //true
console.log("set1 === set2: " + (set1 === set2)); //false


var set3 = new Set([1,2,3]);
console.log("set3.size = " + set3.size);//3
console.log("set3.toString(): " + set3.toString());

var set4 = new Set();
set4.addArrayMembers([1,2,3]);
set4.add([4,5,6]);
console.log("set4.size = " + set4.size);//3 + 1
console.log("set4.toString(): " + set4.toString());

var set5 = new Set([1,2,3], 4,5,6);
console.log("set5.size = " + set5.size);//1 + 3
console.log("set5.toString(): " + set5.toString());

