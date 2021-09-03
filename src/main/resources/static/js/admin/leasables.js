let gridState = false;
var unitScale = 5;

// $(".change-page-button").on("click", changePage());

// let firstPageButton = document.querySelector("#first-page-button");
// firstPageButton.addEventListener("click", (e)=> changePage(e));
// let previousPageButton =  document.querySelector("#previous-page-button");
// previousPageButton.addEventListener("click", (e)=> changePage(e));
// let nextPageButton = document.querySelector("#next-page-button");
// nextPageButton.addEventListener("click", (e)=> changePage(e));
// let lastPageButton  = document.querySelector("#last-page-button");
// lastPageButton.addEventListener("click", (e)=> changePage(e));
//

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
// let controls = fabric.IText({
//     apply:,
//    ctrlKeysMapDown: copy
//    }
// );
// console.log(controls);
// function copy(e){
//     console.log(e);
// }

canvas.add(new fabric.Rect({
    left: 100,
    top: 100,
    fill: 'red',
    width: 20,
    height: 20,
    id: "Starter Block"
}));



canvas.on('object:moving', (e) => handleBorders(e));
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

function handleBorders(e) {
    var obj = e.target;
    console.log(obj);
    var brOld = obj.getBoundingRect();
    console.log(brOld);
    obj.setCoords();
    var brNew = obj.getBoundingRect();
    console.log(brNew);

    if(brNew.left < -5
        || brNew.top < -5
        || brNew.left + brNew.width - 5> obj.canvas.width
        || brNew.top + brNew.height - 5 > obj.canvas.height) {
        obj.left = brOld.left;
        obj.top = brOld.top;
        obj.setCoords();
    }
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
    if(selectedConfig != "default"){
        canvas.clear();
        canvas.loadFromJSON(json);
        configurationName.setAttribute("value", selectedConfig.name);
        console.log("selected configuration name: " + selectedConfig.name);
        // publishConfig.removeAttribute("checked", "checked");
        canvas.renderAll();
    }

}





