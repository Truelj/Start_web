/**
 * Created by jieli on 8/23/16.
 */
//some variables we need throughout
var editor, statusline, savebutton, idletimer;

//the first time the application loads
window.onload=function(){
    //initialize local storage if this is the first time
    if(localStorage.note==null) localStorage.note="";
    if(localStorage.lastModified==null) localStorage.lastModified = 0;
    if(localStorage.lastSaved==null) localStorage.lastSaved = 0;

    //find the elements that are the editor UI. Initialize global variables
    editor = document.getElementById("editor");
    statusline = document.getElementById("statusline");
    savebutton = document.getElementById("savebutton");

    editor.value = localStorage.note; //Initialize  editor with any saved note
    editor.disabled = true; //but don;t allow editing until we sync

    //whether there is input in the textarea
    editor.addEventListener("input", function(e){
        //save the new value in localStorage
        localStorage.note=editor.value;
        localStorage.lastModified=Date.now();
        //reset the idle timer
        if(idletimer) clearTimeout(idletimer);
        idletimer = setTimeout(save, 5000);
        //enable the save button
        savebutton.disabled = false;
    }, false);
    //each time the application loads, try to sync up with the server
    sync();
};

//save to the server before navigating away from the page
window.onbeforeunload = function(){
    if(localStorage.lastModified > localStorage.lastSaved){
        save();
    }
};

//if we go offline, let the user know
window.onoffline = function(){
    status("Offline");
};

//when we come online again, sync up.
window.ononline = function(){
    sync();
};
//notify the user if there is a new version of this application available
// we could also force a reload here with location.reload()
window.applicationCache.onupodateready = function(){
    status("A new version of this application is available. Reload to run it");
};

//also let the user know if htere is not a new version available.
window.applicationCache.onnoupdate = function(){
    status("You are running the latest version of the application");
};

//A function to display a status message in the status line
function status(msg){
    statusline.innerHTML = msg;
}
// upload the note text to the server (if we're online)
//will be automatically called after 5 seconds of inactivity whenever the note has been modified
function save(){
    if(idletimer) clearTimeout(idletimer);
    idletimer = null;

    if(navigator.online){
        var xhr = new XMLHttpRequest();
        xhr.open("PUT", "http://127.0.0.1:8000/");
        xhr.send(editor.value);
        xhr.onload=function(){
            localStorage.lastSaved = Date.now();
            savebutton.disabled = true;
        }
    }
}

//check for a new version of hte note on the server.
//if a newer version is not found, save the current version to the server.
function sync(){
    if(navigator.onLine){
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "http://127.0.0.1:8000/");
        xhr.send();
        xhr.onload=function(){
            var remoteModTime=0;
            if(xhr.status === 200){
                var remoteModTime = xhr.getResponseHeader("Last-Modified");
                remoteModTime = new Date(remoteModTime).getTime();
            }
            //if there's a newer version on the server
            if(remoteModTime > localStorage.lastModified){
                status("Newer note found on server.");
                var useit = confirm("There is a newer version of the note \n"+
                "on the server. Click OK to use that version\n"+
                "or click Cancel to continue editing this\n"+
                "version and overwrite the server");
                var now = Date.now();
                if(useit){
                    editor.value = localStorage.note=xhr.responseText;
                    localStorage.lastSaved = now;
                    status("Newest version downloaded");
                }else{
                    status("Ignoring newer version of hte note.");
                }
                localStorage.lastModified = now;
            }
            //there's no newer version on the server
            else {
                status("You are editing the current version of the note.");
            }
            //
            if(localStorage.lastModified > localStorage.lastSaved){
                save();
                status("Server is updated");
            }
            editor.disabled = false;
            editor.focus();


        }
    }else{
        //if we are currently offline, we can't sync
        status("Can't sync while offline");
        editor.disabled = false;
        editor.focus();
    }
}