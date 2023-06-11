function getListEntry(item) {
    const id = item.reservationId;
    let txt = getChangesString(item)
    const listEntry = createElement('li', {"style": "color: transparent"});
    const postCard = squareFrame(0, 0, 400, 25 * (Object.keys(item).length + 1), 2, 2, txt,
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

async function fetchReservations(el) {
    const userId = sessionStorage.getItem("user");
    if (!userId) {
        alert("Please log in to check reservations")
        return;
    }
    await fetch(getEffectiveGatewayUri() + '/reservations/' + userId, {method: "GET"})
        .then(response => checkResponse(response))
        .then(data => {
            const listOfReservations = document.getElementById('reservations');
            data.forEach(item => {
                const listEntry = getListEntry(item);
                appendChildren(listOfReservations, [listEntry])
            });
        })
}