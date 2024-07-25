/*
STUDENT_ID: 20169321
NAME: Tanaka Chitete
LAB_CLASS: Tuesday at 12:00
*/

-- TASK 2
-- Question 1
-- Creating Branch relation which will be then linked to Employee relation
CREATE TABLE Branch(
	brNo CHAR(5) PRIMARY KEY,
	city VARCHAR(12),
	telephone CHAR(10)
);

-- Creating Employee relation which will be then linked to Branch relation
CREATE TABLE Employee(
	empNo INT(5),
	checkDigit CHAR(1),
	PRIMARY KEY (empNo, checkDigit),
	firstName VARCHAR(12),
	lastName VARCHAR(15),
	salary DECIMAL(8, 2),
	brNo CHAR(5),
	CONSTRAINT fk_brNo
	FOREIGN KEY (brNo) REFERENCES Branch(brNo)
);

-- Question 2
SOURCE PracTest2Task2_20169321.sql;

-- Question 3
INSERT INTO Branch VALUES
('9A99B', 'Perth', '0403891782');
INSERT INTO Employee VALUES
(99120, 9, 'Tanaka', 'Chitete', 999999.00, '9A99B'); 
