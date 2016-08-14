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

    //define the onclick event handler
    function onclick_item(event){
        //set the background of the <li> element
        var item = event.target;
        console.log(item);
        if(!item.colorChanged){
            item.style.background = painting_color;
            item.colorChanged = true;
        }else{
            item.style.background = background_color;
            item.colorChanged = false;
        }

    }

    //Get a NodeList containing all <li> elements in document
    //Register the onclick event handler on each <li> element

    var list_items = document.getElementsByTagName("li");
    for(var i = 0; i < list_items.length; i++){
        list_items[i].onclick = onclick_item;
        //list_items[i].addEventListener("click", onclick_item, false);
        console.log("hello");
        // add a property to the <li> element
        list_items[i].colorChanged = false;
    }
});