@echo off
REM Dynamic Discount Manager - Run Script (Windows)
REM This script runs the application with all required dependencies

REM Get the directory of this script
set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%"

REM Check if classes are built
if not exist "target\classes" (
    echo Error: Project not built. Please run: mvn clean package -DskipTests
    exit /b 1
)

REM Find PostgreSQL driver and Gson in Maven repository
setlocal enabledelayedexpansion
set "PG_JAR="
set "GSON_JAR="

for /r "%USERPROFILE%\.m2\repository" %%f in (postgresql-*.jar) do (
    set "PG_JAR=%%f"
    goto found_pg
)

:found_pg
for /r "%USERPROFILE%\.m2\repository" %%f in (gson-*.jar) do (
    set "GSON_JAR=%%f"
    goto found_gson
)

:found_gson
if "!PG_JAR!"=="" (
    echo Error: PostgreSQL driver not found. Run: mvn clean package -DskipTests
    exit /b 1
)

if "!GSON_JAR!"=="" (
    echo Error: Gson library not found. Run: mvn clean package -DskipTests
    exit /b 1
)

REM Run the application
java -cp "target\classes;!PG_JAR!;!GSON_JAR!" sa.edu.kau.fcit.cpit252.project.DiscountManagerApp
