# my-java-compiler
Welcome to my Java Compiler. 

This is a project that I am working on for my final year at university. My goal is to create a Java compiler with limited functionality (see [specification](Specification.txt)). 

Test cases of fully working code can be found in the [/bin](/bin) directory.

The project uses maven and should be fairly simple to setup. To install and build the project, use:

	mvn clean install assembly:single

This will create a jar file in the /target directory. To run the application:

	java -jar target/my-java-compiler-1.0-SNAPSHOT-jar-with-dependencies.jar <PATH_TO_JAVA_FILE>

Feel free to have a look around or give any suggestions :)
