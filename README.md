# arabot-task

Steps to install the project:

1- check out the project from Git.
2- use maven tool to build the project.
3- edit the database.properties file (WEB-INF/props/database.properties) to let the connection establish successfully.
note: the used DB is MSSQLServer.
4- restore the backedup database.
5- deploy the application on an application server (like: tomcat).
6- use the following URLs:
- http://localhost:8080/wikiArticles/search/add-data
  this API can be used to add the returned data from wiki to the DB.
- http://localhost:8080/wikiArticles/search/articles?title=amman
  this API can be used to retrieve the articles with amman title.
- http://localhost:8080/wikiArticles/search/largest-article
  this API can be used to show the largest stored article based on it's word count.
- http://localhost:8080/wikiArticles/search/smallest-article
  this API can be used to show the smallest stored article based on it's word count.
