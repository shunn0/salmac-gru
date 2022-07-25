FROM maven:3.8.2-jdk-8
WORKDIR .
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run

COPY target/salmachost-0.0.1-SNAPSHOT.jar .
RUN mkdir -p file_upload
CMD java -jar salmachost-0.0.1-SNAPSHOT.jar