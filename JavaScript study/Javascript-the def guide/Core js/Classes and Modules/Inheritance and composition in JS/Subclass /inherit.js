//Created by Jie Li

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