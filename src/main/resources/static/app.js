function getEncryptList() {
    getRequest('/icefire/entry/user/'+localStorage.getItem('userId')).then(data => {
        clearTable();
        return data.map(function (entry) {
            addRow('entryList', entry)
        })
    })
}

function addRow(tableID, data) {
    let tableRef = document.getElementById(tableID);
    let newRow = tableRef.insertRow(-1);
    let newCell = newRow.insertCell(-1);
    let newCell2 = newRow.insertCell(0);
    let newText = document.createTextNode(data.entry);
    let newText2 = document.createTextNode(data.entryId);
    newCell.appendChild(newText);
    newRow.onclick = this.cell_Click;
    newCell2.appendChild(newText2);
}

function login(e){
    e.preventDefault();
    const object = {
        name: document.getElementById('userName').value,
        password: document.getElementById('inputPassword').value,
    };
    postRequest('/icefire/auth/', object).then(data => {
        if(data){
            localStorage.setItem("userId", data.id);
            getEncryptList();
            window.location = '/icefire/';
        }

    })
}
function logOut(e) {
    e.preventDefault();
    localStorage.removeItem("userId");
    window.location = '/icefire/login.html';
}
function clearTable() {
    const node = document.getElementById("entryList");
    while (node.hasChildNodes()) {
        node.removeChild(node.lastChild);
    }
}
let selectedEntry = {};

function cell_Click(event) {
    document.getElementById('textInput').value = event.currentTarget.cells[ 1 ].innerHTML;
    selectedEntry[ 'entry' ] = event.currentTarget.cells[ 1 ].innerHTML;
    selectedEntry[ 'entryId' ] = event.currentTarget.cells[ 0 ].innerHTML;
}
function clearSelected() {
    selectedEntry = {}
}

function sendToServer(e, m) {
    const text = document.getElementById("textInput").value;
    if(!text || text=== ""){
        alert("Please enter text");
        return;
    }
    let object = {
        entry: text,
        userId: localStorage.getItem('userId'),
        entryId: selectedEntry[ 'entryId' ]
    };
    let url = '/icefire/entry/encrypt';
    if (m === 2) {
        url = '/icefire/entry/decrypt';
    }
    e.preventDefault();

    postRequest(url, object).then(data => {
        if (m === 1) {
            document.getElementById('textInput').value = "";
            getEncryptList();
        } else {
            document.getElementById('textInput').value = data.entry;
        }
    })

}




function getRequest(url) {
    return fetch(url)
        .then(response => response.json())
        .catch(error => console.error(error))
}

function postRequest(url, data) {
    return fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
    })
        .then(response => response.json())
}