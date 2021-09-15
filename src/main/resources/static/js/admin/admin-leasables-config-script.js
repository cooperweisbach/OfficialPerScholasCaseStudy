let gridState = false;
var unitScale = 5;

let snapToGrid = document.querySelector("#snap-to-grid");
snapToGrid.addEventListener('change', (event)=> snapToGridFunction(event));

let configurationModal = document.querySelector("#configuration-modal");

let viewConfigModal = document.querySelector("#view-config-modal");
viewConfigModal.addEventListener("click", displayConfigModal);
let configModalClose = document.querySelector("#config-modal-close-button");
configModalClose.addEventListener("click", closeConfigModal);

const publishConfig = document.querySelector("#publish-configuration");
let configurationName = document.querySelector("#configuration-name");
const configNameInput = $("#configuration-name");
const publishCheckBox = $("#publish-configuration");

const saveConfigButton = document.querySelector("#save-configuration");
saveConfigButton.addEventListener("click", saveConfig);

// let selectForSavedConfig = $("#select-saved-configurations");
const listConfigsSelect = document.querySelector("#select-saved-configurations");

const loadConfigButton = document.querySelector("#load-saved-configuration");
loadConfigButton.addEventListener("click", loadConfig);

const addExtraButton = document.querySelector("#addExtraButton");
addExtraButton.addEventListener("click", addExtra);

const objectsInfoDiv = document.querySelector("#selectedObjectIdHeader");
const gridSize = document.querySelector("#grid-size");
gridSize.addEventListener("change",() => {
    gridState= false;
    toggleGrid();
    gridState = true;
    toggleGrid();

});
let clearCanvas = document.querySelector("#clearAll");
clearCanvas.addEventListener("click", () => {
    canvas.clear();
})
let defaultSelectPlotOption = document.querySelector("#defaultSelectPlotOption");
let addPlotButton = document.querySelector("#addPlotButton");
addPlotButton.addEventListener("click", addPlot);
let deletePlotButton = document.querySelector("#deletePlotButton");
deletePlotButton.addEventListener("click", removeSelected);
const toggleGridButton = document.querySelector("#toggleGrid");
toggleGridButton.addEventListener("click", () => {
    console.log(gridState);
    if(gridState == true){
        gridState = false;
    } else {
        gridState = true;
    }
    toggleGrid(gridState);
});

function initCanvas(id) {
    return new fabric.Canvas(id, {
        width: 100 * unitScale,
        height: 100 * unitScale,
    });
}

const canvas = initCanvas('myCanvas');

function displayConfigModal(){
    configurationModal.style.display = 'flex';
    webBody[0].style.overflow = "hidden";
}
function closeConfigModal(){
    configurationModal.style.display = "none";
    webBody[0].style.overflow = "scroll";
}

// canvas.add(new fabric.Rect({
//     left: 100,
//     top: 100,
//     fill: 'red',
//     width: 20,
//     height: 20,
//     id: "Starter Block",
//     dirty: true,
//     hasControls: false
// }));


// canvas.on('object:modified', (e) => handleBordersScaling(e));
canvas.on('object:moving', (e) => handleBordersTranslation(e));
canvas.on('selection:created', (e) => onSelect(e));
canvas.on('selection:updated', (e) => updateSelect(e));
canvas.on('selection:cleared', (e) => exitSelect(e));


function onSelect(e) {
    var obj = e.target;
    console.log(obj);
    // if(obj._objects == undefined){ //single object selected
    //     if(obj.class != undefined){ //has a class (only extra added items have classes)
    //         switch(obj.class){
    //             case "extraCirc":
    //                 break;
    //                 case "extraRect":
    //                     case "extraText":
    //                         case "extraLine":
    //                 default: break;
    //         }
    //     }
    // } else{ //multiple objects selected

    // }

    console.log(obj);
    console.log(e);
    // // obj.opacity = 0.5;
    objectId = document.createElement('span')
    objectId.appendChild(document.createTextNode(obj.id));
    objectsInfoDiv.appendChild(objectId);
    objectId.setAttribute("id", "selectedObjectId");
}

function exitSelect(e){
    // console.log("exit select");
    // console.log(e);
    // console.log(e.target);

    let deselectedExit = e.target;
    console.log(deselectedExit);

    // if(deselectedExit.class != undefined){canvas.sendToBack(deselectedExit);}
    // else if(){

    // }
    document.getElementById("selectedObjectId").remove();
}

function updateSelect(e){
    document.getElementById("selectedObjectId").remove();
    var obj = e.target;
    console.log(obj);
    let deselectedUpdate = e.deselected[0];
    console.log(deselectedUpdate);
// if(deselectedUpdate.class != undefined){canvas.sendToBack(deselectedUpdate);}
    // e.target.opacity = 0.5;
    // e.deselected[0].opacity=1;
    objectId = document.createElement('span')
    objectId.appendChild(document.createTextNode(obj.id));
    objectsInfoDiv.appendChild(objectId);
    objectId.setAttribute("id", "selectedObjectId");
}

