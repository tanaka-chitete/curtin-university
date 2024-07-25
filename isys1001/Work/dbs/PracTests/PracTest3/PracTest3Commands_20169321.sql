/*
STUDENT_ID: 20169321
NAME: TANAKA CHITETE
CLASS: TUESDAY, 12:00
*/

-- TASK 1
-- Question 1
tee PracTest3_workings_20169321.out
-- Question 2
SOURCE PracTest3Tables_1.sql;
SOURCE PracTest3Values_1.sql;
-- Question 3
SHOW TABLES;
DESC Activity;
DESC CommWork;
DESC Faculty;
DESC Student;

-- TASK 2
-- Question 1
SELECT Student.firstName, Student.lastName, Activity.activityNo, Activity.activityName FROM Student JOIN Activity ON Student.stuNo=Activity.leader;
-- Question 2
SELECT Faculty.facultyCode, Faculty.facultyName, Activity.activityNo, Activity.activityName FROM Faculty JOIN Activity ON Faculty.facultyCode=Activity.facultyCode;
-- Question 3
SELECT stuNo, firstName, lastName, commRate FROM Student WHERE commRate > (SELECT commRate FROM Student WHERE Student.firstName='Heather' AND Student.lastName='Nicholls');
-- Question 4
SELECT facultyCode, SUM(estCost) as totalCost, AVG(estCost) avgCost, COUNT(estCost) numActivities FROM Activity GROUP BY facultyCode ORDER BY SUM(estCost) DESC;
-- Question 5
SELECT Student.stuNo, Student.firstName, Student.lastName, SUM(CommWork.hours) AS totalHours, COUNT(CommWork.hours) AS numActivities FROM Student LEFT OUTER JOIN CommWork ON Student.stuNo=CommWork.stuNo GROUP BY Student.stuNo;
