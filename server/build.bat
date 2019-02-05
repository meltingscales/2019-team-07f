REM mvn clean install

REM Starts Tomcat but silently exits if it is already running.
cmd.exe /C "%CATALINA_HOME%\bin\startup.bat"

mvn tomcat7:undeploy

mvn tomcat7:deploy