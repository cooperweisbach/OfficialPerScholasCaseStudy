$( function()
    {
        getData(1, paginationResultsSize, true);
}
    );

let firstPageButton = document.querySelector("#first-page-button");
firstPageButton.addEventListener("click", (e)=> changePage(e));
let previousPageButton =  document.querySelector("#previous-page-button");
previousPageButton.addEventListener("click", (e)=> changePage(e));
let nextPageButton = document.querySelector("#next-page-button");
nextPageButton.addEventListener("click", (e)=> changePage(e));
let lastPageButton  = document.querySelector("#last-page-button");
lastPageButton.addEventListener("click", (e)=> changePage(e));

let returnedModelsDataBody = document.querySelector("#returned-models-data-body");

let paginationButtonHolder = document.querySelector("#pagination-button-holder");
let paginationResultsSizeSelector = document.querySelector(".number-of-results:checked");
let paginationResultsSize= paginationResultsSizeSelector.innerHTML;
$("#select-number-of-results").change(()=>{
    resetTable();
    resetButtons();
    paginationResultsSizeSelector = document.querySelector(".number-of-results:checked");
    paginationResultsSize = parseInt(paginationResultsSizeSelector.innerHTML);
    numberOfResults = paginationResultsSize;
    console.log("Pagination results size");
    console.log(paginationResultsSize);
    getData(1, paginationResultsSize, true)});
console.log("test output");
console.log(paginationResultsSize);
let numberOfResults = parseInt(paginationResultsSize);
console.log(numberOfResults);
let allRows = document.querySelectorAll(".model-data-row");
let totalCount = allRows.length;
let numberOfPages;
let totalInstances;


function resetTable(){
    returnedModelsDataBody.remove();
    returnedModelsDataBody = document.createElement("tbody");
    returnedModelsDataBody.setAttribute("id", "#returned-models-data-body");
    document.querySelector("#returned-models-data").appendChild(returnedModelsDataBody);
}

function resetButtons(){
    paginationButtonHolder.remove();
    paginationButtonHolder =document.createElement("div");
    paginationButtonHolder.setAttribute("id", "#pagination-button-holder");
    document.querySelector("#central-button-holder").appendChild(paginationButtonHolder);
}

function getData(pageNum, paginationResultsSize, initialize){
    console.log("get data variable values");
    console.log(pageNum);
    console.log(paginationResultsSize);
    console.log(initialize);
    let formData = new FormData();
    formData.append("numOfResults", parseInt(paginationResultsSize));
    formData.append("pageNum", pageNum-1);
    fetch('/api/leasables/get-page', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            console.log("returned page");
            console.log(data);
                showDataLeasables(pageNum, data.content);
                numberOfPages = data.totalPages;
                totalInstances = data.totalElements;
                console.log("numberOfPages");
                console.log(numberOfPages);
                if(initialize){
                    console.log("initialize");
                    addLeasablesDataPaginationButtons(numberOfPages);
                }
        });
}


function addLeasablesDataPaginationButtons(numberOfPages){
    console.log("number of pages");
    console.log(numberOfPages);
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
        newButton.addEventListener("click", (event) => getData(goToPage(event), numberOfResults, false));
        paginationButtonHolder.appendChild(newButton);
    }
}


function getCurrentPageNumber(){
    return document.getElementById("current-page").getAttribute("value");
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetTable(); getData(firstPageFunction(), numberOfResults, false);
            break;
        case "previous-page-button": {
                if(getCurrentPageNumber() != 1) {
                    resetTable();
                    getData(previousPageFunction(), numberOfResults, false);
                }
                break;
            }
        case"next-page-button": {
                if (getCurrentPageNumber() != buttonList.length) {
                    resetTable();
                    getData(nextPageFunction(), numberOfResults, false);
                }
                break;
            }
        case "last-page-button": resetTable(); getData(lastPageFunction(), numberOfResults, false);
            break;
        default: break;
    }

}

