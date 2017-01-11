//include Set.js
var set1 = new Set(1,2,3,{name: "Jessie"},{name:"Louis"});

//test standard methods
console.log("set1.toString(): " + set1.toString());
console.log("set1.toLocaleString(): " + set1.toLocaleString());
console.log("set1.toArray(): " + set1.toArray());
console.log("JSON.stringify(set1): " + JSON.stringify(set1));//[1,2,3,null]

var set2 = new Set(1,2,3, {name: "Jessie"});
console.log("set2 contain 1: " + set2.contains(1));
console.log("set1.equals(set2): " + set1.equals(set2)); //false
