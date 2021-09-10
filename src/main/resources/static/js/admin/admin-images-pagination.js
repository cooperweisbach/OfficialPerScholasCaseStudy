$( function()
    {
        getImagesData(1, paginationResultsSize, true);
    }
);

function getImagesData(pageNum, paginationResultsSize, initialize){
    let formData = new FormData();
    formData.append("numOfResults", parseInt(paginationResultsSize));
    formData.append("pageNum", pageNum-1);
    fetch('/api/images/get-page', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            console.log("at data");
            console.log(data);
            showDataImages(pageNum, data.content);
            numberOfPages = data.totalPages;
            totalInstances = data.totalElements;
            if(initialize){
                addImagesDataPaginationButtons(numberOfPages);
            }
        });
}


function addImagesDataPaginationButtons(numberOfPages){
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
        newButton.addEventListener("click", (event) => getImagesData(goToPage(event), paginationResultsSize, false));
        paginationButtonHolder.appendChild(newButton);
    }
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetData(); getImagesData(firstPageFunction(), paginationResultsSize, false);
            break;
        case "previous-page-button": {
            if(getCurrentPageNumber() != 1) {
                resetData();
                getImagesData(previousPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case"next-page-button": {
            if (getCurrentPageNumber() != buttonList.length) {
                resetData();
                getImagesData(nextPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case "last-page-button": resetData(); getImagesData(lastPageFunction(), paginationResultsSize, false);
            break;
        default: break;
    }

}

function showDataImages(pageNum, data){
    let counter = 1;
    let result;
    let newRow;
    let attribute;
    let text;
    let dataPoint;
    let image;
    let imgSRC;
    for(result of data){
        console.log(result);
        newRow = document.createElement('tr');
        newRow.setAttribute('class', 'model-data-row');
        newRow.setAttribute('id', (counter).toString()+"-data-row");
        newRow.addEventListener('click', (event)=>viewLeasableModal(event));
        counter++;
        for(attribute in result){
            switch(attribute){
                case 'imageId': {
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-id-table');
                    break;
                }
                case 'imageUploadName':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-name-table');
                    break;
                }
                case 'uploadPath':{
                    imgSRC = result[attribute];
                    image = document.createElement('img');
                    image.setAttribute('src', imgSRC);
                    image.setAttribute('id', 'image-'+result['imageId']);
                    image.classList.add("stored-image");
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(image);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-image-table');
                    break;
                }
                case 'uploadDate':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-uploaded-table');
                    break;
                }
                case 'imageDescription':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-description-table');
                    break;
                }
                case 'imageType':{
                    dataPoint = document.createElement('td');
                    for(let element of result[attribute]){
                        text = document.createTextNode(element.imageType);
                        dataPoint.appendChild(text);
                    }
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
let modelUploadedViewSpan = document.querySelector("#model-uploaded-modal-span");
let modelTypeViewSpan = document.querySelector("#model-type-modal-span");
let modelTypeViewSelector = document.querySelector("#model-type-modal-selector");
let modelImageView = document.querySelector("#model-image-modal");
let modelDescriptionViewSpan = document.querySelector("#model-description-modal-span");
let modelDescriptionViewTextArea = document.querySelector("#model-description-modal-textarea");
let modelNameViewSpan = document.querySelector("#model-name-modal-span");
let modelNameViewInput = document.querySelector("#model-name-modal-input");

function viewLeasableModal(event){
    event.preventDefault();
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedId = event.currentTarget.id;
    let rowSelected = document.getElementById(rowSelectedId);
    let imageId;
    for (let element of rowSelected.children) {
        switch(element.classList[0]) {
            case "model-id-table": modelIdViewSpan.innerHTML = element.innerHTML;
                modelIdViewInput.setAttribute('value', element.innerHTML);
                modelId = document.querySelector("#model-id-modal-span").innerHTML;
                imageId = element.innerHTML;
                let image = document.querySelector("#image-"+modelId);
                modelImageView.setAttribute('src', image.getAttribute('src'));
                break;
            case "model-type-table": modelTypeViewSpan.innerHTML = element.innerHTML;
                let type = document.querySelector("#"+element.innerHTML);
                type.setAttribute('selected', 'selected');
                break;
            case "model-description-table": modelDescriptionViewSpan.innerHTML = element.innerHTML;
                modelDescriptionViewTextArea.setAttribute('value', element.innerHTML);
                break;
            case "model-uploaded-table": modelUploadedViewSpan.innerHTML = element.innerHTML; break;
            case "model-name-table": modelNameViewSpan.innerHTML = element.innerHTML;
                modelNameViewInput.setAttribute('value', element.innerHTML); break;
            default: break;
        }
    }

    // let formData = new FormData();
    // formData.append('imageId', imageId);
    // fetch('/api/leases/image-id', {method: "POST", body: formData})
    //     .then(response => response.json())
    //     .then(data =>
    //         showHistoryLeases(data));
}


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
    modalViewForm.setAttribute('action', '/admin/images/delete-approved');
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
    modalViewForm.setAttribute('action', '/admin/images/update-approved');
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