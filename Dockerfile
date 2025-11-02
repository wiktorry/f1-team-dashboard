FROM maven:3.9.11-amazoncorretto-25-alpine
COPY . /f1
WORKDIR /f1
RUN mvn package -DskipTests
ENTRYPOINT ["java","-jar","/f1/target/f1-team-dashboard.jar"]