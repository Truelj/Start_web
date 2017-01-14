//Created by Jie Li, Jan 12, 2017
//Define a subclass of Set 
//Include Set.js, inherit.js

//The singleton set contains only one member
function SingletonSet(member){//constructor of singleton set
    this.member = member;
}

//set the prototype object
SingletonSet.prototype = inherit(Set.prototype);
SingletonSet.prototype.constructor = SingletonSet;

//This set is read-only.
SingletonSet.prototype.add = function(){
    throw "ready-only set";
};

SingletonSet.prototype.remove = function(){
    throw "read-only set";
};

//Because this set has only one member,...
SingletonSet.prototype.size = function(){
    return 1;
};

SingletonSet.prototype.contains = function(value){
    return this.member === value;
};

SingletonSet.prototype.forEach = function(f,c){
    f.call(c, this.member);
};

SingletonSet.prototype.equals = function(that){
    return (that instanceof Set) && that.size() === 1 && that.contains(this.member);
};



