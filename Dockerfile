FROM openjdk:17-jdk-alpine
WORKDIR user/
COPY target/BanklyUser-0.0.1-SNAPSHOT.war user/
ENTRYPOINT ["java", "-jar","user/BanklyUser-0.0.1-SNAPSHOT.war"]