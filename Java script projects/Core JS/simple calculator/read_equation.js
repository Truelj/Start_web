//Created by Jie Li, on Jan 8
//include "Enumeration.js"
//include "Operator.js"
var s = "2+3 * 6 + 3";
var i = 0;//At the beginning of s
//var stack1 = new Stack();//for operators
//var stack2 = new Stack();//for postfix expression
var stack1 = [];
var stack2 = [];

//Add function to Array.prototype
Array.prototype.peek = function(){
  if(this.length > 0){
    return this[this.length-1];
  }else{
    return undefined;
  }
}

//Add functions to String.prototype
String.prototype.isOperator = function(){
  return this.valueOf() in Operator;
};

String.prototype.isDigit = function(){
  var string_value = this.valueOf();
  //use conditional statement to decide , later I'll use regular expression
  if(string_value >= "0" && string_value <= "9"){
    return true;
  }else{
    return false;
  }
};

//utility functions
//next: string value of an operator
function processOperator(next){
  var previous = stack1.peek();
  if(!previous){//previous is "undefined"
    stack1.push(next);
  }else{
    if(next === ")"){
      while(stack1.peek() !== "("){
        stack2.push(stack1.pop());
      }
      stack1.pop();//pop "("
    }else if(previous !== "(" && Operator.compare(next, previous)!= 1){
      stack2.push(stack1.pop());//pop previous
      stack1.push(next);//push next
    }else if(previous === "(" || Operator.compare(next, previous) === 1){
      stack1.push(next);
    }
  }

}
//number: string value of a number
function processNumber(number){
  stack2.push(number);
}



//read the expression while converting it into the postfix expression
while(i < s.length){
  if(s[i].isOperator()){
    console.log("operator " + s[i]);
    processOperator(s[i]);
    i++;
    //deal with the operator
  }else if(s[i].isDigit()){
    var number = s[i];
    i++;
    while(i < s.length && s[i].isDigit()){
      number += s[i];
      i++;
    }
    //deal with the number
    console.log("number "+ number);
    processNumber(number);
  }else{
    //ignore other charactors
    i++;
  }

}//end of reading string
//console.log(stack1.toString());
while(stack1.peek()){
  stack2.push(stack1.pop());
}
//end of convertion to postfix expression
//console.log(stack2.toString());
console.log(stack2);
