function cancelHandler(event){
	var event = event || window.event;//for IE
	// Do something to handle the event here
	// Now cancel the default action associated with the event
	if(event.preventDefaut) event.preventDefaut();//standard technique
	if(event.returnValue) event.returnValue = false;//IE
	return false; // For handlers registerd as object properties
}