import asyncio
import random
import datetime
import sys
import CarRentzRestAPI as CarRentzRestAPI
import argparse
random.seed(datetime.datetime.now().microsecond)

parser = argparse.ArgumentParser(description="Simulate a car rental system with multiple branches and representatives.")

parser.add_argument("--numberOfCustomers", type=int, default=10000000, help="Total number of customers in the simulation (default is 10 million)")
parser.add_argument("--numberOfBranches", type=int, default=2800, help="Total number of branches in the simulation (default is 2800)")
parser.add_argument("--numberOfRepsPerBranch", type=int, default=5, help="Number of representatives per branch (default is 5)")
parser.add_argument("--numberOfDaysToSimulate", type=int, default=10, help="Number of days to simulate (default is 10)")
parser.add_argument("--numberOfBranchesToSimulate", type=int, default=5, help="Number of branches to simulate (not the total number of branches in the inventory) (default is 5). ")
parser.add_argument("--numberOfCars", type=int, default=450000, help="Total number of cars in the inventory. If this is non-zero, the inventory will be initialized with random cars. If this is zero, the inventory will not be initialized. (default is 450000)")
parser.add_argument("--minDaysForRental", type=int, default=1, help="Minimum number of days for a rental (default is 1)")
parser.add_argument("--maxDaysForRental", type=int, default=25, help="Maximum number of days for a rental (default is 25)")

args = parser.parse_args()


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

    if (args.numberOfCars != 0):
        numberOfSUVs           = random.randrange(1, args.numberOfCars // 3) # Number of SUVs in the inventory
        numberOfSedans         = random.randrange(1, args.numberOfCars // 3) # Number of Sedans in the inventory
        numberOfVans           = args.numberOfCars - (numberOfSUVs + numberOfSedans) # Number of Vans in the inventory
        # Initialize the CarRentzRestAPI with the number of cars in the inventory but only if numberOfCars!=0
        await mainBodyThread.run_in_executor(None, CarRentzRestAPI.initializeInventory, 
                                             args.numberOfBranches, numberOfSUVs, numberOfSedans, numberOfVans)

    tasksFromAllDaysAllBranchesAndReps = []

    for day in range (args.numberOfDaysToSimulate):
        tasksFromAllBranchesAndRepsToday = []
        for branch in random.sample(range(args.numberOfBranches), args.numberOfBranchesToSimulate): 
                                                        # branches = random.sample(range(numberOfBranches), numberOfBranchesToSimulate)
                                                        # OR
                                                        # branches = range(numberOfBranchesToSimulate)
            tasksFromAllRepsInBranch = []
            for rep in range (args.numberOfRepsPerBranch):
                repID = (branch * args.numberOfRepsPerBranch) + rep                
                tasksFromRepInBranch = await simulateCarRentzForOneDayOneBranchOneRep (day, branch, repID, 
                                                                                       args.numberOfCustomers, 
                                                                                       args.minDaysForRental,
                                                                                       args.maxDaysForRental)
                tasksFromAllRepsInBranch += tasksFromRepInBranch
            tasksFromAllBranchesAndRepsToday += tasksFromAllRepsInBranch
        tasksFromAllDaysAllBranchesAndReps += tasksFromAllBranchesAndRepsToday
        
    await asyncio.gather (*tasksFromAllDaysAllBranchesAndReps)

    # Don't delete the inventory
    #if (numberOfCars != 0):
    #    await mainBodyThread.run_in_executor(None, CarRentzRestAPI.deleteInventory)

if __name__ == "__main__":
    asyncio.run(main())
