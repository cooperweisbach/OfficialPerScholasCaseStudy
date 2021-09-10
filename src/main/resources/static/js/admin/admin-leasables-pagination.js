$( function()
    {
        getLeasablesData(1, paginationResultsSize, true);
}
    );

function getLeasablesData(pageNum, paginationResultsSize, initialize){
    let formData = new FormData();
    formData.append("numOfResults", parseInt(paginationResultsSize));
    formData.append("pageNum", pageNum-1);
    fetch('/api/leasables/get-page', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            console.log("at data");
            console.log(data);
                showDataLeasables(pageNum, data.content);
                numberOfPages = data.totalPages;
                totalInstances = data.totalElements;
                if(initialize){
                    addLeasablesDataPaginationButtons(numberOfPages);
                }
        });
}


function addLeasablesDataPaginationButtons(numberOfPages){
    let maxIndex = 5;
    if(numberOfPages < 5){
        maxIndex = numberOfPages;
    }
    for(let i = 1; i <= maxIndex; ++i) {
        let newButton = document.createElement('button');
        newButton.appendChild(document.createTextNode(i.toString()));
        newButton.setAttribute("value", i.toString());
        newButton.setAttribute("class", "pagination-button");
        if(i == 1){
            newButton.setAttribute("id", "current-page");
        }
        newButton.addEventListener("click", (event) => getLeasablesData(goToPage(event), paginationResultsSize, false));
        paginationButtonHolder.appendChild(newButton);
    }
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetData(); getLeasablesData(firstPageFunction(), paginationResultsSize, false);
            break;
        case "previous-page-button": {
                if(getCurrentPageNumber() != 1) {
                    resetData();
                    getLeasablesData(previousPageFunction(), paginationResultsSize, false);
                }
                break;
            }
        case"next-page-button": {
                if (getCurrentPageNumber() != buttonList.length) {
                    resetData();
                    getLeasablesData(nextPageFunction(), paginationResultsSize, false);
                }
                break;
            }
        case "last-page-button": resetData(); getLeasablesData(lastPageFunction(), paginationResultsSize, false);
            break;
        default: break;
    }

}

function showDataLeasables(pageNum, data){
    let counter = 1;
    let result;
    let newRow;
    let attribute;
    let text;
    let dataPoint;
    for(result of data){
        newRow = document.createElement('tr');
        newRow.setAttribute('class', 'model-data-row');
        newRow.setAttribute('id', (counter).toString()+"-data-row");
        newRow.addEventListener('click', (event)=>viewLeasableModal(event));
        counter++;
        for(attribute in result){
            switch(attribute){
                case 'leasableId': {
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-id-table');
                    break;
                }
                case 'leasableCode':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-code-table');
                    break;
                }
                case 'leasableCreationDate':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-date-table');
                    break;
                }
                case 'leasableSize':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-size-table');
                    break;
                }
                case 'leasableYearlyRent':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-rent-table');
                    break;
                }
                case 'leasableStatus':{
                    console.log("leasable status");
                    console.log(result[attribute]);
                    console.log(result[attribute].leasableStatus);
                    text = document.createTextNode(result[attribute].leasableStatus);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-status-table');
                    break;
                }
                case 'leasableType':{
                    text = document.createTextNode(result[attribute].leasableTypeName);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-type-table');
                    break;
                }
                default: break;
            }
        }
        returnedModelsDataBody.appendChild(newRow);
    }
    allRows = document.querySelectorAll(".model-data-row");
}


//Modal views
//Specific internal modal ids
let modelIdViewSpan = document.querySelector("#model-id-modal-span");
let modelIdViewInput = document.querySelector("#model-id-modal-input");
let modelCodeViewSpan = document.querySelector("#model-code-modal-span");
let modelCodeViewInput = document.querySelector("#model-code-modal-input");
let modelTypeViewSpan = document.querySelector("#model-type-modal-span");
let modelTypeViewInput = document.querySelector("#model-type-modal-input");
let modelSizeViewSpan = document.querySelector("#model-size-modal-span");
let modelSizeViewInput = document.querySelector("#model-size-modal-input");
let modelRentViewSpan = document.querySelector("#model-rent-modal-span");
let modelRentViewInput = document.querySelector("#model-rent-modal-input");
let modelStatusViewSpan = document.querySelector("#model-status-modal-span");
let modelStatusViewInput = document.querySelector("#model-status-modal-input");

function viewLeasableModal(event){
    event.preventDefault();
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedId = event.currentTarget.id;
    let rowSelected = document.getElementById(rowSelectedId);
    let leasableId;
    for (let element of rowSelected.children) {
        switch(element.classList[0]) {
            case "model-id-table": modelIdViewSpan.innerHTML = element.innerHTML;
                modelIdViewInput.setAttribute('value', element.innerHTML);
                leasableId = element.innerHTML; break;
            case "model-code-table": modelCodeViewSpan.innerHTML = element.innerHTML;
                modelCodeViewInput.setAttribute('value', element.innerHTML); break;
            case "model-type-table": modelTypeViewSpan.innerHTML = element.innerHTML;
                let type = document.querySelector('#'+element.innerHTML);
                type.setAttribute('selected', 'selected'); break;
            case "model-size-table": modelSizeViewSpan.innerHTML = element.innerHTML;
                modelSizeViewInput.setAttribute('value', element.innerHTML); break;
            case "model-rent-table": modelRentViewSpan.innerHTML = element.innerHTML;
                modelRentViewInput.setAttribute('value', element.innerHTML); break;
            case "model-status-table": modelStatusViewSpan.innerHTML = element.innerHTML;
                let status = document.querySelector('#'+element.innerHTML);
                status.setAttribute('selected', 'selected'); break;
            default: break;
        }
    }

    let formData = new FormData();
    formData.append('leasableId', leasableId);
    fetch('/api/leases/leasable-id', {method: "POST", body: formData})
        .then(response => response.json())
        .then(data =>
            showHistoryLeases(data));
}


