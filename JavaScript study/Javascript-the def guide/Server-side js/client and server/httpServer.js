/**
 * Created by jieli on 8/19/16.
 */
/*
    This is a simple NodeJS Http server that can serve files from the current directory and also implements
    two special URLs for testing.
    Connect to the server at http://localhost:8000 or http://127.0.0.1:8000
 */
// First, load the modules we'll be using
var http= require('http'); //HTTP server API
var fs= require('fs'); //For working with local files

var server =new http.Server(); //create a new http server
server.listen(8000); //run it on port 8000

// Node uses the "on()" method to register event handlers
// When the server gets a new request, run this function to handle it.
server.on("request", function(request, response){
    //parse the requested URL
    var url=require('url').parse(request.url);
    if(url.pathname === '/'){
        response.writeHead(200, {
            "Content-type": "text/plain; charset=UTF-8",
            "Access-Control-Allow-Origin":"*"
        });
        response.write("hello");
        response.end();
        return;
    }
    //a special url that just makes the server wait before sending the response.
    //this can be useful to simulate a slow network connection.
    else if(url.pathname === '/test/delay'){
        //use query string for delay amount, or 2000 milliseconds
        var delay =parseInt(url.query)||2000;
        //set hte response status code and headers
        response.writeHead(200, {
            "Content-Type": "text/plain; charset=UTF-8",
            "Access-Control-Allow-Origin":"*"
        });
        //start writing hte response body right away
        response.write("Sleeping for "+ delay + " milliseconds...");
        //and then finish it in another function invoked later
        setTimeout(function(){
            response.write("done.");
            response.end();
        }, delay);

    }
    //If the request was for "/test/mirror", send back the request verbatim
    //Useful when you need to see the request headers and body
    else if(url.pathname === "/test/mirror"){
        //response status and headers
        response.writeHead(200, {
            "Content-Type": "text/plain; charset=UTF-8",
            "Access-Control-Allow-Origin":"*"
        });
        //begin the response body with the request
        response.write(request.method + " " + request.url + " HTTP/" + request.httpVersion + "\r\n");
        //and the request headers
        for(var h in request.headers){
            response.write(h + " : " + request.headers[h] + "\r\n");
        }

        response.write("\r\n");//end headers with an extra blank line

        //we complete the response in these event handler functions:
        //when a chunk of hte request body, add it to hte response.
        request.on("data", function(chunk){response.write(chunk)});
        request.on("end", function(chunk){response.end();});

    }
    //otherwise, seve a file from the local directory
    else{
        //Get local filename and guess its content type based on its extension
        var filename = url.pathname.substring(1);//Strip leading
        var type;
        switch(filename.substring(filename.lastIndexOf(".") + 1)){
            case "html":case "htm":
                type="text/html;charset=UTF-8"; break;
            case "js":
                type="application/javascript;charset=UTF-8"; break;
            case "css":
                type="text/css;charset=UTF-8"; break;
            case "text":
                type="text/plain; charset=UTF-8"; break;
            case "manifest":
                type="text/cache-manifest; charset=UTF-8"; break;
            case "xml":
                type="application/xml;charset=UTF-8"; break;
            default: type="application/octet-stream"; break;
        }

        //read the file asynchronously and pass the content as a single
        //chunk to the callback function. For really large files,
        // using the streaming API with fs.createReadStream() would be better.
        fs.readFile(filename, function(err, content){
            if(err){
                response.writeHead(404, {
                    "Content-Type": "text/plain;charset=UTF-8",
                    "Access-Control-Allow-Origin":"*"
                });
                response.write(err.message);
                response.end();
            }else{
                response.writeHead(200,{
                    "Content-Type" : type,
                    "Access-Control-Allow-Origin":"*"

                });
                response.write(content);
                response.end();
            }
        });
    }
});