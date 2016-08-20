/**
 * Created by jieli on 8/19/16.
 */
/*
post a string of text to a server and ignores any response the server sends
 */
var host = "http://127.0.0.1:8000/";
var path1 = "test/delay";
var path2 = "test/mirror";
var filename= "serverXML.xml";
postMessage(host);
getText(host,callback);
getText(host+path1,callback);
getText(host+path2,callback);
get(host+filename,callback);

function postMessage(url, msg){
    var request = new XMLHttpRequest();//new request
    request.open("POST", url);
    //Send the message, in plain-text, as the request body
    request.setRequestHeader("Content-Type", "text/plain; charset=UTF-8");
    request.send(msg);
    //The request is done. We ignore nay response or any error.
}

//Issue an http get request for the contents of the specified URL
//When the response arrives successfully, verify that it is plain text
//and if so, pass it to the specified call back function
function getText(url, callback){
    var request = new XMLHttpRequest();
    request.open("GET", url);
    request.onreadystatechange=function(){//define event listener
        //if the request is compete and was successful
        if(request.readyState === 4 && request.status === 200) {
            var type = request.getResponseHeader("Content-Type");
            if (type.match(/^text/))
                callback(request.responseText);
        }
    };
    request.send(null);
}

//define a callback function
function callback(text) {
    console.log(text);

}
//parsing the HTTP response
//issue an http get request for the contents of the specified URL.
//When the response arrives, pass it to the callback function as a
//parsed XML Document object, a JSON-parsed object, or a string
function get(url, callback){
    var request = new XMLHttpRequest();
    request.open("GET", url);
    request.onreadystatechange = function(){
        //if teh request is compete and was successful
        if(request.readyState === 4 && request.status === 200){
            //get the type of the response
            var type = request.getResponseHeader("Content-Type");
            //Check type so we don't get HTML documents in the future
            if(type.indexOf("xml") !== -1 && request.responseXML){
                callback(request.responseXML);                      //document response
            }else if(type=="application/json"){
                callback(JSON.parse(request.responseText));         //JSON response
            }else{
                callback(request.responseText)                      //String response
            }
        }
    };
    request.send(null);
}













