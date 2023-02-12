FROM maven:3.6.3-openjdk-8 AS build
WORKDIR /app
COPY . /app/

RUN mvn -B -f /app/pom.xml  exec:exec@clean exec:exec@install exec:exec@package exec:exec@scalac exec:exec@scalar -DskipTests 
RUN ls /app

FROM tomcat:9.0
COPY --from=build /app/big-data-training-prod/target/big-data-training-prod-1.0-SNAPSHOT.jar /usr/local/tomcat/webapps/big-data-training-server.war
RUN sed -i 's/port="8080"/port="8077"/' /usr/local/tomcat/conf/server.xml
EXPOSE 8077
CMD ["catalina.sh", "run"]