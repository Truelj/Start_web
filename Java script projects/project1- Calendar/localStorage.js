/**
 * An API for calender.js to manipulate localStorage
 * Created by jieli on 8/25/16.
 */
//Use local storage as client-side storage mechanism

//when the window is loaded, fetch record from local storage
//return undefined if record is undefined
function getRecord(){
    return localStorage["record"];
}

//before the user leave the window, store record to local storage
//record == document.getElementById("history").innerHTML;
function saveRecord(record){
    localStorage.record = record; //get a copy of record and store it in localStorage
}

//return the number of jobs done by user
function getJobs(){
    return localStorage["jobs"];
}

function saveJobs(jobs){
    localStorage.jobs = jobs;
}

/**
 * Code below is for debugging
 */

//query all stored name/value pair
function logLocalStorage(){
    for(var name in localStorage){
        var value = localStorage[name];
        console.log("name: " + name + " value: "+ value);
        /* e.g
         log:
         name: lastModified value: 1472000115122
         name: lastSaved value: 0
         name: note value: lllllll
         */
    }

}
//claer the local storage
function clearLocalStorage(){
    localStorage.clear();
}
clearLocalStorage();