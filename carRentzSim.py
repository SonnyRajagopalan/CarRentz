# Initialize
import asyncio
import random
import sys
import CarRentzRestAPI as CarRentzRestAPI


async def rentalReturn (rID, dur):
    await asyncio.sleep(dur)
    response = CarRentzRestAPI.returnARental(rID)    
    print (f"Rental {rID} returned after {dur} days. Response: {response}")

async def seeNCustomers (day, n, numberOfCustomers, minDaysForRental, maxDaysForRental):
    tasks = []
    for c in range (n):

        thisAsyncIterationOfLoop = asyncio.get_running_loop()

        customerID = random.randint(1, numberOfCustomers)
        carType = random.choice(['Sedan', 'SUV', 'Van'])
        rentalDuration = random.randint(minDaysForRental, maxDaysForRental)

        # Are cars available of the requested type?
        availableCar = await thisAsyncIterationOfLoop.run_in_executor(None, CarRentzRestAPI.findAnAvailableCar, carType, 0.0104)
        if availableCar is None:
            print(f"No available cars of type {carType} for customer {customerID}.")
            continue
        # Excellent--car available--now rent it
        carID = availableCar['carID']
        response = await thisAsyncIterationOfLoop.run_in_executor(None, CarRentzRestAPI.rentACar, day, carType, carID, rentalDuration, customerID, 0.007)
        if response is None:
            print(f"Failed to rent car of type {carType} for customer {customerID}.")
            continue
        # Rental successful, now the customer has the car for the specified duration
        task = asyncio.create_task(rentalReturn(response['rentalID'], rentalDuration))
        tasks.append(task)
        print(f"{day} Customer {customerID} is renting a/an {carType} for a duration of {rentalDuration} days")

    return tasks

async def simulateCarRentz (numberOfDaysToSimulate, numberOfCustomers, minDaysForRental, maxDaysForRental):

    allRentalEvents = []
    for day in range(numberOfDaysToSimulate):
        # All of the following, before the sleep for 2/3 of a second should only take 1/3 second.
        # Morning
        rentalEvents = await seeNCustomers (day, 5, numberOfCustomers, minDaysForRental, maxDaysForRental)
        allRentalEvents += rentalEvents
        # Mid-morning
        rentalEvents = await seeNCustomers (day, 4, numberOfCustomers, minDaysForRental, maxDaysForRental)
        allRentalEvents += rentalEvents
        # Afternoon
        rentalEvents = await seeNCustomers (day, 6, numberOfCustomers, minDaysForRental, maxDaysForRental)
        allRentalEvents += rentalEvents
        # Late afternoon
        rentalEvents = await seeNCustomers (day, 4, numberOfCustomers, minDaysForRental, maxDaysForRental)
        allRentalEvents += rentalEvents

        await asyncio.sleep(0.67) # Only 8 hours of work a day

    return allRentalEvents

async def main ():
    mainBodyThread = asyncio.get_running_loop()

    numberOfCustomers      = int (sys.argv[1])
    numberOfDaysToSimulate = int (sys.argv[2])
    numberOfCars           = int (sys.argv[3]) # Total number of cars in the inventory
    numberOfSUVs           = random.randrange(1, numberOfCars // 3) # Number of SUVs in the inventory
    numberOfSedans         = random.randrange(1, numberOfCars // 3) # Number of Sedans in the inventory
    numberOfVans           = numberOfCars - (numberOfSUVs + numberOfSedans) # Number of Vans in the inventory
    minDaysForRental    = 1
    maxDaysForRental    = 25
    # Initialize the CarRentzRestAPI with the number of cars in the inventory
    await mainBodyThread.run_in_executor(None, CarRentzRestAPI.initializeInventory, numberOfSUVs, numberOfSedans, numberOfVans)

    allTasksFromSimulation = await simulateCarRentz (numberOfDaysToSimulate, numberOfCustomers, minDaysForRental, maxDaysForRental)
    await asyncio.gather (*allTasksFromSimulation)
    await mainBodyThread.run_in_executor(None, CarRentzRestAPI.deleteInventory)

if __name__ == "__main__":
    asyncio.run(main())