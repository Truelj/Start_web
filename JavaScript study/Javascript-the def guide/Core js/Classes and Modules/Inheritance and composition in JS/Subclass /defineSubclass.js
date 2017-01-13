//created by Jie Li, on Jan 12, 2017
//include extend.js
function defineSubclass(superclass, constructor, methods, statics){
    
    //set up the prototype object of the subclass
    constructor.prototype = inherit(superclass.prototype);
    constructor.prototype.constructor = constructor;
    
    if(methods) extend(constructor.prototype, methods);
    if(statics) extend(constructor, statics); 
}