//Modal views
let modalContainer = document.querySelector("#modal-container");
let modalContent = document.querySelector("#modal-content");
let modelIdView = document.querySelector("#model-id-modal");
let modelCodeView = document.querySelector("#model-code-modal");
let modelTypeView = document.querySelector("#model-type-modal");
let modelSizeView = document.querySelector("#model-size-modal");
let modelRentView = document.querySelector("#model-rent-modal");
let modelStatusView = document.querySelector("#model-status-modal");

let tableRows = document.querySelectorAll(".model-data-row");
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
let modelHistoryIds = document.querySelectorAll(".model-history-identifier");
let webBody = document.getElementsByTagName("body");

function viewModal(event){
    event.preventDefault();
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedIndex = event.currentTarget.id;
    console.log("current target id");
    console.log(event.currentTarget.id);
    let rowSelected = document.getElementById(rowSelectedIndex);
    console.log(rowSelected.children);
    let modelId;
    for (let element of rowSelected.children) {
        switch(element.classList[0]) {
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                modelId = element.innerHTML; break;
            case "model-code-table": modelCodeView.innerHTML = "Code: " + element.innerHTML; break;
            case "model-type-table": modelTypeView.innerHTML = "Type: " + element.innerHTML; break;
            case "model-size-table": modelSizeView.innerHTML = "Size: " + element.innerHTML; break;
            case "model-rent-table": modelRentView.innerHTML = "Yearly Rent: " + element.innerHTML; break;
            case "model-status-table": modelStatusView.innerHTML = "Status: " + element.innerHTML; break;
            default: break;
        }
    }

    for(let modelHistoryId of leasableHistoryIds){
        if(modelHistoryId.innerHTML == modelId){
            modelHistoryId.parentElement.style.display="block";
        }
    }

}


function closeView(event){
    event.preventDefault();
    console.log("this function is being called");
    modalContainer.style.display = "none";
    webBody[0].style.overflow = "scroll";
    for(let modelHistoryId of modelHistoryIds){
        modelHistoryId.parentElement.style.display="none";
    }
}

function updateModal(event){
    event.preventDefault();

}

function deleteModal(event){
    event.preventDefault();

}