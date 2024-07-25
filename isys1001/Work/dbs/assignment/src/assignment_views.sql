-- MySQL file for creating views for Assignment

-- Drop views
DROP VIEW IF EXISTS representatives;
DROP VIEW IF EXISTS winners;

-- Create representatives view
CREATE VIEW representatives AS SELECT country_fk AS country, COUNT(*) AS representatives FROM athletes GROUP BY country_fk;

-- Create winners view
CREATE VIEW winners AS SELECT a.family_name, a.given_name, a.country_fk AS country FROM athletes a INNER JOIN results r ON a.athlete_pk = r.athlete_fk WHERE r.medal = 'G';