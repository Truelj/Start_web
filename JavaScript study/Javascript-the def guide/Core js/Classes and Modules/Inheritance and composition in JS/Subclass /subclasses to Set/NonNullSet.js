//Created by Jie Li, on Jan 13, 2017

//NonNullSet is a subclass of Set. It modifies the add() method, so null and undefined values won't be added to the set
function NonNullSet(){
    //constructor chaining
    //Set.apply(this, arguments);
}

NonNullSet.prototype = inherit(Set.prototype);
//NonNullSet.prototype = new Set(1,2);
NonNullSet.prototype.constructor = NonNullSet;

NonNullSet.prototype.add = function(){
    for(var i = 0; i < arguments.length; i++){
        if(arguments[i]==null){
            throw new Error("Can't add '" + arguments[i] + "' to a NonNullSet");
        }
    }
    //Chain to the superclass to perfomr the actual insertion
    return Set.prototype.add.apply(this, arguments);
}

