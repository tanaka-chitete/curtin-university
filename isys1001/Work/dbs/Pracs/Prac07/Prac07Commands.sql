-- TASK 1
-- Question 8
SOURCE createdept.sql;
SOURCE createemp.sql;
SOURCE createproj.sql;
SOURCE createpworks.sql;
SOURCE insdept.sql;
SOURCE insemp.sql;
SOURCE insproj.sql;
SOURCE inspworks.sql;

-- Question 1
SELECT deptname FROM Dept WHERE deptno IN (SELECT deptno FROM Proj WHERE prstdate IS NOT NULL);

-- Question 2
SELECT Proj.projname, Proj.prstdate, Pworks.hours FROM Proj INNER JOIN Pworks ON (SELECT hours F

SELECT Dept.deptnme FROM Dept JOIN Dept ON Dept.deptno = Proj.deptno WHERE Dept.deptno IN (SELECT COUNT(deptno) as projects, deptno FROM Proj GROUP BY deptno WHERE COUNT(deptno) = 1);
