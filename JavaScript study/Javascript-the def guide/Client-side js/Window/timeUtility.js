/* 
Schedule an invocation or invocations of f() in hte future.
Wait start milliseconds, then call f() every interval milliseconds,
stopping after a total of start+end milliseconds
if interval is specified but end is omitted, then never stop invoking f
if interval and end are omitted, then just invoke f once after start ms
if only f is specified, behave as if start was 0
note that the call to invoke() does not block; it retures right away
*/
function invoke(f, start, interval, end){
	if(!start) start = 0;
	if(arguments.length <= 2){
		setTimeout(f, start);
	}else{
		setTimeout(repeat, start);
	}
	function repeat(){
		var h = setInterval(f, interval);
		if(end){
			setTimeout(function(){clearInterval(h);}, end);
		}
	}
}
invoke(function(){
	console.log("I love you");
}, 1000, 1000);
