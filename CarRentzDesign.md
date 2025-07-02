# Design of CarRent, a car rental system

## Assumptions/given (each of this can be assumed without loss of generality)
* This is a simulation request, but many parts of the commentary here is only relevant to produtionalizing this simulation--I expected the questions from the panel to go that route, hence my approach.
* It is known that only a specific set of cars is available. For example, 25 Sedans, 35 SUVs and 20 Vans.
* The quantity of these cars in the rental system is fixed and does not change over time (i.e. no new cars are being added during the simulation)
* A renter is only able to rent the car in units of days (this is modeled as minutes in the system)
* While this system expects an explicit return action (REST API), you could also model a system where the customer will not return the car on time based on a random value and model the system penalizing the renter for the lateness (late fees? Other?). These actions are not modeled here.
* I am aware that there is a discrepancy in the data models--some of the table attributes are not "fully" used. I am expecting to improve on this design after I submit this initial design.
* An epoch in the simulation which is equivalent to a day, is modeled as a minute (`sleep 60` in `bash` shell)

## REST Interfaces
* `init` (`GET`): will initialize the three cars with the quanties
* `rental?driverID=a&carType=b&days=c` (`POST`): Rents a car of type  `b` by renter with driver id `a` for `c` days. Returns a `carID` if possible, returns `-1` if not possible.
* `returnCar?carID=b`(`PUT`): Returns a car by `carID` 
* `penalizeForLateReturn` (`rentalID`): (See above, TBD: will need a separate table for charges, which is currently not shown)
* `invoice?rentalID=a` (`GET`): Gets the invoice for a rental.
* `invoice` (`POST`): Sets up the invoice for a rental that was just made
* `showCurrentInventory`: TBD
* `showStats` : TBD

## Data modeling / DB design (assumes MySQL backend)
### Customers table
* customerID: unique not null auto_increment primary key
* driverID: unique not null index COMMENT "Same as license"
* licenseExpiryDate: datetime
* licenseIssueDate: datetime
* phoneNumber: unique not null index
* address: unique not null
* creditCard: unique not null index

