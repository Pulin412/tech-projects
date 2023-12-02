## CGM-Assignment-SpringBoot
A Java 8, Spring boot program that works as a wrapper for the command line utility and can be run as a Spring boot application.

### About
- Technologies used : Java, Spring Boot

### Assumptions
- Spring Boot framework is used assuming future increments to the solution with multiple and complex entities and dependencies. 
- Auto configuration is used to add the dependency to the classpath at the start of the application so that no other change is required.

### Response Messages
- If the user asks a question which is not stored yet in the program, “the answer to life, universe and everything is 42” is printed.
- If the user asks a question which is  stored in the program, all answers to that question are printed in a separate line.
- If a question, which is already saved, is tried to save in the program with same/different answers, "Question is already present." is printed and question is discarded.
- If any question with invalid format (without '?') or as null is passed to the program, "Question format is incorrect" , "Question is invalid" is printed.
- Correct format for question - 
    `<question>? “<answer1>” “<answer2>” “<answerX>”`

### Running the application

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

```bash
$ java -version
java version "1.8.0_152"
Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)
```

```bash
$mvn -v
Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T13:28:13+05:30)
Maven home: D:\softwares\apache-maven-3.5.2\bin\..
Java version: 1.8.0_152, vendor: Oracle Corporation
Java home: C:\Program Files\Java\jdk1.8.0_152\jre
```
 
#### Run the application with arguments - 
```bash
java -jar target/answerToEverything-0.0.1-SNAPSHOT.jar What is Peters favorite food? "Pizza" "Spaghetti" "Ice cream"
``` 

To exit the application, press **ctrl-c**.
