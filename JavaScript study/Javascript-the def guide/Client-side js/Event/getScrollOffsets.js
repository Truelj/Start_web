//return teh current scrollbar offsets as the x and y properties of an object
function getScrollOffsets(w){
	//use the specified window or the curretn window if no argument
	w = w|| window;
	//this works for all browsers except IE versions 8 and before
	if(w.pageXOffset != null) return { x: w.pageXOffset, y:w.pageYOffset};

	//for IE(or any browser) in Standards mode
	var d =w.document;
	if(document.compatMode == "CSS1Compat"){
		return {x: d.documentElement.scrollLeft,
				y: d.documentElement.scrollTop};
	}
	//for browsers in Quirks mode
	return 	{x:d.body.scrollLeft,
			 y:d.body.scrollTop};
}