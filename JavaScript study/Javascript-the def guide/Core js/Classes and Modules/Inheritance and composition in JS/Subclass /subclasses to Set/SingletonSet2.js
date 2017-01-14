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
