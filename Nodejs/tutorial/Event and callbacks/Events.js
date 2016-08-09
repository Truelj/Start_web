//Import events module
var events = require("events");
//Create an eventEmitter object
var eventEmitter = new events.EventEmitter();
//Create an event handler as follows
var connectHandler = function connected(){
	console.log("connection successful.");
	//fire the data_received event
	eventEmitter.emit("data_received");
}
//Bind event and event handler as follows
eventEmitter.on('connection', connectHandler);
//Bind the data_received event with the anonymous function
eventEmitter.on("data_received", function(){
	console.log("data received successfully.")
})

//fire the connection event
eventEmitter.emit("connection");
console.log("end");