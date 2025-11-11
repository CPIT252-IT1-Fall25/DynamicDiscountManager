[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Fv869B0L)
# DynamicDiscountManager

## Description
Many small businesses do struggle every day to manage discounts efficiently. 
Some requireing manual prices adjustments and lack automatic discount activation based on relvent factors like date and stocks. 
Staff must constantly check inventory and calendar dates to apply appropriate promotions, leading to errors in pricing, missed opportunities 
on trends and more.

### Our solution
A command-line discount controller built using utilizing Java and PostgreSQL 
Administrators can configure complex discount strategies through its simple CLI commands, setting rules like "20% off when stock > 100 units" or a complex layerd 
pricing rules like "10% off for 2-5 units, 15% off for 6+ units from Oct 9 to Oct 12 only". 
PostgreSQL ensures data integrity, supports complex queries for discount validation, 
and maintains comprehensive audit trails to track discount history and performance.
With it an integrated QR maker to print a current item price with its discounts.


## Features
- Viewing and managing stocked items on a Database
- Creating and assigning complex layerd rules for discounts and assigning them to items
- Printing a QR code of an item price with its discount's information

## Usage

To build and run the app, use:

while in 'DynamicDiscountManager\src\main\java' run following commands on your terminal:
```shell
javac sa/edu/kau/fcit/cpit252/project/App.java
java sa.edu.kau.fcit.cpit252.project.App
```

## Screenshots


## License

Pick a project license
