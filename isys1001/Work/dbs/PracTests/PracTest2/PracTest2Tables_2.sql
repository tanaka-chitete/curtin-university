
/* ParcTest2Tables_2.sql: SQL file for table creation in Practical test-2 */

-- create Students table

 CREATE TABLE Students(
	stuNo     CHAR(6),
        firstName VARCHAR(12)  NOT NULL,
        lastName VARCHAR(15)  NOT NULL,
	address1  VARCHAR(10), 		-- House number
	address2  VARCHAR(20), 		-- Street 
	address3  VARCHAR(20), 		-- Suburb 
        address4  VARCHAR(20),          -- State/area	
	country   VARCHAR(20), 
	courseCode    CHAR(5),                
	phoneNo   CHAR(10),
	startDate  DATE,                  
	birthDate  DATE,                  
	fieldDeposit    DECIMAL(8,2),          
	PRIMARY KEY(stuNo)
);

