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
                    text = document.createTextNode(result[attribute].imageType);
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
let modelIdView = document.querySelector("#model-id-modal");
let modelUploadedView = document.querySelector("#model-uploaded-modal");
let modelTypeView = document.querySelector("#model-type-modal");
let modelImageView = document.querySelector("#model-image-modal");
let modelDescriptionView = document.querySelector("#model-description-modal");
let modelNameView = document.querySelector("#model-name-modal");

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
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                imageId = element.innerHTML; break;
            case "model-image-table": modelImageView.setAttribute('src', element.getAttribute('src')); break;
            case "model-type-table": modelTypeView.innerHTML = "Type: " + element.innerHTML; break;
            case "model-description-table": modelDescriptionView.innerHTML = "Image Description: " + element.innerHTML; break;
            case "model-uploaded-table": modelUploadedView.innerHTML = "Upload Date: " + element.innerHTML; break;
            case "model-name-table": modelNameView.innerHTML = "Image Name: " + element.innerHTML; break;
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


function updateModal(event){
    event.preventDefault();

}

function deleteModal(event){
    event.preventDefault();

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