# Employee Reimbursment System (ERS)

## Executive Summary

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

**Activity Diagram**

![](./imgs/activity.jpg)

## Technologies / Dependencies

- Maven build automation tool
- JDBC and Postgres database
- Servlet and Tomcat server
- Web: HTML, CSS, Bootstrap, JavaScript, Fetch API
- Version Control Github
- Jenkins pipline
- Remote cloud database with Amazon RDS
- Virtual machine with Amazon EC2
- Host static website with Amazon S3

## Features

1. Constructing an End point for create User Account by using Postman
2. Encrypted password before storing in database
3. Functionality for User login to account from website
4. Session is stored for User credential after login
5. Ability for Employee Account can submit a reimbursment or view them all.
6. Ability for Manager Account can view all reimbursments
7. Ability for Manager can filter past or pending reimbursments
8. Ability for Manager can apporve or deny a pending reimbursment

## Strech Goals:

1. Replace JDBC with Hibernate to manage the database connection.
2. Users can upload a document or image of their receipt when submitting reimbursements which can stored in the database and reviewed by a financial manager.
3. Using Angular for Frontend

## Getting Started

- Clone the project to your local machine and open it with a JDK
- Install a Postgres database in local or remoted host(RDS)
- Config database connecion url, username and password in package: com.revature.ers.utilities
- Create database following:

**ER Diagram**

![](./imgs/physical.jpg)

- Maven Update to build dependencies
- Install Tomcat on running enviroment

### 1. Runing on Local Host

- Open CorsFillter.java in com.reavature.ers.web.fillters
  - Change the res.setHeader("Access-Control-Allow-Origin", "null") for local machine
- Run Maven project on local Tomcat server
- Open frontend folder
  - Modify the const url for all javascript to localhost:8080
  - Open the user.html to start application

### 2. Runing with AWS RDS, EC2, S3

- Upload frontend files to S3 bucket
  - Deploy a Static Website Hosting to get endpoint
  - Setting permissions: public access, bucket policy and Cross-origin resource sharing (CORS)
- Open CorsFillter.java in com.reavature.ers.web.fillters
  - Change the res.setHeader("Access-Control-Allow-Origin", //hosting endpoint//) for local machine
- Setup RDS runing
- Setup EC2 runing and get public IP address
  - Install Jenkins and run service on EC2
  - Install Tomcat9 and run service on EC2 with config own port
  - Config EC2 Inbound and Outbound for Jenkins, Tomcat, Postgres and other trafics
- Create a repository on github account
  - Add and push project to the repository
  - Adding webhook to the repository that connect to Jenkins IP address
- Login to the admin account on Jenkin server
  - Create a new freestyle item
  - Config for Source Code Management which the Repository URL
  - Add the command Execute Shell in Buid tab:
  ```
  mvn clean package
  rm -f /**/tomcat9/webapps/_.war
  mv target/_.war /**/tomcat9/webapps/
  ```
  - Save and run "Build Now"
- Open Website Hosting endpoint in S3 to start application

## Usage
