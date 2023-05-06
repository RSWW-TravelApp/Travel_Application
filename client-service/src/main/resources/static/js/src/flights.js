async function fetchDestinations(el) {
  var queryParams = window.location.search;
  const numberOfPeople = getSearchRequestParams(['available_seats'])['available_seats'];
  if (!numberOfPeople) {
    queryParams += (queryParams == "" ? "?" : "&") + `available_seats=${document.getElementById('available_seats').value}`
  }
  await fetch(getEffectiveGatewayURI() + '/flights' + queryParams, {method: "GET"})
  .then(response => checkResponse(response))
  .then(data => {
    const listOfFlights = createElement('div');
    data.forEach(item => {
                const flightItem = createElement('div', {'id': item.flightId})
                const form = createElement('form', {'action': '/offers'});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': item.flightId,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const arrivalCountryInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'arrival_country',
                    'name': 'arrival_country',
                    'value': item.arrival_country
                });
                const departureCountryInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'date',
                    'name': 'date',
                    'value': item.date
                });
                const numberOfPeopleInput = createElement('input', {
                    'type': 'hidden',
                    'id': 'max_adults',
                    'name': 'max_adults',
                    'value': document.getElementById('available_seats').value
                });
                const postCard = squareFrame(0, 0, 250, 100, 2, 2, txt=`From: ${item.departure_country}, ${item.departure_city}\nTo: ${item.arrival_country}, ${item.arrival_city}\nAvailable seats: ${item.available_seats}`, {'class': 'svg-button'});
                appendChildren(button, [postCard]);
                appendChildren(form, [button, arrivalCountryInput, departureCountryInput, numberOfPeopleInput]);
                appendChildren(flightItem, [form]);
                appendChildren(listOfFlights, [flightItem]);
              });
    const flightsCounter = document.getElementById('flightsCounter');
    flightsCounter.textContent = `${data.length} flights found`;
    const container = document.getElementById('container');
    appendChildren(container, [listOfFlights]);
  })
  .catch(error => {
        const flightsCounter = document.getElementById('flightsCounter');
        flightsCounter.textContent = "0 flights found";
  });
}