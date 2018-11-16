"# spring-boot-mybatis-board"


#### pox.xml에서 아래 패키지 추가
```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>


		<!-- JSTL for JSP -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>

		<!-- Need this to compile JSP -->
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
			<scope>provided</scope>
		</dependency>
```		

#### application.properties에서 로그 관련 xml 추가
```
logging.config=classpath:logback-spring.xml
```


#### SQL 로깅 찍는 방법
##### 1. application.properties에서 로그 관련 spring.datasource.driver-class-name 추가
```
Java의 JDBC는 DAO 객체와 대화를 하고 DAO 객체는 Connection객체와 대화를 한다.
Connection은 Driver 객체와 대화를 하고 Driver는 DB와 대화를 한다.
- DAO객체 <==> Connection 객체 <==> Driver객체 <==> DB

datasource가 connection 객체를 가지고 있음
DAO <==> Jdbc Template(DataSource(connection객체)) <==> driver <==> DB

sql의 로그를 찍는 기술은 driver를 포장하는 거다.
DriverSpy가 DB를 대화를 하고 로그를 찍고 connection 객체에게 넘겨준다. (Proxy 패턴 기술 사용)
그런데 DriverSpy가는 어느 DB랑 대화를 할지 모른다. Oracle인지, mysql인지 모르기 때문에
어떤 DB하고 대화를 하는 것인지 spring.datasource.url에 h2라는 메모리 DB하고 대화를 할꺼라고
jdbc:log4jdbc:h2값을 명시해준다.

spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.url=jdbc:log4jdbc:h2:mem:TESTDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
```

###### 2. logback-spring.xml 파일에 아래 내용 추가
```
<logger name="jdbc" level="OFF" />
<logger name="jdbc.sqlonly" level="DEBUG" additivity="false">
	<appender-ref ref="STDOUT" />
	<appender-ref ref="FILE" />
</logger>
<logger name="jdbc.resultsettable" level="DEBUG" additivity="false">
	<appender-ref ref="STDOUT" />
	<appender-ref ref="FILE" />
</logger>
```

###### 3. log4jdbc.log4j2.properties 파일에 아래 내용 추가
```
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
```
