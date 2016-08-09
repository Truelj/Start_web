var fs = require("fs");
console.log("Going to get file info!");
fs.stat("sample.txt", function(err, stats){
	if(err){
		return console.error(err);
	}
	console.log(stats);
	console.log("Got file info successfully!");
	//check file type
	console.log("is File ?" + stats.isFile());
	console.log("isDirectory? "+ stats.isDirectory());
});