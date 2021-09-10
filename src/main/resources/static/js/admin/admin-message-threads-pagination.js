$( function()
    {
        console.log(window.location.href);
        getThreadData(1, paginationResultsSize, true);
    }
);

function getThreadData(pageNum, paginationResultsSize, initialize){
    console.log("pagination results size");
    console.log(paginationResultsSize);
    let formData = new FormData();
    formData.append("numOfResults", paginationResultsSize);
    formData.append("pageNum", pageNum-1);
    fetch("/api/message-threads/get-page", {method: "POST", body: formData})
        .then(response => response.json())
        .then(data => {
            console.log("at data");
            console.log(data);
            showDataThreads(pageNum, data.content);
            numberOfPages = data.totalPages;
            totalInstances = data.totalElements;
            if(initialize){
                addThreadsDataPaginationButtons(numberOfPages);
            }
        });
}


function addThreadsDataPaginationButtons(numberOfPages){
    let maxIndex = 5;
    if(numberOfPages < 5){
        maxIndex = numberOfPages;
    }
    for(let i = 1; i <= maxIndex; ++i) {
        console.log("i: "+ i.toString());
        let newButton = document.createElement('button');
        newButton.appendChild(document.createTextNode(i.toString()));
        newButton.setAttribute("value", i.toString());
        newButton.setAttribute("class", "pagination-button");
        if(i == 1){
            newButton.setAttribute("id", "current-page");
        }
        newButton.addEventListener("click", (event) => getThreadData(goToPage(event), paginationResultsSize, false));
        paginationButtonHolder.appendChild(newButton);
    }
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetData(); getThreadData(firstPageFunction(), paginationResultsSize, false);
            break;
        case "previous-page-button": {
            if(getCurrentPageNumber() != 1) {
                resetData();
                getThreadData(previousPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case"next-page-button": {
            if (getCurrentPageNumber() != buttonList.length) {
                resetData();
                getThreadData(nextPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case "last-page-button": resetData(); getThreadData(lastPageFunction(), paginationResultsSize, false);
            break;
        default: break;
    }

}

function showDataThreads(pageNum, data){
    console.log("threads");
    console.log(data);
    let counter = 1;
    let result;
    let newRow;
    let attribute;
    let text;
    let dataPoint;
    let imgSRC;
    let img;
    let images= new Map();
    for(result of data){
        newRow = document.createElement('tr');
        newRow.setAttribute('class', 'model-data-row');
        newRow.setAttribute('id', (counter).toString()+"-data-row");
        newRow.addEventListener('click', (event)=>viewThreadModal(event));
        counter++;
        for(attribute in result){
            switch(attribute){
                case 'messageThreadId': {
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-id-table');
                    break;
                }
                case 'messageThreadName':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-name-table');
                    break;
                }
                case 'messageThreadCreation':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-created-table');
                    break;
                }
                case 'messageThreadStatus':{
                    text = document.createTextNode(result[attribute].messageThreadStatus);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-status-table');
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
let modelNameViewSpan = document.querySelector("#model-name-modal-span");
let modelNameViewInput = document.querySelector("#model-name-modal-input");
let modelCreatedViewSpan = document.querySelector("#model-created-modal-span");
let modelStatusViewSpan = document.querySelector("#model-status-modal-span");
let modelStatusViewSelector = document.querySelector("#model-status-modal-selector");


function viewThreadModal(event){
    event.preventDefault();
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedId = event.currentTarget.id;
    let rowSelected = document.getElementById(rowSelectedId);
    let threadId;
    for (let element of rowSelected.children) {
        switch(element.classList[0]) {
            case "model-id-table": modelIdViewSpan.innerHTML = element.innerHTML;
                modelIdViewInput.setAttribute('value', element.innerHTML);
                threadId = element.innerHTML; break;
            case "model-created-table": modelCreatedViewSpan.innerHTML = element.innerHTML;
                break;
            case "model-name-table": modelNameViewSpan.innerHTML = element.innerHTML;
                modelNameViewInput.setAttribute('value', element.innerHTML);
                break;
            case "model-status-table": modelStatusViewSpan.innerHTML = element.innerHTML;
                let status = document.querySelector('#'+element.innerHTML);
                status.setAttribute('selected', 'selected');
                break;
            default: break;
        }
    }

    // let formData = new FormData();
    // formData.append('threadId', threadId);
    // fetch('/api/leases/thread-id', {method: "POST", body: formData})
    //     .then(response => response.json())
    //     .then(data =>
    //         showHistoryLeases(data));
}

//
// function showHistoryLeases(data) {
//     resetHistory();
//     let result;
//     let newRow;
//     let attribute;
//     let text;
//     let dataPoint;
//     for (result of data) {
//         newRow = document.createElement('tr');
//         newRow.setAttribute('class', 'model-history-row');
//         for (attribute in result) {
//             switch (attribute) {
//                 case 'leasable': text = document.createTextNode(result[attribute].leasableCode);
//                     dataPoint = document.createElement("td");
//                     dataPoint.setAttribute('class', 'model-history-leasable');
//                     break;
//                 case 'leaseId':text = document.createTextNode(result[attribute]);
//                     dataPoint = document.createElement("td");
//                     dataPoint.setAttribute('class', 'model-history-id');
//                     break;
//                 case 'startDate': text = document.createTextNode(result[attribute]);
//                     dataPoint = document.createElement("td");
//                     dataPoint.setAttribute('class', 'model-history-start');
//                     break;
//                 case 'endDate':text = document.createTextNode(result[attribute]);
//                     dataPoint = document.createElement("td");
//                     dataPoint.setAttribute('class', 'model-history-end');
//                     break;
//                 default:
//                     break;
//             };
//             dataPoint.appendChild(text);
//             newRow.appendChild(dataPoint);
//         }
//         modelHistoryDataBody.appendChild(newRow);
//     }
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
    modalViewForm.setAttribute('action', '/admin/message-threads/delete-approved');
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
    modalViewForm.setAttribute('action', '/admin/message-threads/update-approved');
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