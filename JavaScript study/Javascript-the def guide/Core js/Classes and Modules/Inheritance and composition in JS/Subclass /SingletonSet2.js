//Created by Jie Li, on Jan 12, 2017

//use defineSubset() to create a subclass of Set
//include: defineSubclass.js

//the only way to define the subclass constructor
function SingletonSet(member){
    this.member = member;
}
defineSubclass(Set, SingletonSet,
            {   add: function(){throw "read-only set";},
                remove: function(){throw "read-only set";},
                contains: function(value){return this.member === value;},
                size: function(){return 1;},
                equals: function(that){return (that instanceof Set) && that.size() === 1 && that.contains(this.member)},
                foreach: function(f,c){return f.call(c, this.member)}
});

//test
var s1 = new Set(1);
var s2 = new SingletonSet(1);
console.log("s2.constructor: " + s2.constructor);
console.log("s2.equals(s1): " + s2.equals(s1));
console.log("s1.equals(s2): " + s1.equals(s2));