function firstPageFunction(){
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(buttonList);
    for(let i = 0; i < buttonList.length; i++) {
        console.log(buttonList[i])
        if(i != 0){
            buttonList[i].setAttribute("id", "");
        }
        else {
            buttonList[i].setAttribute("id", "current-page");
        }
        buttonList[i].setAttribute("value", (i+1).toString());
        buttonList[i].innerHTML = (i+1).toString();
    }
    return 1;
}
function previousPageFunction(){
    let buttonList = document.getElementsByClassName("pagination-button");
    let currentPageElement = document.getElementById("current-page");
    let currentPageValue = parseInt(currentPageElement.getAttribute("value"));
    let startOfRange = buttonList[0].getAttribute("value");
    let endOfRange = buttonList[buttonList.length-1].getAttribute("value");
    let maxNumOfPages = buttonList.length;

    if(startOfRange != 1 && currentPageValue <= (maxNumOfPages-2)){
        for(let element of buttonList){
            element.setAttribute("value", (parseInt(element.getAttribute("value")) - 1).toString());
            element.innerHTML = (parseInt(element.innerHTML) - 1).toString();
        }
        return getCurrentPageNumber();
    } else if(currentPageValue != 1) {
        let newCurrentPageIndex;
        for(let element = buttonList.length -1; element >=0; element--){
            console.log("element");
            console.log(element);
            if(buttonList[element].id == "current-page"){
                console.log("found current page element");
                buttonList[element].setAttribute("id", "");
                newCurrentPageIndex=(element-1);
                console.log(newCurrentPageIndex);
            }
            if(element == newCurrentPageIndex){
                buttonList[element].setAttribute("id", "current-page");
                return buttonList[element].getAttribute("value");
            }
        }
    } else {
        return getCurrentPageNumber();
    }
}

function nextPageFunction(){
    let buttonList = document.getElementsByClassName("pagination-button");
    let currentPageElement = document.getElementById("current-page");
    let currentPageValue = parseInt(currentPageElement.getAttribute("value"));
    let startOfRange = buttonList[0].getAttribute("value");
    let endOfRange = buttonList[buttonList.length-1].getAttribute("value");

    let maxNumOfPages = buttonList.length;

    if(endOfRange != maxNumOfPages && currentPageValue >= 3){
        for(let element of buttonList){
            element.setAttribute("value", (parseInt(element.getAttribute("value"))+1).toString());
            element.innerHTML = (parseInt(element.innerHTML) + 1).toString();
        }
        return getCurrentPageNumber();
    } else if(currentPageValue != maxNumOfPages) {
        let newCurrentPageIndex;
        for(let element in buttonList){
            console.log("element");
            console.log(element);
            if(buttonList[element].id == "current-page"){
                console.log("found current page element");
                buttonList[element].setAttribute("id", "");
                newCurrentPageIndex=(parseInt(element)+1);
                console.log(newCurrentPageIndex);
            }
            if(element == newCurrentPageIndex){
                buttonList[element].setAttribute("id", "current-page");
                return buttonList[element].getAttribute("value");
            }
        }
    } else {
        return getCurrentPageNumber();
    }
}

function lastPageFunction(){
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(buttonList);
    for(let i = buttonList.length-1, page = buttonList.length; i >= 0; i--, page--) {
        console.log(buttonList[i]);
        if(i != buttonList.length-1){
            buttonList[i].setAttribute("id", "");
        }
        else {
            buttonList[i].setAttribute("id", "current-page");
        }
        buttonList[i].setAttribute("value", page.toString());
        buttonList[i].innerHTML = page.toString();
    }
    return buttonList.length;
}

function goToPage(event){
    event.preventDefault();
    console.log("target");
    console.log(event.target);
    let newPage = event.target.getAttribute("value");
    let currentPageNum = getCurrentPageNumber();
    console.log("new page");
    console.log(newPage);
    console.log("current Page");
    console.log(currentPageNum);
    let difference = parseInt(currentPageNum) - parseInt(newPage);
    console.log("difference");
    console.log(difference);
    for(let counter = 0; counter < Math.abs(difference); counter++){
        if(difference < 0){
            nextPageFunction();
        } else if(difference > 0) {
            previousPageFunction();
        }
    }
    resetTable();
    return newPage;
}
function showDataLeasables(pageNum, data){
    let result;
    let newRow;
    let attribute;
    let text;
    let dataPoint;
    for(result of data){
        newRow = document.createElement('tr');
        newRow.setAttribute('class', 'model-data-row');

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


}

function showData(page){
    console.log("page");
    console.log(page);
    let upperLimit = page*numberOfResults;
    console.log("upperLimit");
    console.log(upperLimit);
    let lowerLimit = (page-1)*numberOfResults;
    console.log("lowerLimit");
    console.log(lowerLimit);
    console.log("elements");
    for(let row of allRows){
        console.log(row);
        let id = parseInt(row.getAttribute("id"));
        if(id <= upperLimit && id > lowerLimit){
            row.classList.remove("hide-data");
        } else {
            row.classList.add("hide-data");
        }
        console.log(row.classList);
        console.log(row);
    }
}
