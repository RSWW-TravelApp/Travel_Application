import requests
import json
import random


def initFlights():
    url = "http://localhost:8082/flights"
    master_url = "http://localhost:8086/flights"
    for i in range(200):
        dstCountry = random.choice(
            ["Poland", "Spain", "Germany", "USA", "France", "UK", "Belgium", "Netherlands", "Italy"])
        dstCity = random.choice(["Warsaw", "Madrid", "Berlin", "Washington", "London", "Brussels", "Amsterdam", "Roma"])
        srcCountry = random.choice(
            ["Poland", "Spain", "Germany", "USA", "France", "UK", "Belgium", "Netherlands", "Italy"])
        srcCity = random.choice(["Warsaw", "Madrid", "Berlin", "Washington", "London", "Brussels", "Amsterdam", "Roma"])
        payload = json.dumps({
            "departure_country": dstCountry,
            "departure_city": dstCity,
            "arrival_country": srcCountry,
            "arrival_city": srcCity,
            "available_seats": random.randint(1, 20),
            "date": "2023-05-08"
        })
        headers = {
            'Content-Type': 'application/json'
        }

        response1 = requests.request("POST", url, headers=headers, data=payload)
        response2 = requests.request("POST", master_url, headers=headers, data=payload)

        print(response1.text)
        print(response2.text)
        print("-------------------------------------------------------------------------------------------------------")


def initOffers():
    url = "http://localhost:8081/offers"
    master_url = "http://localhost:8086/offers"
    for i in range(200):
        Country = random.choice(
            ["Poland", "Spain", "Germany", "USA", "France", "UK", "Belgium", "Netherlands", "Italy", "Greece"])
        City = random.choice(["Warsaw", "Madrid", "Berlin", "Washington", "London", "Brussels", "Amsterdam", "Roma"])
        HotelName = random.choice(["Sheraton", "Kisawa Residence", "Sommerro House", "Six Senses Israel",
                                   "Royal Mansour", "Hotel Château du Grand-Lucé", "Capella Ubud", "The Alpina Gstaad",
                                   "Grootbos Private Nature Reserve", "Amangiri", "Nihi Sumba", "Amanyara",
                                   "InterContinental Geneva"])
        payload = json.dumps({
            "hotel_name": HotelName,
            "image": "img1",
            "country": Country,
            "city": City,
            "stars": random.randint(1, 5),
            "start_date": "2023-05-01",
            "end_date": "2023-05-07",
            "room_type": random.choice(["smallRoom", "mediumRoom", "apartament"]),
            "max_adults": random.randint(1, 5),
            "max_children_to_3": random.randint(0, 5),
            "max_children_to_10": random.randint(0, 5),
            "max_children_to_18": random.randint(0, 5),
            "meals": random.choice(["onlyBreakfast", "breakfastAndDinner", "allInclusive"]),
            "price": random.randint(100, 400),
            "available": True
        })
        headers = {
            'Content-Type': 'application/json'
        }

        response1 = requests.request("POST", url, headers=headers, data=payload)
        response2 = requests.request("POST", master_url, headers=headers, data=payload)

        print(response1.text)
        print(response2.text)
        print("-------------------------------------------------------------------------------------------------------")


initFlights()
initOffers()
