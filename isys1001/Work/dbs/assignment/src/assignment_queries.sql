-- MySQL file for querying tables for Assignment

-- The following questions and associated queries are in regards to the Tokyo 2020 Summer Olympics.

-- Q1. Show the number of athletes representing each country. Format results appropriately.
SELECT country_fk AS country, COUNT(*) AS 'count' FROM athletes GROUP BY country_fk;

-- Q2. Show all participating countries ordered by name (in the form "<name> (<code>)"). Format results appropriately.
SELECT CONCAT(country_name, ' (', country_pk, ')') AS country_listing FROM countries ORDER BY country_name DESC;

-- Q3. Show the age of the average American athlete. Format results appropriately.
SELECT ROUND(AVG(DATEDIFF('2021-07-23', dob) / 365.25)) AS average_age FROM athletes WHERE country_fk='USA';

-- Q4. Show the names and nationalities of all gold medalists (in the form "<family_name>, <given_name> (<nationality>)"). Format results appropriately.
SELECT CONCAT(a.family_name, ', ', given_name, ' (', a.country_fk, ')') AS gold_medalists FROM athletes a INNER JOIN results r ON a.athlete_pk = r.athlete_fk WHERE r.medal = 'G';

-- Q5. Show the height of the average athlete. Format results appropriately.
SELECT ROUND(AVG(height)) as avg_height FROM athletes;

-- Q6. Show the names and nationalities of all athletes shorter than than the average athlete (in the form "<family_name> (<nationality>)"). Format results appropriately. 
SELECT CONCAT(family_name, ', ', given_name, ' (', country_fk, ')') AS athletes FROM athletes WHERE height < (SELECT AVG(height) FROM athletes); 
