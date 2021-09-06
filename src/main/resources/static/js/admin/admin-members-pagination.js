$( function()
    {
        console.log(window.location.href);
        getMemberData(1, paginationResultsSize, true);
    }
);

function getMemberData(pageNum, paginationResultsSize, initialize){
    console.log("pagination results size");
    console.log(paginationResultsSize);
    let formData = new FormData();
    formData.append("numOfResults", paginationResultsSize);
    formData.append("pageNum", pageNum-1);
    fetch("/api/users/get-page", {method: "POST", body: formData})
        .then(response => response.json())
        .then(data => {
            console.log("at data");
            console.log(data);
             showDataMembers(pageNum, data.content);
            numberOfPages = data.totalPages;
            totalInstances = data.totalElements;
            if(initialize){
                addMembersDataPaginationButtons(numberOfPages);
            }
        });
}


function addMembersDataPaginationButtons(numberOfPages){
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
        newButton.addEventListener("click", (event) => getMemberData(goToPage(event), paginationResultsSize, false));
        paginationButtonHolder.appendChild(newButton);
    }
}

function changePage(event){
    event.preventDefault();
    let buttonList = document.getElementsByClassName("pagination-button");
    console.log(event.target);
    switch(event.target.id){
        case "first-page-button":resetData(); getMemberData(firstPageFunction(), paginationResultsSize, false);
            break;
        case "previous-page-button": {
            if(getCurrentPageNumber() != 1) {
                resetData();
                getMemberData(previousPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case"next-page-button": {
            if (getCurrentPageNumber() != buttonList.length) {
                resetData();
                getMemberData(nextPageFunction(), paginationResultsSize, false);
            }
            break;
        }
        case "last-page-button": resetData(); getMemberData(lastPageFunction(), paginationResultsSize, false);
            break;
        default: break;
    }

}

function showDataMembers(pageNum, data){
    console.log("members");
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
        newRow.addEventListener('click', (event)=>viewMemberModal(event));
        counter++;
        for(attribute in result){
            switch(attribute){
                case 'memberId': {
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-id-table');
                    break;
                }
                case 'profilePic':{
                    img = document.createElement('img');
                    console.log(result[attribute]);
                    try{
                        console.log(images);
                        imgSRC = result[attribute].uploadPath;
                        images.set(imgSRC.imageId, imgSRC.uploadPath);
                    }catch(exception){
                        imgSRC = images.get(result[attribute]);
                    }
                    img.setAttribute('src', imgSRC);
                    img.classList.add("stored-image");
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(img);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-image-table');
                    break;
                }
                case 'fullName':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-name-table');
                    break;
                }
                case 'email':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-email-table');
                    break;
                }
                case 'phoneNumber':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-phone-table');
                    break;
                }
                case 'joinedDate':{
                    text = document.createTextNode(result[attribute]);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-date-table');
                    break;
                }
                case 'memberStatus':{
                    text = document.createTextNode(result[attribute].memberStatus);
                    dataPoint = document.createElement('td');
                    dataPoint.appendChild(text);
                    newRow.appendChild(dataPoint);
                    dataPoint.setAttribute('class', 'model-status-table');
                    break;
                }
                case 'userRoles':{
                    let roleList = '';
                    for(let role in result[attribute] )
                        roleList = result[attribute][role].userRoleName + ' ' + roleList ;
                    text = document.createTextNode(roleList);
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
let modelNameView = document.querySelector("#model-name-modal");
let modelTypeView = document.querySelector("#model-type-modal");
let modelJoinedView = document.querySelector("#model-joined-modal");
let modelPhoneView = document.querySelector("#model-phone-modal");
let modelStatusView = document.querySelector("#model-status-modal");
let modelEmailView = document.querySelector("#model-email-modal");


function viewMemberModal(event){
    event.preventDefault();
    webBody[0].style.overflow = "hidden";
    modalContainer.style.display = "flex";
    modalContainer.style.justifyContent = "center";
    modalContainer.style.alignItems = "center";
    let rowSelectedId = event.currentTarget.id;
    let rowSelected = document.getElementById(rowSelectedId);
    let memberId;
    for (let element of rowSelected.children) {
        switch(element.classList[0]) {
            case "model-id-table": modelIdView.innerHTML = "Id: " + element.innerHTML;
                memberId = element.innerHTML; break;
            case "model-email-table": modelEmailView.innerHTML = "Email: " + element.innerHTML; break;
            case "model-phone-table": modelPhoneView.innerHTML = "Phone: " + element.innerHTML; break;
            case "model-type-table": modelTypeView.innerHTML = "Type: " + element.innerHTML; break;
            case "model-name-table": modelNameView.innerHTML = "Name: " + element.innerHTML; break;
            case "model-joined-table": modelJoinedView.innerHTML = "Joined: " + element.innerHTML; break;
            case "model-status-table": modelStatusView.innerHTML = "Status: " + element.innerHTML; break;
            default: break;
        }
    }

    let formData = new FormData();
    formData.append('memberId', memberId);
    fetch('/api/leases/member-id', {method: "POST", body: formData})
        .then(response => response.json())
        .then(data =>
            showHistoryLeases(data));
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
                case 'leasable': text = document.createTextNode(result[attribute].leasableCode);
                    dataPoint = document.createElement("td");
                    dataPoint.setAttribute('class', 'model-history-leasable');
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