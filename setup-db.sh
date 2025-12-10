
echo "Setting up PostgreSQL database..."

sudo -u postgres psql << EOF
CREATE DATABASE "DynamicDiscountDB";
CREATE USER admin WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE "DynamicDiscountDB" TO admin;
\q
EOF

psql -h localhost -U admin -d DynamicDiscountDB -f init.sql

echo "Database setup complete!"