//created by Jie Li, on Jan 12, 2017

//Invoked on a superclass constructor to get a subclass constructor
//constructor: subclass constructor
//methods: instance methods that would be added to constructor.prototype
//statics: properties that would be added to constructor
Function.prototype.extend = function(constructor, methods, statics){
    define(this, constructor,methods,statics);
}