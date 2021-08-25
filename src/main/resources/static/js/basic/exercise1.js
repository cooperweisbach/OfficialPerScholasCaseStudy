var happy = document.querySelector("#happy-emotion");
var sad = document.querySelector("#sad-emotion");
var excited = document.querySelector("#excited-emotion");
var angry = document.querySelector("#angry-emotion");
var robot = document.querySelector("#robot-emotion");
var selectAll = document.querySelector("#select-all-button");
var deselectAll = document.querySelector("#deselect-all-button");
console.log(happy);
console.log(happy);


selectAll.addEventListener("click", selectAllBoxes);
deselectAll.addEventListener("click", deselectAllBoxes);

function selectAllBoxes() {
    happy.checked = true;
    sad.checked = true;
    excited.checked = true;
    angry.checked = true;
    robot.checked = true;
}

function deselectAllBoxes() {
    happy.checked = false;
    sad.checked = false;
    excited.checked = false;
    angry.checked = false;
    robot.checked = false;
}