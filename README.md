Application Topology Manager
============

How To Run
============
- Compile: mvn clean package
- Run: mvn jetty:run

Dependency
============
Project based on Java (1.6) with 
- Jetty for appserv
- Jersey for rest
- Maven builder
- SQLite database.

Configuration
============

In that project i have a DataSource and you must configure jetty start.ini file with that options
OPTIONS=Server,jsp,jmx,resources,websocket,ext,plus,annotations
and config
etc/jetty-plus.xml
to work with DataSource.

