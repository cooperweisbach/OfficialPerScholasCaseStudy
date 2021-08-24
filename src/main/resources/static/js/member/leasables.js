let canvas = new fabric.Canvas("leasables-configuration",{selection: false});
let leasables = [];
let closed = [];
let available = [];
let leased = [];


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


// canvas.on("object:added", () => canvas.renderAll());
//
//
//
// canvas.on('selection:created', () => console.log(canvas.getActiveObject()));

canvas.on('object:moving', (e) => handleBorders(e));

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

