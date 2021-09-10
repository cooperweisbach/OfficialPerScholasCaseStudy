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
let paginationResultsSize= parseInt(paginationResultsSizeSelector.innerHTML);

let allRows;
let numberOfPages;
let totalInstances;

//Modal views
let modelHistoryDataBody = document.querySelector("#model-history-data-body");
let modelHistoryTable = document.querySelector("#model-history-table");
let modalContainer = document.querySelector("#model-modal-container");

let updateButton = document.querySelector("#modal-update-button");
updateButton.addEventListener("click", (event) => viewUpdateModal(event));
let deleteButton = document.querySelector("#modal-delete-button");
deleteButton.addEventListener("click", (event) => viewDeleteModal(event));
let closeViewButton = document.querySelector(".modal-close-button");
closeViewButton.addEventListener("click", (event) => closeView(event));
let webBody = document.getElementsByTagName("body");
let modalViewForm = document.querySelector("#modal-view-form");
let modelId;
let continueDeletePrompt;
let continueDeleteConfirm;
let continueDeleteCancel;
let continueUpdatePrompt;
let continueUpdateConfirm;
let continueUpdateCancel;
let modelViewInfo;
let modelInputInfo;

function resetData(){
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

function getCurrentPageNumber(){
    return document.getElementById("current-page").getAttribute("value");
}

$("#select-number-of-results").change(()=>{
    resetData();
    resetButtons();
    paginationResultsSizeSelector = document.querySelector(".number-of-results:checked");
    paginationResultsSize = parseInt(paginationResultsSizeSelector.innerHTML);
    console.log("Pagination results size");
    console.log(paginationResultsSize);
    getLeasablesData(1, paginationResultsSize, true)});


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
    resetData();
    return newPage;
}



function resetHistory(){
    modelHistoryDataBody.remove();
    modelHistoryDataBody = document.createElement('tbody');
    modelHistoryTable.appendChild(modelHistoryDataBody);
    modelHistoryDataBody.setAttribute('id', 'model-history-data-body');
    modelHistoryDataBody.setAttribute('class', 'flex flex-vertical');
}

function closeView(event){
    event.preventDefault();
    console.log("this function is being called");
    modalContainer.style.display = "none";
    webBody[0].style.overflow = "scroll";

}
