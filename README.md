# Java EE 7 Base Project #


This is a Maven base project that you can use for Java EE development.

This project contains configuration for a WildFly Swarm server and dependencies for the following: 

* JPA
  * ``jpa``
      Configuration Steps:
      1. Open the file db-scripts/db-script.sql and execute it on any mysql server instance (remote or local)
      2. Add the server's IP to your hosts file under the name "mysql-host"

* EJB
  * ``ejb``
      
* CDI
  * ``cdi``
      
* JAX-RS and JAX-WS
  * ``jaxrs-&jaxws``
      

## How to run? ##

1. Open a terminal (linux) or command line (windows) in the project's root directory
2. Run the command "mvn clean install". This builds the project to the directory "target"
3. In target directory, you can find two files, java-ee-base.war and java-ee-base-swarm.jar
4. From the project's root directory again, run the command "java -jar target/java-ee-base-thorntail.jar". This starts the Swarm server instance and deploys your application to it
5. Your application will run locally and use the port 8880

