$( function()
    {
        getPostsData(1, paginationResultsSize, true);
    }
);

function getPostsData(pageNum, paginationResultsSize, initialize){
    let formData = new FormData();
    formData.append("numOfResults", parseInt(paginationResultsSize));
    formData.append("pageNum", pageNum-1);
    fetch('/api/posts/get-page', {method: 'POST', body: formData})
        .then(response => response.json())
        .then(data => {
            showDataPosts(pageNum, data.content);
            numberOfPages = data.totalPages;
            totalInstances = data.totalElements;
            if(initialize){
                addPostsDataPaginationButtons(numberOfPages);
            }
        });
}


function addPostsDataPaginationButtons(numberOfPages){
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
        newButton.addEventListener("click", (event) => getPostsData(goToPage(event), paginationResultsSize, false));
        paginationButtonHolder.appendChild(newButton);
    }
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetData(); getPostsData(firstPageFunction(), paginationResultsSize, false);
            break;
        case "previous-page-button": {
            if(getCurrentPageNumber() != 1) {
                resetData();
                getPostsData(previousPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case"next-page-button": {
            if (getCurrentPageNumber() != buttonList.length) {
                resetData();
                getPostsData(nextPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case "last-page-button": resetData(); getPostsData(lastPageFunction(), paginationResultsSize, false);
            break;
        default: break;
    }

}


function showDataPosts(pageNum, data){
    console.log("this was called");
    console.log(data);
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
                case 'postId': {
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-id-table');
                    break;
                }
                case 'creationDate':{
                    let val = result[attribute];
                    if(val != null)
                        text = document.createTextNode(val);
                    else{
                        text = document.createTextNode("none");
                    }
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-creation-table');
                    break;
                }
                case 'postDate':{
                    let val = result[attribute];
                    if(val != null)
                        text = document.createTextNode(val);
                    else{
                        text = document.createTextNode("none");
                    }
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-publish-table');
                    break;
                }
                case 'member':{
                    text = document.createTextNode(result[attribute].firstName + ' ' + result[attribute].lastName);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-member-table');
                    break;
                }
                case 'postStatus':{
                    let val = result[attribute].postStatus;
                    if(val != null)
                        text = document.createTextNode(val);
                    else{
                        text = document.createTextNode("none");
                    }
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-status-table');
                    break;
                }
                case 'postTagList':{
                    let val = result[attribute];
                    if(val.length != 0)
                        text = document.createTextNode(result[attribute].map(postTag => {
                            return postTag.postTagTitle;
                        }));
                    else{
                        text = document.createTextNode("none");
                    }
                    console.log("tag list");
                    console.log(text);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-tags-table');
                    break;
                }
                case 'postTitle':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-title-table');
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
let modelPublishView = document.querySelector("#model-published-modal");
let modelCreationView = document.querySelector("#model-created-modal");
let modelMemberView = document.querySelector("#model-member-modal");
let modelStatusView = document.querySelector("#model-status-modal");
let modelTagsView = document.querySelector("#model-tags-modal");
let modelTitleView = document.querySelector("#model-title-modal");

function viewLeaseModal(event){
    event.preventDefault();
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedId = event.currentTarget.id;
    let rowSelected = document.getElementById(rowSelectedId);
    let postId;
    for (let element of rowSelected.children) {
        switch(element.classList[0]) {
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                postId = element.innerHTML; break;
            case "model-creation-table": modelCreationView.innerHTML = "Created: " + element.innerHTML; break;
            case "model-publish-table": modelPublishView.innerHTML = "Published: " + element.innerHTML; break;
            case "model-title-table": modelTitleView.innerHTML = "Title: " + element.innerHTML; break;
            case "model-tags-table": modelTagsView.innerHTML = "Tags: " + element.innerHTML; break;
            case "model-status-table": modelStatusView.innerHTML = "Status: " + element.innerHTML; break;
            case "model-member-table": modelMemberView.innerHTML = "Member: " + element.innerHTML; break;
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