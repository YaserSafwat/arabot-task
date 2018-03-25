# arabot-task

Steps to install the project:

1- check out the project from Git.<br />
2- use maven tool to build the project.<br />
3- edit the database.properties file (WEB-INF/props/database.properties) to let the connection establish successfully.<br />
note: the used DB is MSSQLServer.<br />
4- restore the backedup database.<br />
5- deploy the application on an application server (like: tomcat).<br />
6- use the following URLs:<br />
- http://localhost:8080/wikiArticles/search/add-data<br />
  this API can be used to add the returned data from wiki to the DB.<br />
- http://localhost:8080/wikiArticles/search/articles?title=amman<br />
  this API can be used to retrieve the articles with amman title.<br />
- http://localhost:8080/wikiArticles/search/largest-article<br />
  this API can be used to show the largest stored article based on it's word count.<br />
- http://localhost:8080/wikiArticles/search/smallest-article<br />
  this API can be used to show the smallest stored article based on it's word count.<br />
