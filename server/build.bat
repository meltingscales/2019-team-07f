mvn clean install

REM Starts Tomcat but silently exits if it is already running.
START /MIN CMD.EXE /C %CATALINA_HOME%/bin/startup.bat

mvn tomcat7:redeploy