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
                    text = document.createTextNode(result[attribute].threadStatus);
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
let modelIdView = document.querySelector("#model-id-modal");
let modelNameView = document.querySelector("#model-name-modal");
let modelCreatedView = document.querySelector("#model-created-modal");
let modelStatusView = document.querySelector("#model-status-modal");


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
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                threadId = element.innerHTML; break;
            case "model-created-table": modelCreatedView.innerHTML = "Created: " + element.innerHTML; break;
            case "model-name-table": modelNameView.innerHTML = "Name: " + element.innerHTML; break;
            case "model-status-table": modelStatusView.innerHTML = "Status: " + element.innerHTML; break;
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


function updateModal(event){
    event.preventDefault();

}

function deleteModal(event){
    event.preventDefault();

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