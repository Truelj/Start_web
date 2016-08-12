/*
The DnD APi is quite complicated, and browsers are not fully interoperable.
This example gets the basics right, but each browser is a little different and
each one seems to have its own unique bugs. This code does not attempt
browser-specific workarounds.
 */

whenReady(function(){
    //find all <ul clsss='dnd'> elements and call the dnd() function on them
    var lists = document.getElementsByTagName("ul");
    var regexp = /\bdnd\b/;
    for(var i = 0; i < lists.length; i++){
        if(regexp.test(lists[i].className)) dnd(lists[i]);
    }

    //add drag-and-drop handlers to a list element
    function dnd(list){
        var original_class=list.className;
        var entered =0;

        //This handler is invoked when a drag first enters the list. It checks
        //that the drag contains data in a format it can process and, if so,
        //returns false to indicate interest in a drop. in that case, it also
        //highlights the drop target to let the user know of that interest.

        list.ondragenter = function(e){
            e = e||window.event;
            var from = e.relatedTarget;

            //dragenter and dragleave events bubble, which makes it tricky to
            //know when to highlight or unhighlight the element in a case like this
            //where the ul element has li children. in browsers that define
            //relatedTarget we can track that.
            //Otherwise, cwe count enter/leave pairs

            //If we entered from outside the list or if this is the first entrance then we need to do some stuff
            entered++;

            if((from && !ischild(from,list)) || entered == 1){
                //All the DnD info is in this dataTransfer object
                var dt = e.dataTransfer;
                //The dt.types object lists the types or formats that the data
                //being dragged is available in. HTML5 says the type has a
                //contains() methods. In some browsers it is an array with an indexOf method.
                //In IE8 and before, it simply does not exist.

                var types = dt.types;

                //If we don't have any type data or if we do have type data and data is available in plain text format,
                //then highlight the list to let the user know we're listening for drop and return false
                //to let the browser know
                if(!types ||                                             //IE
                    (types.contains && types.contains("text/plain")) ||  //HTML 5
                    (types.indexOf && types.indexOf("text/plain" != -1))) //Webkit
                {
                    list.className = original_class + "droppable";
                    return false; //cancel the event
                }
                //if we do not recognize the data type, we do not want a drop
                return; //without canceling

            }
            return false; //if not the first enter, we are still interested.
        };

        //this handler is invoked as the mouse moves over the list
        //we have to define this handler and return false or the drag will be canceled.
        list.ondragover = function(e){return false;}

        //This handler is invoked when the drag moves out of the list
        //or out of one of its children. If we are actually leaving the list
        // not just going form one list item to another, then unhighlight it
        list.ondragleave = function(e){
            e = e || window.event;
            var to = e.relatedTarget;

            //if we're leaving for something outside the list or if this leave
            //balances out the enters, then unhighlight the list
            entered--;
            if((to && !ischild(to, list)) || entered <= 0){
                entered = 0;
            }
            return false;
        };

        //this handler is invoked between a drop actually happens
        //we take the dropped text and make it into a new li element
        list.ondrop = function(e){
            e = e||window.e;
            //get the data that was dropped in plain text format
            //"Text" is a nickname for "text/plain"
            //IE does not support "text/plain" , so we use "Text"here
            var dt =  e.dataTransfer;
            var text = dt.getData("Text");

            //if we got some text, turn it into a new item at list end;
            if(text){
                var item = document.createElement("li");
                item.draggable = true;
                item.appendChild(document.createTextNode(text));
                list.appendChild(item);

                //restore the list's original style and reset the entered count
                list.className = original_class;
                entered = 0;

                return false;
            }
        };

        //make all items that were originally in hte lsit draggable
        var items = list.getElementsByTagName("li");
        for(var i =0; i <items.length; i++){
            items[i].draggable = true;
        }
        //and register event handlers for dragging list items
        //Note that we put thee handlers on the list and let events bubble up from the items

        //this handler is invoked when a drag is initiated within the list
        list.ondragstart = function(e){
            var e = e||window.event;
            var target = e.target || e.srcElement;

            //If it bubbled up from something other than a li, ignore it
            if(target.tagName !== "LI") return false;
            //get hte all-important dataTransfer object
            var dt = e.dataTransfer;
            //tell it what data we have to drag and what format it is in
            dt.setData("Text", target.innerText|| target.textContent);
            //Tell it wwe know how to allow copies or moves of hte data
            dt.effectAllowed = "copyMove";
        };

        //this handler is invoked after a successful drop occurs
        list.ondragend = function(e){
            e = e|| window.event;
            var target = e.target || e.srcElement;
            if(e.dataTransfer.dropEffect === "move"){
                target.parentNode.removeChild(target);
            }

        };
        function ischilde(a,b){
            for(; a; a = a.parentNode) if (a === b) return true;
            return false;
        }
    }//end of dnd
})