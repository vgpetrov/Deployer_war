Deployer_war
============

Project based on Java with Jetty for appserv, Jersey for rest, Maven builder and SQLite databse.
In that project i have a DataSource and you must configure jetty start.ini file with that options
OPTIONS=Server,jsp,jmx,resources,websocket,ext,plus,annotations
and config
etc/jetty-plus.xml
to work with DataSource.
