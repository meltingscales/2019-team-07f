# What is this?

This is (currently) a skeleton of a web server that will eventually be the
finished webserver for our project.

It currently will serve a fun random number generator to the following URL:

    http://localhost:8080/searchable-video-library/index.jsp

# How do I build and run this?

Using Vagrant:

`vagrant up` at the root directory (`/`) of this repository.

Manually:

## Universal requirements

- Install Java.
- 
  - Make sure the `JAVA_HOME` environment variable is set correctly.
    
    For reference, mine is `C:\Program Files\Java\jdk1.8.0_201`


- Install Apache Tomcat (no EXE/BIN, just an archive).
  - Set the `CATALINA_HOME` environment variable to the folder you installed
    Apache Tomcat to.
  
    For reference, mine is `P:\lib\apache-tomcat-9.0.4`.

## The easy way (Eclipse)

### Installation

- Install Eclipse.

- Import this folder as a project. (`File > Open Projects from file system`)

  Note I've included a `.project` file inside
  this folder, which contains important metadata that will reduce the amount of
  configuration you will need to do.

- Go to `Window > Show View > Servers`.

- Click on `Click this link to add a new server...`

- Select the server that applies to your installed Tomcat.
  - If you've never added a server to Eclipse, click `Add...` under `Server
    Runtime Environment` and fill out that dialog box.

- Once you've selected a server runtime environment, click `Finish`.

- In the `Servers` view, right-click your server and click on `Add and
  remove...`.

- Ensure that your project is added as a "configured" resource (on the right
  panel).

- Right-click on the server in `Servers` and click on `Start`.

- If the stars are aligned, you should be able to go to the URL mentioned above
  and see a live site.

Alternatively, you can right-click on `index.jsp` in the `Project Explorer` view
and click on `Run as... > Run on Server` to see a preview of `index.jsp` on the
server.

## The *Cool and Totally Not Hard Way*™ (Maven and Tomcat via CLI)

Sourced from [here](https://www.baeldung.com/tomcat-deploy-war).

- Go to `$CATALINA_HOME/conf/tomcat-users.xml` to add users and roles to Tomcat.
  - Add the following lines to allow remote access to the server to set up,
    configure, and deploy WAR (Web Archive) files:

        <role rolename="manager-gui"/>
        <role rolename="manager-script"/>
        <user username="admin" password="password" roles="manager-gui, manager-script"/>
        
    Note that these are NOT secure settings.
    
  - If you feel like it, run `$CATALINA_HOME/bin/startup.[bat|sh]` to start Tomcat
    and try to log in at `localhost:8080` with those credentials to test if
    you've done it correctly.
    
    If it doesn't work, you've done something wrong.

- Install Maven.
  
- Go to `$M2_HOME/conf/settings.xml` to add our server as one that Maven can
  deploy to.
  
  Add the following lines as children to the `<servers>` XML tag (around line
  110)
  
        <server>
            <id>TomcatServer</id>
            <username>admin</username>
            <password>password</password>
        </server>
        
  This tells Maven how to connect to our server.
  
  At this point, you can run `./build.[bat|sh]` as you have everything installed.
  Or, you could do it manually.

- Run `mvn clean install` to generate a WAR file, ready to be deployed.

- Start Tomcat by running `$CATALINA_HOME/bin/startup.[bat|sh]`.

  Tomcat must be running for you to deploy.

- Deploy by running `mvn tomcat7:deploy`.

- Undeploy with `mvn tomcat7:undeploy`.

- Redeploy after making changes with `mvn tomcat7:redeploy`.
