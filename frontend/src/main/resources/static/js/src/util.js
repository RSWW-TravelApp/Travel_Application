function setAttributes(el, attrs) {
  for(var key in attrs) {
    el.setAttribute(key, attrs[key]);
  }
}

function squareFrame(x, y, w, h, thickness = 2, padding = 2, txt = undefined) {
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

    if (txt != undefined) {
        const textObj = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        setAttributes(textObj,
            {'xmlns': 'http://www.w3.org/2000/svg',
            'x': "50%",
            'y': "50%",
            'dominant-baseline': 'middle',
            'text-anchor': 'middle',
            'fill': 'black',
            });
        textObj.textContent = txt;
        squareSVG.appendChild(textObj);
    }
    return squareSVG;
}