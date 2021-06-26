# Film Query Application

### Overview
This program simulates a command-line application that retrieves film data from MySQL DBMS and displays the information. We utilize Java language's JDBC API through Eclipse for a user to choose options from a displayed menu and submit query data.

### Technical Skills Used
* Java and Eclipse IDE
* MAMP Software & MySQL DBMS
* JDBC API
* ORM (Object-Relational Mapping) Software Development
* Encapsulation (getter and setter methods)
* Constructors 
* If/Else methods
* While Loops
* User input with Scanner

### Connecting to the MySQL Database
The JDBC API application programming interface is used for manipulating a database's information through the use of JDBC drivers to connect to a certain type of database. Within the context of this project, we used JDBC to retrieve data from a MySQL DBMS through queries. In order to connect to a MySQL database, it required a MySQL-connector-Java dependency. Then, we registered our driver using Class.forName() method. We created our connection with getConnection() method--which derives from DriverManager class--that takes a URL String parameter. For MySQL, we used a "jdbc:mysql://localhost:3306/..." URL and provided a local user and password. From there, we created PreparedStatement objects (using setX() to assign our SQL statement parameters) for executing SQL commands to retrieve the information from the database tables with executeQuery() method, which returned a ResultSet type object. Lastly, we used ResultSet object--which derives from ResultSet Interface--and its next() method to (a) traverse the table's entities, (b) retrieve values for each table cell with getX() methods, where X is the data type of the cell data, and (c) return Java objects that represented the associated table in the database.


### How to Run
This program runs on Eclipse IDE and requires a keyboard for menu-based user selection.

### Lessons Learned
I have learned about Object-Relational Mapping and how code can be used to translate data from a relational database into objects and fields of an Object-oriented language application. I also became more comfortable with primary keys and foreign keys to relate entities and attributes of a database. 
