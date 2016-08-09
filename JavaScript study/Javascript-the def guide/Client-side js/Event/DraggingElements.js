/**
Drag.js: drag absolutely positioned HTML elements
This module defines a single drag() function that is designed to be called 
from an onmousedown event handler. Subsequent mousemove events will move
the specified element. A mouseup event will terminate the drag.
This implementation works with both the standard and IE event models.
It requires the getScrollOffsets() function from elsewhere

Arguments:
	elementToDrag: the element that received the mousedown event or
	some containing element. It must be absolutely positioned. Its 
	style.left and style.top values will be changed based on the user's
	drag

event: the Event object for mousedown event.
*/
function drag(elementToDrag, event){
	//The initial mouse position, converted to document coordinates
	var scroll = getScrollOffsets();
	var startX = event.clientX + scroll.x;
	var startY = event.clientY + scroll.y;

	//The original position (in document coordinates) of the element
	//that is going to be dragged. Since elementToDrag is absolutely positioned,
	//we assume that its offsetParent is the documnet body.
	var origX = elementToDrag.offsetLeft;
	var origY = elementToDrag.offsetTop;

	//Compute the distane between the mouse down event and the upper-left
	//corner of the element. We'll maintain this distance as the mouse moves
	var deltaX = startX - origX;
	var deltaY = startY - origY;

	//Register the event handles that will respond to the mousemove events
	//and the mosueup event that follow this mousedown event.
	if(document.addEventListener){//Standard event model
		//Register capturing event handlers on the document
		document.addEventListener("mousemove", moveHandler, true);
		document.addEventListener("mouseup", upHandler,true);
	}else if(document.attachEvent){//IE Event Model for IE5-8
		//In the IE event model, we capture events by calling
		//setCapture() on the element to capture them
		elementToDrag.setCapture();
		elementToDrag.attachEvent("onmousemove", moveHandler);
		elementToDrag.attachEvent("onmouseup", upHandler);
		//Treat loss of mouse capture as a mouseup event
		elementToDrag.attachEvent("onlosecapture", upHandler);

	}
	//we've handled this event. Don't let anybody else see it
	if(event.stopPropagation) event.stopPropagation();//standard model
	else event.cancelBubble = true; //IE

	//now prevent any default action
	if(event.preventDefault) event.preventDefault(); //standard modal
	else event.returnValue = false;// IE

	/*
	This is the handler that captures mousemove events when an element is being dragged.
	It is reponsible for moving the element
	*/
	function moveHandler(e){
		if(!e) e=window.event; //IE
		//move the element to the current mouse position, adjusted by the
		//position of hte scrollbars and the offset of hte initial click
		var scroll = getScrollOffsets();
		elementToDrag.style.left = (e.clientX + scroll.x - deltaX) + "px";
		elementToDrag.style.top = (e.clientY + scroll.y - deltaY) + "px";
		//And don't let anyone else see this event.
		if(e.stopPropagation) e.stopPropagation();//Standard.
		else e.cancelBubble = true;
	}
	/*
	This is the handler that captures the final mouseup event that occurs at
	the end of a drag
	*/
	function upHandler(e){
		if(!e) e = window.event; //IE Event Model
		//Unregister the capturing event handlers
		if(document.removeEventListener){//DOM event model
			document.removeEventListener("mouseup", upHandler, true);
			document.removeEventListener("mousemove", moveHandler, true);

		}else if(document.detachEvent){//IE 5+ Event Model
			elementToDrag.detachEvent("onlosecapture", upHandler);
			elementToDrag.detachEvent("onmouseup", upHandler);
			elementToDrag.detachEvent("onmousemove",moveHandler);
			elementToDrag.releaseCapture();

		}
		//And don't let teh event propagate any further
		if(e.stopPropagation) e.stopPropagation();
		else e.cancelBubble = true;
	}














}