#!/bin/bash
# Dynamic Discount Manager - Run Script (Linux/macOS)
# This script runs the application with all required dependencies

cd "$(dirname "$0")" || exit

# Check if classes are built
if [ ! -d "target/classes" ]; then
    echo "Error: Project not built. Please run: mvn clean package -DskipTests"
    exit 1
fi

# Find PostgreSQL driver
PG_JAR=$(find ~/.m2/repository -name "postgresql-*.jar" -type f | head -1)
GSON_JAR=$(find ~/.m2/repository -name "gson-*.jar" -type f | head -1)

if [ -z "$PG_JAR" ]; then
    echo "Error: PostgreSQL driver not found. Run: mvn clean package -DskipTests"
    exit 1
fi

if [ -z "$GSON_JAR" ]; then
    echo "Error: Gson library not found. Run: mvn clean package -DskipTests"
    exit 1
fi

# Run the application
java -cp "target/classes:$PG_JAR:$GSON_JAR" sa.edu.kau.fcit.cpit252.project.DiscountManagerApp
