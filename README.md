#Stock Service CRUD With Springboot 2.7.2

#Important NOTE:
data insertion is done with spring commander and embedded H2 is Used For Project

#swagger address
http://localhost:8080/api/swagger-ui/index.html

#deployment
#in root of project there is docker-compose file please run:
option 1:  'docker-compose up -d'
option 2: 'mvn clean install' then navigate to target directory and run 'java -jar stock-api-1.0.0.jar'
option 3: run 'mvn clean install' then 'mvn spring-boot:run'

#postman
to facilitate api testing just put postman collection u can import it

