FROM openjdk:17
COPY target/StudentManagement*.jar /usr/src/StudentManagement.jar
COPY  src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/StudentManagement.jar", "--spring.config.location=file:/opt/conf/application.properties"]