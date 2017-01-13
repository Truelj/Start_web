//Created by Jie Li, on Jan 12, 2017

//Copy enumerable properties of p to o, and return o
//If o and p have a property by the same name, o's property is overwirtten.
//This function does not handle getters and setters or copy attributes.
function extend(o,p){
    for(var prop in p){
        o[prop] = p[prop];
    }
    return o;
}