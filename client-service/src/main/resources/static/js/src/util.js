function getEffectiveGatewayUri() {
    const uri = document.getElementById('gateway-config').textContent;
    if (uri === null) {
        return "http://localhost:8080";
    }
    return uri;
}

function checkResponse(response) {
    if (response.status === 404) {
        throw new Error("Not found");
    }
    if (response.status === 500) {
        throw new Error("Service not found");
    }
    if (response.status !== 200)  {
        throw new Error("Not known error occurred");
    }
    return response.json()
}

function getSearchRequestParams(paramNames) {
    const searchParams = new URLSearchParams(window.location.search);
    returnParams = {}
    for (const name of paramNames) {
        returnParams[name] = searchParams.get(name);
    }
    return returnParams;
}

function setFilterStartingValues() {
    const urlSearchParams = new URLSearchParams(window.location.search);
    const params = Object.fromEntries(urlSearchParams.entries());
    for (const [key, value] of Object.entries(params)) {
        const formElement = document.getElementById(key);
        if (formElement != null) {
            formElement.setAttribute('value', value);
            if (formElement.nodeName == "SELECT") {
                        const option = formElement.querySelector(`[value="${value}"]`);
                        option.setAttribute('selected', 'selected');
            }
        }
    }
}

function disableNullFields() {
    window.addEventListener('submit', function() {
      let forms = document.getElementsByClassName('skipEmptyFields');
      for (let form of forms) {
        form.addEventListener('formdata', function(event) {
          let formData = event.formData;
          for (let [name, value] of Array.from(formData.entries())) {
            if (value === '' || value == "null") formData.delete(name);
          }
        });
      }
    });
}


function setAttributes(el, attrs) {
  for(var key in attrs) {
    el.setAttribute(key, attrs[key]);
  }
}

function squareFrame(x, y, w, h, thickness = 2, padding = 2, txt = undefined, params) {
    const squareSVG = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    setAttributes(squareSVG,
    {'xmlns': 'http://www.w3.org/2000/svg',
        'version': '2.0',
        'viewBox': [x, y, w, h].join(" "),
        'width': w,
        'height': h,
        'thickness': thickness,
        'padding': padding
    });
    setAttributes(squareSVG, params);


    pathway = `M${padding} ${padding} L${padding} ${h - padding} L${w - padding} ${h - padding} L${w - padding} ${padding} L${padding} ${padding}`
    const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    setAttributes(path,
    {'xmlns': 'http://www.w3.org/2000/svg',
    'fill': 'none',
    'stroke': 'black',
    'stroke-width': thickness,
    'd': pathway
    });
    squareSVG.appendChild(path);

    if (txt != undefined) {
        const textObj = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        const textLines = txt.split("\n");
        const interline = 1.2;
        const y = `${50 - 7 * (textLines.length - 1)}%`;
        setAttributes(textObj,
                    {
                    'x': '50%',
                    'y': y,
                    'xmlns': 'http://www.w3.org/2000/svg',
                    'dominant-baseline': 'middle',
                    'text-anchor': 'middle',
                    'fill': 'black',
                    });
        for (var i in textLines) {
            const tspan = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
            const dy = (i == 0) ? '0' : `${interline}em`;
            setAttributes(tspan,
                        {'x': '50%',
                        'dy': dy,
                        'text-anchor': 'middle',
                        'dominant-baseline': 'middle',
                        });
            tspan.textContent = textLines[i];
            textObj.appendChild(tspan);
        }
        squareSVG.appendChild(textObj);
    }
    return squareSVG;
}

function createElement(element, attributes) {
    const object = document.createElement(element);
    setAttributes(object, attributes);
    return object;
}

function appendChildren(element, children) {
    for(var child of children) {
        element.appendChild(child);
    }
    return element;
}

function createLabel(txt, params, horizontalBreaks = 0) {
    const label = createElement('label', params);
    label.textContent = txt;
    for(var i = 0; i<horizontalBreaks; i++) {
        label.innerHTML += '&nbsp;';
    }

    return label;
}

function labeledSquareProperty(x, y, w, h, thickness = 2, padding = 2, txt = undefined, params, labelText) {
    const container = createElement('div');
    if (labelText != undefined && params['id'] != undefined) {
        const label = createElement('label', {'for': params['id'], 'id': `${params['id']}-label`});
        label.textContent = labelText;
        label.innerHTML += '&nbsp;';
        appendChildren(container, [label]);
    }
    const postCard = squareFrame(x, y, w, h, thickness, padding, txt, params);
    appendChildren(container, [postCard]);
    return container;
}

function createUserInfoBox(loginCallback, logOutCallback) {
    const currentUser = sessionStorage.getItem("user");
    if (!currentUser) {
        createNotLoggedUserInfoBox(logOutCallback);
        return;
    }
    fetch(getEffectiveGatewayUri() + "/login/" + `${currentUser}/password`, {method: "GET"})
    .then(response => response.text())
              .then(response => {
                  const [status, body] = response.split(",");
                  if (status === "404") {
                      createNotLoggedUserInfoBox(logOutCallback);
                      return;
                  }
                  createLoggedUserInfoBox(currentUser, loginCallback, logOutCallback);
              });
}

function createLoggedUserInfoBox(currentUser, loginCallback, logOutCallback) {
    const parentDiv = document.getElementById('userInfo');
    const userInfoDiv = createElement('div', {'id': 'loginInfo'});
    const button = createElement('button', {
                                'name': 'loginButton',
                                'id': 'loginButton',
                                'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                                });
    button.onclick = function() { createNotLoggedUserInfoBox(logOutCallback); };
    const postCard = squareFrame(0, 0, 100, 50, 2, 2, "Log out", {'class': 'svg-button', 'id': 'loginButtonSVG'});
    const label = createLabel(`Current user: ${currentUser}`, {'for': 'loginInfo', 'style': 'font-size:1.3em;'}, 2);

    appendChildren(button, [postCard]);
    appendChildren(userInfoDiv, [label, button]);
    appendChildren(parentDiv, [userInfoDiv]);
    if (loginCallback) {
        loginCallback();
    }
}

function createNotLoggedUserInfoBox(logOutCallback) {
    sessionStorage.removeItem('user');
    const loggedUserInfoDiv = document.getElementById('loginInfo');
    if (loggedUserInfoDiv != null) {
        loggedUserInfoDiv.remove();
    }

    const parentDiv = document.getElementById('userInfo');
    const userInfoDiv = createElement('div', {'id': 'loginInfo'});
    const button = createElement('button', {
                                'name': 'loginButton',
                                'id': 'loginButton',
                                'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                                });
    button.onclick = function() {
        sessionStorage.setItem('previousPage', window.location.href);
        window.location.href = "/login";
    };
    const postCard = squareFrame(0, 0, 100, 50, 2, 2, "Log in", {'class': 'svg-button', 'id': 'loginButtonSVG'});

    appendChildren(button, [postCard]);
    appendChildren(userInfoDiv, [button]);
    appendChildren(parentDiv, [userInfoDiv]);
    if (logOutCallback) {
        logOutCallback();
    }
}

