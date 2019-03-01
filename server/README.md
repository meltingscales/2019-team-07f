# What is this?

This is (currently) a skeleton of a web server that will eventually be the
finished webserver for our project.

It currently will serve a fun random number generator to the following URL:

    http://localhost:8080/searchable-video-library/

# How do I build and run this?

## Using Vagrant:

`vagrant up` at the root directory (`/`) of this repository.

## Manually:

### Universal requirements

- Install Java 8 or above.

  - Make sure the `JAVA_HOME` environment variable is set correctly.
    
    For reference, mine is `C:\Program Files\Java\jdk1.8.0_201`

- Install Apache Tomcat (no EXE/BIN, just an archive).

  - Set the `CATALINA_HOME` environment variable to the folder you installed
    Apache Tomcat to.
  
    For reference, mine is `P:\lib\apache-tomcat-9.0.4`.

- Install MySQL.

  Make sure you've got a user identified by:

      username: root
      password: password
  
  [Here's a guide](https://dev.mysql.com/doc/refman/8.0/en/resetting-permissions.html) on how to change passwords in MySQL. 
  
  If you encounter issues authenticating, specifically for MySQL Server 8.* or get this error:
  
        Unable to load authentication plugin 'caching_sha2_password'

  Then executing this command might help.       
  
        ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
      
  Then, there will be setup scripts under `/server/src/main/resources/*.sql`.
  Run `setup.sql`. <!-- TODO Remove this manual step. I (-H) tried. -->
  
  [Here's a guide](https://dev.mysql.com/doc/refman/8.0/en/mysql-batch-commands.html) on how to run SQL files using the MySQL console.
    
### The easiest way (IntelliJ IDEA)

- Install IntelliJ IDEA.

- Import `/server/` as an Eclipse Project, using Maven as the model.

- Check `Import Maven projects automatically`.

- Make sure the Maven artifact called
  `searchable-video-library:searchable-video-library:0.0.1-SNAPSHOT` is
  selected.

- Choose a Project SDK.

- Click Finish.

At this point, you won't be able to run the project. You need to add a build configuration!

- In the upper-right, click on `Add Configuration...`.

- Click on the `+` in the upper-left to add a run configuration from a template. Go to `Tomcat Server > Local`.

  - `Application server:` should be set to your Tomcat server, configured in
    Intellij IDEA. If it doesn't show up, configure it with `Configure...`.
  
  - HTTP Port should be 8080.

  - In the `Deployment` tab, go to the `+` and add an `Artifact` to be deployed.

  - It should be an exploded WAR of our project, called `searchable-video-library:war`.
  
  - Change the application context of `searchable-video-library:war` is `/searchable-video-library`.

  There's a lot more configuration that IntelliJ IDEA does based off of your
  `pom.xml` and framework file structures it detects, so it's super useful in
  that regard.
  
### The easy way (Eclipse)

Note: Eclipse may be hard to setup. I know it was for me, as other steps might
be omitted or platform/configuration-dependent.

Recently, Eclipse has stopped working for me, so attempt this at your own risk.

#### Installation

- Install Eclipse.

- Import this folder as a project. (`File > Open Projects from file system`)

  Note I've included a `.project` file inside
  this folder, which contains important metadata that will reduce the amount of
  configuration you will need to do.

- Install [the Kotlin Plugin from here](https://kotlinlang.org/docs/tutorials/getting-started-eclipse.html).
  
- Right-click your project and go to `Configure Kotlin > Add Kotlin nature...`.
  
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

### The *Cool and Totally Not Hard Way*™ (Maven and Tomcat via CLI)

Sourced from [here](https://www.baeldung.com/tomcat-deploy-war).

- Go to `$CATALINA_HOME/conf/tomcat-users.xml` to add users and roles to Tomcat.
  - Add the following lines to allow remote access to the server to set up,
    configure, and deploy WAR (Web Archive) files:

		<role rolename="manager-gui"/>
		<role rolename="manager-script"/>
		<role rolename="admin-gui"/>
		<role rolename="admin-script"/>
		<user username="admin" password="password" roles="manager-gui, manager-script, admin-gui, admin-script"/>
        
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
