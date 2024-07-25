-- MySQL file for inserting values into tables for Assignment

-- Insert values into countries table
CALL insert_country
('SWE', 'Sweden');
CALL insert_country
('USA', 'United States');
CALL insert_country
('AUS', 'Australia');
CALL insert_country
('ZAM', 'Zambia');
CALL insert_country
('JPN', 'Japan');

-- Insert values into sports table
CALL insert_sport
('482301', '3x3 Basketball');
CALL insert_sport
('328931', 'Swimming');
CALL insert_sport
('673480', 'Shooting');
CALL insert_sport
('938031', 'Boxing');

-- Insert values into events table
CALL insert_event
('937802', '482301', 'M', "Men's Final: Australia vs USA", '2021-07-26 10:00:00', 'Miyagi Stadium');
CALL insert_event
('274100', '328931', 'W', "Women's 100m Butterfly Final", '2021-07-27 11:30:00', 'Tokyo Aquatics Centre');
CALL insert_event
('318402', '673480', 'M', "Men's Skeet Second Round", '2021-07-27 15:45:00', 'Asaka Shooting Range');
CALL insert_event
('936103', '673480', 'M', "Men's Skeet Final Round", '2021-07-28 15:45:00', 'Asaka Shooting Range');
CALL insert_event
('371121', '938031', 'M', "Winwood vs Chinyemba", '2021-07-28 18:00:00', 'Kokugikan Arena');
CALL insert_event
('345235', '123819', 'W', "Men's 100m Butterfly Final", '2021-07-29 18:00:00', 'Kokugikan Arena'); -- INVALID: arg1 (sport_fk) doesn't exist

-- Insert values into athletes table
CALL insert_athlete
('383131', 'AUS', 'BEHICH', 'Aziz', 'M', '1990-12-16', 170, 63);
CALL insert_athlete
('582322', 'AUS', 'KARACIC', 'Fran', 'M', '1996-05-12', 181, 75);
CALL insert_athlete
('839111', 'AUS', 'MCGOWAN', 'Ryan', 'M', '1989-08-15', 191, 75);
CALL insert_athlete
('398012', 'AUS', 'WRIGHT', 'Bailey', 'M', '1992-08-15', 186, 84);
CALL insert_athlete
('389121', 'USA', 'RYAN', 'Mat', 'M', '1992-08-04', 184, 82);
CALL insert_athlete
('193432', 'USA', 'GRANT', 'Rhyan', 'M', '1991-08-01', 173, 72);
CALL insert_athlete
('839198', 'USA', 'ELDER', 'Steve', 'M', '1995-01-01', 180, 67);
CALL insert_athlete
('547232', 'USA', 'BOYLE', 'Martin', 'M', '1993-04-23', 191, 75);
CALL insert_athlete
('193131', 'USA', 'AALTEN', 'Cornelia', 'W', '1999-12-28', 172, 48); 
CALL insert_athlete
('281921', 'ZAM', 'MAPFUMO', 'Lucy', 'W', '2000-04-06', 170, 47); 
CALL insert_athlete
('193821', 'JPN', 'AOKI', 'Kushina', 'W', '1998-06-06', 173, 48); 
CALL insert_athlete
('481640', 'USA', 'MALTO', 'Stephanie', 'W', '1997-03-03', 171, 47); 
CALL insert_athlete
('029145', 'SWE', 'AALTONEN', 'Paavo', 'M', '1983-12-04', 180, 75); 
CALL insert_athlete
('182321', 'SWE', 'AARBERG', 'Jan-Erik', 'M', '1985-07-09', 182, 78);
CALL insert_athlete
('193021', 'USA', 'ABBOTT', 'Jeremy', 'M', '1991-03-23', 173, 72);
CALL insert_athlete
('283191', 'AUS', 'SCOTT', 'Lachlan', 'M', '1992-08-21', 177, 75);
CALL insert_athlete
('390112', 'USA', 'DUKE', 'James', 'M', '1992-08-21', 174, 74);
CALL insert_athlete
('489231', 'AUS', 'CHRIS', 'David', 'M', '1990-01-11', 171, 73);
CALL insert_athlete
('115612', 'ZAM', 'CHINYEMBA', 'Patrick', 'M', '2001-07-28', 172, 61); 
CALL insert_athlete
('381022', 'AUS', 'WINWOOD', 'Alex', 'M', '1997-02-02', 174, 60);
CALL insert_athlete
('452252', 'BRA', 'SILVA', 'Thiago', 'M', '1994-03-01', 182, 85); -- INVALID: arg1 (country_fk) doesn't exist

-- Insert values into results table
CALL insert_result
('937802', '383131', 1, 'G');
CALL insert_result
('937802', '582322', 1, 'G');
CALL insert_result
('937802', '839111', 1, 'G');
CALL insert_result
('937802', '398012', 1, 'G');
CALL insert_result
('937802', '389121', 2, 'S');
CALL insert_result
('937802', '193021', 2, 'S');
CALL insert_result
('937802', '839198', 2, 'S');
CALL insert_result
('937802', '547232', 2, 'S');
CALL insert_result
('274100', '193131', 1, 'G');
CALL insert_result
('274100', '281921', 2, 'S');
CALL insert_result
('274100', '193821', 3, 'B');
CALL insert_result
('274100', '481640', 4, NULL);
CALL insert_result
('318402', '029145', 1, NULL);
CALL insert_result
('318402', '182321', 2, NULL);
CALL insert_result
('318402', '193021', 3, NULL);
CALL insert_result
('318402', '283191', 4, NULL);
CALL insert_result
('318402', '390112', 5, NULL);
CALL insert_result
('318402', '489231', 6, NULL);
CALL insert_result
('936103', '029145', 1, 'G');
CALL insert_result
('936103', '182321', 2, 'S');
CALL insert_result
('936103', '193021', 3, 'B');
CALL insert_result
('371121', '115612', 1, 'G');
CALL insert_result
('371121', '381022', 2, NULL);
CALL insert_result
('342323', '453452', 1, 'G'); -- INVALID: arg0 (athlete_fk) doesn't exist
