function person(first, last, age, eye){
    this.firstName = first;
    this.lastname = last;
    this.age = age;
    this.eyeColor = eye;
}

var myFather = new person("John","Doe",50,"blue");
var myMother = new person("Sally","Rally",48,"green");
document.getElementById("demo").innerHTML = "My father is "+ myFather.age + 
        "My mother is " + myMother.age;



