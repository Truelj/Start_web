//Set.js
//Created by Jie Li, on Jan 10, 2017

//Set is a data structure that represents an unordered collection of values, with no duplicates.
//The fundamental operations on sets are adding values and testing whether a value is a member of the set
//Each member of a set includes a name and a value.

//A constructor for creating an arbitrary set of values
//Argument: a list of values seperated by comma, or an array.
function Set(){
    //create instance properties
    this.values = {};
    this.n = 0; 
    //if there's only one argument and that argument is an array
    if(arguments.length === 1 && isArrayLike(arguments[0])){
        this.add.apply(this,arguments[0]);
    }else if(arguments.length > 0){
        this.add.apply(this, arguments);
    }
    
}

//add array members to the current set
//a: an array, or an array like object
Set.prototype.addArrayMembers = function(a){
    this.add.apply(this,a);
}

Set.prototype.add = function(){
    for(var i = 0; i  < arguments.length; i++){
        var name = Set.nameValue(arguments[i]);
        if(!this.values.hasOwnProperty[name]){
            this.values[name] = arguments[i];
            this.n++;
        }
        console.log(name + " is added");
    }
    return this;
};

//remove a value from the current set
Set.prototype.remove = function(value){
    var name = Set.nameValue(value);
    if(!this.values.hasOwnProperty(name)){
        delete this.values[name];
        this.n--;
    }
    console.log(name + " is removed")
    return this;
};
//Test whether the current set has a value
Set.prototype.contains = function(value){
    var name = Set.nameValue(value);
    //console.log("contians is called, name is "+ name);
    return this.values.hasOwnProperty(name);
};
Set.prototype.size = function(){
    return this.n;
}
//foreach method
Set.prototype.forEach = function(f,c){
    for(var name in this.values){
        if(this.values.hasOwnProperty(name)){
            f.call(c,this.values[name]);//f would be invoked with one argument
        }
    }
};

//Add conversion methods 
Set.prototype.toString = function(){
    var i = 0, s = "{";
    this.forEach(
        function(value){
            s += ((i++ > 0) ? ",":"") + value;
        }
    );
    return s + "}";
                                     
}

Set.prototype.toLocaleString = function(){
    var i = 0, s = "{";
    this.forEach(
        function(value){
            if(i++ > 0) s += ", ";
            if(value == null){
                s += value;
            }else{
                s += value.toLocaleString();
            }
        }
    );
    return s + "}";                                  
};

Set.prototype.toArray = function(){
    var a = [];
    this.forEach(function(value){a.push(value);});
    return a;
};

Set.prototype.toJSON = Set.prototype.toArray;

Set.prototype.equals = function(that){
    if(this === that){return true;}
    if(!(that instanceof Set)){
        return false;
    }
    if(that.size() !== this.size()){
        return false;
    }
    try{
        this.forEach(
            function(value){
                if(!that.contains(value)) throw false;
            }
        );
        return true;
    }catch(x){
        if(x === false){
            return false;
        }else{
            throw x;
        }
    }
};

//Assign an unique name for a value
Set.nameValue = function(value){
    switch(value){//value could be number, string, function, object, ture, false, null, undefined
        case null: return "null";
        case undefined: return "undefined";
        case true: return "true";
        case false: return "false";
        default:
            switch(typeof value){
                case "number": return "#" + value;
                case "string": return "*" + value;
                default: return "@" + getObjectId(value);//for function and object value...
            }
            
    }
    //create a nested function for nameValue use
    function getObjectId(o){
        var objectId = "**objectId**";
        if(!o.hasOwnProperty(objectId)){
            o[objectId] = Set.nameValue.nextObjectId++;
        }
        return o[objectId];
    }
};

Set.nameValue.nextObjectId = 100;
