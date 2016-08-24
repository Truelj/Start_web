/**
 * Created by jieli on 8/23/16.
 */
// First, load the modules we'll be using
var http= require('http'); //HTTP server API

var server =new http.Server(); //create a new http server
server.listen(8000); //run it on port 8000

server.on("request", function(request, response) {
    //parse the requested URL
    var url = require('url').parse(request.url);
    if (url.pathname === '/') {
        response.writeHead(200, {
            "Content-type": "text/plain; charset=UTF-8",
            "Access-Control-Allow-Origin": "*"
        });
        response.write("hello");
        response.end();
        return;
    }
});