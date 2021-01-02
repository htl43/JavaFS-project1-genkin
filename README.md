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
- Amazon RDS
- Amazon EC2
- Amazon S3

## Features

1. Developer can use POSTMAN to create employee or manager account through a end point
2. Employee or manager can login to their account by using website
3. Employee and manager can see their profile after login
4. Employee can submit a new reimbursment and view all their existed reimbursments.
5. Manager can view all reimbursments
6. Manager can filter past or pending reimbursments
7. Manager can apporve or deny a pending reimbursment

## Strech Goals:

1. Replace JDBC with Hibernate to manage the database connection.
2. Users can upload a document or image of their receipt when submitting reimbursements which can stored in the database and reviewed by a financial manager.
3. Using Angular for Frontend

## Getting Started

1. Clone the project to your local machine
