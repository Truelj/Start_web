// register the specified handler function to handle events of the specified type
// on the specified target. ensure the handler will always be invoked 
// as a method of the target

function addEvent(target, type, handler){
	if(target.addEventListener){
		target.addEventListener(type, handler, false);
	}else{
		target.attachEvent("on" + type, 
			function(event){
				//invoke the handler as a method of target,
				//passing on the event object
				return handler.call(target,event);

		});
	}
}