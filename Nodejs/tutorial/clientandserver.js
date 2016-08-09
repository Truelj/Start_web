// import required module
// we use require directive to load http module and 
// store returned HTTP instance into http variable as follows
var http = require("http");

// create server
// create an HTTP server which listens and wait for a request over 8081 port on local machine
http.createServer(function (request, response){
	//send the HTTP header
	//HTTP Status:200:ok
	//Content type: text/plain
	response.writeHead(200, {'Content-Type': 'text/plain'});
	//Send the resonsse body as "Hello"
	response.end("Hello\n");

}).listen(8081);

//Console will print the message
console.log("Server runnning at http://127.0.0.1:8081");

