function createNotificationListener() {
    createEventListener(
        function(event) {
            console.log(event.message);},
        function(error) {
            console.log(error);
        }, false)
}

function cleanForm() {
    const form = document.getElementById('form');
    form.reset();
}

async function loginUser() {
    const login = document.getElementById('login').value;
    const password = document.getElementById('password').value;
    const result = document.getElementById('actionResult');

    if (login === "" || password === "") {
        result.textContent = "Login and password cannot be empty!";
        return;
    }

    await fetch(getEffectiveGatewayUri() + '/login' + `/${login}/${password}`, {method: "GET"})
          .then(response => response.text())
          .then(response => {
              const [status, body] = response.split(",");
              result.textContent = body;
              if (status === "404" || status === "400") {
                  return;
              }
              sessionStorage.setItem("user", login);
              const previousPage = sessionStorage.getItem('previousPage');
              if (previousPage == null) {
                  window.location.href = document.getElementById("homeButton").href;
                  return;
              }
              sessionStorage.removeItem('previousPage');
              window.location.href = previousPage;
          });
}

async function registerUser() {
    const login = document.getElementById('login').value;
    const password = document.getElementById('password').value;
    const result = document.getElementById('actionResult');

    if (login === "" || password === "") {
        result.textContent = "Login and password cannot be empty!";
        return;
    }

    await fetch(getEffectiveGatewayUri() + '/register' + `/${login}/${password}`, {method: "POST"})
          .then(response => response.text())
          .then(response => {
              const [status, body] = response.split(",");
              result.textContent = body;
              if (status === "404" || status === "400") {
                  return;
              }
              // cos robie
          });
}