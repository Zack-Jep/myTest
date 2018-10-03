# largesofteng
Course material for the course "Large-Scale Software Engineering".

cd to POM.xml. Then run the commands below. The first one builds the project.
The second comand runs the program.

mvn verify
mvn exec:java -Dexec.mainClass="se.lth.base.server.BaseServer"

For windows users:
Download maven for windows @ https://maven.apache.org/download.cgi
Under Files -> Binary Zip archive.
Extract to C:/
Control panel -> Search for "environment variables" -> Edit the system environment variables -> Environment Variables
In the lower list, select "Path" and edit. 
New -> C:\apache-maven-3.5.4\bin

open cmd, check that you can use the command: mvn --version

Try running java -version, if it doesn't work. Check that you also have a java JDK in your environment variable Path. If you don't, download a JDK from https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

java SE Development Kit -> windows x64 or x86 depending on your system.
Install, then follow the steps above and do the same thing for the JDK.
The JDK is usually located under C:\Program Files\Java (unless you changed it) and the path should be:
C:\Program Files\Java\jdk1.8.0_181\bin

To run the system, open cmd:
cd to where you cloned the github repo.
run mvn verify and mvn exec as above.
For me this is:
cd C:\Users\Gustav\Software\Java\ETSN\etsn05-samakning\base\server
mvn verify && mvn exec:java -Dexec.mainClass="se.lth.base.server.BaseServer"

If you don't want to do this manually every time. Create a new txt file and add the following (remember to change PATH_TO_FOLDER). Then change the file extension to .bat (mine is called runETSN.bat)
@echo off
cd C:\PATH_TO_FOLDER\etsn05-samakning\base\server
mvn verify && mvn exec:java -Dexec.mainClass="se.lth.base.server.BaseServer"

! if you don't use version 8 of the JDK you'll have to add the following dependencies in the pom.xml.
        <dependency>
        <groupId>javax.activation</groupId>
        <artifactId>activation</artifactId>
        <version>1.1.1</version>
        </dependency>
        <dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
            <version>2.3.0</version>
        </dependency>
        <dependency>
            <groupId>com.sun.xml.bind</groupId>
            <artifactId>jaxb-core</artifactId>
            <version>2.3.0</version>
        </dependency>
        <dependency>
            <groupId>com.sun.xml.bind</groupId>
            <artifactId>jaxb-impl</artifactId>
            <version>2.3.0</version>
        </dependency>


