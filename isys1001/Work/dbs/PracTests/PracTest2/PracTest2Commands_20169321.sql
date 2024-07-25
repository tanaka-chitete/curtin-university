/*
STUDENT_ID: 20169321
NAME: Tanaka Chitete
LAB_CLASS: Tuesday at 12:00
*/

-- TASK 1
-- Question 1
tee PracTest2_workings_20169321.out

-- Question 2
USE dswork;
SOURCE PracTest2Tables_2.sql;
SOURCE PracTest2Values_2.sql;

-- Question 3
SHOW TABLES;
DESC Students;

-- Question 4
SELECT stuNo, phoneNo, courseCode FROM Students WHERE phoneNo IS NULL;

-- Question 5
SELECT stuNo, CONCAT(address1, ', ', address2, ', ', address3, ', ', address4) AS StreetAddress FROM Students WHERE courseCode NOT IN ('CS5SU', 'EG5DU', 'AG2PG');

-- Question 6
-- Showing student number, first name, last name and course code of all students with course code beginning with 'EG'
SELECT stuNo, firstName, lastname, courseCode FROM Students WHERE courseCode LIKE 'EG%';

-- Question 7
SELECT firstName, lastName, courseCode, DATE_FORMAT(birthDate, '%W %e %M %Y') AS Birthday FROM Students WHERE courseCode LIKE 'EG%';

-- Question 8
SELECT stuNo, firstName, startDate FROM Students WHERE DATEDIFF(NOW(), startDate) / 365.25 <= 4;

-- Question 9
-- Part A
SELECT stuNo, firstName, lastName, ROUND(fieldDeposit) AS OldDeposit, ROUND(fieldDeposit * 1.12) AS NewDeposit FROM Students;
-- Part B
SELECT firstName, lastname, ROUND(fieldDeposit) AS OldDeposit, ROUND(fieldDeposit * 1.12) AS NewDeposit FROM Students WHERE ROUND(fieldDeposit * 1.12) > 2000;
