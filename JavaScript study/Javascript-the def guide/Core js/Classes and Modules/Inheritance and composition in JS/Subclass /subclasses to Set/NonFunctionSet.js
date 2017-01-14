function NonFunctionSet(){
    Set.apply(this, arguments);
    //this.addArrayMembers([1,2,3]);
}

//NonFunctionSet.prototype = new Set(1,2);
NonFunctionSet.prototype = inherit(Set.prototype);
NonFunctionSet.prototype.constructor = NonFunctionSet;

//var s1 = new NonFunctionSet(3,4);
//console.log("s1.size(): " + s1.size() );//2
//console.log("NonFunctionSet.prototype.size(): " + NonFunctionSet.prototype.size() );//2


NonFunctionSet.prototype.add = function(){
    for(var i = 0; i < arguments.length; i++){
        if(typeof arguments[i] === "function"){
            throw new Error("Can't add function to NonFunctionSet");
        }
    }
    //chain to the superclass to peform the actual insertion
    Set.prototype.add.apply(this,arguments);
}