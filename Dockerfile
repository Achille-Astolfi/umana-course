FROM mdsol/java11-jre
COPY target/umana-course-0.0.1-SNAPSHOT.jar /var/app/umana-course-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD [ "java", "-jar", "/var/app/umana-course-0.0.1-SNAPSHOT.jar" ]
