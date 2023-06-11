function getListEntry(item) {
    const id = item.flightId !== undefined ? item.flightId : item.offerId;
    let txt = item.flightId !== undefined ?
        `${item.timestamp}\nflight with id ${id} changed:\n${getChangesString(item.changes)}` :
        `${item.timestamp}\noffer with id ${id} changed:\n${getChangesString(item.changes)}`
    const listEntry = createElement('li', {"style": "color: transparent"});
    const postCard = squareFrame(0, 0, 400, 25 * (Object.keys(item.changes).length + 2), 2, 2, txt,
        {"id": `${id}`, "class": "svg-button", "style": "fill: lightgray"});
    appendChildren(listEntry, [postCard])
    return listEntry;
}

function getChangesString(changesObject) {
    let txt = "";
    for (let key in changesObject) {
        txt += `${key}: ${changesObject[key]}\n`;
    }
    return txt;
}

async function createNotificationListener() {
    createEventListener(
        function(event) {
            if (!event.properties.groups.includes("all") || event.type !== 'multicast' || event.properties.changes === undefined) {
                return;
            }
            const recentChangesList = document.getElementById('recentChanges');
            if (recentChangesList.children.length >= 10) {
                recentChangesList.lastChild.remove();
            }

            const listEntry = getListEntry(event.properties);
            recentChangesList.insertBefore(listEntry, recentChangesList.firstChild);
            console.log(`[${event.type}] ${event.message}`);},
        function(error) {
            console.log(error);
        })
}

async function fetchRecentChanges() {
    await fetch(getEffectiveGatewayUri() + '/recentChanges', {method: "GET"})
        .then(response => checkResponse(response))
        .then(data => {
            const recentChangesList = document.getElementById('recentChanges');
            data.forEach(item => {
                const listEntry = getListEntry(item);
                appendChildren(recentChangesList, [listEntry]);
            });
        })
}