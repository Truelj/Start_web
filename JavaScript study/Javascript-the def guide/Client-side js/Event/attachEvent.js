// It is common to see event handler registration code that uses addEventLlistener()
// in broswers that support it and otherwise uses attachEvent()
var b = document.getElementById("mybutton");
var handler = function(){
	alert("Thanks!");
	if(b.addEventLlistener){
		B.addEventLlistener("click", handler, false);
	}else if(b.attachEvent){
		b.attachEvent("onclick", handler);
	}
}