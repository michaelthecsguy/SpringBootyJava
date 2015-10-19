# SpringBootyJava

This is using Java 1.7 and Spring Boot 1.2.7

This is a demo for simple Web RESTFul service.  Similar to SparkyJava but using Spring Boot.

/helloworld

/helloworld/{input name}

/createAPost

/createPost

/listAllPosts

/* How to Launch the WebService? */

~$ mvn clean install

~$ mvn exec:java -Dexec.mainClass="com.myang.App"

It will launch TomCat with port 8080
