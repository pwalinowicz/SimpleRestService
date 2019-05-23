# SimpleRestService

Technologies used:
- Java 8
- JAX-RS
- Jersey
- Spring
- Console App
        
Service has following functionalities:
- Lists all posts
- Creates a post
- Gets one post by id
- Deletes one post by id
- Modifies one post by id
A "Post" have the attributes [id:String, title:String, content:String]. 
JSON is default type of consumed/produced data.
	
Run using payara-micro local distribution jar using following commands:
1. mvn clean install
2. java -jar payara-micro-[version_of_your_local_payara_micro].jar --deploy [path_to_target_webservice_war]
3. java -jar [path_to_target_client_jar]

TODO:
- configure embedded server (e.g. Tomcat, Jetty etc.)
- rebuild the Client to be a web application using Spring+Thymeleaf instead of a console app.
- add UTs 
