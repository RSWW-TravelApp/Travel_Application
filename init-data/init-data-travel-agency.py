from datetime import datetime

import requests
import json
import csv

def csv_to_json(csv_path, json_path, host, port, collection):
    url = f"http://{host}:{port}/{collection}"
    jsonArray = []

    with open(csv_path, encoding='utf-8') as csv_file:
        csvReader = csv.DictReader(csv_file)

        for row in csvReader:
            if collection == 'flights':
                date_str = row['date']
                date_obj = datetime.strptime(date_str, '%d/%m/%Y').date()

                row['date'] = date_obj.strftime('%Y-%m-%d')

            elif collection == 'offers':
                date_str_start = row['start_date']
                date_str_end = row['end_date']
                date_obj_start = datetime.strptime(date_str_start, '%d/%m/%Y').date()
                date_obj_send = datetime.strptime(date_str_end, '%d/%m/%Y').date()

                row['start_date'] = date_obj_start.strftime('%Y-%m-%d')
                row['end_date'] = date_obj_send.strftime('%Y-%m-%d')

            jsonArray.append(row)
            payload = json.dumps(row)

            headers = {
                'Content-Type': 'application/json'
            }
            response = requests.request("POST", url, headers=headers, data=payload)
            print(response.text)

    with open(json_path, 'w', encoding='utf-8') as json_file:
        jsonString = json.dumps(jsonArray, indent=4, ensure_ascii=False)
        json_file.write(jsonString)


offers_csv_path = r'hoteleOfertyBaza.csv'
offers_json_path = r'offers.json'
# csv_to_json(offers_csv_path, offers_json_path, "localhost", 8085, "offers")

flights_csv_path = r'lotyOfertyBaza.csv'
flights_json_path = r'flights.json'
# csv_to_json(flights_csv_path, flights_json_path, "localhost", 8085, "flights") # 5245 cluster 8085 local