//
// let paginationButtonHolder = document.querySelector("#pagination-button-holder");
// // let startIndex = 0;
// let numberOfResults = 20;
// // let lastPagination = numberOfResults;
// let allRows = document.querySelectorAll(".leasable-data-row");
// let totalCount = allRows.length;
//
// function calculateNumberOfPages(){
//     if((totalCount/numberOfResults - Math.floor(totalCount/numberOfResults) > 0 )){
//         return Math.floor(totalCount/numberOfResults) + 1;
//     } else{
//         return Math.floor(totalCount/numberOfResults);
//     }
// }
//
// showData(addLeasablesDataPaginationButtons());
//
// function addLeasablesDataPaginationButtons(){
//     console.log("number of pages:");
//     let maxIndex = 5;
//     if(calculateNumberOfPages() < 5){
//         maxIndex = calculateNumberOfPages();
//     }
//     console.log(calculateNumberOfPages());
//     for(let i = 1; i <= maxIndex; ++i) {
//         console.log("i: "+ i.toString());
//         let newButton = document.createElement('button');
//         newButton.appendChild(document.createTextNode(i.toString()));
//         newButton.setAttribute("value", i.toString());
//         newButton.setAttribute("class", "pagination-button");
//         if(i == 1){
//             newButton.setAttribute("id", "current-page");
//         }
//         newButton.addEventListener("click", (event) => showData(goToPage(event)));
//         paginationButtonHolder.appendChild(newButton);
//     }
//     return 1;
// }
//
//
// function getCurrentPageNumber(){
//    return document.getElementById("current-page").getAttribute("value");
// }
//
// function changePage(event){
//     event.preventDefault();
//     console.log(event.target);
//     switch(event.target.id){
//         case "first-page-button": showData(firstPageFunction());
//             break;
//         case "previous-page-button": showData(previousPageFunction());
//             break;
//         case "next-page-button": showData(nextPageFunction());
//             break;
//         case "last-page-button": showData(lastPageFunction());
//             break;
//         default: break;
//     }
//
// }
//
// function firstPageFunction(){
//     let buttonList = document.getElementsByClassName("pagination-button");
//     console.log(buttonList);
//     for(let i = 0; i < buttonList.length; i++) {
//         console.log(buttonList[i])
//         if(i != 0){
//             buttonList[i].setAttribute("id", "");
//         }
//         else {
//             buttonList[i].setAttribute("id", "current-page");
//         }
//         buttonList[i].setAttribute("value", (i+1).toString());
//         buttonList[i].innerHTML = (i+1).toString();
//     }
//     return 1;
// }
// function previousPageFunction(){
//     let buttonList = document.getElementsByClassName("pagination-button");
//     let currentPageElement = document.getElementById("current-page");
//     let currentPageValue = parseInt(currentPageElement.getAttribute("value"));
//     let startOfRange = buttonList[0].getAttribute("value");
//     let endOfRange = buttonList[buttonList.length-1].getAttribute("value");
//     let maxNumOfPages = calculateNumberOfPages();
//
//     if(startOfRange != 1 && currentPageValue <= (maxNumOfPages-2)){
//         for(let element of buttonList){
//             element.setAttribute("value", (parseInt(element.getAttribute("value")) - 1).toString());
//             element.innerHTML = (parseInt(element.innerHTML) - 1).toString();
//         }
//         return getCurrentPageNumber();
//     } else if(currentPageValue != 1) {
//         let newCurrentPageIndex;
//         for(let element = buttonList.length -1; element >=0; element--){
//             console.log("element");
//             console.log(element);
//             if(buttonList[element].id == "current-page"){
//                 console.log("found current page element");
//                 buttonList[element].setAttribute("id", "");
//                 newCurrentPageIndex=(element-1);
//                 console.log(newCurrentPageIndex);
//             }
//             if(element == newCurrentPageIndex){
//                 buttonList[element].setAttribute("id", "current-page");
//                 return buttonList[element].getAttribute("value");
//             }
//         }
//     } else {
//         return getCurrentPageNumber();
//     }
// }
//
// function nextPageFunction(){
//     let buttonList = document.getElementsByClassName("pagination-button");
//     let currentPageElement = document.getElementById("current-page");
//     let currentPageValue = parseInt(currentPageElement.getAttribute("value"));
//     let startOfRange = buttonList[0].getAttribute("value");
//     let endOfRange = buttonList[buttonList.length-1].getAttribute("value");
//     let maxNumOfPages = calculateNumberOfPages();
//
//     if(endOfRange != maxNumOfPages && currentPageValue >= 3){
//         for(let element of buttonList){
//             element.setAttribute("value", (parseInt(element.getAttribute("value"))+1).toString());
//             element.innerHTML = (parseInt(element.innerHTML) + 1).toString();
//         }
//         return getCurrentPageNumber();
//     } else if(currentPageValue != maxNumOfPages) {
//         let newCurrentPageIndex;
//         for(let element in buttonList){
//             console.log("element");
//             console.log(element);
//             if(buttonList[element].id == "current-page"){
//                 console.log("found current page element");
//                 buttonList[element].setAttribute("id", "");
//                 newCurrentPageIndex=(parseInt(element)+1);
//                 console.log(newCurrentPageIndex);
//             }
//             if(element == newCurrentPageIndex){
//                 buttonList[element].setAttribute("id", "current-page");
//                 return buttonList[element].getAttribute("value");
//             }
//         }
//     } else {
//         return getCurrentPageNumber();
//     }
// }
//
// function lastPageFunction(){
//     let buttonList = document.getElementsByClassName("pagination-button");
//     console.log(buttonList);
//     for(let i = buttonList.length-1, page = calculateNumberOfPages(); i >= 0; i--, page--) {
//         console.log(buttonList[i]);
//         if(i != buttonList.length-1){
//             buttonList[i].setAttribute("id", "");
//         }
//         else {
//             buttonList[i].setAttribute("id", "current-page");
//         }
//         buttonList[i].setAttribute("value", page.toString());
//         buttonList[i].innerHTML = page.toString();
//     }
//     return calculateNumberOfPages();
// }
//
// function goToPage(event){
//     event.preventDefault();
//     let newPage = event.target.getAttribute("value");
//     let currentPageNum = getCurrentPageNumber();
//     console.log("new page");
//     console.log(newPage);
//     console.log("current Page");
//     console.log(currentPageNum);
//     let difference = parseInt(currentPageNum) - parseInt(newPage);
//     console.log("difference");
//     console.log(difference);
//     for(let counter = 0; counter < Math.abs(difference); counter++){
//         if(difference < 0){
//             nextPageFunction();
//         } else if(difference > 0) {
//                 previousPageFunction();
//         }
//     }
//     return newPage;
// }
//
// function showData(page){
//     console.log("page");
//     console.log(page);
//     let upperLimit = page*numberOfResults;
//     console.log("upperLimit");
//     console.log(upperLimit);
//     let lowerLimit = (page-1)*numberOfResults;
//     console.log("lowerLimit");
//     console.log(lowerLimit);
//     console.log("elements");
//     for(let row of allRows){
//         console.log(row);
//         let id = parseInt(row.getAttribute("id"));
//         if(id <= upperLimit && id > lowerLimit){
//             row.classList.remove("hide-data");
//         } else {
//             row.classList.add("hide-data");
//         }
//         console.log(row.classList);
//         console.log(row);
//     }
// }
//




