# WixChallenge
HTTP server for a to-do list app

This is my HTTP Server.

**TO LAUNCH**: In order to run the application, you will need to download Jetty. 

1. Download the  jetty-distribution-9.3.9.v20160517 folder.  

2. In your commandline, navigate to the jetty-distribution-9.3.9.v20160517/WixBase directory

3. Call the command java -jar myDirectory/jetty-distribution-9.3.9.v20160517/start.j. Then navigate to http://localhost:8080//WixChallenge/

**Implementation Details.**

I created an HTML file called index.html, WixServlet.java, and a JUnit test file called ServletTest.java.

Server: I used Jetty for the server. I exported my code to a .war file and deployed it using Jetty. The .war file is also included in this repo. 

Storage: I chose to store my data as a hash and write it to a file for persistent storage. When the server is initialized, it searches for the specific data files and, if successful, reads in the task and log info. This ensures that the data is maintained across muliple relaunches.

I created a TaskObj class in WixServlet that packages the task ID, description, and date into one object. It stores all the information as strings. If additional functionality was required, (like to search for a task given it's date, etc.), I would restructure the TaskObj to store a Date object rather than the string of the date. Since this assignment only requires printing out the data, strings suffice. Utilzing strings enabled me to use the TaskObj to store history entries as well. Instead of storng the task description, I simply stored whether the object was added or deleted. Again, if additional functionality was required, I would create a more complex action object.

WixServlet implements addItem, deleteItem, getAllItems, and showHistory. I modified the doPost function to handle requests and call the appropriate function based on the request context. DoPost then redirect the URl, which calls the get endpoint. 

The interface is very functional and not very designed - if I were creating a real app, I would spend more time developing the html files for a cleaner look. In the interest of time, I left it simple but organized. 

Please let me know if you have any questions regarding implementation details!