### CarInventory (a list of all the cars in the system, along with rental status) table
* carID`: unique not null primary key
* carType: ENUM("Sedan", "SUV", "Van")
* miles: decimal (6,2) not null
* currentlyRented: boolean
* ratePerDay: decimal(6,2) COMMENT "randint (30, 70)"

### Invoices table
* invoiceID: unique not null auto_increment primary key
* rentalID: unique not null index foreign key Rentals(rentalID)
* customerID: unique not null index foreign key Customers (customerID)
* expectedCharges: decimal (8,2)
* actualCharges: decimal (8,2) COMMENT "may differ from above based on late returns etc."

### Rentals table
* rentalID: not null unique auto_increment primary key
* carID: foreign key CarInventory (carID)
* driverID: foreign key Customers (driverID)
* rentDate: datetime
* returnDate: datetime
* expectedCharges: not null decimal(8,2) COMMENT "Initial design does not use this"
* actualCharges: not null decimal(8,2) COMMENT "can this be less than expectedCharges? Initial design does not use this -- see assumptions)"
* liabilityInsurancePerDay: decimal (6,2) not null COMMENT "depends on customer insurance"
* otherInsurancePerDay: decimal (6,2) not null COMMENT "depends on customer insurance"

## Basic idea:

### -> Does not show everything that is possible with this model <-

### Initialization (`/init` -- does not have to be a REST call)

The `CarInventory` table is populated with a stipulated number of Sedans, SUVs and Vans.

The Car rental system `CarRent` now is ready for rentals.

### Rental process (three separate REST calls)

#### Customer Table Transaction (`/manageCustomer`):
* If a Customer `C` is not in the system, they are added to the `Customers` table
* If the Customer does not have a drivers ID, they are denied a rental, `rollback`.
* If the drivers license is not valid, they are denied a rental, `rollback`.
* The Customer's drivers license is used to populate the `Customers` table.
* `commit`

#### Rentals Table Transaction (`/rental` & `/invoice`):
While (no rental has been made to `C`):
* Get Customer's choice of car between three `carType`s.
* Ask for Customer `C`'s rental preference: `carType` (potentially add `miles` as a preference--i.e., low mileage car etc.). `commit`.
* Check if a car of type `carType` is available (`currentlyRented` == true) (optionally, offer `miles` below a number as an option)
* If yes, insert into the `Rentals` table  `VALUES (CarInventory.carID, Customer.driverID, Current_date(), Current_date()+d)` where `d` is the number of days it is being rented
* If no, then inform `C` that such a carType is not available, exit.
* Set `currentlyRented = true` to the specific car rented
* Add an invoice to the table `Invoices` (`POST /invoice`) If error occurs, `rollback`
* If a rental has been made, exit while () loop, `commit` to `Rentals` table
* Else, `rollback` from `Rentals` table, start over at the top.


#### Car Returns for a Rental `R` (which exists in `Rentals` table; `/returns`)
* `Update Rentals set returnDate=current_date() where R.driverID = Rentals.driverID`
* `Update CarInventory set currenlyRented where R.carID=CarInventory.carID`
* `commit`
* (If any error occurs, `rollback`, manage the error appropriately)
* Return row from `CarRentals` for this rental.


## Simulation process
(Pseudocode below sometimes uses python semantics)

````
// Initialize CarInventory table with 
function generateACustomerRequest ()
{
    return {"returning": choice ("true", "false"), // if false, renting
            "rentalDuration: randint (1, 15), // 0 if returning
            "customerID": 0, // INSERTING with 0 on 
                             //auto_increment will generate correct ID
            "driverID": uuid.uuid1(), // Like the python library uuid
            "licenseExpiry": randomDateBetween ("01012035", "01012025")
            "licenseIssue": dateBefore(licenseExpiry, 10, "YEARS")
            "phoneNumber": randomPhoneNumber ("US"), // generate US 
                                                    // phone numbers, 
                                                    // maybe using faker 
                                                    // library in python
            "address": randomAddress("US"), // From faker library
            "creditCard": randInt (1000, 10000000)
    }
}

// Initialize CarInventory table with 
function initializeCarInventory(){
    httpCall ('GET', '/init');
}

while (true)
{
    request=generateCustomerRequest ()
    customerMgmtResponse=httpCall ('PUT', '/manageCustomer', request)
    if (request.returning == true){
        rentalResponse = httpCall ('PUT', '/returns', request)
        invoiceRequest = httpCall ('GET', '/invoice', rentalResponse.rentalID)
        if (rentalResponse.late == true) {
            print ("invoice contains late charges")
        } else {
            print ("Thank you for your rental! Please rent from us again!")
        }
    } else {
        rentalResponse = httpCall ('POST', '/rental', request)
        if (rentalResponse.carID != -1) {
            print ("Enjoy this {carType} rental from us!")
        } else {
            print ("Sorry--but no rental available for your {carType} now")
        }
    }
}
````





## Discussion points

### Many of these discussion points are only relevant if we are moving this design into production for an actual company, but I thought I should mention these here.

### Assumptions
* Question the assumptions. How flexible do they make the architecture? What kind of tech debt are we likely to incur with this architecture as it stands?

### Distributed computing
* REST--should everything be REST (synchronous semantics) or could some of them be async (message queue semantics). Could we get away with returns being via message queue, for example? Or penalizing late returns? That could also be MQ-based?
* HTTP return codes--what is appropriate where

### Data layer
* Choice of RDBMS vs. NoSQL (RDBMS is better, NoSQL for analytics later)
* Choice of MySQL vs. say, PostgresQL
* Adding telemetry to everything so we can observe if our model is doing well
* Connection pooling: max size, min size
* Data modeling questions: indexing and key mgmt.
* Scaling the OLTP DB (i.e., the MySQL table). vertical scaling is bad, but how to plan this. How to manage this. What options exist--Azure SQL Hyperscale?
* Database availability issues
* Redis or memcached over the CarInventory and Customer table?
* Data analytics for the business people (should be OLAP)

### Infrastructure, release engg., HA and georedundancy

* IaaS and IaC and Georedundancy solutions--through AzureDNS
* Zero trust for infrastructure and application service-principals
* Deployment strategy: blue/green; canary or rolling
* Kubernetes cluster design for prod, dev and staging: language and pod sizing/design
* What choice of CI/CD?
* Stateless design for the pods --how to design the pods s.t if they crash, they only affect the transaction(s) they were working on
* Secrets mgmt across the board: for MySQL access, kubernetes service principals, registry access, CI/CD

### Observability, operational concenrs
* PagerDuty, governance of process


### Compliance, governance and security
* Compliance with industry standards--e.g., PCI/DSS, GDPR (if renting in Europe or for Europeans)
* Governance of rental processes
