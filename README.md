Many small businesses do struggle every day to manage discounts efficiently. Some requireing manual prices adjustments and lack automatic discount activation based on relvent factors like date and stocks. Staff must constantly check inventory and calendar dates to apply appropriate promotions, leading to errors in pricing, missed opportunities on trends and more.
Our solution

<<<<<<< HEAD
A command-line discount controller built using utilizing Java and PostgreSQL Administrators can configure complex discount strategies through its simple CLI commands, setting rules like "20% off when stock > 100 units" or a complex layerd pricing rules like "10% off for 2-5 units, 15% off for 6+ units from Oct 9 to Oct 12 only". PostgreSQL ensures data integrity, supports complex queries for discount validation, and maintains comprehensive audit trails to track discount history and performance. With it an integrated QR maker to print a current item price with its discounts.
Features

    Viewing and managing stocked items on a Database
    Creating and assigning complex layerd rules for discounts and assigning them to items
    Printing a QR code of an item price with its discount's information
=======
## Description
Many small businesses do struggle every day to manage discounts efficiently. 
Some requireing manual prices adjustments and lack automatic discount activation based on relvent factors like date and stocks. 
Staff must constantly check inventory and calendar dates to apply appropriate promotions, leading to errors in pricing, missed opportunities 
on trends and more.
>>>>>>> fd96099128f99430f32af22b11487a49d810d00f

### Our solution
A command-line discount controller built using utilizing Java and PostgreSQL 
Administrators can configure complex discount strategies through its simple CLI commands, setting rules like "20% off when stock > 100 units" or a complex layerd 
pricing rules like "10% off for 2-5 units, 15% off for 6+ units from Oct 9 to Oct 12 only". 
PostgreSQL ensures data integrity, supports complex queries for discount validation, 
and maintains comprehensive audit trails to track discount history and performance.
With it an integrated QR maker to print a current item price with its discounts.


<<<<<<< HEAD

## Prerequisites
=======
## Features
- Viewing and managing stocked items on a Database
- Creating and assigning complex layerd rules for discounts and assigning them to items
- Printing a QR code of an item price with its discount's information
>>>>>>> fd96099128f99430f32af22b11487a49d810d00f

- **Java 17** or higher
- **PostgreSQL 12** or higher
- **Maven 3.6** or higher

## Database Setup

<<<<<<< HEAD
### PostgreSQL Installation and Configuration

### Automated Setup (Recommended)
```bash
# Make the setup script executable and run it
chmod +x setup-db.sh
./setup-db.sh
=======
while in 'DynamicDiscountManager\src\main\java' run following commands on your terminal:
```shell
javac sa/edu/kau/fcit/cpit252/project/App.java
java sa.edu.kau.fcit.cpit252.project.App
>>>>>>> fd96099128f99430f32af22b11487a49d810d00f
```

### Manual Setup (Alternative)
If the automated setup doesn't work, follow the manual steps:

1. **Install PostgreSQL**:
   ```bash
   sudo apt update
   sudo apt install postgresql postgresql-contrib
   ```

2. **Start PostgreSQL service**:
   ```bash
   sudo systemctl start postgresql
   sudo systemctl enable postgresql
   ```

3. **Run the database setup**:
   ```bash
   sudo -u postgres psql -f setup-db.sh
   ```

4. **Test the connection**:
   ```bash
   psql -h localhost -U admin -d DynamicDiscountDB
   ```

## Installation & Usage

### Clone the Repository
```bash
git clone https://github.com/CPIT252-IT1-Fall25/DynamicDiscountManager.git
cd DynamicDiscountManager
```

### Build and Run
```bash
# Clean, compile, and run the application
mvn clean compile exec:java

# Or run tests
mvn test

# Create JAR file
mvn package
```




## License

MIT License - see LICENSE file for details

## Authors

- CPIT252 Project Team
- King Abdulaziz University, Faculty of Computing and Information Technology

---

**Course**: CPIT252 - Object-Oriented Programming  
**Institution**: King Abdulaziz University  
**Academic Year**: 2025
