# Report

## Nihar Patel
## Henry post
## Divin Gregis Baniekona
## Idris Fagbemi
## Jimmy Tran
## Yi-Ting Lin

#### 1. Language and framework of your choice:
a. Languages: Java, MySQL, bash, kotlin, python, HQL

b. Servers: MySQL, Apache Tomcat

c. Framework: hibernate, java server faces, spring boot (webpages)

d. Built and deploy: Maven, Vagrant

#### 2. Operating System Platform of Choice:
Ubuntu. Because package manager has a lot of packages so that we don’t have to build it from source.

DOWN SIDE: Builds are not deterministic.

a. Packages and dependencies (installers): Java, Mavin Tomcat, MySQL

b. Process of Secrets management: No Secrets Management.

c. Capture of application metrics:We have Tomcat logging engine, MySQL database to run analysis on that, Maven to tell how long it takes to deploy.

#### 3. Use of Data Store/Storage:
a. Database or similar storage technology: MySQL

#### 4. Data encrypted at rest:
a. We are going to. We don’t have password yet!

#### 5. Database makes use of master/slave replication:
a. Schema creation: No. We are not going to.

b. Caching layer implementation: No.

c. Master for database writes: No.

d. Slave for database reads: No.

#### 6. Use of Responsive design:
Yes we are creating one.

#### 7. Use of https:
a. Self-signed certs: We do not have any.

b. Login authentication mechanism: Yes. We will impose the Nist rule on users because they are simple.

c. Explanation of security assumptions relating to:

  1. Firewall: All ports will be locked down except for https and http. No ssh. No remote desktop.

  2. Authentication keys: None. They are in the git history. They are hard coded in the file and they are very simple.

  3. Seeding of usernames and passwords: No.
iv. Pre-seeding databases/datastores with schema and records: We will have them.

#### 8. Use of user authentication:
a. Must use HTTP Session: We will force https when we are done. Right now we have http.

b. Different UI for Unauthenticated users:

  1. Must have read/only features for unauthenticated users: We plan to have it when we get our authentication and session done.

c. Different UI for Authenticated users:

  1. Must have a user account management page: We plan to have it when we get our authentication and session done.

d. Different UI for Administrative users:

  1. Must have an administrative database dump and restore feature: We plan to have it when we get our authentication and session done.

e. UI is modified per authenticated user via CSS: We are doing to do it.

#### 9. Creation of Dev Environment (local laptop):
a. Must work according to specification: We have a build guide but we do not have automated way to set up an environment.

b. Environment must be configurable via script pre-deploy

  1. No manual editing or installing: We do not have an automated development environment.

c. Explanation of UI/UX testing methodology and bug reporting: We do not have any testing methodology.

#### 10. Layout design:
a. Diagrams of site functionality need to match delivered features: We do not have any.

b. Diagrams of colors, fonts, and other usability features: We do not have any.

#### 11. Management of Visio (or comparable) diagram tool of work flow:
We do not have any.

#### 12. Management of project progress:
a. [Trello](https://trello.com/b/03OdRjtq/2019-team-07f)

b. [Slack](https://itmt-430-group.slack.com)

c. [GitHub](https://github.com/illinoistech-itm/2019-team-07f)

d. GitHub Issues to resolve bug posts from UI/UX tester

  1. Must post at least 10 bugs in final sprint: We will have it by final sprint.

#### 13. Team must generate at least 15 real “test” user data that is inserted upon instance creation and proper data to test functionality of a system:
a. No system is ever used “blank” always fill it up with real data: We will have them for final sprint.
