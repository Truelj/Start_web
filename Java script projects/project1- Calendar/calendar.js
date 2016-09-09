
//get the reference to the main <div> elements
var text_div = document.getElementById("text");
var text_area = document.getElementById('mytextarea');
var history_div = document.getElementById("history");
var save_button = document.getElementsByTagName("button")[0];

var change; //"true" for textarea change
var currentcell_name ;
var currentcell_element;
var jobs;//Integer value

//When the window is loaded, initialize a series of values
window.onload=function (){
	//hide the text div
	text_div.style.display = "none";
    //fetch the value of jobs from local storage
    jobs = getJobs();
    if(jobs === undefined){
        jobs = 0;
    }else{
        jobs = parseInt(jobs);
    }
    //fetch the record from local storage
    var record = getRecord();
    if(record === undefined || jobs === 0){  //if record is "undefined" or jobs equals to 0, hide the history_div
        history_div.style.display = "none";
    }else{
        //Otherwise, append record to history_div
        history_div.innerHTML = record;
    }

	change = false;
	currentcell_name = ""; // store the name of the current cell that the user clicks on, e.g "Monday_priority1".
	currentcell_element=null; //store the the current element that the user clicks on
	save_button.style.display = "none";

};
//save record and jobs to localStorage before leave the current window
window.onbeforeunload=function(){
    var record = history_div.innerHTML;
    saveRecord(record);
    saveJobs(jobs);
};


//<td onclick="td_onclick(this)" name = "Monday_priority1"></td>
function td_onclick(nextcell_element){
	//- save the current cell if there's any change
	if(change == true){
		//save the record from text area to history
		var record = text_area.value;
		saveToHistory(currentcell_name, record);
		change = false; //reset change
		text_area.value = ""; //clear textarea
		history_div.style.display = "block"; //show history
	}
	//- change the background color of the current cell when the user clicks on a different cell
	if(currentcell_element != null && currentcell_element != nextcell_element ){
		currentcell_element.style.backgroundColor = nextcell_element.style.backgroundColor;
	}
	//change nextcell background color
	nextcell_element.style.backgroundColor="beige";

	//- update the currentcell_element to nextcell_element
	if(currentcell_element != nextcell_element){
		currentcell_element = nextcell_element;
		currentcell_name = currentcell_element.getAttribute("name");
		//display a notice above text area
		var day = currentcell_name.substring(0, currentcell_name.indexOf("_", 0));
		var priority = currentcell_name.substr(currentcell_name.indexOf("_", 0) + 1);
		document.getElementById("notice").innerHTML = day + "\'s " + priority;
		//fetch the record from history
		var record_div = document.getElementById(currentcell_name);
		if(record_div != null){
			text_area.value = record_div.firstChild.innerHTML;
		}else{
			//clear the text in the text area
			text_area.value = "";
		}
	}

	//- show the text area below the calendar
	text_div.style.display = "block";
	//- show the save button below the calendar
	save_button.style.display = "inline";

}
//<textarea onchange="textarea_onchange()" >
function textarea_onchange(){
	change = true;
}

function savetohistory_onclick(){
	//check change
	if(change == true){
		change = false;
		var record = text_area.value;
		saveToHistory(currentcell_name, record);
		//show the history board
		history_div.style.display = "block";
	}else{
		alert("nothing changed");
	}
	return false; //prevent defautl actions
}

/**
 * Declare a new function to save the record to history
 * 	currentcell_name - contains the name of the current cell, e.g "Monday_priority"
 * 	record - contains the text value of the current textarea
 */
function saveToHistory(currentcell, record){
	//look for record with id of currentcell_name is in the history
	var record_div = document.getElementById(currentcell_name);
	if(!record_div){ //the record is not in the document
		//look for week day in history
		var weekday = currentcell_name.substring(0, currentcell_name.indexOf("_"));
		var weekday_div = document.getElementById(weekday); 
		//check if that day exists in the document
		//if not, create a div for that day
		if(!weekday_div){
			//create a index div
			var index_div = document.createElement('div');
			index_div.id = getDayId(weekday)+ ""; //assign an id for that day. E.g. "1" for "Monday", "2" for "Tuesday", and so on.
			//create a new div to store records for that day
			weekday_div = document.createElement("div");
			//set id for that div
			weekday_div.id = weekday;
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
		//create a record div and set id attribute to currentcell_name value
		record_div = document.createElement("div");
		record_div.id = currentcell_name;
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
function getDayId(weekday){
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

//Sort <div>s within records <div> according to "id" attributes
//Use Node object's properties and methods to traverse the tree
//records - <div id="records">
function sortDays(records){
	//store direct children into a record array
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
//Sort priorities in a weekday div
//weekday - <div id = "Monday">
function sortPriorities(weekday){
	var priorities = weekday.getElementsByTagName('div');
	//Because priorities is a Nodelist, a read-only dynamic array-like object, get a static copy of it.
	var copy = Array.prototype.slice.call(priorities, 0); //copy is a true array
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

