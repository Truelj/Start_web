//sort the rows in first <tbody> of the specified table according to 
//the value of nth cell within each row.
//use the comparator function if one is specified.
//otherwise, compare the values alphabetically.
function sortrows(table, n, comparator){
	//var tbody = table.tBodies[0];
	var tbody = document.getElementsByTagName('tbody')[0];
	//var rows = tbody.getElementsByTagName("tr");
	var rows = document.getElementsByTagName('tr');
	//get a copy of the rows, which is a read-only NodeList
	rows = Array.prototype.slice.call(rows, 1);

	//now sort the rows based on hte text in hte nth <td> element
	rows.sort(function(row1, row2){
		var cell1 = row1.getElementsByTagName('td')[n];
		var cell2 = row2.getElementsByTagName('td')[n];
		var var1 = cell1.textContent||cell1.innerText;
		var var2 = cell2.textContent||cell2.innerText;
		if(comparator){
			return comparator(var1, var2);
		}else{
			return var1 - var2;
		}
	});
	//now append the rows into the tbody in their sorted order
	//this automatically moves them from their current location, so there
	//is no need to remove them first. If the <tbody> contains any 
	//nodes other than <tr> elements,those nodes will float to the top
	for(var i =0; i <rows.length; i++) tbody.appendChild(rows[i]);
}

	//find the <th> element of the table (assuming there is only one row of them)
	//and make them clickable so that clicking on a column header sorts by that column
function makeSortable(table){
		//var headers = table.getElementsByTagName("th");
		var headers =document.getElementsByTagName('th');
		for(var i = 0; i < headers.length; i++){
			//nested function to create a local scope
			(function(n){
				headers[i].onclick = function(){sortrows(table,n);};
			}(i));
		}
}