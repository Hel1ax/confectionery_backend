FROM bellsoft/liberica-openjdk-alpine:11
WORKDIR /app
COPY . /app
EXPOSE 8081
CMD [ "java", "-jar", "/app/build/libs/confectionery-1.0-SNAPSHOT.jar" ]
