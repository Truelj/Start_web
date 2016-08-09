var buf = new Buffer(256);
//write to the buffer
len = buf.write("Simply Easy learning");
console.log("Octets written: "+ len);

buf = new Buffer(26);
for(var i = 0; i<26; i++){
	buf[i] = i + 97;
}
console.log(buf.toString('ascii'));
//console.log(buf,toString('ascii', 0, 5));
//console.log(buf.toString('utf8',0,5));
//console.log(buf.toString(undefined,0,5));

buf = new Buffer("simply easy");
var json = buf.toJSON(buf);
console.log(json);

var buf1 = new Buffer("a");
var buf2 = new Buffer("b");
var buf3 = Buffer.concat([buf1,buf2]);
console.log("buffer3 content: " + buf3.toString());

buf1 = new Buffer('A');
buf2 = new Buffer('B');
buf1.copy(buf3, 0);
buf2.copy(buf3,1, 0);
console.log('buffer 3 content: '+ buf3.toString());