var events = require("events");
var eventEmitter = new events.EventEmitter();

//listener #1
var listener1 = function listener1(){
	console.log("listener1 executed");
}
//listener #2
var listener2 = function listener2(){
	console.log("listener2 executed");
}
//bind the connection event with the listner1 function
eventEmitter.addListener('connection', listener1);
eventEmitter.addListener('connection', listener2);

var eventListeners = require("events").EventEmitter.listenerCount(eventEmitter, "connection");
console.log(eventListeners + " Listeners listening to connection event");

eventEmitter.emit("connection");

eventEmitter.removeListener("connection", listener1);
console.log("listener1 will not listen now");

eventEmitter.emit("connection");

eventListeners = require("events").EventEmitter.listenerCount(eventEmitter,"connection");
console.log(eventListeners + "Listeners listening to connection event");