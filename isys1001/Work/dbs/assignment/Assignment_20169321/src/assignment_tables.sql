-- MySQL file for creating tables for Assignment

-- Drop tables
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS sports;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS athletes;
DROP TABLE IF EXISTS results;
SET FOREIGN_KEY_CHECKS = 1;

-- Create countries table
CREATE TABLE countries(
	country_pk CHAR(3),
	country_name VARCHAR(36) NOT NULL UNIQUE,
	PRIMARY KEY(country_pk)
);

-- Create sports table
CREATE TABLE sports(
	sport_pk CHAR(6),
	sport_name VARCHAR(36) NOT NULL UNIQUE,
	PRIMARY KEY(sport_pk)
);

-- Create events table
CREATE TABLE events(
	event_pk CHAR(6),
	sport_fk CHAR(6) NOT NULL,
	gender CHAR(1),
	event_name VARCHAR(36) NOT NULL UNIQUE,
	event_datetime DATETIME NOT NULL,
	venue VARCHAR(36) NOT NULL,
	PRIMARY KEY(event_pk),
	FOREIGN KEY(sport_fk) REFERENCES sports(sport_pk)
);

-- Create athletes table
CREATE TABLE athletes(
	athlete_pk CHAR(6),
	country_fk CHAR(3) NOT NULL,
	family_name VARCHAR(36) NOT NULL,
	given_name VARCHAR(36) NOT NULL,
	gender CHAR(1) NOT NULL,
	dob DATE NOT NULL,
	height INTEGER(3) NOT NULL,
	athlete_weight INTEGER(3) NOT NULL,
	PRIMARY KEY(athlete_pk),
	FOREIGN KEY(country_fk) REFERENCES countries(country_pk)
);

-- Create results table
CREATE TABLE results(
	event_fk CHAR(6),
	athlete_fk CHAR(6),
	position INTEGER(1) NOT NULL,
	medal CHAR(1),
	PRIMARY KEY(event_fk, athlete_fk),
	FOREIGN KEY(event_fk) REFERENCES events(event_pk),
	FOREIGN KEY(athlete_fk) REFERENCES athletes(athlete_pk)
);
