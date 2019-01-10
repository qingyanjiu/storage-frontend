FROM maven:3
ADD . /web/
WORKDIR /web
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:8-jdk-alpine
COPY --from=0 /web/target/*.jar /web/storage-frontend.jar
EXPOSE 8000
WORKDIR /web
CMD java -jar *.jar