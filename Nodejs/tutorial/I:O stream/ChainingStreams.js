var fs =require("fs");
var zlib = require('zlib');

//Compress the file sample.txt to input.txt.gz
fs.createReadStream('sample.txt')
	.pipe(zlib.createGzip())
	.pipe(fs.createWriteStream('sample.txt.gz'));
fs = require("fs");
zlib = require("zlib");
fs.createReadStream('sample.txt.gz')
	.pipe(zlib.createGunzip())
	.pipe(fs.createWriteStream('output2.txt'));

console.log("File Compressed");

