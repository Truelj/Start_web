/**
 * Created by jieli on 8/25/16.
 */
//Use local storage as client-side storage mechanism
//An API for calender.js to manipulate localStorage

//when the window is loaded, fetch record from local storage
//return undefined if record is undefined
function getRecord(){
    return localStorage["record"];;
}

//before the user leave the window, store record to local storage
function saveRecord(record){
    localStorage.record = record;
}

function getJobs(){
    return localStorage["jobs"];
}

function saveJobs(jobs){
    localStorage.jobs = jobs;
}
/**
Before is for debug
 */

//query all stored name/value pair
function logLocalStorage(){
    for(var name in localStorage){
        var value = localStorage[name];
        console.log("name: " + name + " value: "+ value);
        /*
         log:
         name: lastModified value: 1472000115122
         name: lastSaved value: 0
         name: note value: lllllll
         */
    }

}

function clearLocalStorage(){
    localStorage.clear();
}