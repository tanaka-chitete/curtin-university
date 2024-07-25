-- MySQL file for creating stored procedures for Assignment

-- Drop procedures
DROP PROCEDURE IF EXISTS insert_country;
DROP PROCEDURE IF EXISTS insert_sport;
DROP PROCEDURE IF EXISTS insert_event;
DROP PROCEDURE IF EXISTS insert_athlete;
DROP PROCEDURE IF EXISTS insert_result;

-- Create insert_country procedure
DELIMITER // 
CREATE PROCEDURE insert_country(
    IN country_pk CHAR(6),
    IN country_name VARCHAR(36)
    )
    COMMENT 'Insert country into countries table'
    BEGIN
        INSERT INTO countries
        (country_pk, country_name) VALUES
        (country_pk, country_name);
    END //
DELIMITER ;

-- Create insert_sport procedure
DELIMITER // 
CREATE PROCEDURE insert_sport(
    IN sport_pk CHAR(6),
    IN sport_name VARCHAR(36)
    )
    COMMENT 'Insert sport into sports table'
    BEGIN
        INSERT INTO sports
        (sport_pk, sport_name) VALUES
        (sport_pk, sport_name);
    END //
DELIMITER ;

-- Create insert_event procedure
DELIMITER // 
CREATE PROCEDURE insert_event(
    IN event_pk CHAR(6),
    IN sport_fk CHAR(6),
    IN gender CHAR(1),
    IN event_name VARCHAR(36),
    IN event_datetime DATETIME,
    IN venue VARCHAR(36)
    )
    COMMENT 'Insert event into events table'
    BEGIN
        INSERT INTO events
        (event_pk, sport_fk, gender, event_name, event_datetime, venue) VALUES
        (event_pk, sport_fk, gender, event_name, event_datetime, venue);
    END //
DELIMITER ;

-- Create insert_athlete procedure
DELIMITER // 
CREATE PROCEDURE insert_athlete(
    IN athlete_pk CHAR(6),
    IN country_fk CHAR(3),
    IN family_name VARCHAR(36),
    IN given_name VARCHAR(36),
    IN gender CHAR(1),
    IN dob DATE,
    IN height INTEGER(3),
    IN athlete_weight INTEGER(3)
    )
    COMMENT 'Insert athlete into athletes table'
    BEGIN
        INSERT INTO athletes
        (athlete_pk, country_fk, family_name, given_name, gender, dob, height, athlete_weight) VALUES
        (athlete_pk, country_fk, family_name, given_name, gender, dob, height, athlete_weight);
    END //
DELIMITER ;

-- Create insert_result procedure
DELIMITER // 
CREATE PROCEDURE insert_result(
    IN event_fk CHAR(6),
    IN athlete_fk CHAR(6),
    IN position INTEGER(1),
    IN medal CHAR(1)
    )
    COMMENT 'Insert result into results table'
    BEGIN
        INSERT INTO results
        (event_fk, athlete_fk, position, medal) VALUES
        (event_fk, athlete_fk, position, medal);
    END //
DELIMITER ;
