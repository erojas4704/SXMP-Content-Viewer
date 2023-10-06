FROM eclipse-temurin:17.0.3_7-jdk-alpine

WORKDIR /app
COPY target/SXMproject-0.2.0.jar ./app.jar
COPY deployment/start.sh /app/start.sh


#postgres
RUN apk update && apk add postgresql postgresql-client
RUN mkdir -p /var/lib/postgresql/data
COPY deployment/postgresql.conf /var/lib/postgresql/data/
RUN chown -R postgres:postgres /var/lib/postgresql/data

RUN mkdir -p /run/postgresql && chown -R postgres:postgres /run/postgresql

RUN chmod 0700 /var/lib/postgresql/data  # Set owner permissions
RUN chmod 0750 /var/lib/postgresql/data  # Set group permissions

RUN rm -rf /var/lib/postgresql/data/* || true

USER postgres
RUN initdb -D /var/lib/postgresql/data


EXPOSE 5432


EXPOSE 8080
CMD ["/app/start.sh"]

