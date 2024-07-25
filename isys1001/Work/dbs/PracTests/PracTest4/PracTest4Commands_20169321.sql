/*
STUDENT_ID: 20169321
NAME: TANAKA CHITETE
CLASS: TUESDAY, 12:00
*/

-- SETUP
-- Question 1
tee PracTest4_workings_20169321.out
-- Question 2
USE dswork;
SOURCE PracTest4Tables.sql;
SOURCE PracTest4Values.sql;
-- Question 3
SHOW TABLES;
DESC Activity;
DESC CommWork;
DESC Faculty;
DESC Student;

-- TASK 1
-- Question 1
SELECT leader FROM Activity WHERE estCost < (SELECT AVG(estCost) FROM Activity);

-- Question 2
SELECT Student.stuNo, Student.firstName, Student.lastName FROM Student INNER JOIN Activity ON Student.stuNo = Activity.leader WHERE Activity.estCost < (SELECT AVG(estCost) FROM Activity);

-- Question 3
CREATE VIEW StudentHours AS SELECT Student.stuNo, CONCAT(Student.firstName, " ", Student.lastName) AS name, SUM(CommWork.hours) AS hours, COUNT(CommWork.hours) AS numActivities FROM Student LEFT OUTER JOIN CommWork ON Student.stuNo=CommWork.stuNo GROUP BY Student.stuNo;

-- Question 4
DELIMITER //
CREATE PROCEDURE insactivity (
	insactivityNo CHAR(6),
	insactivityName VARCHAR(24),
	insfacultyCode CHAR(3),
	insleader CHAR(6),
	insduration SMALLINT(6),
	OUT totalEstCost DECIMAL(8, 2)
	)
	COMMENT 'Insert new activity into table Activity'
	BEGIN
		IF insduration < 0 THEN
			SET insduration = NULL;
		END IF;
		INSERT INTO Activity(activityNo, activityName, facultyCode, leader, estCost, travelCost, duration) VALUES(insactivityNo, insactivityName, insfacultyCode, insleader, 0.00, NULL, insduration);
		SELECT SUM(estCost) FROM Activity INTO totalEstCost;
	END //
DELIMITER ;
CALL insactivity("C03107", "Science for kids 2", "A01", "000012", -1, @a);
SELECT @a;
CALL insactivity("C03108", "Daffodil day 2021", "A02", "000021", 1, @a);
SELECT @a;
CALL insactivity("C03109", "Games Night -Sem2", "A01", "000015", 1, @a);
SELECT @a;

-- Question 5
ALTER TABLE Faculty ADD COLUMN projectCost DECIMAL(8, 2);
UPDATE Faculty SET projectCost = 0.00;
DELIMITER //
CREATE TRIGGER updateprojectcost 
	AFTER UPDATE ON Activity
	FOR EACH ROW
	BEGIN
		IF OLD.travelCost IS NOT NULL
			AND NEW.travelCost IS NOT NULL THEN
		    UPDATE Faculty
			SET projectCost	= projectCost + NEW.travelCost - OLD.travelCost
		        WHERE facultyCode = NEW.facultyCode;
		END IF;
	END //
