try{
    var s1 = new NonFunctionSet(function(){});
}catch(e){
    console.log(e.message);
}