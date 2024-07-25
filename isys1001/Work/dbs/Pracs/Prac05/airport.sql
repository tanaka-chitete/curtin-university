/*
STUDENT_ID: 20169321
NAME: TANAKA CHITETE
*/

CREATE TABLE Gate(
        gateno CHAR(3) PRIMARY KEY,
        comno CHAR(3) NOT NULL
);

CREATE TABLE Flight(
        flightno CHAR(3) PRIMARY KEY,
       	depdatetime DATETIME NOT NULL,
       	origin CHAR(3) NOT NULL,
	dest CHAR(3) NOT NULL,
        gateno CHAR(3),
        CONSTRAINT fk_gateno
        FOREIGN KEY (gateno) REFERENCES Gate(gateno)
);

CREATE TABLE Seat(
        seatno CHAR(3) PRIMARY KEY,
        class CHAR(1) NOT NULL,
        flightno CHAR(3),
        CONSTRAINT fk_flightno
        FOREIGN KEY (flightno) REFERENCES Flight(flightno)
);

CREATE TABLE DepartureKeys(
	flightno CHAR(3),
	gateno CHAR(3),
	PRIMARY KEY (flightno, gateno)
);

CREATE TABLE DepartureInfo(
	flightno CHAR(3) PRIMARY KEY,
	depdatetime DATETIME NOT NULL
);

CREATE TABLE CheckInKeys(
	ticketno CHAR(3),
	seatno CHAR(3),
	PRIMARY KEY (ticketno, seatno)
);

CREATE TABLE CheckInDetails(
	ticketno CHAR(3) PRIMARY KEY,
	numbags INT(1) NOT NULL, 
	cidatetime DATETIME NOT NULL
);
