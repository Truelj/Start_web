/*
return hte nth ancestor of e, 
or null if there is  no such ancestor or if that ancestor is not an Element
If n is 0 reuturn e itself.
If n is 1 (or ommitted) return the parent.
If n is 2, return the grandparent, etc
*/
function parent(e,n){
	if(n === undefined) n = 1;
	while(n-- && e) e = e.parentNode;
	if(!e || e.nodeType !== 1) return null;
	return e;
}

/*
return the nth sibling element of element e
if n is positive return hte nth next sibling element
if n is negative return the -nth previous sibling element
if n is 0, return e itself
*/
function sibling(e, n){
	while(e && n!==0){
		if(n > 0){
			if(e.nextElementSibling){
				e = e.nextElementSibling;
			}else{
				for(e = e.nextSibling; e && e.nodeType !== 1; e = e.nextSibling){
					/*empty loop*/
				}
			}
		}else{
			if(e.previousElementSibling){
				e = e.previousElementSibling;
			}else{
				for(e = e.previousSibling; e && e.nodeType !== 1; e = e.previousSibling){
					/*empty loop*/
				}
			}
			n++;
		}
	}
	return e;
}

/*
return the nth element child of e (counting from 0), 
or null if it does not have one negatvie values of n count from the end.
0 means the first child, but -1 means the last child, -2 means the second to last, and so on
*/
function child(e, n){
	if(e.children){
		if(n<0) n += e.children.length;
		if(n<0) return null;
		return e.children[n];
	}
	//if e does not have a children array, find the first child and count forward or find 
	//the last child and count backwards from there
	if(n>=0){
		//find the first child element of e
		if(e.firstElementChild) e = e.firstElementChild;
		else{
			for(e = e.firstChild; e && e.nodeType !== 1; e = e.nextSibling)		
		}
		return sibling(e,n);//return the nth sibling of the first child
	}else{
		//find the last child element of e
		if(e.lastElementChild) e = e.lastElementChild;
		else{
			for(e = e.lastChild; e&& e.nodeType !== 1; e = e.previousSibling)
		}
		return sibling(e, n + 1);//+1 because -1 means the last child
	}
}





