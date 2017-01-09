//Created by Jie Li, on Jan 8

var s = "6*( 2+ 2 +5)+ 7";
var i = 0;//At the beginning of s
var stack1 = new Stack();//for operators
var stack2 = new Stack();//for postfix expression

//Add functions to String.prototype
String.prototype.isOperator = function(){
  var string_value = this.valueOf();
  //use conditional statement to decide
  if(string_value === "*"||
    string_value === "/" ||
    string_value === "+" ||
    string_value === "-" ||
    string_value === "(" ||
    string_value === ")"
  ){
    return true;
  }else{
    return false;
  }
};

String.prototype.isDigit = function(){
  var string_value = this.valueOf();
  //use conditional statement to decide , later I'll use regular expression
  if(string_value === "0"||
    string_value === "1"||
    string_value === "2"||
    string_value === "3"||
    string_value === "4"||
    string_value === "5"||
    string_value === "6"||
    string_value === "7"||
    string_value === "8"||
    string_value === "9"
  ){
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
      stack1.pop();
    }else if(previous !== "(" && compareOperators(next, previous)!= 1){
      stack2.push(stack1.pop());//pop previous
      stack1.push(next);//push next
    }else if(previous === "(" || compareOperators(next, previous) === 1){
      stack1.push(next);
    }
  }

}
//number: string value of a number
function processNumber(number){
  stack2.push(number);
}

//operatorList is used to compare the precedences among operators
var operatorList = {
  "+": 0, "-": 0, "*": 1, "/": 1, "(": 2, ")":2
}
//return 1, if first operator has higher precendence than next
//return 0, if first has the same precendence as second
//return -1, if first has lower precendene as second
function compareOperators(first, second){
  return operatorList[first] - operatorList[second];

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
console.log(stack2.toString());
//reverse the stack
while(stack2.peek()){
  stack1.push(stack2.pop());
}
console.log(stack1.toString());
