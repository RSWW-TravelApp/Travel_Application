function buildFlightInfo(flightItem) {
    const flightDataFromContainer = document.getElementById('flightDataFrom');
    appendChildren(flightDataFromContainer, [
        createLabel("From", {'for': 'departureCountryInfo'}, 1),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.departure_country}`, {'class': 'svg-button', 'id': 'departureCountryInfo'}),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.departure_city}`, {'class': 'svg-button', 'id': 'departureCityInfo'})
    ]);
    const flightDataToContainer = document.getElementById('flightDataTo');
    appendChildren(flightDataToContainer, [
        createLabel("To", {'for': 'arrivalCountryInfo'}, 1),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.arrival_country}`, {'class': 'svg-button', 'id': 'arrivalCountryInfo'}),
        squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.arrival_city}`, {'class': 'svg-button', 'id': 'arrivalCityInfo'})
    ]);
    const flightDataDateContainer = document.getElementById('flightDataDate');
    appendChildren(flightDataDateContainer, [
        labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${flightItem.date}`, {'class': 'svg-button', 'id': 'dateInfo'}, "Flight date")
    ]);
}

async function fetchOffers(el) {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch('http://localhost:8080/offers' + window.location.search, {method: "GET"})
  .then(response => response.json())
  .then(data => {
    const listOfOffers = createElement('div');
    data.forEach(item => {
                const offerItem = createElement('div', {'id': item.offerId});
                const form = createElement('form', {'action': `/offers/${item.offerId}`});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': flightId,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const postCard = squareFrame(0, 0, 250, 100, 2, 2, txt=`${item.country}\n${item.city}\n${item.hotel_name}`, {'class': 'svg-button'});

                appendChildren(button, [postCard]);
                appendChildren(form, [button]);
                appendChildren(offerItem, [form]);
                appendChildren(listOfOffers, [offerItem]);
              });
    const offersCounter = document.getElementById('offersCounter');
    offersCounter.textContent = `${data.length} offers found`;
    const container = document.getElementById('container');
    appendChildren(container, [listOfOffers]);
  });
}

async function fetchChosenFlight(el) {
    const flightId = getSearchRequestParams(['flightId'])['flightId'];
    await fetch('http://localhost:8080/flights/' + flightId, {method: "GET"})
        .then(response => checkResponse(response))
        .then(flightItem => buildFlightInfo(flightItem))
        .catch(error => buildFlightInfo(defaultFlightItem));
}