//reading from stream
var fs = require("fs");
var data = "";
//create a readable stream
var readerStream = fs.createReadStream("sample.txt");
//set the encoding to be utf8
readerStream.setEncoding('UTF8');
//handle stream events -- > data, end, and error
readerStream.on("data", function(chunk){
	data += chunk;
});
readerStream.on('end', function(){
	console.log(data);
});
readerStream.on('error', function(error){
	console.log(error.stack);
});
console.log("Program Ended");