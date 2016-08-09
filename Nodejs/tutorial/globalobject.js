console.log(__filename);
console.log(__dirname);
//print hello after 2000 seconds;
setTimeout(printHello, 2000);
//clearTimeout(timer)
function printHello(){
	console.log("hello");
}
//print hello every 2 seconds
var timer = setInterval(printHello, 2000);
//clearInterval(timer)