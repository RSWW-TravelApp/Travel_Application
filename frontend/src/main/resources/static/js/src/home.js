async function fetchDestinations(el) {
  await fetch('http://localhost:8080/destinations', {method: "GET"})
  .then(response => response.json())
  .then(data => {
    const listOfDestinations = document.createElement('div');
    data.forEach(item => {
                const destinationItem = document.createElement('div');
                destinationItem.setAttribute("class", "destination_card");
                destinationItem.appendChild(squareFrame(0, 0, 100, 100, 2, 2, txt=item.name));
                listOfDestinations.appendChild(destinationItem);
              });
    const container = document.getElementById('container');
    container.appendChild(listOfDestinations);
  });
}