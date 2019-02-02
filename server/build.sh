# TODO test this file.

mvn clean install

# Starts Tomcat but silently exits if it is already running.
sh $CATALINA_HOME/bin/startup.sh > /dev/null 2>&1 4&

mvn tomcat7:redeploy