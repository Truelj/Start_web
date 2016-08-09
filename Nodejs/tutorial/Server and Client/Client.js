var http =require("http");
var options = {
	host: 'localhost', port:'8081', path:'/index.html'
};
// callback function is used to deal with response
var callback = function(response){
	//continuously update stream with data
	var body ='';
	response.on('data', function(data){
		body+=data;
	});
	response.on('end', function(){
		//data received completely
		console.log(body);
	});
}
//make a request to the server
var req = http.request(options, callback);
req.end();