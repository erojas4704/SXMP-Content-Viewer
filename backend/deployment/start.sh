#!/bin/sh
postgres -D /var/lib/postgresql/data &

for i in {1..30}; do
    if echo '\q' | psql -U postgres; then
        break
    fi
    echo "Waiting for PostgreSQL to start..."
    sleep 1
done

#Check if database exists
DATABASE_EXISTS=$(psql -U postgres -tAc "SELECT 1 FROM pg_database WHERE datname='sxmp'")

if [ "$DATABASE_EXISTS" != "1" ]; then
  createdb -U postgres sxmp
fi

java -jar app.jar