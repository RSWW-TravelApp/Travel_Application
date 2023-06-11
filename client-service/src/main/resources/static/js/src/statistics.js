async function createNotificationListener() {
    createEventListener(
        function(event) {
            if (event.type !== 'multicast' || event.properties['statistics'] === undefined) {
                return;
            }
            const statisticsType = event.properties['statistics'];
            const helperMap = {"destinations": "/statistics/flights/destinations",
                "hotels": "/statistics/offers/hotels", "rooms": "/statistics/offers/rooms"};
            const statisticsList = document.getElementById(helperMap[statisticsType]);
            const firstEntry = createElement('li', {});
            firstEntry.textContent = `TOP ${statisticsType}`
            statisticsList.innerHTML = '';
            appendChildren(statisticsList, [firstEntry]);
            let place = 1;
            event.properties['top'].forEach( item => {
                const listEntry = getListEntry(item['first'], place);
                appendChildren(statisticsList, [listEntry]);
                place += 1;
            });
            console.log(`[${event.type}] ${event.message}`);},
        function(error) {
            console.log(error);
        })
}

function getListEntry(item, place) {
    const listEntry = createElement('li', {"style": "color: transparent"});
    const base_length = 15;
    const additionalLength = item.length > base_length ? item.length - base_length : 0;
    const postCard = squareFrame(0, 0, (base_length + additionalLength) * 10 , 30, 2, 2, `${place}. ${item}`,
        {"class": "svg-button", "style": "fill: lightgray"});
    appendChildren(listEntry, [postCard])
    return listEntry;
}

async function fetchStatistics(statisticEndpoint) {
    await fetch(getEffectiveGatewayUri() + statisticEndpoint, {method: "GET"})
        .then(response => checkResponse(response))
        .then(data => {
            let place = 1;
            const statisticsList = document.getElementById(statisticEndpoint);
            data.forEach(item => {
                const listEntry = getListEntry(item, place);
                appendChildren(statisticsList, [listEntry]);
                place += 1;
            });
        })
}