function handleBordersTranslation(e) {
    console.log(e);
    var obj = e.target;
    console.log(obj);
    var brOld = obj.getBoundingRect();
    console.log(brOld);
    obj.setCoords();
    var brNew = obj.getBoundingRect();
    console.log(brNew);

    if(brNew.left < -5
        || brNew.top < -5
        || brNew.left + brNew.width - 5 > obj.canvas.width
        || brNew.top + brNew.height - 5 > obj.canvas.height) {

            obj.left = brOld.left;
            obj.top = brOld.top;

            obj.setCoords();
    }
    // else
    //     {
    //         prevLeft = brOld.left;
    //         prevTop = brOld.top;
    //         prevWidth = brOld.width;
    //         prevHeight = brOld.height;
    //     }
}


let prevLeft;
let prevTop;
let maxWidth;
let maxHeight;

function handleBordersScaling(e){
    console.log(e);
    var obj = e.target;
    console.log(obj);
    var brOld = obj.getBoundingRect();
    console.log(brOld);
    // obj.setCoords();
    var brNew = obj.getBoundingRect();
    console.log(brNew);

    // if(brNew.left < -5 ){
    //     obj.left = -5;
    //     obj.setCoords();
    // } else if(brNew.top < -5){
    //     obj.top = -5;
    //     obj.setCoords();
    // }else if(brNew.left + brNew.width - 5 > obj.canvas.width){
    //     obj.width = obj.canvas.width + 5;
    //     obj.setCoords();
    // } else if(brNew.top + brNew.height - 5 > obj.canvas.height){
    //     obj.height = obj.canvas.height + 5;
    //     obj.setCoords();
    // }
    // console.log("max width");
    // console.log(maxWidth);
    // console.log("max height");
    // console.log(maxHeight);
    if(obj.left < -5){
        obj.width = obj.width + (3 + obj.left);
        obj.left = 0;
        obj.setCoords();
    }
    if(obj.top < -5){
        obj.height = obj.height + (3 + obj.top);
        obj.top = 0;
        obj.setCoords();
    }
    if(obj.left + obj.width - 5 > obj.canvas.width){
        obj.width = obj.canvas.width - obj.left;
        obj.setCoords();
    }
    if(obj.top + obj.height - 5 > obj.canvas.height){
        obj.height = obj.canvas.height - obj.top;
        obj.setCoords();
    }




    // if(brNew.left < -5
    //     || brNew.left + brNew.width - 5 > obj.canvas.width){
    //     if(maxWidth == undefined || maxWidth > brOld.width){
    //         maxWidth = brOld.width;
    //         console.log("max width");
    //         console.log(maxWidth);
    //     }
    //
    //     obj.hasControls = false;
    //     // obj.width = maxWidth;
    //     // obj.setCoords();
    // } else if(
    //     brNew.top < -5
    //     || brNew.top + brNew.height - 5 > obj.canvas.height){
    //
    //     if(maxHeight == undefined || maxHeight > brOld.height){
    //         maxHeight = brOld.height;
    //         console.log("max height");
    //         console.log(maxHeight);
    //     }
    //     obj.height = maxHeight;
    //     obj.setCoords();
    // }
    // if(brNew.left > 0 && brNew.left + brNew.width < obj.canvas.width){
    //     maxWidth = undefined;
    // }
    // if(brNew.top > 0 && brNew.top + brNew.height < obj.canvas.height){
    //     maxHeight = undefined;
    // }
}

function toggleGrid() {
    if(gridState){
        let grid = gridSize.value;
        for(var i = 0; i < (canvas.width/grid); i++) {
            canvas.add(new fabric.Line([ i*grid, 0, i*grid, canvas.height], { type:'line', stroke: '#ccc', selectable: false,  class: 'gridline', hoverCursor:"default"}));
            canvas.add(new fabric.Line([ 0, i*grid, canvas.width, i*grid], { type:'line', stroke: '#ccc', selectable: false, class: 'gridline',  hoverCursor:"default"}));
        }
    } else {
        // canvas.array.forEach(element => {

        // });
        canvas.forEachObject(function(obj) {
                if (obj.class === 'gridline') {canvas.remove(obj);}
            }
        );

    }
}





function addPlot() {
    defaultSelectPlotOption = document.querySelector("#defaultSelectPlotOption");
    let selectedPlot = document.querySelector(".plotOption:checked");
    let value = selectedPlot.getAttribute('value');
    defaultSelectPlotOption.removeAttribute("selected");
    console.log(value);
    if(value !== "Default"){

        let myPlot = new fabric.Rect({
            id: value,
            width: 20,
            height: 20,
            // hasBorders: false,
            hasControls: false})
        canvas.add(myPlot);
        canvas.setActiveObject(myPlot);
        selectedPlot.setAttribute('disabled',true );
        defaultSelectPlotOption.setAttribute('selected', true);
        // defaultSelectPlotOption.options[defaultSelectPlotOption.options.selectedIndex].selected = true;
    }
}





