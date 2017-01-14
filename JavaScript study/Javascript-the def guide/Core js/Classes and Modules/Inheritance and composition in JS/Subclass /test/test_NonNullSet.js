try{
    var s1 = new NonNullSet(1,2,3,null);
}catch(e){
    console.log(e.message);
}

try{
    var s2 = new NonNullSet(1,2,3, undefined);
}catch(e){
    console.log(e.message);
}