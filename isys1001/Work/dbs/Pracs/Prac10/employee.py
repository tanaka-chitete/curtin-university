# Base code is taken from https://pynative.com/python-mysql-database-connection/ and 
#                      https://realpython.com/python-mysql/

# MySQL connctivity with pythom3 

import mysql.connector
from getpass import getpass

try:
    # define the connection 
    conn = mysql.connector.connect(
                host="localhost",
                user=input("Enter username: "),
                password=getpass("Enter password: "), 
                database="company"
                )
    if conn.is_connected():
        # Create a Cursor object to execute queries
        cur = conn.cursor()
        # show the database you are conencted to 
        cur.execute("SELECT DATABASE();")
        record = cur.fetchone()
        print("You're connected to database: ", record)
        
        # create a query to select data
	
except mysql.connector.Error as err:
    print("Error while connecting to MySQL", err)   

finally:
    if conn.is_connected():
        cur.close()
        conn.close()
        print("MySQL connection is closed now")
