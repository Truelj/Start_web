//range2(fiexed).js
//Created by Jie Li, on 1/3/2017
//Add a property named "constructor" to the prototype of the constructor

//define a constructor
function Range3(from, to){//the constructor would be invoked as the method of the newly created object
  this.from = from;
  this.to = to;
  //no need to return a value. The newly created object would be created.
}

//change the prototype of the constructor
Range3.prototype = {
  constructor: Range3,

  includes: function(x){
    return this.from <= x && x <= this.to;
  },
  foreach: function(f){
    for(var i = Math.ceil(this.from); i < this.to; i++) f(i);
  },
  //return a string representation of the range
  toString: function(){
    return "(" + this.from + "..." + this.to + ")";
  },
  equals: function(that){
    if(!(that instanceof Range3)){
        return false;
    }
    return this.from === that.from && this.to === that.to;
  },
  compareTo: function(that){
    if(!(that instanceof Range3)){
        throw new Error("Can't compare a Range with " + that);
    }
    var diff = this.from - that.from;
    if(diff === 0){
        diff = this.to - that.to;
    }
    return diff;
  }
};