let searchBar = document.getElementById("leasables-search");
searchBar.addEventListener("keyup", (event)=> searchLeasables(event));

function searchLeasables(event){
    console.log(event.target.value);
    let input = searchBar.value.toLowerCase();
    if(input.length != 0){
        console.log("value does not have a length of zero");
        for (let row of allRows) {
            if (row.children[2].innerHTML.toLowerCase().includes(input)) {
                if (row.classList.contains("hide-data")) {
                    row.classList.remove("hide-data");
                }
            } else {
                if (!row.classList.contains("hide-data")) {
                    row.classList.add("hide-data");
                }
            }
        }
    }else{
        showData(getCurrentPageNumber());
    }

}













//Modal views
let modalContainer = document.querySelector("#modal-container");
let modalContent = document.querySelector("#modal-content");
let leasableIdView = document.querySelector("#leasable-id");
let leasableCodeView = document.querySelector("#leasable-code");
let leasableTypeView = document.querySelector("#leasable-type");
let leasableSizeView = document.querySelector("#leasable-size");
let leasableRentView = document.querySelector("#leasable-rent");
let leasableStatusView = document.querySelector("#leasable-status");

let tableRows = document.querySelectorAll(".leasable-data-row");
for(let row of tableRows){
    row.addEventListener("click", (event)=> viewModal(event));
}
let updateButton = document.querySelector("#update-button");
updateButton.addEventListener("click", (event) => viewModal(event));
let deleteButton = document.querySelector("#delete-button");
deleteButton.addEventListener("click", (event) => viewModal(event));
let closeViewButton = document.querySelector("#close");
closeViewButton.addEventListener("click", (event) => closeView(event));
console.log("close view button");
console.log(closeViewButton);
let leasableHistoryIds = document.querySelectorAll(".leasable-history-identifier");
let webBody = document.getElementsByTagName("body");

function viewModal(event){
    event.preventDefault();
    // console.log("weblog");
    // // console.log(webBody);
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedId = event.currentTarget.id;
    console.log("current target id");
    console.log(event.currentTarget.id);
    let rowSelected = document.getElementById(rowSelectedId);
    console.log(rowSelected.children);
    let leasableId;
    for (let element of rowSelected.children) {
        switch(element.id) {
            case "leasable-id-node": leasableIdView.innerHTML = "Leasable Id: " + element.innerHTML;
                                    leasableId = element.innerHTML; break;
            case "leasable-code-node": leasableCodeView.innerHTML = "Leasable Code: " + element.innerHTML; break;
            case "leasable-type-node": leasableTypeView.innerHTML = "Leasable Type: " + element.innerHTML; break;
            case "leasable-size-node": leasableSizeView.innerHTML = "Leasable Size: " + element.innerHTML; break;
            case "leasable-rent-node": leasableRentView.innerHTML = "Leasable Yearly Rent: " + element.innerHTML; break;
            case "leasable-status-node": leasableStatusView.innerHTML = "Leasable Status: " + element.innerHTML; break;
            // case "leasable-date-node": leasableCreationDateView.innerHTML = "Creation Date: " + rowSelected[element]; break;
            default: break;
        }
    }

    for(let leasableHistoryId of leasableHistoryIds){
        if(leasableHistoryId.innerHTML == leasableId){
            leasableHistoryId.parentElement.style.display="block";
        }
    }

}


function closeView(event){
    event.preventDefault();
    console.log("this function is being called");
    modalContainer.style.display = "none";
    webBody[0].style.overflow = "scroll";
    for(let leasableHistoryId of leasableHistoryIds){
        leasableHistoryId.parentElement.style.display="none";
    }
}

function updateModal(event){
    event.preventDefault();

}

function deleteModal(event){
    event.preventDefault();

}