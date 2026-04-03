## **Employee Management System**

A Java Swing desktop application for managing employee records with secure admin authentication and MySQL database integration. The system supports login validation, adding new employees, updating details, deleting records, and viewing all employee data in a structured interface.

This project follows a clean architectural layout using a DAO + Service Layer pattern for maintainability.

**Features**

*Core Functionality*

- Secure admin login (supports multiple admins).

- Add new employees.

- Update existing employee records.

- Delete employees.

- View all employees in a searchable table.

- Navigate through screens via a centralized application controller.

- Technical Highlights

- Java Swing–based graphical user interface.

- MySQL relational database integration.

- DAO and Service Layer architecture.

- Prepared statements for SQL safety.

- Configurable connection through Conn.java.

- Maven project structure for easy builds.

- Supports external libraries (JCalendar, RS2XML).

**Project Structure**

src/main/java/com/greglie/employeemanagement/

│

├── App.java                       # Main application controller

│

├── ui/                            # User interface screens

│   ├── Login.java

│   ├── AddEmployee.java

│   ├── ViewEmployee.java

│   ├── UpdateEmployee.java

│   └── RemoveEmployee.java

│

├── dao/

│   └── EmployeeDAO.java           # Direct database operations

│

├── service/

│   └── EmployeeService.java       # Business logic layer

│

└── util/

|

   └── Conn.java                  # MySQL connection manager

**Database Setup**

*1. Create the database*

CREATE DATABASE IF NOT EXISTS employee_db;

USE employee_db;

*2. Create the admin login table*

CREATE TABLE IF NOT EXISTS log_in (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'admin'
);

*3. Insert default admins*

INSERT INTO log_in (username, password, role) VALUES

('admin1', '1234', 'admin'),

('admin2', 'password', 'admin');

*4. Create the employee table*

CREATE TABLE IF NOT EXISTS employee (
    empId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    salary DOUBLE,
    phone VARCHAR(20),
    email VARCHAR(100),
    department VARCHAR(50)
);

**Requirements**

- Java 17 or newer

- MySQL Server 8.x

- Maven 3.x

- Dependencies (included in pom.xml):

- MySQL Connector/J

- JCalendar

- RS2XML

**Running the Application**

*1. Clone the repository*

git clone https://github.com/your-username/Employee-Management-System.git

cd Employee-Management-System

*2. Build with Maven*

mvn clean install

*3. Run the application*

mvn exec:java -Dexec.mainClass="com.greglie.employeemanagement.App"


Or run the main class directly from your IDE.

**Configuration**

Modify your database settings in:

src/main/java/com/greglie/employeemanagement/util/Conn.java

Default:

private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC";

private static final String DB_USER = "root";

private static final String DB_PASSWORD = "";


Update credentials to match your local MySQL setup.

**Future Improvements**

- Password hashing (BCrypt)

- Role-based dashboards (Admin / HR / Manager)

- Export employee data (Excel / PDF)

- Modern UI themes (FlatLaf)

- REST API layer for web integration

**License**

This project is open-source and free to use.
