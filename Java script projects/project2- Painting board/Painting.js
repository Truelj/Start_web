/**
 * Created by jieli on 8/10/16.
 * Plate form supported: Chrome
 * The Painting.js is used to register event handlers to the event happening to the list items
 */


//functions passed to whenReady() would be invoked on behalf of the document object*/
whenReady(function(){
    //The default background color would be yellow
    var background_color = "yellow";
    //The default painting color would be red
    var painting_color = "red";
    var list = document.getElementById("painting-board");
    //Get a NodeList containing all <li> elements in document
    var list_items = document.getElementsByTagName("li");
    var current_item;
    var move = false;
    var option = 5;//default option
    //declare an array of options
    var options = [{rows:10,columns:10},{rows:20, columns:20}, {rows:25, columns:25},
                    {rows:50, columns:50}, {rows:100, columns:100}];
    //define the onclick event handler
    function changeColor(){
        console.log("change color");
        //set the background of the <li> element

        if(!current_item.colorChanged){
            current_item.style.background = painting_color;
            current_item.colorChanged = true;
        }else{
            if(!move)
                current_item.style.background = background_color;
                current_item.colorChanged = false;
        }

    }


    function downHandler(event){ console.log("drag");
        //set/reset the current item
        current_item = event.target;
        //change the color of current cell
        changeColor();
        //register mouse move event handlers on every item
        for(var i = 0; i < list_items.length; i++){
            list_items[i].addEventListener("mousemove", moveHandler, false);
        }
        //Register capturing event handlers on the document
        //document.addEventListener("mousemove", moveHandler, true);
        document.addEventListener("mouseup", upHandler,true);
        //stop event propagation
        event.stopPropagation();
        //prevent any default action
        event.preventDefault();
    }

    function moveHandler(event){ console.log("move");
        move = true;
        if(event.target !== current_item){
            current_item = event.target;
            changeColor();
        }
        //stop event propagation
        event.stopPropagation();
        //prevent any default action
        event.preventDefault();

    }
    function upHandler(event){ console.log("up");
        console.log("event target: "+ event.target);
        for(var i = 0; i < list_items.length; i++)
        {
            list_items[i].removeEventListener("mousemove", moveHandler,false);
        }
        move = false;
        //document.removeEventListener("mousemove", moveHandler,true);
        document.removeEventListener("mouseup", upHandler, true);
        //stop event propagation
        event.stopPropagation();
        //prevent any default action
        event.preventDefault();
    }

    function create(){
        console.log("create");
        //remove current <li> elements in <ul>
        for(var i = 0; i < list_items.length; i++){
            list.removeChild(list_items[i]);
        }
        //append a new number of <li> elements in <ul>
        var total = options[option-1].rows * options[option-1].columns;
        for(var i = 0; i < total; i++){
            var item = document.createElement("li");
            item.className = "option" + option;
            list.appendChild(item);
        }
    }
    //create the painting board, with rows and columns default to be 10
    create();

    //Register mouse down event handler on each <li> element
    for(var i = 0; i < list_items.length; i++){
        list_items[i].addEventListener("mousedown", downHandler,false);
        console.log("hello");
        // add a property named "colorChanged" to the <li> element.
        list_items[i].colorChanged = false;
    }
});