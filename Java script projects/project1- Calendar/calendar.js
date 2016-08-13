//this is the javascrip file for calendar
//connect the event handler with the event

//get the reference to the text div element
var text_div = document.getElementById("text");
var text_area = document.getElementById('mytextarea');
var history_div = document.getElementById("history");
var change;
var currentcell;
var currentcell_element;
var save_button = document.getElementsByTagName("button")[0];
function window_onload(){
	//hide the text div
	text_div.style.display = "none";
	//hide the history div
	history_div.style.display = "none";
	change = false;
	currentcell = "";
	currentcell_element=null;
	save_button.style.display = "none";

}

function td_onclick(that){

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
		//restore the currentcell backgroud color
		currentcell_element.style.backgroundColor = that.style.backgroundColor;
	}
	if(currentcell_element != that){
		//get the new cell name
		currentcell = that.getAttribute("name");
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
	//set the new cell backgroud color
	that.style.backgroundColor="beige";
	//update the current cell element
	currentcell_element = that;
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
//currentcell is the value of an id like "Monday_priority"
//record is the plain text 
function saveToHistory(currentcell, record){
	//look for record in history
	var record_div = document.getElementById(currentcell);
	//check if that record, with id of currentcell value, exists in the document
	if(!record_div){
		//look for week day in history
		var index = currentcell.indexOf("_");
		var weekday = currentcell.substring(0, index);
		var weekday_div = document.getElementById(weekday); 
		//check if that day exists in the document
		//if not, create a div for that day
		if(!weekday_div){
			//create a index div
			index_div = document.createElement('div');
			index_div.id = getDay(weekday)+ "";
			//create a new div to store records for that day
			weekday_div = document.createElement("div");
			//set id for that div
			weekday_div.setAttribute("id",weekday);
			//create a heading for that day
			var weekday_h = document.createElement("h4");
			var weekday_name = document.createTextNode(weekday);
			weekday_h.appendChild(weekday_name);
			//append heading to weedday div
			weekday_div.appendChild(weekday_h);
			//append weekday div to index div
			index_div.appendChild(weekday_div);
			//append index div to records div
			document.getElementById("records").appendChild(index_div);
			//sort index divs in records div
			sortDays(document.getElementById("records"));

		}
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
	}else{
		//overrite the record
		record_div.firstChild.innerHTML = record;
	}

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

//document.table.tr.td.onclick = td_onclick
//get an array of table rows and assign it to a new variable
//if(document.getElementById("mytable") == null){
//	alert("document.getElementById('mytable') == null");
//}else{
//	var table_rows = document.getElementById("mytable").rows;
//	var row_cells;
//	for(var i = 0; i < table_rows.length; i++){
//		//get an array of table cells and assign it to a new variable
//		row_cells = table_rows[i].cells;
//		for(var j = 0; j < row_cells.length; j++){
//			//add the onclick event to each cell
//			row_cells[j].onclick = td_onclick;
//		}
//	}
//}
