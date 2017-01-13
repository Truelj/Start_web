//Created by Jie Li
//Test whether an object is like array

function isArrayLike(o){
    //skip null and undefined
    if(o == null){
        return false;
    }
    //skip function, number, string, true and false
    if(typeof(o) === "object"
      && o.length
      && isFinite(o.length)
      && Math.floor(o.length)=== o.length
      && o.length < 4294967295)
    {
        return true;
    }else{
        return false;
    }
}
