
let searchBar = document.getElementById("models-search");
searchBar.addEventListener("keyup", (event)=> searchLeasables(event));

function searchLeasables(event){

    console.log(event.target.value);
    let input = searchBar.value.toLowerCase();
    if(input.length != 0){
        console.log("value does not have a length of zero");
        for (let row of allRows) {
            if (row.children[2].innerHTML.toLowerCase().includes(input)) {
                if (row.classList.contains("hide-data")) {
                    row.classList.remove("hide-data");
                }
            } else {
                if (!row.classList.contains("hide-data")) {
                    row.classList.add("hide-data");
                }
            }
        }
    }else{
        showDataLeasables(getCurrentPageNumber(), numberOfResults);
    }

}

function searchMembers(event){

}

function searchLeases(event){

}
function searchPosts(event){

}

function searchMessageThreads(event){

}
