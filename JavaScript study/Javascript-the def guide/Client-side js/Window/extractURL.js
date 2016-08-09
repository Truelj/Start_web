/*
This function parses ampersand-separated name=value argument pairs from
the query string of the URL. It stores the name=value pairs in
properties of an object and returns that object. Use it like this:
var arg= urlArgs();
var q = arg.q||"";
var n = args.n? parseInt(aggs.n): 10;
*/
function urlArgs(){
	var args={};//starting wiht an empty object
	var query =location.search.substring(1); //Get query string, minus '?'
	var pairs = query.split('&'); //Split at ampersands
	for(var i = 0; i < pairs.length; i++){
		var pos=pairs[i].indexOf('=');
		if(pos == -1) continue;
		var name = pairs[i].substring(0, pos);
		var value = pairs[i].substring(pos+1);
		value = decodeURIComponent(value);
		args[name]=value;
	}
	return args;
}
