//type.js
//Created by Jie Li, 1/3/2017

//Return the type of o as a string
//If o is null, return "null". if o is NaN, return "nan"
//If typeof returns a value other than "object", return that value
//If the class of o is anything other than "Object", return that;
//If o has a constructor and that constructor has a name, return it.
//otherwise, just return "Object".

function type(o){
  var t, c, n;//type, class, name
  if(o === null) return "null";
  if(o !== o) return "nan";
  if((t = typeof o) !== "object") return t;
  if((c = classof(o)) !== "Object") return c;
  if(o.constructor && typeof constructor === "function" && (n = o.constructor.getName())) return n;
  return "Object...";
}

//return the class of an object
function classof(o){
  return Object.prototype.toString.call(o).slice(8,-1);
}

//return the name of a function or null for non funciton
Function.prototype.getName = function(){
  if("name" in this ) return this.name;
  return this.name = this.toString().match(/function\s*([^(]*)\(/)[1];
};

console.log(type(2));//number
console.log(type(function(){}));//function
console.log(type(new Array(10)));//array
//Object.prototype.constructor === Object;
console.log(Object.prototype.constructor === Object);//true
console.log({}.constructor.getName());//Object
console.log(type({}));//Object
console.log(type(Object.create({})));//Object
console.log(type(Object.create(new classof())));//classof

console.log(type(new classof({})));//classof
