async function fetchDestinations(el) {
  await fetch('http://localhost:8080/flights' + window.location.search, {method: "GET"})
  .then(response => response.json())
  .then(data => {
    const listOfFlights = createElement('div');
    data.forEach(item => {
                const flightItem = createElement('div', {'id': item.id})
                const form = createElement('form', {'action': '/offers'});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': item.id,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const dstCountryInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'dstCountry',
                    'name': 'dstCountry',
                    'value': item.dstCountry
                });
                const srcCountryInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'startDate',
                    'name': 'startDate',
                    'value': item.startDate
                });
                const postCard = squareFrame(0, 0, 250, 100, 2, 2, txt=`From: ${item.srcCountry}, ${item.srcCity}\nTo: ${item.dstCountry}, ${item.dstCity}\nPrice: ${item.price}`, {'class': 'svg-button'});
                appendChildren(button, [postCard]);
                appendChildren(form, [button, dstCountryInput, srcCountryInput]);
                appendChildren(flightItem, [form]);
                appendChildren(listOfFlights, [flightItem]);
              });
    const flightsCounter = document.getElementById('flightsCounter');
    flightsCounter.textContent = `${data.length} flights found`;
    const container = document.getElementById('container');
    appendChildren(container, [listOfFlights]);
  });
}