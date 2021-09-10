$( function()
    {
        getLeasesData(1, paginationResultsSize, true);
    }
);

function getLeasesData(pageNum, paginationResultsSize, initialize){
    let formData = new FormData();
    formData.append("numOfResults", parseInt(paginationResultsSize));
    formData.append("pageNum", pageNum-1);
    fetch('/api/leases/get-page', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            showDataLeases(pageNum, data.content);
            numberOfPages = data.totalPages;
            totalInstances = data.totalElements;
            if(initialize){
                addLeasesDataPaginationButtons(numberOfPages);
            }
        });
}


function addLeasesDataPaginationButtons(numberOfPages){
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
        newButton.addEventListener("click", (event) => getLeasesData(goToPage(event), paginationResultsSize, false));
        paginationButtonHolder.appendChild(newButton);
    }
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetData(); getLeasesData(firstPageFunction(), paginationResultsSize, false);
            break;
        case "previous-page-button": {
            if(getCurrentPageNumber() != 1) {
                resetData();
                getLeasesData(previousPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case"next-page-button": {
            if (getCurrentPageNumber() != buttonList.length) {
                resetData();
                getLeasesData(nextPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case "last-page-button": resetData(); getLeasesData(lastPageFunction(), paginationResultsSize, false);
            break;
        default: break;
    }

}


function showDataLeases(pageNum, data){
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
        newRow.addEventListener('click', (event)=>viewLeaseModal(event));
        counter++;
        for(attribute in result){
            switch(attribute){
                case 'leaseId': {
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-id-table');
                    break;
                }
                case 'startDate':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-start-table');
                    break;
                }
                case 'endDate':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-end-table');
                    break;
                }
                case 'member':{
                    text = document.createTextNode(result[attribute].firstName + ' '+ result[attribute].lastName);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-member-table');
                    // text = document.createTextNode(result[attribute].memberId);
                    // dataPoint =document.createElement('td');
                    // dataPoint.appendChild(text);
                    // newRow.appendChild(dataPoint);
                    // dataPoint.setAttribute('class', 'model-memberId-table');
                    // dataPoint.setAttribute('hidden', 'hidden');
                    break;
                }
                case 'leaseStatus':{
                    text = document.createTextNode(result[attribute].leaseStatus);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-status-table');
                    break;
                }
                case 'leasable':{
                    console.log("Leasablessssssss");
                    console.log(result[attribute]);
                    console.log(result[attribute].leasableCode);
                    text = document.createTextNode(result[attribute].leasableCode);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-leasable-table');
                    text = document.createTextNode(result[attribute].leasableId);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-leasableId-table');
                    dataPoint.setAttribute('hidden', 'hidden');
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
let modelStartViewSpan = document.querySelector("#model-start-modal-span");
let modelStartViewInput = document.querySelector("#model-start-modal-input");
let modelEndViewSpan = document.querySelector("#model-end-modal-span");
let modelEndViewInput = document.querySelector("#model-end-modal-input");
let modelMemberViewSpan = document.querySelector("#model-member-modal-span");
let modelMemberViewSelector = document.querySelector("#model-member-modal-selector");
let modelStatusViewSpan = document.querySelector("#model-status-modal-span");
let modelStatusViewSelector = document.querySelector("#model-status-modal-selector");
let modelLeasableViewSpan = document.querySelector("#model-leasable-modal-span");
let modelLeasableViewSelector = document.querySelector("#model-leasable-modal-selector");
let currentLeasableOption = document.querySelector("#current-leasable-option");


function viewLeaseModal(event){
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
                modelId = document.querySelector("#model-id-modal-span").innerHTML;
                leaseId = element.innerHTML; break;
            case "model-start-table": modelStartViewSpan.innerHTML = element.innerHTML;
                modelStartViewInput.setAttribute('value', element.innerHTML); break;
            case "model-end-table": modelEndViewSpan.innerHTML = element.innerHTML;
                modelEndViewInput.setAttribute('value', element.innerHTML); break;
            case "model-member-table": modelMemberViewSpan.innerHTML = element.innerHTML; break;
            case "model-memberId-table":
                let member = document.querySelector("#member-"+element.innerHTML);
                member.setAttribute('selected', 'selected'); break;
            case "model-leasable-table":
                console.log("current leasable code");
                console.log(element.innerHTML);
                modelLeasableViewSpan.innerHTML = element.innerHTML;
                currentLeasableOption.innerHTML = element.innerHTML;
                console.log(currentLeasableOption); break;
            case "model-leasableId-table":
                console.log("current leasable ID");
                console.log(element.innerHTML);
                currentLeasableOption.setAttribute('value', element.innerHTML); break;
            case "model-status-table": modelStatusViewSpan.innerHTML = element.innerHTML;
                let status = document.querySelector("#"+element.innerHTML);
                status.setAttribute('selected', 'selected'); break;
            default: break;
        }
    }

    // let formData = new FormData();
    // formData.append('leaseId', leasableId);
    // fetch('/api/leasables/lease-id', {method: "POST", body: formData})
    //     .then(response => response.json())
    //     .then(data =>
    //         showHistoryLeasables(data));
}

//
// function showHistoryLeasables(data) {
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
//                 case 'member': text = document.createTextNode(result[attribute].firstName + ' ' + result[attribute].lastName);
//                     dataPoint = document.createElement("td");
//                     dataPoint.setAttribute('class', 'model-history-member');
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
    modalViewForm.setAttribute('action', '/admin/leases/delete-approved');
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
    modalViewForm.setAttribute('action', '/admin/leases/update-approved');
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