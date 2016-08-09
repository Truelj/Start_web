var http = require('http');
var fs = require("fs");
var url = require('url');

//Create a server
http.createServer(function(request,response){
	//Parse teh request containing file name
	var pathname = url.parse(request.url).pathname;
	//print the name of hte file for which request is made
	console.log("Request for "+ pathname + " received.");
	//read the requested file content from file system
	fs.readFile(pathname.substr(1), function(err, data){
		if(err){
			console.log(err);
			//http status: 404: not found
			//content type: text/plain
			response.writeHead(404, {"Content-Type": "text/html"});
		}else{
			//page found
			//HTTP Status: 200 :0k
			//Content Type: text/plain
			response.writeHead(200, {'Content-Type':'text/html'});
			//write the content of the file to response body
			response.write(data.toString());
		}
		//send the response body
		response.end();
	});

}).listen(8081);

console.log("server runnig at https://127.0.0.1:8081 \n");