function removeSelected() {
    let selectedObject = canvas.getActiveObject();
    console.log(selectedObject);
    try {
        document.querySelector("#"+selectedObject.id).removeAttribute("disabled");
    } catch(exception) {
        canvas.requestRenderAll();
    }
    canvas.remove(selectedObject);
}








function addExtra(){
    let addExtraOption = document.querySelector(".extraOption:checked");
    console.log(addExtraOption);
    let extra = addExtraOption.getAttribute("value");
    console.log(extra);
    switch(extra) {
        case "1": let myRect = new fabric.Rect({
            width: 20,
            height: 20,
            stroke: 'grey',
            fill: 'transparent',
            strokeWidth: 0.5,
            strokeUniform: true,
            class: "extraRect"

        });
            canvas.add(myRect);
            canvas.setActiveObject(myRect);
            break;
        case "2": let myCircle = new fabric.Circle({
            stroke: 'grey',
            strokeWidth: 0.5,
            strokeUniform: true,
            fill:'transparent',
            radius: 20,
            class: "extraCirc"
        });
            canvas.add(myCircle);
            canvas.setActiveObject(myCircle);
            break;
        case "3": let myTextBox = new fabric.Textbox('text',{
            width:40,
            fontSize:20,
            class: "extraText"
        });
            canvas.add(myTextBox);
            canvas.setActiveObject(myTextBox);
            break;
        case "4": let myLine = new fabric.Line([0, 20, 100, 20], {
            strokeDashArray: [1, 1],
            stroke: 'black',
            class: "extraLine"
        });
            canvas.add(myLine);
            canvas.setActiveObject(myLine);
        default: break;
    }
}


function saveConfig(){

    let formData = new FormData();
    // let publish = publishConfig.getAttribute("value");
    let publish = publishCheckBox.val();
    // let nameValue = configurationName.getAttribute("value");
    let nameValue = configNameInput.val();
    let json = JSON.stringify(canvas.toJSON(['id','class']));
    console.log("publish: "+ publish);
    console.log("name: "+ nameValue);
    if(!publish){publish=false};
    console.log("publish: "+publish);
    formData.append("publish", publish);
    formData.append("json", json);
    formData.append("name", nameValue);
    fetch("/api/configurations/save",{method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let nameAdded = false;
            for(let child of listConfigsSelect.children){
                if(child.getAttribute("id") == data.configurationId){
                    nameAdded = true;
                }
            }
            if(!nameAdded){
                let optionElement = document.createElement('option');
                optionElement.appendChild(document.createTextNode(data.configurationName));
                optionElement.setAttribute("id", data.configurationId);
                optionElement.setAttribute("value", data.jsonification);
                optionElement.setAttribute("name", data.configurationName);
                optionElement.setAttribute("class","saved-configuration");
                listConfigsSelect.appendChild(optionElement);
            }
        });


}

function loadConfig(){
    let selectedConfig = $(".saved-configuration:checked");
    let json = selectedConfig.val();
    console.log("selected config");
    console.log(selectedConfig);
    if(selectedConfig != "default"){
        canvas.clear();
        canvas.loadFromJSON(json);
        configNameInput.val(selectedConfig[0].innerText);
        console.log("selected configuration name: " + selectedConfig.name);
        canvas.renderAll();
    }
    json = JSON.parse(json);
    let usedPlots = [];
    for(let element of json.objects){
        if(element.id != null){
            usedPlots.push(element.id);
        }
    }
    console.log(usedPlots);
    let plots = document.querySelectorAll(".plotOption");
    for(let plot of plots){
        console.log(plot);
        if(!(usedPlots.indexOf(plot.getAttribute('id')) < 0))
            plot.setAttribute('disabled', 'disabled');
        else{
            plot.removeAttribute('disabled');
        }
    }

}


function snapToGridFunction(event){
    console.log("checked");
    console.log(snapToGrid.getAttribute('checked'));
    console.log(snapToGrid);

    if(event.target.checked){
        if(!gridState){
            gridState = true;
            toggleGrid();
        }
        canvas.on("object:moving",
            function (event) {
                event.target.set({
                    top: Math.round(event.target.top / gridSize.value)*gridSize.value,
                    left: Math.round(event.target.left / gridSize.value)*gridSize.value
                });
            });
            // snap(event)

    } else{
        console.log("try to remove event listener");
        canvas.off("object:moving");
        canvas.on("object:moving",(e) => handleBordersTranslation(e));
    }
}

function snap(event){
    event.target.set({
        top: Math.round(event.target.top / gridSize.value)*gridSize.value,
        left: Math.round(event.target.left / gridSize.value)*gridSize.value
    });
}