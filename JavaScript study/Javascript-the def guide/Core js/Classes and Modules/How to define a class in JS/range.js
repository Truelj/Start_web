//range.js
//created by Jie Li, on 1/3/2017
//Define a javascript class by defining a factory function that create a new object by inheriting another object

//This is a factory function that returns a new range object
function range(from, to){
  var r = inherit(range.methods);//r is a newly created object that inherits range.methods
  //set properties of r
  r.from = from;
  r.to = to;
  return r;
}
//This prototype object defines methods inherited by all range objects
range.methods = {
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

//creating a new object that inherits from a prototype
function inherit(p){
  if(p == null) throw TypeError();
  if(Object.create){
    return Object.create(p);
  }
  if(typeof p !== "Ojbect" && typeof p !== "function") throw TypeError();
  function f(){};
  f.prototype = p;//change the prototype of f
  return new p();
}

//Here are example uses of a range object
var r = range(1,3);
console.log(r.includes(2));
r.foreach(console.log);
console.log(r);//test r.toString()
