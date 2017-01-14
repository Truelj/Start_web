//Use the following code to test SingletonSet.js and SingletonSet2.js
var s1 = new Set(1);
var s2 = new SingletonSet(1);
console.log("s2.constructor: " + s2.constructor);
console.log("s2.equals(s1): " + s2.equals(s1));
console.log("s1.equals(s2): " + s1.equals(s2));