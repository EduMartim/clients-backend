/*

const url = "http://localhost:8080/task/user/1";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(tasks) {
    let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
            <th scope="col">Username</th>
            <th scope="col">UserId</th>
        </thead>
    `;

    for (let task of tasks) {
        tab += `
            <tr>
                <td>${task.id}</td>
                <td>${task.description}</td>
                <td>${task.user.username}</td>
                <td>${task.user.id}</td>
            </tr>
        `;
    }
    document.getElementById("tasks").innerHTML = tab;
}

async function getAPI(url) {
    const response = await fetch(url, { method: "GET" });
    const data = await response.json();
    console.log(data);
    if (response.status === 200) {
        hideLoader();
        show(data);
    }

}

getAPI(url); // Call the function name getAPI with the url as a parameter to fetch the data from the server and display it in the browser.

*/

const apiUrl = "http://localhost:8080/task/user/1";
const tasksContainer = document.getElementById("tasks");

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

function showTask(task) {
  return `
        <tr>
            <td>${task.id}</td>
            <td>${task.description}</td>
            <td>${task.user.username}</td>
            <td>${task.user.id}</td>
        </tr>
    `;
}

function renderTasks(tasks) {
  const tableHeader = `
        <thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
            <th scope="col">Username</th>
            <th scope="col">UserId</th>
        </thead>
    `;

  const tableRows = tasks.map(showTask).join("");
  const table = tableHeader + tableRows;

  tasksContainer.innerHTML = table;
}

async function fetchData(url) {
  try {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    hideLoader();
    renderTasks(data);
  } catch (error) {
    console.error("Error fetching data:", error.message);
  }
}

fetchData(apiUrl);
