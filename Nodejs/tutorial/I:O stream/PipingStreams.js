var fs=require("fs");
var readerStream = fs.createReadStream("sample.txt");
var writerStream=fs.createWriteStream("output.txt");
//pipe the read and write operations
//read sample.txt and write data to output.txt
readerStream.pipe(writerStream);
console.log("Program Ended");