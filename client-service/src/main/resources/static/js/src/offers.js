async function fetchOffers(el) {
  const flightId = getSearchRequestParams(['flightId'])['flightId'];
  await fetch('http://localhost:8080/offers' + window.location.search, {method: "GET"})
  .then(response => response.json())
  .then(data => {
    const listOfOffers = createElement('div');
    data.forEach(item => {
                const offerItem = createElement('div', {'id': item.id});
                const form = createElement('form', {'action': `/offers/${item.id}`});
                const button = createElement('button', {
                    'name': 'flightId',
                    'value': flightId,
                    'style': "color: transparent; background-color: transparent; border-color: transparent; cursor: default;"
                });
                const postCard = squareFrame(0, 0, 250, 100, 2, 2, txt=`${item.country}\n${item.city}\n${item.name}`, {'class': 'svg-button'});

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
    .then(response => response.json())
    .then(flightItem => {
        const flightDataFromContainer = document.getElementById('flightDataFrom');
        appendChildren(flightDataFromContainer, [
            createLabel("From", {'for': 'srcCountryInfo'}, 1),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.srcCountry}`, {'class': 'svg-button', 'id': 'srcCountryInfo'}),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.srcCity}`, {'class': 'svg-button', 'id': 'srcCityInfo'})
        ]);
        const flightDataToContainer = document.getElementById('flightDataTo');
        appendChildren(flightDataToContainer, [
            createLabel("To", {'for': 'dstCountryInfo'}, 1),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.dstCountry}`, {'class': 'svg-button', 'id': 'dstCountryInfo'}),
            squareFrame(0, 0, 100, 25, 2, 2, txt=`${flightItem.dstCity}`, {'class': 'svg-button', 'id': 'dstCityInfo'})
        ]);
        const flightDataStartDateContainer = document.getElementById('flightDataStartDate');
        appendChildren(flightDataStartDateContainer, [
            labeledSquareProperty(0, 0, 100, 25, 2, 2, txt=`${flightItem.startDate}`, {'class': 'svg-button', 'id': 'startDateInfo'}, "Flight date")
        ]);
    });
}