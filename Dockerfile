FROM tomcat:10.1.54-jdk21
COPY ./target/project-gorillaz-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
ENV JAVA_OPS="-Xms256m -Xmx450m --XX:UseContainerSupport"
EXPOSE 8080
