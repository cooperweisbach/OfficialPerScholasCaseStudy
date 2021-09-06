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
                    text = document.createTextNode(result[attribute].leasableCode);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-leasable-table');
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
let modelIdView = document.querySelector("#model-id-modal");
let modelStartView = document.querySelector("#model-start-modal");
let modelEndView = document.querySelector("#model-end-modal");
let modelMemberView = document.querySelector("#model-member-modal");
let modelStatusView = document.querySelector("#model-status-modal");
let modelLeasableView = document.querySelector("#model-leasable-modal");

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
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                leaseId = element.innerHTML; break;
            case "model-start-table": modelStartView.innerHTML = "Start: " + element.innerHTML; break;
            case "model-end-table": modelEndView.innerHTML = "End: " + element.innerHTML; break;
            case "model-member-table": modelMemberView.innerHTML = "Member: " + element.innerHTML; break;
            case "model-leasable-table": modelLeasableView.innerHTML = "Leasable: " + element.innerHTML; break;
            case "model-status-table": modelStatusView.innerHTML = "Status: " + element.innerHTML; break;
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

function updateModal(event){
    event.preventDefault();

}

function deleteModal(event){
    event.preventDefault();

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