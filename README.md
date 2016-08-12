# play-api-demo

Setup Environment

1. Install JDK 1.8 from http://www.oracle.com/technetwork/java/javase/downloads/index.html
2. Install IDEA from https://www.jetbrains.com/idea/download/
3. Download Activator from https://playframework.com/download
4. Extract Activator to a location that doesn't require sudo permission to write.
5. Add to your login profile. Usually, this is $HOME/.profile:
    export PATH=/path/to/activator-x.x.x:$PATH
   
    Make sure that the activator script is executable. If it’s not:
     chmod u+x /path/to/activator-x.x.x/activator
6. Create your first play application with this command: activator play-api-demo play-java
7. Run your play app with these commands:
   
    cd play-api-demo
   
    activator run
8. Go to http://localhost:9000


Setup Project in IDEA

1. Open IDEA
2. File -> New -> Project from Existing Sources...
3. Select play-api-demo that you just created.
4. Select "Import project from external model" with SBT. Click Next.
5. Check "Use auto-import". Make sure Project SDK is set to "1.8".
6. Click Finish


Setup MySQL

From http://dev.mysql.com/downloads/, download and install the Community Server. WorkBench is also recommended.

