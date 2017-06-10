# debug-jdbc-ssl

Quick and dirty command-line utility to connect to MySQL via JDBC with SSL debugging enabled.


Usage:


```
sbt "run -t path/to/truststore.ts --h 127.0.0.1 -P 3306 -u root -p password"
```
