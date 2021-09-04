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
//     let maxNumOfPages = buttonList.length;
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
//
//     let maxNumOfPages = buttonList.length;
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
//     for(let i = buttonList.length-1, page = buttonList.length; i >= 0; i--, page--) {
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
//     return buttonList.length;
// }
//
// function goToPage(event){
//     event.preventDefault();
//     console.log("target");
//     console.log(event.target);
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
//             previousPageFunction();
//         }
//     }
//     resetData();
//     return newPage;
// }
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
let modelIdView = document.querySelector("#model-id-modal");
let modelCodeView = document.querySelector("#model-code-modal");
let modelTypeView = document.querySelector("#model-type-modal");
let modelSizeView = document.querySelector("#model-size-modal");
let modelRentView = document.querySelector("#model-rent-modal");
let modelStatusView = document.querySelector("#model-status-modal");

let updateButton = document.querySelector("#modal-update-button");
updateButton.addEventListener("click", (event) => viewLeasableModal(event));
let deleteButton = document.querySelector("#modal-delete-button");
deleteButton.addEventListener("click", (event) => viewLeasableModal(event));
let closeViewButton = document.querySelector(".modal-close-button");
closeViewButton.addEventListener("click", (event) => closeView(event));

let modelHistoryIds = document.querySelectorAll(".model-history-identifier");
let webBody = document.getElementsByTagName("body");

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
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                leasableId = element.innerHTML; break;
            case "model-code-table": modelCodeView.innerHTML = "Leasable Code: " + element.innerHTML; break;
            case "model-type-table": modelTypeView.innerHTML = "Type: " + element.innerHTML; break;
            case "model-size-table": modelSizeView.innerHTML = "Leasable Size: " + element.innerHTML; break;
            case "model-rent-table": modelRentView.innerHTML = "Leasable Yearly Rent: " + element.innerHTML; break;
            case "model-status-table": modelStatusView.innerHTML = "Status: " + element.innerHTML; break;
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