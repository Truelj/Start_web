//Create a new Operator class with the corresponding values that are in the argument
//include "Enumeration.js"
var Operator = enumeration({
  "+":0, "-":0,
  "*":1, "/":1,
  "(":2, ")":2
});
//Operator has two properties, values and foreach
//add other properties to Operator class

//compare operators.
//first: string
//second: string
//return 1, if first has the higher precendence than second
//return 0, if first has the same precendence as second
//return -1, if first has the lower precendene than second
Operator.compare = function(first, second){
  return this[first].value - this[second].value;
};
