var fs = require("fs");
console.log("Going to open file!");
fs.open("sample.txt", "r+", function(err,fd){
	if(err){
		return console.error(err);
	}
	console.log("File opened successfully!");
});