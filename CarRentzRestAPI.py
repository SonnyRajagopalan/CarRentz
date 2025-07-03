import requests
import json
import asyncio

rentalsUrl = "http://localhost:8080/rentals"
availableCarsUrl = "http://localhost:8080/rentals/availableByCarType/"

def makeRequest (url, method, data=None):
    headers = { "Content-Type": "application/json" }
    if data is not None:
        headers["Content-Length"] = str(len(data))
    response = requests.request(method, url, headers=headers, json=data)

    if response.status_code == 200:
        return response.json()
    else:
        print(f"Error: {response.status_code} - {response.text}")
        return None

def findAnAvailableCar (carType):
    url = availableCarsUrl + carType
    response = makeRequest (url, "GET")
    if response is not None and len(response) > 0:
        return response  # Return the first available car
    else:
        print("No cars available of type:", carType)
        return None

def rentACar (carType, carID, duration, customerID):
    url = rentalsUrl
    data = {
        "carType": carType,
        "carID": carID,
        "duration": duration,
        "customerID": customerID
    }
    response = requests.post (url, json=data)
    print (response.status_code)
    if response.status_code == 200:
        print("Rental created successfully:", response)
        return response.json()
    else:
        print(f"Failed to create rental {data}")

    

def returnARental (rentalID):
    url = rentalsUrl + "/" + str(rentalID)
    print ("Returning rental with ID:", rentalID, "using URL:", url)
    response = requests.delete(url)
    if response is not None:
        print("Rental returned successfully:", response)
        return response
    else:
        print("Failed to return rental")
    return None

    

