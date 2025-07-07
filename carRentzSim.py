import asyncio
import random
import sys
import CarRentzRestAPI as CarRentzRestAPI

async def rentalReturn (rID, dur):
    await asyncio.sleep(dur)
    response = CarRentzRestAPI.returnARental(rID)    
    # print (f"Rental {rID} returned after {dur} days. Response: {response}")

async def seeNCustomers (day, branch, rep, customerServedNumber, numberOfCustomers, minDaysForRental, maxDaysForRental):
    tasks = []
    for c in range (customerServedNumber):

        thisAsyncIterationOfLoop = asyncio.get_running_loop()

        customerID = random.randint(1, numberOfCustomers)
        carType = random.choice(['Sedan', 'SUV', 'Van'])
        rentalDuration = random.randint(minDaysForRental, maxDaysForRental)

        # Are cars available of the requested type?
        availableCar = await thisAsyncIterationOfLoop.run_in_executor(None, CarRentzRestAPI.findAnAvailableCar, 
                                                                      day, branch, carType, rentalDuration, customerID, rep, 0.0104)
        if availableCar is None:
            # print(f"No available cars of type {carType} for customer {customerID}.")
            continue
        # Excellent--car available--now rent it
        
        response = await thisAsyncIterationOfLoop.run_in_executor(None, CarRentzRestAPI.rentACar, day, branch, rep, availableCar, rentalDuration, customerID, 0.007)
        if response is None:
            # print(f"Failed to rent car of type {carType} for customer {customerID}.")
            continue
        # Rental successful, now the customer has the car for the specified duration
        task = asyncio.create_task(rentalReturn(response['rentalID'], rentalDuration))
        tasks.append(task)
        # print(f"{day} Customer {customerID} is renting a/an {carType} for a duration of {rentalDuration} days")

    return tasks

async def simulateCarRentzForOneDayOneBranchOneRep (day, branch, rep, numberOfCustomers, minDaysForRental, maxDaysForRental):

    allRentalEvents = []

    # All of the following, before the sleep for 2/3 of a second should only take 1/3 second.
    # Morning
    rentalEvents = await seeNCustomers (day, branch, rep, 5, numberOfCustomers, minDaysForRental, maxDaysForRental)
    allRentalEvents += rentalEvents
    # Mid-morning
    rentalEvents = await seeNCustomers (day, branch, rep, 4, numberOfCustomers, minDaysForRental, maxDaysForRental)
    allRentalEvents += rentalEvents
    # Afternoon
    rentalEvents = await seeNCustomers (day, branch, rep, 6, numberOfCustomers, minDaysForRental, maxDaysForRental)
    allRentalEvents += rentalEvents
    # Late afternoon
    rentalEvents = await seeNCustomers (day, branch, rep, 4, numberOfCustomers, minDaysForRental, maxDaysForRental)
    allRentalEvents += rentalEvents

    await asyncio.sleep(0.67) # Only 8 hours of work a day

    return allRentalEvents

async def main ():
    mainBodyThread = asyncio.get_running_loop()

    numberOfCustomers      = int (sys.argv[1])
    numberOfBranches       = int (sys.argv[2])
    numberOfRepsPerBranch  = int (sys.argv[3])
    numberOfDaysToSimulate = int (sys.argv[4])
    numberOfCars           = int (sys.argv[5]) # Total number of cars in the inventory
    minDaysForRental       = int (sys.argv[6])
    maxDaysForRental       = int (sys.argv[7])
    if (numberOfCars != 0):
        numberOfSUVs           = random.randrange(1, numberOfCars // 3) # Number of SUVs in the inventory
        numberOfSedans         = random.randrange(1, numberOfCars // 3) # Number of Sedans in the inventory
        numberOfVans           = numberOfCars - (numberOfSUVs + numberOfSedans) # Number of Vans in the inventory
    
    # Initialize the CarRentzRestAPI with the number of cars in the inventory but only if numberOfCars!=0
    if (numberOfCars != 0): # This simulation will create the Inventory. If numberOfCars is 0, then the Inventory will not be created by this simulation.
        await mainBodyThread.run_in_executor(None, CarRentzRestAPI.initializeInventory, numberOfBranches, numberOfSUVs, numberOfSedans, numberOfVans)

    tasksFromAllDaysAllBranchesAndReps = []

    for day in range (numberOfDaysToSimulate):
        tasksFromAllBranchesAndRepsToday = []
        for branch in range (numberOfBranches):
            tasksFromAllRepsInBranch = []
            for rep in range (numberOfRepsPerBranch):
                repID = (branch * numberOfRepsPerBranch) + rep
                # tasksFromRepInBranch = await simulateCarRentz (numberOfDaysToSimulate, numberOfCustomers, minDaysForRental, maxDaysForRental) #####
                tasksFromRepInBranch = await simulateCarRentzForOneDayOneBranchOneRep (day, branch+1, repID, numberOfCustomers, minDaysForRental, maxDaysForRental)
                tasksFromAllRepsInBranch += tasksFromRepInBranch
            tasksFromAllBranchesAndRepsToday += tasksFromAllRepsInBranch
        tasksFromAllDaysAllBranchesAndReps += tasksFromAllBranchesAndRepsToday
        
    await asyncio.gather (*tasksFromAllDaysAllBranchesAndReps)

    # Don't delete the inventory
    #if (numberOfCars != 0):
    #    await mainBodyThread.run_in_executor(None, CarRentzRestAPI.deleteInventory)

if __name__ == "__main__":
    asyncio.run(main())
