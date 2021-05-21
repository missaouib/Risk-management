<h2>RISK#rule-management-service APIs</h2>
<br>created date: 28 01, 2021, by SmartOSC Fintech | RISK team

********************************************************************************************************* 
# Technology
	> Spring Web
	> MongoDB
	> MySQL
	> Spring AOP
	> Spring Data with 
	> Swagger 2
	> Spring Cloud config (client) -- next version
	> Spring cloud bus amqp -- next version

### Features
	* Rule/Workflow Management

### Integration
	* Auth-Services
	* other

### Docker build

### Configuration & data

	+ [configuration] use K8s ConfigMap

### Unit Test
	gradlew build -x test to ignore unit tests
	gradlew bootRun  --> run application
	test one or many Class :   
	gradlew test --tests full_package_and_ClassName
	gradlew build test --tests *ClassName --> to test one or some classes  
	Browse APIs via swagger: http://localhost:8080/swagger-ui.html#

### Docker
Mariadb for develop:

    docker run --name rulemanagement-mariadb -p 3306:3306 -e MARIADB_ROOT_PASSWORD='rulemanagement!123' -e MARIADB_DATABASE=rulemanagement-service -v mariadb_volume:/var/lib/mysql -d mariadb/server
### Docker
Mariadb for develop:

    docker run --name rulemanagement-mariadb -p 3306:3306 -e MARIADB_ROOT_PASSWORD='rulemanagement!123' -e MARIADB_DATABASE=rulemanagement-service -v mariadb_volume:/var/lib/mysql -d mariadb/server
### Docker
Mariadb for develop:
