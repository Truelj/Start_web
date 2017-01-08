//Created by Jie Li
//Implement the idea of Stack using JS
//Provide basic stack operations such as push(), pop(), isEmpty(),isFull()

//A stack constructor that defines a class of Stack objects
function Stack(capacity){
  this.capacity = capacity;
  this.currentLength = 0;//default empty stack
  this.top = null;
}

//A Node constructor that defines a class of nodes used in Stack
function StackNode(data, next){
  this.data = data;
  this.next = next;
}

//Add Stack operations
Stack.prototype.push = function(data){
  if(this.isFull()){
    //throw an exception
    throw new Error("Stack overflow");
  }else{
    var next = new StackNode(data, this.top);
    //console.log(data);
    this.top = next;
    this.currentLength++;
  }
};
Stack.prototype.pop = function(){
  if(this.isEmpty()){
    //throws an exception
    throw new Error("stack is empty")
  }else{
    var data = this.top.data;
    this.top = this.top.next;
    this.currentLength--;
    return data;
  }
};

Stack.prototype.isEmpty = function(){
  if(this.currentLength === 0){
    return true;
  }else{
    return false;
  }
};

Stack.prototype.isFull = function(){
  if(this.currentLength === this.capacity){
    return true;
  }else{
    return false;
  }
};

Stack.prototype.toString = function(){
  var next = this.top;
  var returnString = "";
  while(next){
    returnString += next.data;
    returnString += " ";
    next = next.next;
  }
  return returnString;
};
//test
//create a stack of length 10;
var stack1 = new Stack(10);
//add digit 0 - 9 to stack1
for(var i = 0; i < 11; i++){
  try{
    stack1.push(i);
  }catch(error){
    console.log(error);
    break;
  }
}

//print the stack
console.log(stack1.toString());//shoulde be 9 8 7 ....1 0

//try pop
for(i = 0; i < 11; i++){
  try{
    stack1.pop();
  }catch(error){
    console.log(error);
    break;
  }
}
//print the stack
console.log(stack1.toString());//shoulde be nothing
