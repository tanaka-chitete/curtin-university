# python3 file implementing MySQL connctivity for Assignment

# Adapted from https://pynative.com/python-mysql-database-connection/
# (No author) 
# (Accessed 26 October 2021)

# Adapted from C. Baweja, 
# https://realpython.com/python-mysql/
# (Accessed 26 October 2021)

import mysql.connector
from getpass import getpass

try:
    # define the connection 
    conn = mysql.connector.connect(
                host="localhost",
                user=input("Enter username: "),
                password=getpass("Enter password: "), 
                database="assignment_20169321"
                )
    if conn.is_connected():
        # Create a Cursor object to execute queries
        cur = conn.cursor()
        # show the database you are connected to 
        cur.execute("SELECT DATABASE();")
        record = cur.fetchone()
        print("You're connected to database: ", record)
        
        # Q1. Show the number of athletes representing each country. Format results appropriately.
        print("Q1. Show the number of athletes representing each country. Format results appropriately.")
        stmt = "SELECT country_fk AS country, COUNT(*) AS 'count' FROM athletes GROUP BY country_fk;"
        cur.execute(stmt)
        for row in cur:
            print(f"country: {row[0]}, count: {row[1]}")

        # Q2. Show all participating countries ordered by name (in the form "<name> (<code>)"). Format results appropriately.
        print('Q2. Show all participating countries ordered by name (in the form "<name> (<code>)"). Format results appropriately.')
        stmt = "SELECT CONCAT(country_name, ' (', country_pk, ')') AS country_listing FROM countries ORDER BY country_name DESC;"
        cur.execute(stmt)
        for row in cur:
            print(f"country: {row[0]}")

        # Q3. Show the age of the average American athlete. Format results appropriately.
        print("Q3. Show the age of the average American athlete. Format results appropriately.")
        stmt = "SELECT ROUND(AVG(DATEDIFF('2021-07-23', dob) / 365.25)) AS average_age FROM athletes WHERE country_fk='USA';"
        cur.execute(stmt)
        print(f"age: {int(cur.fetchone()[0])}")

        # Q4. Show the names and nationalities of all athletes who won a gold medal (in the form "<family_name>, <given_name> (<nationality>)"). Format results appropriately.
        print('Q4. Show the names and nationalities of all athletes who won a gold medal (in the form "<family_name>, <given_name> (<nationality>)"). Format results appropriately.')
        stmt = "SELECT CONCAT(a.family_name, ', ', given_name, ' (', a.country_fk, ')') AS gold_medalists FROM athletes a INNER JOIN results r ON a.athlete_pk = r.athlete_fk WHERE r.medal = 'G';"
        cur.execute(stmt)
        for row in cur:
            print(f"athlete: {row[0]}")

        # Q5. Show the height of the average athlete. Format results appropriately.
        print("Q5. Show the height of the average athlete. Format results appropriately.")
        stmt = "SELECT ROUND(AVG(height)) as avg_height FROM athletes;"
        cur.execute(stmt)
        print(f"height: {int(cur.fetchone()[0])}")

        # Q6. Show the names and nationalities of all athletes who are shorter than than the average athlete (in the form "<family_name> (<nationality>)"). Format results appropriately. 
        print('Q6. Show the names and nationalities of all athletes who are shorter than than the average athlete (in the form "<family_name> (<nationality>)"). Format results appropriately.')
        stmt = "SELECT CONCAT(family_name, ', ', given_name, ' (', country_fk, ')') AS athletes FROM athletes WHERE height < (SELECT AVG(height) FROM athletes);"
        cur.execute(stmt)
        for row in cur:
            print(f"athlete: {row[0]}")

except mysql.connector.Error as err:
    print("Error while connecting to MySQL", err)   

finally:
    if conn.is_connected():
        cur.close()
        conn.close()
        print("MySQL connection is closed now")
