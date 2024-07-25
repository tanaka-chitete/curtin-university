# syntax=docker/dockerfile:1
FROM python:3

# Quality of life python environment settings
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# Make /vjserver the working directory for next commands
WORKDIR /vjserver

# Copy requirements.txt file to /vjserver (image)
COPY requirements.txt requirements.txt

# Install requirements.txt packages (image)
RUN pip install -r requirements.txt

# GDAL required for 'django.contrib.gis' django app
RUN apt-get update &&\
    apt-get install -y gdal-bin

# Cron required for crontab jobs in Django
RUN apt-get install -y cron

# wihout Daphne: python manage.py runserver 0.0.0.0:8000
CMD python manage.py crontab add && service cron start && daphne -b 0.0.0.0 -p 8000 server.asgi:application
    