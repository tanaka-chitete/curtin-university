# Django Backend for the Virtual Jukebox Application

## Development Setup
1. Create a .env file based off of <i>example.env</i> (Used to create database (+user) and redis cache)
2. Ensure DEBUG = True in settings.py
3. In the directory with the <i>docker-compose.yml</i> file, run:
    ```sh
    docker compose up -d
    ```
    (Note: will start all containers and run the django server)
4. In a separate terminal, run 
   ```sh
   docker exec vj-django-server python manage.py makemigrations
   docker exec vj-django-server python manage.py migrate
   ```
### Environment Variable File Help: 
  - After creating a ".env" file, copy the contents of "example.env" to it and alter the following:
    - database info variables (will create a database with a user specified)
    - redis info variables (will assign a password to the redis cache)
    - allowed hosts (list of hosts that may host the Django server)
    - logging variables (optional, leave commented for defaults)
    - django secret key
    - spotify info variables (redirect URI = frontend URL, client ID/secret = Spotify app data)
## Development Environment
- To access any container's virtual filesystem through a terminal (after docker compose up):
    ```sh
    docker exec -it <container_id/name> bash
    ```
    (Note: run 'docker ps' to see containers)

- To access PostgreSQL database in CLI:
    ```sh
    docker exec -it db bash    
    su <postgres_username>
    psql
    ```
- To access the Redis cache via CLI:
    ```sh
    docker exec -it redis bash
    redis-cli 
    AUTH <redis_password>
    ```
- To run Django manage.py commands (e.g. makemigrations):
    ```sh
    docker exec vj-django-server <command_here>
    ```

- To download new packages (in requirements.txt) without rebuilding image:
    ```sh
    docker exec vj-django-server pip install -r requirements.txt
    ```

- To run unit test cases:
    ```sh
    docker exec vj-django-server python manage.py test
    ```

### Background tasks (Cron):
- Two Cron jobs are setup to clear inactive users (not in session and no actions for a day), and to clear inactive sessions (playsong not called for 3 hours, or host inactive).
- These background tasks are run every hour and every minute respectively
- These background tasks start once the server Docker image is run (but can be stopped and run manually).
- To stop all background cron tasks, run:
    ```sh
    docker exec vj-django-server python manage.py crontab remove
    ```
- To start all background cron tasks, run:
    ```sh
    docker exec vj-django-server python manage.py crontab add
    ```
- To show all background cron tasks, run:
    ```sh
    docker exec vj-django-server python manage.py crontab show
    ```

### Important Spotify Notes:
  - A Spotify (developer) app is required for hosting Virtual Jukebox sessions (https://developer.spotify.com/dashboard/)
  - Provide the app credentials (Client secret and Client ID) in the .env file, provided by the Spotify developer app.
  - The redirect URI must also be added to the Spotify app via the link above.
  - Spotify user emails must be added 
  - Until a Spotify quota extension is requested and approved, Spotify users must be manually added to the developer app (via email) to enable Virtual Jukebox session hosting, and is limited to 25 users. 

## Production
### Production Deployment Checklist:
  - 'docker compose down' previously deployed release containers (front and backend)
  - Remove Docker volumes (Redis and Postgis) if you wish to use a new database.
  - Remove Docker images (front and backend) for a fresh install (or if code has changed)
  - Add .env to backend root folder if not already, and fill in values.
  - Set DEBUG = False in settings.py
  - Provide the server-side Django SECRET KEY .env (Set .env secret key as the one generated)
  - Run the migrate command for Django database integration. 
  - Ensure Nginx is configured as reverse proxy
  - 'docker compose up -d' for backend and frontend

## Accessing and Configuring Logs
- Django logs will be stored in the directory: ./logs/
- Optionally specify the following in .env as <DEBUG/INFO/WARNING/ERROR/CRITICAL>:
  
  - LOG_LEVEL_CONSOLE (defaults to INFO)
  - LOG_LEVEL_DEBUG (defaults to DEBUG)
  - LOG_LEVEL_PRODUCTION (defaults to WARNING)
  
  Note: Log levels will include itself and all higher levels of logs.
- File: <i>./logs/console.log</i> will have the same level as console logs.
- Files: <i>./logs/console.log</i> AND <i>./logs/debug.log</i> will not be populated during production.
- To show logs of a docker container, run: 
    ```sh
    docker logs <containerID/name>
    ```
- To follow live logs of a docker container, run: 
    ```sh
    docker logs --follow <containerID/name>
    ```