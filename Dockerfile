FROM maven:3-jdk-11

WORKDIR /usr/src/app

# Copy the project files
COPY . /usr/src/app

RUN echo "Checking credentials" && cat /usr/src/app/.m2/settings.xml && env

# Build the application using the custom settings.xml
RUN mvn package -s /usr/src/app/.m2/settings.xml -DskipTests

EXPOSE 5000

# Run the application with the custom settings.xml
CMD [ "sh", "-c", "mvn -s /usr/src/app/.m2/settings.xml -Dserver.port=5000 spring-boot:run -Dspring-boot.run.arguments=--server.address=0.0.0.0" ]

