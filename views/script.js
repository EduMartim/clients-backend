const url = "http://localhost:8080/tasks/user/1";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

/*
function show(tasks) {
    let output = "";
    tasks.forEach((task) => {
        output += `
        <div class="card">
            <h3>${task.title}</h3>
            <p>${task.description}</p>
        </div>
        `;
    });
    document.getElementById("tasks").innerHTML = output;
}
*/

function show(tasks) {
    let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
            <th scope="col">Username</th>
            <th scope="col">UserI d</th>
        </thead>`;

    for (let task in tasks) {
        tab += `<tr>
            <td>${task.id}</td>
            <td>${task.description}</td>
            <td>${task.user.username}</td>
            <td>${task.user.id}</td>
        </tr>`;
    }
    document.getElementById("tasks").innerHTML = tab;
}

async function getAPI(url) {
    const response = await fetch(url, { method: "GET" });
    const data = await response.json();
    console.log(data);
    if(response.status === 200) {
        hideLoader();
        show(data);
    }

}

getAPI(url); // Call the function name getAPI with the url as a parameter to fetch the data from the server and display it in the browser.