function showHistoryLeases(data) {
    resetHistory();
    let result;
    let newRow;
    let attribute;
    let text;
    let dataPoint;
    for (result of data) {
        newRow = document.createElement('tr');
        newRow.setAttribute('class', 'model-history-row');
        for (attribute in result) {
            switch (attribute) {
                case 'member': text = document.createTextNode(result[attribute].firstName + ' ' + result[attribute].lastName);
                    dataPoint = document.createElement("td");
                    dataPoint.setAttribute('class', 'model-history-member');
                    break;
                case 'leaseId':text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement("td");
                    dataPoint.setAttribute('class', 'model-history-id');
                    break;
                case 'startDate': text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement("td");
                    dataPoint.setAttribute('class', 'model-history-start');
                    break;
                case 'endDate':text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement("td");
                    dataPoint.setAttribute('class', 'model-history-end');
                    break;
                default:
                    break;
            };
            dataPoint.appendChild(text);
            newRow.appendChild(dataPoint);
        }
        modelHistoryDataBody.appendChild(newRow);
    }
}


//Search leasables
//
// let searchBar = document.querySelector("#models-search");
// searchBar.addEventListener("keydown", (event)=> searchLeasables(event));
//
// function searchLeasables(event){
//     console.log(event);
//     let input = searchBar.value.toLowerCase();
//     if(input.length != 0){
//         console.log("value does not have a length of zero");
//         for (let row of allRows) {
//             console.log(row);
//             // if (row.children[2].innerHTML.toLowerCase().includes(input)) {
//             //     if (row.classList.contains("hide-data")) {
//             //         row.classList.remove("hide-data");
//             //     }
//             // } else {
//             //     if (!row.classList.contains("hide-data")) {
//             //         row.classList.add("hide-data");
//             //     }
//             // }
//         }
//     }else{
//         // getLeasablesData(1, paginationResultsSize, true);
//
//     }
//
// }


function viewDeleteModal(event){
    event.preventDefault();
    updateButton.classList.add("hidden");
    deleteButton.classList.add("hidden");
    continueDeletePrompt = document.querySelector("#continue-delete-prompt");
    continueDeleteConfirm = document.querySelector("#continue-delete-yes");
    continueDeleteCancel = document.querySelector("#continue-delete-no");
    continueDeleteConfirm.addEventListener("click", (event) =>continueDeleteModal(event));
    continueDeleteCancel.addEventListener("click", (event) =>continueDeleteModal(event));
    continueDeletePrompt.classList.remove("hidden");
    continueDeleteConfirm.classList.remove("hidden");
    continueDeleteCancel.classList.remove("hidden");
    modalViewForm.setAttribute('action', '/admin/leasables/delete-approved');
    return false;
}

function continueDeleteModal(event){
    let target = event.target.getAttribute("id");
    if(target == "continue-delete-no"){
        event.preventDefault();
        updateButton.classList.remove("hidden");
        deleteButton.classList.remove("hidden");
        continueDeletePrompt.classList.add("hidden");
        continueDeleteConfirm.classList.add("hidden");
        continueDeleteCancel.classList.add("hidden");
        modalViewForm.removeAttribute('action');
        return false;
    }
}


function viewUpdateModal(event){
    event.preventDefault();
    updateButton.classList.add("hidden");
    deleteButton.classList.add("hidden");
    continueUpdatePrompt = document.querySelector("#continue-update-prompt");
    continueUpdateConfirm = document.querySelector("#continue-update-confirm");
    continueUpdateCancel = document.querySelector("#continue-update-cancel");
    continueUpdateConfirm.addEventListener("click", (event)=> continueUpdateModal(event));
    continueUpdateCancel.addEventListener("click", (event)=> continueUpdateModal(event));
    continueUpdatePrompt.classList.remove("hidden");
    continueUpdateConfirm.classList.remove("hidden");
    continueUpdateCancel.classList.remove("hidden");
    modalViewForm.setAttribute('action', '/admin/leasables/update-approved');
    modelViewInfo = document.querySelectorAll(".model-view-info");
    modelViewInfo.forEach((row)=>{row.classList.add('hidden')});
    modelInputInfo = document.querySelectorAll(".model-input-info");
    modelInputInfo.forEach((row)=>{row.classList.remove('hidden')});

    return false;
}

function continueUpdateModal(event){
    let target = event.target.getAttribute("id");
    if(target == 'continue-update-cancel'){
        event.preventDefault();
        updateButton.classList.remove("hidden");
        deleteButton.classList.remove("hidden");
        continueUpdatePrompt.classList.add("hidden");
        continueUpdateConfirm.classList.add("hidden");
        continueUpdateCancel.classList.add("hidden");
        modalViewForm.removeAttribute('action');
        modelInputInfo.forEach((row)=>{row.classList.add('hidden')});
        modelViewInfo.forEach((row)=>{row.classList.remove('hidden')});
        return false;
    }
}