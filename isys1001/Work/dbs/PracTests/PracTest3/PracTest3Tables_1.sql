/* MySQL file for creating tables for Practical-test3 */

-- create Faculty table
DROP TABLE IF EXISTS Faculty;
CREATE TABLE Faculty(
            facultyCode   		CHAR(3),
            facultyName		VARCHAR(36) NOT NULL,
            PRIMARY KEY(facultyCode)
);

-- create Students table
DROP TABLE IF EXISTS Students;
CREATE TABLE Student(
        stuNo     	CHAR(6)	         ,
        firstName 	VARCHAR(12)            ,
        lastName  	VARCHAR(15)            ,
        facultyCode  	CHAR(3)                ,
        regDate   	DATE                ,
        commRate        CHAR(4)                ,
        PRIMARY KEY(stuNo),
        FOREIGN KEY(facultyCode) REFERENCES Faculty(facultyCode)
);


-- create Activity table

DROP TABLE IF EXISTS Activity;
CREATE TABLE Activity(
    activityNo		CHAR(6) NOT NULL,
    activityName	VARCHAR(24) NOT NULL,
    facultyCode       	CHAR(3) NOT NULL,
    leader		CHAR(6)	NOT NULL,
    estCost   	DECIMAL(8,2),
    travelCost 	DECIMAL(8,2),
    duration	SMALLINT(6),
    PRIMARY KEY(activityNo),
    FOREIGN KEY(facultyCode) REFERENCES Faculty(facultyCode),
    FOREIGN KEY(leader) REFERENCES Student(stuNo)
);

-- create CommWork table
DROP TABLE IF EXISTS CommWork;
CREATE TABLE CommWork(
stuNo     		CHAR(6),
activityNo		CHAR(6),
hours 		      	SMALLINT,
PRIMARY KEY(stuNo,activityNo),
FOREIGN KEY(stuNo) REFERENCES Student(stuNo) ON DELETE CASCADE,
FOREIGN KEY(activityNo) REFERENCES Activity(activityNo) ON DELETE CASCADE);
