REM mvn clean install

REM Starts Tomcat but silently exits if it is already running.
CMD.EXE /C "%CATALINA_HOME%\bin\startup.bat"

mvn tomcat7:redeploy