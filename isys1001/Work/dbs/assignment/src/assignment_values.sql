-- MySQL file for inserting values into tables for Assignment

-- Insert values into countries table
INSERT INTO countries VALUES
('SWE', 'Sweden');
INSERT INTO countries VALUES
('USA', 'United States');
INSERT INTO countries VALUES
('AUS', 'Australia');
INSERT INTO countries VALUES
('ZAM', 'Zambia');
INSERT INTO countries VALUES
('JPN', 'Japan');

-- Insert values into sports table
INSERT INTO sports VALUES
('482301', '3x3 Basketball');
INSERT INTO sports VALUES
('328931', 'Swimming');
INSERT INTO sports VALUES
('673480', 'Shooting');
INSERT INTO sports VALUES
('938031', 'Boxing');

-- Insert values into events table
INSERT INTO events VALUES
('937802', '482301', 'M', "Men's Final: Australia vs USA", '2021-07-26 10:00:00', 'Miyagi Stadium');
INSERT INTO events VALUES
('274100', '328931', 'W', "Women's 100m Butterfly Final", '2021-07-27 11:30:00', 'Tokyo Aquatics Centre');
INSERT INTO events VALUES
('318402', '673480', 'M', "Men's Skeet Second Round", '2021-07-27 15:45:00', 'Asaka Shooting Range');
INSERT INTO events VALUES
('936103', '673480', 'M', "Men's Skeet Final Round", '2021-07-28 15:45:00', 'Asaka Shooting Range');
INSERT INTO events VALUES
('371121', '938031', 'M', "Winwood vs Chinyemba", '2021-07-28 18:00:00', 'Kokugikan Arena');
INSERT INTO events VALUES
('345235', '123819', 'W', "Men's 100m Butterfly Final", '2021-07-29 18:00:00', 'Kokugikan Arena'); -- INVALID: arg1 (sport_fk) doesn't exist

-- Insert values into athletes table
INSERT INTO athletes VALUES
('383131', 'AUS', 'BEHICH', 'Aziz', 'M', '1990-12-16', 170, 63);
INSERT INTO athletes VALUES
('582322', 'AUS', 'KARACIC', 'Fran', 'M', '1996-05-12', 181, 75);
INSERT INTO athletes VALUES
('839111', 'AUS', 'MCGOWAN', 'Ryan', 'M', '1989-08-15', 191, 75);
INSERT INTO athletes VALUES
('398012', 'AUS', 'WRIGHT', 'Bailey', 'M', '1992-08-15', 186, 84);
INSERT INTO athletes VALUES
('389121', 'USA', 'RYAN', 'Mat', 'M', '1992-08-04', 184, 82);
INSERT INTO athletes VALUES
('193432', 'USA', 'GRANT', 'Rhyan', 'M', '1991-08-01', 173, 72);
INSERT INTO athletes VALUES
('839198', 'USA', 'ELDER', 'Steve', 'M', '1995-01-01', 180, 67);
INSERT INTO athletes VALUES
('547232', 'USA', 'BOYLE', 'Martin', 'M', '1993-04-23', 191, 75);
INSERT INTO athletes VALUES
('193131', 'USA', 'AALTEN', 'Cornelia', 'W', '1999-12-28', 172, 48); 
INSERT INTO athletes VALUES
('281921', 'ZAM', 'MAPFUMO', 'Lucy', 'W', '2000-04-06', 170, 47); 
INSERT INTO athletes VALUES
('193821', 'JPN', 'AOKI', 'Kushina', 'W', '1998-06-06', 173, 48); 
INSERT INTO athletes VALUES
('481640', 'USA', 'MALTO', 'Stephanie', 'W', '1997-03-03', 171, 47); 
INSERT INTO athletes VALUES
('029145', 'SWE', 'AALTONEN', 'Paavo', 'M', '1983-12-04', 180, 75); 
INSERT INTO athletes VALUES
('182321', 'SWE', 'AARBERG', 'Jan-Erik', 'M', '1985-07-09', 182, 78);
INSERT INTO athletes VALUES
('193021', 'USA', 'ABBOTT', 'Jeremy', 'M', '1991-03-23', 173, 72);
INSERT INTO athletes VALUES
('283191', 'AUS', 'SCOTT', 'Lachlan', 'M', '1992-08-21', 177, 75);
INSERT INTO athletes VALUES
('390112', 'USA', 'DUKE', 'James', 'M', '1992-08-21', 174, 74);
INSERT INTO athletes VALUES
('489231', 'AUS', 'CHRIS', 'David', 'M', '1990-01-11', 171, 73);
INSERT INTO athletes VALUES
('115612', 'ZAM', 'CHINYEMBA', 'Patrick', 'M', '2001-07-28', 172, 61); 
INSERT INTO athletes VALUES
('381022', 'AUS', 'WINWOOD', 'Alex', 'M', '1997-02-02', 174, 60);
INSERT INTO athletes VALUES
('452252', 'BRA', 'SILVA', 'Thiago', 'M', '1994-03-01', 182, 85); -- INVALID: arg1 (country_fk) doesn't exist

-- Insert values into results table
INSERT INTO results VALUES
('937802', '383131', 1, 'G');
INSERT INTO results VALUES
('937802', '582322', 1, 'G');
INSERT INTO results VALUES
('937802', '839111', 1, 'G');
INSERT INTO results VALUES
('937802', '398012', 1, 'G');
INSERT INTO results VALUES
('937802', '389121', 2, 'S');
INSERT INTO results VALUES
('937802', '193021', 2, 'S');
INSERT INTO results VALUES
('937802', '839198', 2, 'S');
INSERT INTO results VALUES
('937802', '547232', 2, 'S');
INSERT INTO results VALUES
('274100', '193131', 1, 'G');
INSERT INTO results VALUES
('274100', '281921', 2, 'S');
INSERT INTO results VALUES
('274100', '193821', 3, 'B');
INSERT INTO results VALUES
('274100', '481640', 4, NULL);
INSERT INTO results VALUES
('318402', '029145', 1, NULL);
INSERT INTO results VALUES
('318402', '182321', 2, NULL);
INSERT INTO results VALUES
('318402', '193021', 3, NULL);
INSERT INTO results VALUES
('318402', '283191', 4, NULL);
INSERT INTO results VALUES
('318402', '390112', 5, NULL);
INSERT INTO results VALUES
('318402', '489231', 6, NULL);
INSERT INTO results VALUES
('936103', '029145', 1, 'G');
INSERT INTO results VALUES
('936103', '182321', 2, 'S');
INSERT INTO results VALUES
('936103', '193021', 3, 'B');
INSERT INTO results VALUES
('371121', '115612', 1, 'G');
INSERT INTO results VALUES
('371121', '381022', 2, NULL);
INSERT INTO results VALUES
('342323', '453452', 1, 'G'); -- INVALID: arg0 (athlete_fk) doesn't exist
