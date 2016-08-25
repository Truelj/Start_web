//this is the javascrip file for calendar
//connect the event handler with the event

//get the reference to the text div element
var text_div = document.getElementById("text");
var text_area = document.getElementById('mytextarea');
var history_div = document.getElementById("history");
var change;
var currentcell ;
var currentcell_element;
var save_button = document.getElementsByTagName("button")[0];
var jobs;//Integer value

//When the window is loaded, initialize a series of values
window.onload=function (){
	//hide the text div
	text_div.style.display = "none";
    //fetch the value of jobs from local storage
    jobs = getJobs();
    if(!jobs){//jobs is NaN
        jobs = 0;
    }else{
        jobs = parseInt(jobs);
    }
    //fetch the record from local storage
    var record = getRecord();
    if(record === "undefined" || jobs === 0){  //if record is "undefined" or jobs equals to 0, hide the history_div
        history_div.style.display = "none";
    }else{
        //Otherwise, append record to history_div
        history_div.innerHTML = record;
    }

	change = false;
	currentcell = "";// store the name of the current cell, e.g "Monday_priority1".
	currentcell_element=null; //store the the current element that the user clicks on
	save_button.style.display = "none";

}
//save record and jobs to localStorage before leave the current window
window.onbeforeunload=function(){
    var record = history_div.innerHTML;
    saveRecord(record);
    saveJobs(jobs);
}

//invoked when the user clicks on a record cell
//- save the current cell if there's any change
//- change the background color of the current cell
//- update the currentcell's value to the value of nextcell
//- update the currentcell_element to nextcell
function td_onclick(nextcell){//nextcell is not an Event object. Because this function would
                              //be invoked inside another event handler function.
	if(change == true){
		//save the record from text area to history
		var record = text_area.value;
		saveToHistory(currentcell, record);
		change = false;
		//clear the text
		text_area.value = "";
		//show the history board;
		history_div.style.display = "block";
	}
	if(currentcell_element != null){
		//restore the current cell's background color
		currentcell_element.style.backgroundColor = nextcell.style.backgroundColor;
	}
	if(currentcell_element != nextcell){
		//get the new cell name
		currentcell = nextcell.getAttribute("name");
		//display the message above text area
		var day = currentcell.substring(0, currentcell.indexOf("_", 0));
		var priority = currentcell.substr(currentcell.indexOf("_", 0) + 1);
		document.getElementById("sticker").innerHTML = day + "\'s " + priority;
		//fetch the record from history
		var record_div = document.getElementById(currentcell);
		if(record_div != null){
			text_area.value = record_div.firstChild.innerHTML;
		}else{
			//clear the text in the text area
			text_area.value = "";
		}
	}
	//show the text area below the calendar
	text_div.style.display = "block";
	//show the save button below the calendar
	save_button.style.display = "inline";
	//set the new cell background color
	nextcell.style.backgroundColor="beige";
	//update the current cell element
	currentcell_element = nextcell;
	//update the current cell name
	currentcell = currentcell_element.getAttribute("name");
}

function textarea_onchange(){
	change = true;
}

function savetohistory_onclick(){
	//check change
	if(change == true){
		change = false;
		var record = text_area.value;
		saveToHistory(currentcell, record);
		//show the history board
		history_div.style.display = "block";
	}else{
		alert("nothing changed");
	}
	return false;
}

//declare a new function to save the record to history
//"currentcell" contains the name of the cell, e.g "Monday_priority"
//"record" contains the text value of the current textarea
function saveToHistory(currentcell, record){
	//look for record with id of currentcell is in the history
	var record_div = document.getElementById(currentcell);
	if(!record_div){ //the record is not in the document
		//look for week day in history
		var index = currentcell.indexOf("_");
		var weekday = currentcell.substring(0, index);
		var weekday_div = document.getElementById(weekday); 
		//check if that day exists in the document
		//if not, create a div for that day
		if(!weekday_div){
			//create a index div
			var index_div = document.createElement('div');
			index_div.id = getDay(weekday)+ ""; //assign an id for that day. E.g. "1" for "Monday", "2" for "Tuesday", and so on.
			//create a new div to store records for that day
			weekday_div = document.createElement("div");
			//set id for that div
			weekday_div.setAttribute("id",weekday);
			//create a heading for that day
			var weekday_h = document.createElement("h4");
			var weekday_name = document.createTextNode(weekday);
			weekday_h.appendChild(weekday_name);
			//append heading to weekday div
			weekday_div.appendChild(weekday_h);
			//append weekday div to index div
			index_div.appendChild(weekday_div);
			//append index div to records div
			document.getElementById("records").appendChild(index_div);
			//sort index divs in records div
			sortDays(document.getElementById("records"));

		}
		//If a record for that day exists,
		//create a record div and set id attribute to currentcell value
		record_div = document.createElement("div");
		record_div.id = currentcell;
		//create a record p
		var record_p = document.createElement("p");
		//create a record text
		var record_text = document.createTextNode(record);
		//append text to p
		record_p.appendChild(record_text);
		//append p to record div
		record_div.appendChild(record_p);
		//append record div to weekday div
		weekday_div.appendChild(record_div);
		//sort priorities
		//sortPriorities(weekday_div);
		sortPriority(weekday_div);
		alert("success");
        //Increase jobs
        jobs++;
        updateJobs();
	}else{
		//overwrite the record
		record_div.firstChild.value = record;
	}

}
function updateJobs(){
    var span = document.getElementById("jobs");
    span.innerHTML = jobs;
}
//to convert the name of the weekday into a number 
function getDay(weekday){
		switch(weekday){
			case "Monday": return 1; 
			case "Tuesday":return 2;
			case "Wednesday": return 3;
			case "Thursday": return 4;
			case "Friday": return 5;
			case "Saturday": return 6;
			case "Sunday": return 7;
			default: return 0;
		}
	}
//to sort records in records div according to index div
//records is the div element with id = "records"
function sortDays(records){
	//store children into a record array
	var record_array = new Array();
	var i = 0;
	var next_record = records.firstChild;
	if(next_record){
		record_array[i] = next_record;
		while(next_record.nextSibling){
			i++;
			next_record = next_record.nextSibling;
			record_array[i] = next_record;
		}
	}
	//sort array
	record_array.sort(function(div1, div2){
		var id1 = div1.id;
		var id2 = div2.id;
		return id1 - id2;
	});
	for(i = 0; i < record_array.length; i++){
		records.appendChild(record_array[i]);
	}
}
//sort priorities in a weekday div
//weekday is the div element with id like "Monday"
function sortPriorities(weekday){
	var priorities = weekday.getElementsByTagName('div');
	//because priorities is a Nodelist, a read-only dynamic array-like object
	//get a static copy of it. 
	//copy is a true array
	var copy = Array.prototype.slice.call(priorities, 0);
	copy(function(div1, div2){
			var id1 = div1.id;
			var id2 = div2.id;
			if(id1 < id2) return -1;
			if(id1 > id2) return 1;
			return 0;
	});
	for(var i = 0; i < copy.length; i++){
		weekday.appendChild(copy[i]);
	}
}

function sortPriority(weekday){
	var priority_array = new Array();
	var next_node = weekday.firstChild;
	while(next_node.nextSibling){
		next_node = next_node.nextSibling;
		priority_array.push(next_node);
	}
	priority_array.sort(function(div1,div2){
		var id1 = div1.id;
		var id2 = div2.id;
		if(id1<id2) return -1;
		if(id1>id2) return 1;
		return 0;
	});
	for(var i = 0; i < priority_array.length; i++){
		weekday.appendChild(priority_array[i]);
	}
}

