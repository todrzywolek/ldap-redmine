FROM openjdk:14-jdk-slim-buster

# Add dependencies
ADD target/ldap/lib                                 /usr/share/redmine-backend/lib

# Add jar
ARG JAR_FILE
ADD target/${JAR_FILE}                              /usr/share/redmine-backend/app.jar

# Set working directory
WORKDIR /usr/share/redmine-backend

ENTRYPOINT ["java","-jar","app.jar"]