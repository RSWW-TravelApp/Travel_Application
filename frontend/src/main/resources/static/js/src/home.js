function setAttributes(el, attrs) {
  for(var key in attrs) {
    el.setAttribute(key, attrs[key]);
  }
}

function addSquareFrame(node, x, y, w, h, thickness = 2, padding = 2) {
    const squareSVG = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    setAttributes(squareSVG,
    {'xmlns': 'http://www.w3.org/2000/svg',
        'viewBox': [x, y, w, h].join(" "),
        'width': w,
        'height': h,
        'thickness': thickness,
        'padding': padding
    });


    pathway = `M${padding} ${padding} L${padding} ${h - padding} L${w - padding} ${h - padding} L${w - padding} ${padding} L${padding} ${padding}`
    console.log(pathway);
    const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    setAttributes(path,
    {'xmlns': 'http://www.w3.org/2000/svg',
    'fill': 'none',
    'stroke': 'black',
    'stroke-width': thickness,
    'd': pathway
    });
    var xd = "xd";
    squareSVG.appendChild(path);
    const div = document.createElement('div').appendChild(squareSVG);
    return node.appendChild(div);
}

function addText(node, text) {
  const div = document.createElement('div');
  var textObj = document.createElement('p');
  textObj.textContent = text;
  div.appendChild(textObj);
  return node.appendChild(div);
}

async function fetchDestinations(el) {
  await fetch('http://localhost:8080/destinations', {method: "GET"})
  .then(response => response.json())
  .then(data => {
    const listOfDestinations = document.createElement('div');
    data.forEach(item => {
                const destinationItem = document.createElement('div');
                destinationItem.setAttribute("class", "destination_card");
                addSquareFrame(destinationItem, 0, 0, 100, 100);
                addText(destinationItem, item.name);
                listOfDestinations.appendChild(destinationItem);
              });
    const container = document.getElementById('container');
    container.appendChild(listOfDestinations);
  });
}