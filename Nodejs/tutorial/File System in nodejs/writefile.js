var fs=require("fs");
console.log("write to file!");
fs.writeFile("input.txt","I love you too!", function(err){
	if(err){
		return console.error(err);
	}
	console.log("Data written suc");
	console.log("let's read newly written data");
	fs.readFile("input.txt", function(err, data){
		if(err){
			return console.error(err);
		}
		console.log("Asynchronous read: "+ data.toString());
	})
});