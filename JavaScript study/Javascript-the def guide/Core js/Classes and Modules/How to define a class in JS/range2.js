//range2.js
//Created by Jie Li, on 1/3/2017
//Define another Javascript clss by using constructor and prototype

//define a constructor
function Range2(from, to){//the constructor would be invoked as the method of the newly created object
  this.from = from;
  this.to = to;
  //no need to return a value. The newly created object would be created.
}

//change the prototype of the constructor
Range2.prototype = {
  includes: function(x){
    return this.from <= x && x <= this.to;
  },
  foreach: function(f){
    for(var i = Math.ceil(this.from); i < this.to; i++) f(i);
  },
  //return a string representation of the range
  toString: function(){
    return "(" + this.from + "..." + this.to + ")";
  }
};

//Here are example uses of a range object
var r = new Range2(1,3);
console.log(r.includes(2));
r.foreach(console.log);
console.log(r);//test r.toString()
