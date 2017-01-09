//Created by Jie Li
//Implement the idea of Stack using JS
//Provide basic stack operations such as push(), pop(), isEmpty(),isFull()

//A stack constructor that defines a class of Stack objects
function Stack(){
  this.top = null;//empty stack by default
}

//A Node constructor that defines a class of nodes used in Stack
function StackNode(data, next){
  this.data = data;
  this.next = next;
}

//Add Stack operations
Stack.prototype.push = function(data){
    var next = new StackNode(data, this.top);
    //console.log(data);
    this.top = next;
    this.currentLength++;
};
Stack.prototype.pop = function(){
  if(this.isEmpty()){
    return undefined;
  }else{
    var data = this.top.data;
    this.top = this.top.next;
    this.currentLength--;
    return data;
  }
};
Stack.prototype.peek = function(){
  if(this.isEmpty()){
    return undefined;
  }else{
    return this.top.data;
  }
}

Stack.prototype.isEmpty = function(){
  return this.top === null;
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
