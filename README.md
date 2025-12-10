# Dynamic Discount Manager

Many small businesses struggle every day to manage discounts efficiently. Some require manual price adjustments and lack automatic discount activation based on relevant factors like date and stocks. Staff must constantly check inventory and calendar dates to apply appropriate promotions, leading to errors in pricing, missed opportunities on trends and more.

## Our Solution

A command-line discount controller built using Java and PostgreSQL. Administrators can configure complex discount strategies through simple CLI commands, setting rules like "20% off when stock > 100 units" or complex layered pricing rules like "10% off for 2-5 units, 15% off for 6+ units from Oct 9 to Oct 12 only". PostgreSQL ensures data integrity, supports complex queries for discount validation, and maintains comprehensive audit trails to track discount history and performance. Includes an integrated QR maker to print current item prices with discount information.

## Features

- 📦 **349 Pre-Loaded Products** - Automatically imported and ready to use
- 🔍 **Product Browsing** - View all items with barcode ID, name, base price, current discount, and stock
- 💰 **Smart Discount Suggestions** - Automatic recommendations based on:
  - Expiry dates (CRITICAL, URGENT, SOON, OK)
  - Stock levels (overstock handling)
  - Seasonal rules
  - Category-based rules
- 🎯 **Apply Discounts** - Quickly apply suggested or custom discounts by product ID
- 📅 **Time Simulation** - Advance the date to test how discount rules respond to changing conditions
- 📊 **Persistent Database** - All changes saved to PostgreSQL with audit trails
- 🚀 **Zero-Setup** - Database auto-initializes and auto-loads products on first run

## Prerequisites

- **Java 17** or higher
- **PostgreSQL 12** or higher
- **Maven 3.6** or higher

---

## Quick Start (First Time)

### Prerequisites

- **Java 17** or higher
- **PostgreSQL 12** or higher running locally
- **Maven 3.6** or higher

### Setup (One-Time)

The database is **fully pre-configured** with schema and 349 products ready to load. On first run:
1. The application initializes the database schema
2. Products are automatically loaded from PostgreSQL
3. The interactive CLI menu starts immediately

**That's it!** No manual data setup needed - everything is included.

---

## Database Setup Details

### Linux

1. **Install PostgreSQL** (if not already installed):
   ```bash
   sudo apt update
   sudo apt install postgresql postgresql-contrib
   ```

2. **Start PostgreSQL service**:
   ```bash
   sudo systemctl start postgresql
   sudo systemctl enable postgresql
   ```

3. **Create the database user** (run once):
   ```bash
   sudo -u postgres psql -c "CREATE USER admin WITH PASSWORD 'password';"
   sudo -u postgres psql -c "CREATE DATABASE DynamicDiscountDB OWNER admin;"
   ```

4. **Verify connection**:
   ```bash
   psql -h localhost -U admin -d DynamicDiscountDB
   ```

### Windows

1. **Install PostgreSQL** from [postgresql.org](https://www.postgresql.org/download/windows/)
   - Remember the password you set for the `postgres` user
   - Add PostgreSQL `bin` to your PATH

2. **Create the database user** (Open Command Prompt as Administrator):
   ```cmd
   psql -U postgres
   ```
   Then in the psql shell:
   ```sql
   CREATE USER admin WITH PASSWORD 'password';
   CREATE DATABASE DynamicDiscountDB OWNER admin;
   GRANT ALL PRIVILEGES ON DATABASE DynamicDiscountDB TO admin;
   \q
   ```

3. **Verify connection**:
   ```cmd
   psql -h localhost -U admin -d DynamicDiscountDB
   ```

---

## Installation & Running the Application

### 1. Clone the Repository

```bash
git clone https://github.com/CPIT252-IT1-Fall25/DynamicDiscountManager.git
cd DynamicDiscountManager
```

### 2. Build the Project

```bash
mvn clean package -DskipTests
```

### 3. Run the Application

**Linux / macOS:**
```bash
chmod +x run.sh
./run.sh
```

**Windows:**
```cmd
run.bat
```

**Alternative (Manual classpath - if scripts don't work):**
```bash
# Linux/macOS
java -cp target/classes:~/.m2/repository/org/postgresql/postgresql/42.7.0/postgresql-42.7.0.jar:~/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar sa.edu.kau.fcit.cpit252.project.DiscountManagerApp

# Windows
java -cp "target\classes;%USERPROFILE%\.m2\repository\org\postgresql\postgresql\42.7.0\postgresql-42.7.0.jar;%USERPROFILE%\.m2\repository\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar" sa.edu.kau.fcit.cpit252.project.DiscountManagerApp
```

On **first run**, the application will:
- ✅ Create the database schema
- ✅ Load 349 pre-configured products from the database
- ✅ Display the interactive menu

All subsequent runs will use the persisted data from PostgreSQL.

### 4. Run Tests (Optional)

```bash
mvn test
```

---

## Troubleshooting

### PostgreSQL Connection Issues

#### Linux
```bash
# Check if PostgreSQL is running
sudo systemctl status postgresql

# Restart PostgreSQL
sudo systemctl restart postgresql

# Check PostgreSQL logs
sudo tail -f /var/log/postgresql/postgresql-*-main.log
```

#### Windows (Run as Administrator)
```cmd
REM Check PostgreSQL service status
sc query postgresql-x64-16

REM Start PostgreSQL service
net start postgresql-x64-16

REM Stop PostgreSQL service
net stop postgresql-x64-16
```

### Maven Issues

#### Linux / macOS
```bash
# Verify Maven installation
mvn -version

# Clear Maven cache and rebuild
mvn clean install -U

# Skip tests during build
mvn package -DskipTests
```

#### Windows
```cmd
REM Verify Maven installation
mvn -version

REM Clear Maven cache and rebuild
mvn clean install -U

REM Skip tests during build
mvn package -DskipTests
```

### Database Password Issues

The default database credentials are:
- **Username**: `admin`
- **Password**: `password`
- **Database**: `DynamicDiscountDB`

If you need to reset the password:

#### Linux
```bash
sudo -u postgres psql -c "ALTER USER admin WITH PASSWORD 'password';"
```

#### Windows (in psql as postgres)
```sql
ALTER USER admin WITH PASSWORD 'password';
```

---

## Project Structure

```
DynamicDiscountManager/
├── src/
│   ├── main/java/sa/edu/kau/fcit/cpit252/project/
│   │   ├── DiscountManagerApp.java      # Main application entry point
│   │   ├── cli/                        # Command-line interface
│   │   ├── DB/                         # Database utilities
│   │   ├── discount/                   # Discount calculation logic
│   │   ├── domain/                     # Domain models (Product)
│   │   ├── observer/                   # Observer pattern implementation
│   │   ├── persistence/                # Data persistence (Repository)
│   │   ├── rules/                      # Discount rules engine
│   │   └── ui/                         # Terminal UI
│   └── test/java/                      # Unit tests
├── pom.xml                             # Maven configuration
├── init.sql                            # Database schema and 349 pre-loaded products
├── run.sh                              # Linux/macOS run script
├── run.bat                             # Windows run script
└── README.md                           # This file
```

---

## License

MIT License - see LICENSE file for details

## Authors

- CPIT252 Project Team
- King Abdulaziz University, Faculty of Computing and Information Technology

---

**Course**: CPIT252 - Object-Oriented Programming  
**Institution**: King Abdulaziz University  
**Academic Year**: 2025
