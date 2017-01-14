function filteredSet(superclass, f){
    //create a subclass constructor
    sub = function(){
        //chain to the superclass constructor
        superclass.apply(this, arguments);
    };
    sub.prototype = inherit(superclass.prototype);
    sub.prototype.constructor = sub;
    
    sub.prototype.add = function(){
        for(var i = 0; i < arguments.length; i++){
            if(!f(arguments[i])){
                throw new Error("wrong element");
            }
        }
        superclass.prototype.add.apply(this, arguments);
    }  
    return sub;
}

var NonNullSet = filteredSet(Set, function(v){ return v!==null && v != undefined});

try{
    var s1 = new NonNullSet(1,2,3, null);
}catch(e){
    console.log(e.message);
}

var NonFunctionSet = filteredSet(Set, function(v){return typeof v !== "function"});
try{
    var s2 = new NonFunctionSet(1,2,3, function(){});
}catch(e){
    console.log(e.message);
}

var NonNullNonFunctionSet = filteredSet(NonNullSet,function(v){return typeof v !== "function"});
try{
    var s3 = new NonFunctionSet(1,2,null, function(){});
}catch(e){
    console.log(e.message);
}
