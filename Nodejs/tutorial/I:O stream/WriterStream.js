var fs=require("fs");
var data="I LOVE U too!!!\n";
//creat a writable stream
var writerStream = fs.createWriteStream('output.txt');
//write the data to stream with encoding to be utf8
writerStream.write(data, 'utf8');
//Mark the end of file
writerStream.end();
//Handle stream events --> finish, and error
writerStream.on("finish", function(){
	console.log("Write complete");
});
writerStream.on('error', function(error){
	console.log(error.stack);
});
console.log("Program ended");