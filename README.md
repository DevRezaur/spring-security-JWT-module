# JWT Auth with Spring Boot

This is a ready to use template code for implementing `JWT based authentication and authorization` using Spring Boot.

<br />

## Documentation and Usage

### Authentication API

`POST` URI: `localhost:8080/auth/authenticate`

<br />

![authenticate-api](https://github.com/DevRezaur/spring-security-JWT-module/blob/main/Screenshots/authenticate-api.PNG)

### Access User Controller

`GET` URI: `localhost:8080/user/`

<br />

![user-controller](https://github.com/DevRezaur/spring-security-JWT-module/blob/main/Screenshots/user-controller.PNG)

### Access Admin Controller

`GET` URI: `localhost:8080/admin/`

<br />

![admin-controller](https://github.com/DevRezaur/spring-security-JWT-module/blob/main/Screenshots/admin-controller.PNG)

### Token Expired / Access Denied Example

`GET` URI: `localhost:8080/admin/`

<br />

![access-denied](https://github.com/DevRezaur/spring-security-JWT-module/blob/main/Screenshots/access-denied.PNG)

### User Registration API

`POST` URI: `localhost:8080/auth/registerUser`

<br />

![user-registration](https://github.com/DevRezaur/spring-security-JWT-module/blob/main/Screenshots/user-registration.PNG)

### Admin Registration API

Since admin registration is a `admin privileged` feature, we protected it via `/admin/` mapping. So sending authentication header is required with this request. 

`POST` URI: `localhost:8080/admin/registerAdmin`

<br />

![admin-registration](https://github.com/DevRezaur/spring-security-JWT-module/blob/main/Screenshots/admin-registration.PNG)

<br />

## Migrate to MySQL

By default this module uses H2 database. To use MySQL edit the following configuration.

<br />

Open `pom.xml` and configure it as below:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.5.0</version>
		<relativePath />
	</parent>
	<groupId>dev.rezaur</groupId>
	<artifactId>Spring-Security-JWT</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring-Security-JWT</name>
	<description>Spring security with JWT</description>

	<properties>
		<java.version>15</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.module</groupId>
			<artifactId>jackson-module-kotlin</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

<br />

After that open `src/main/resources/application.properties` and configure like below:

```properties
server.port=8080
spring.jpa.open-in-view=false
spring.h2.console.enabled=true
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true

# JWT secret
jwt.secret=secret
# JWT token lifespan (5 mins)
jwt.jwtExp=300000
```

<br />

## Additional Configuration

`src/main/resources/data.sql` file is used to insert initial data to the database. This file is not mandatory. Feel free to `modify/delete` this `data.sql` file.

```sql
INSERT INTO roles
VALUES(1, 'ROLE_ADMIN');

INSERT INTO roles
VALUES(2, 'ROLE_USER');

INSERT INTO users (user_id, fullname, username, password)
VALUES('101', 'Rezaur Rahman', 'DevRezaur', 'iamadmin');

INSERT INTO users (user_id, fullname, username, password)
VALUES('102', 'Sanzida Sultana', 'SanzidaSultana', 'iamuser');

INSERT INTO user_role
VALUES('101', 1);

INSERT INTO user_role
VALUES('101', 2);

INSERT INTO user_role
VALUES('102', 2);
```

<br />

## Run the Project

* `Clone the repository`
* `Update maven build (Project > Maven > Update Project)`
* `Run (Project > Run As > Spring Boot App)`

<br />

## Relevant Projects

> [JWT Auth with Refresh Token using Spring Boot](https://github.com/DevRezaur/JWT-refresh-token-spring-boot)

<br />

Feel free to leave a star if you find this helpful :smile:
