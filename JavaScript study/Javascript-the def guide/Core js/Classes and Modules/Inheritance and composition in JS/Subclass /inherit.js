//Created by Jie Li
//return an object that has a prototype object equal to o

function inherit(o){
    if(o == null){
        throw TypeError();
    }
    if(Object.create){
        return Object.create(o);
    }
    var t = typeof o;
    if(t !=="object" && t !== "function"){
        throw TypeError();
    }
    function f(){}
    f.prototype = o;
    return new f();
}