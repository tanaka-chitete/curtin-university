
/* createtables.sql: MySQL fle for table creation in Practical-3 */

-- create dept table
--
 CREATE TABLE Dept
               (deptno   CHAR(3)        NOT NULL,
                deptname VARCHAR(36)    NOT NULL,
                mgrno    CHAR(6)                ,
                admrdept CHAR(3)        NOT NULL,
        	PRIMARY KEY(DEPTNO)
);

-- create Emp table
--
 CREATE TABLE Emp
               (empno     CHAR(6)	NOT NULL,
                firstname VARCHAR(12)   NOT NULL,
                midinit	  CHAR(1)       NOT NULL,
                lastname  VARCHAR(15)   NOT NULL,
                workdept  CHAR(3)               ,
                phoneno   CHAR(4)               ,
                hiredate  DATE                  ,
                job	  CHAR(10)              ,
                edlevel	  INT(2)              	,
                sex       CHAR(1)               ,
                birthdate DATE                  ,
                salary    DECIMAL(8,2)          ,
                bonus     DECIMAL(8,2)          ,
                comm      DECIMAL(8,2)		,
		PRIMARY KEY(EMPNO)
		
);
