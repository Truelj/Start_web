//Test how the Function.prototype method bind() functions like
//bind() is not supported in ES3

function f(y,z){
    return this.x + y + z;
}
var o = {x: 1};
var y = 2, z = 3;
var f2 = f.bind(o, y, z);//carry-on 2 arguments

//How to make f invoked on o?
//Way1: f.call(o, y, z); 
//Way2: f.apply(o, [y,z]);
//Way3: f2();
console.log(f2());

var f3 = f.bind(o);//no carry-on arguments
console.log(f3(y,z));

//since ES3 doesn't support bind, we can define one.
//call bind as f.bind(o,...)
Function.prototype.bind = /*Function.prototype.bind ||*/
    function(o /*, arguments*/){
        //store the bind function
        var f = this;
        //store arguments, which includes the optional carry-on arguments
        var args = arguments;
        //return a function
        return function(){
            //collect all arguments
            var a = [];
            for(var i = 1; i < args.length; i++){
                a.push(args[i]);
            }
            for(i = 0; i < arguments.length; i++){
                a.push(arguments[i]);
            }
            //call f
            f.apply(this, a);
        }
          
};
   