let canvas = new fabric.Canvas("leasables-configuration",{selection: false});
let leasables = [];
let closed = [];
let available = [];
let leased = [];
let objectCode = undefined;
let objectStatus = undefined;
let objectType = undefined;
let objectRent = undefined;
let objectSize = undefined;
const objectCodeDiv = document.querySelector("#selectedObjectCodeHeader");
const objectStatusDiv = document.querySelector("#selectedObjectStatusHeader");
const objectTypeDiv = document.querySelector("#selectedObjectTypeHeader");
const objectRentDiv = document.querySelector("#selectedObjectRentHeader");
const objectSizeDiv = document.querySelector("#selectedObjectSizeHeader");
const submitRequestToRentForm = document.querySelector("#submitRentOrderForm");
const hiddenIdFieldToSubmit = document.querySelector("#hiddenIdFieldToSubmit");

canvas.on('selection:created', (e) => onSelect(e));
canvas.on('selection:updated', (e) => updateSelect(e));
canvas.on('selection:cleared', (e) => exitSelect(e));
canvas.on('object:moving', (e) => handleBorders(e));

submitRequestToRentForm.addEventListener("submit", notDefault);

$(function(){
    fetch("/api/leasables/get-all",{method: 'GET'})
        .then(response =>response.json())
        .then(data => {console.log(data);
                for (let leasable in data) {
                    leasables.push(data[leasable])
                    switch (data[leasable].leasableStatus.leasableStatus) {
                        case "leased":
                            console.log(data[leasable].leasableCode);
                            leased.push(data[leasable].leasableCode);
                            break;
                        case "open":
                            console.log(data[leasable].leasableCode);
                            available.push(data[leasable].leasableCode);
                            break;
                        case "closed":
                            console.log(data[leasable].leasableCode);
                            closed.push(data[leasable].leasableCode);
                            break;
                        default: break;
                    };
                }
                return fetch("/api/configurations/load", {method: 'GET'})
            }).then(response => response.json())
                .then(data => {
                    for (let field in data) {
                        if (field === "jsonification") {
                            let json = clientLayer1(data[field]);
                            // canvas.renderOnAddRemove = false;
                            canvas.loadFromJSON(json,function(){
                                clientLayer2();
                            });
                            canvas.renderAll();
                        }
                    }
                });
    });


function handleBorders(e){
    var obj = e.target;
    // console.log(obj);
    var brOld = obj.getBoundingRect();
    obj.left = brOld.left;
    obj.top = brOld.top;
    obj.setCoords();
}

function clientLayer1(unmodifiedJson){
    unmodifiedJson = JSON.parse(unmodifiedJson);
    console.log("unmodified json");
    console.log(unmodifiedJson);
    for (let element of unmodifiedJson.objects) {
        console.log("element");
        console.log(element);
        if (element.class != undefined) {
            element.selectable = false;
            element.hoverCursor = 'default';
            canvas.sendToBack(element);
        } else {
            element.hasControls = false;
            element.hoverCursor = 'pointer';
            if (leased.includes(element.id)) {
                console.log("included in leased");
                element.fill = 'orange';
                element.stroke = 'black';
            } else if (closed.includes(element.id)) {
                console.log("included in closed");
                element.fill = 'grey';
                element.stroke = 'black';
            } else {
                console.log("included in open");
                element.fill = 'green';
                element.stroke = 'black';
                // element.strokeWidth = '0.5';
            }
        }
    }
    let modifiedJson = unmodifiedJson;
    console.log(modifiedJson);
    return modifiedJson;
}


function clientLayer2(){
    let objects = canvas.getObjects();
    console.log(objects);
    for (let element in objects) {
    let currentElement = objects[element];
    console.log(currentElement);
        if (currentElement.class) {
            currentElement.selectable = false;
            currentElement.hoverCursor = 'default';
            canvas.sendToBack(currentElement);
        } else {
            currentElement.hasControls = false;
            currentElement.hoverCursor = 'pointer';
        }
    }
    canvas.requestRenderAll();
}

function onSelect(e){
    let obj =  e.target;
    for(let element of leasables){
        if(element.leasableCode == obj.id){
            let object = element;
            console.log("This is the object that was selected");
            console.log(object);
            console.log(object.leasableStatus)
            console.log(object.leasableStatus.leasableStatus);
            objectCode = document.createElement('span');
            objectCode.appendChild(document.createTextNode(object.leasableCode));
            objectCodeDiv.appendChild(objectCode);
            objectCode.setAttribute("id", "selectedObjectCode");
            objectStatus = document.createElement('span');
            objectStatus.appendChild(document.createTextNode(object.leasableStatus.leasableStatus));
            objectStatusDiv.appendChild(objectStatus);
            objectStatus.setAttribute("id", "selectedObjectStatus");
            objectType = document.createElement('span');
            objectType.appendChild(document.createTextNode(object.leasableType.leasableTypeName));
            objectTypeDiv.appendChild(objectType);
            objectType.setAttribute("id", "selectedObjectType");
            objectRent = document.createElement('span');
            objectRent.appendChild(document.createTextNode(object.leasableYearlyRent));
            objectRentDiv.appendChild(objectRent);
            objectRent.setAttribute("id", "selectedObjectRent");
            objectSize = document.createElement('span');
            objectSize.appendChild(document.createTextNode(object.leasableSize));
            objectSizeDiv.appendChild(objectSize);
            objectSize.setAttribute("id", "selectedObjectSize");
            if(object.leasableStatus.leasableStatus == "open"){
                hiddenIdFieldToSubmit.setAttribute("value",object.leasableId);
                document.getElementById("rentSelectedPlot").removeAttribute("disabled");
                    // let form = document.querySelector("#submitRentOrderForm");
                    // let rent = form.appendChild(document.createElement('input'));
                    // rent.setAttribute('id', 'rentSelectedPlot');
                    // rent.setAttribute('value','RENT');
                    // rent.setAttribute('type', 'submit');
            }
        }
    }
}
function exitSelect(e){
    document.getElementById("selectedObjectCode").remove();
    document.getElementById("selectedObjectType").remove();
    document.getElementById("selectedObjectSize").remove();
    document.getElementById("selectedObjectRent").remove();
    document.getElementById("selectedObjectStatus").remove();
    document.getElementById("hiddenIdFieldToSubmit").setAttribute("value", "");
    document.getElementById("rentSelectedPlot").setAttribute("disabled", "true");
}

function updateSelect(e){
    let event = e;
    exitSelect(event);
    onSelect(event);
}


function notDefault(event){
    if(hiddenIdFieldToSubmit.getAttribute("value") === ""){
        event.preventDefault();
        return false;
    }
    return true;
}