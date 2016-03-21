# Calories Application

### Requirements

* JDK 1.8
* Java EE Application Server (production environment)
* MySQL (production environment)

### Compiling the Application

* Clone the repository to a Linux, Mac OS X or Windows with Cygwin (`bash` and `make`)
* Type on console (git root directory): `./configure`
* Type on console (git root directory): `make`

### Deploying the Application

##### Test environment:

* Type on console (git root directory): `make run`

##### Production environment:

* After compile (`make`) deploy the war located on `target` directory to a Java EE Application Server (example Tomcat)
* The J2EE EE Application Server most have a DataSource (`jndi:jdbc/CaloriesDS`) point to a empty MySQL DataBase (user with DBA permission)

### Using the Application

* This application needs only a valid e-mail to login, if the user is accessing the application for the first time, he will be able to create your own account
* There are tree types of users (roles) and each user has your only meals
* Regular: can edit your own information (email, first name, last name, daily calories, role) and create/edit/remove meals (text, calories, date and time)
* Manager: have all the permissions from Regular and can create/edit/remove other users (without meals)
* Administrator: have all the permissions from Manager and can create/edit/remove meals for any user.

### Technologies:

* Maven
* TestNG
* Jersey
* Spring
* Hibernate
* Jetty
* C3P0
* H2

