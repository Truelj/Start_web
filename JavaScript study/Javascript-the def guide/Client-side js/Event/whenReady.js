// It's usually safe, however, to start running your scripts after the document is
// fully parsed but before images are downloaded. You can imporove the startup time
// of your web applications if you trigger your scripts on events other than "load"

/*
pass a function to whenReady() and it will be invoked (as a method of the document)
when the document is parsed and ready for manipulation. Registerd functions are triggered
by the first DOMContentLoaded, readystatechange, or load event that occurs. Once the 
document is ready and all functions have been invoked, any functions passed to wehnReady()
will be invoked immediately.
*/

var whenReady = (function(){
	var funcs =[];
	var ready = false;//switch to true when the handler is triggered

	//the event handler invoked when the document becomes ready
	function handler(e){
		//if we've already run once, just return
		if(ready) return;

		//if this was a readystatechange event where the state changed to 
		//something other than "complete", then we're not ready yet
		if(e.type === 'readystatechange' && document.readyState !== 'complete')
			return;
		//otherwise it's a DOMContentloaded event or window's load event
		//run all registered functions. 
		for(var i = 0; i < funcs.length; i++){
			funcs[i].call(document);
		}
		//now set the ready flag to ture and forget the functions
		ready = true;
		funcs = null;
	}
	//register the handler for any event we might receive
	if(document.addEventListener){
		document.addEventListener("DOMContentLoaded", handler, false);
		document.addEventListener("readystatechange",handler, false);
		window.addEventListener("load", handler, false);
	}else if(document.attachEvent){
		document.attachEvent("onreadystatechange",handler);
		window.attachEvent("onload",handler);
	}
	//return the whenReady function
	return function whenReady(f){
		if(ready) f.call(document);
		else funcs.push(f);
	}
}());