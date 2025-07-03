import requests
import json
import asyncio
import random

inventoryUrl = "http://localhost:8080/inventory"
rentalsUrl = "http://localhost:8080/rentals"
availableCarsUrl = "http://localhost:8080/rentals/availableByCarType/"

def populateInventoryWithOneTypeOfCar (carType, number):
    url = inventoryUrl

    for i in range(number):
        color = random.choice (['Parrot Red', 'Blue Agate', 'Black', 'White', 'Burgundy', 'Steelblue', 'Green'])
        make = random.choice (['Toyota', 'Honda', 'Ford', 'Chevrolet', 'Nissan'])
        model = random.choice (['RAV4', 'CR-V', 'Escape', 'Equinox', 'Rogue'])
        milesDriven = random.randint(0, 10000)
        pricePerDay = random.randint(25, 50)
        year = random.randint(2022, 2025)
        data = {
            "available": True,
            "carType": carType,
            "color": color,
            "make": make,
            "model": model,
            "milesDriven": milesDriven,
            "pricePerDay": pricePerDay,
            "year": year
        }

        response = requests.post(url, json=data)
        if response is not None:
            print("Inventory initialized successfully:", response)
        else:
            print("Failed to initialize inventory")

def initializeInventory (numberOfSUVs, numberOfSedans, numberOfVans):
    
    populateInventoryWithOneTypeOfCar("SUV", numberOfSUVs)
    populateInventoryWithOneTypeOfCar("Sedan", numberOfSedans)
    populateInventoryWithOneTypeOfCar("Van", numberOfVans)

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

    
def deleteInventory ():
    url = inventoryUrl
    response = requests.delete(url)
    if response.status_code == 200:
        print("Inventory deleted successfully")
        return response
    else:
        print(f"Failed to delete inventory: {response.status_code} - {response.text}")
    return None
