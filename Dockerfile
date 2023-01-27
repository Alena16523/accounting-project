FROM amd64/maven:3.8.6-openjdk-11
WORKDIR usr/app
COPY .   .
RUN mvn clean install -DskipTests
ENTRYPOINT ["mvn","spring-boot:run"]


#FROM maven:3.6.3-jdk-11-openj9 as builder
#
##  Create a directory /app in the Container
#WORKDIR /app
##  Copy pom.xml from this application and paste inside /app directory..
#COPY pom.xml .
##  Run maven clean command and remove /target folder from our application..
#RUN mvn clean
##  Then copy src folder of our application to Containers ./src folder which will be created..
#COPY src ./src
##  Run maven package lifecycle without running test code..This will create .jar file in /target folder..
#RUN mvn install -DskipTests
#
##  NOTE that we created some ready code and we will call it --from=builder below...
#
##  Get openjdk image which is one of many choices for Java Applications..
#FROM adoptopenjdk/openjdk11
##  Copy .jar file from builder and paste inside Container root folder -> /root/accounting.jar
#COPY --from=builder /app/target/java-hedgehogs-project-0.0.1-SNAPSHOT.jar /java-hedgehogs-project-0.0.1-SNAPSHOT.jar
##  Inside Container, open a TERMINAL and run "java -jar accounting.jar" command -> This will run jar file
#ENTRYPOINT ["java","-jar","java-hedgehogs-project-0.0.1-SNAPSHOT.jar"]