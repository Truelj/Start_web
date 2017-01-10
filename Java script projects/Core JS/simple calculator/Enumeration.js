//Created by Jie Li, on Jan 9, 2017
//This function creates a new enumerated type.
//The argument object specifies the names and values of each instance of the class.
//The return value is a constructor function that identifies the new class.
//Note, however htat the constructor throws an exception: you can't use it to create
//new instances of the type. The returned constructor has properties that map the name of
//a value to the value itself, and also a values array, a foreach() iterator function

function enumeration(namesToValues){
  var enumeration = function(){throw "Can't instantiate Enumerations"};
  enumeration.prototype = {
    constructor: enumeration,
    toString: function(){ return this.name},
    valueOf: function(){return this.value},
    toJSON: function(){return this.name},
  };
  enumeration.values = [];//to store all instances of enumeration

  function f(){};
  f.prototype = enumeration.prototype;

  for(name in namesToValues){
    var e = new f();
    //add instance properties to e
    e.name = name;
    e.value = namesToValues[name];
    //make it a property of the constructor
    enumeration[name] = e;
    enumeration.values.push(e);
  }

  enumeration.foreach = function(f,c){
    for(var i = 0; i < this.values.length; i++){
      f.call(c,this.values[i]);
    }
  };

  return enumeration;
}

//test the enumeration function
var Coin = enumeration({Penny: 1, Nickel: 5, Dime: 10, Quarter: 25});
var c = Coin.Dime;
console.log(Coin.Penny);
console.log(Coin.Nickel);
console.log(Coin.Dime);
console.log(Coin.Quarter);
console.log(c instanceof Coin);//true
console.log(Coin.Dime * 10);//100
console.log(String(Coin.Penny));//Penney
console.log(String(Coin.Penny) + ": " + Coin.Penny);//Penny: 1
