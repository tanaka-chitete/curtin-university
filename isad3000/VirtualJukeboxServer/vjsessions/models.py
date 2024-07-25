from django.contrib.gis.db import models
from django.contrib.gis.geos import GEOSGeometry
from geopy.distance import distance
from vjusers.models import User
from django.core.cache import cache
import uuid
import pytz
from datetime import datetime

import logging
logger = logging.getLogger("django")

# default: null=False (NOT NULL)

# Create your models here.
class Session(models.Model):
    # Primary Key - UUID used to avoid leaking data such as total sessions and 
    # session id guessing
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)

    # Foreign keys protected on delete to ensure soft delete only
    host_id = models.ForeignKey(User, on_delete=models.PROTECT, 
        related_name="host_id")
    created_by = models.ForeignKey(User, on_delete=models.PROTECT, 
        related_name="created_by")
    updated_by = models.ForeignKey(User, on_delete=models.PROTECT, 
        related_name="updated_by")

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(null=True)
    is_active = models.BooleanField(default=True)

    is_private = models.BooleanField()
    session_name = models.CharField(max_length=50)
    session_description = models.CharField(max_length=200)
    session_password = models.CharField(max_length=256, null=True, blank=True)
    session_location = models.GeometryField(srid=4326)
    connection_range = models.IntegerField(null=True)


    # VJ Session as a string
    def __str__(self):
        return "ID: " + str(self.id) + " | VJ_SESSION: " + str(self.session_name)


class SessionHelper:

    # Returns true if the user is in a session (either guest or host), false otherwise
    def alreadyInSession(user_id): 
        keys = cache.get_or_set("session_keys", [])
        if not keys:
            return False

        # Use UUID representation of the user id instead of string (if string passed in).
        if isinstance(user_id, str):
            user_id = uuid.UUID(user_id)

        for key in keys: # For each cache key
            object = cache.get(key)
            if object:
                if "guests" in object: # If the key is a VJ session
                    guest_json = object["guests"]
                    if user_id in guest_json: # user is a guest 
                        return True
                    if user_id == object["host"]["id"]: # user is a host
                        return True
        return False


    # Returns true if the user is in the provided session (id). 
    def isInSession(vj_session_id, guest_id):
        session_json = cache.get(vj_session_id)
        if "guests" in session_json: # If the json represents a session
            guest_json = session_json["guests"]
            if uuid.UUID(guest_id) in guest_json: # The guest is in the session 
                return True
            if uuid.UUID(guest_id) == session_json["host"]["id"]:
                return True
        return False


    # Returns true if the guest is in the provided session (id). 
    def isGuest(vj_session_id, guest_id):
        session_json = cache.get(vj_session_id) 
        if "guests" in session_json: # If the json represents a session
            guest_json = session_json["guests"]
            if uuid.UUID(guest_id) in guest_json: # The guest is in the session 
                return True

        return False


    # Returns the VJ session object given a guest user ID.
    def getSessionFromGuest(user_id):
        session_id = None
        keys = cache.get_or_set("session_keys", [])
        if not keys:
            return False

        # Use UUID representation of the user id instead of string (if string passed in).
        if isinstance(user_id, str):
            user_id = uuid.UUID(user_id)

        for key in keys: # For each cache key
            object = cache.get(key)
            if object:
                if "guests" in object: # If the key is a VJ session
                    guest_json = object["guests"]
                    if user_id in guest_json: # user is a guest 
                        session_id = key
                        break

        if session_id:
            return Session.objects.filter(id=session_id, is_active=True)[0]


    # Returns true if the user is a guest in any session.
    def isAGuest(user_id):
        keys = cache.get_or_set("session_keys", [])
        if not keys:
            return False

        # Use UUID representation of the user id instead of string (if string passed in).
        if isinstance(user_id, str):
            user_id = uuid.UUID(user_id)

        for key in keys: # For each cache key
            object = cache.get(key)
            if object:
                if "guests" in object: # If the key is a VJ session
                    guest_json = object["guests"]
                    if user_id in guest_json: # user is a guest 
                        return True
        return False


    # Returns true if the user is a host of any session.
    def isAHost(user_id): 
        keys = cache.get_or_set("session_keys", [])
        if not keys:
            return False

        # Use UUID representation of the user id instead of string (if string passed in).
        if isinstance(user_id, str):
            user_id = uuid.UUID(user_id)

        for key in keys: # For each cache key
            object = cache.get(key)
            if object:
                if "guests" in object: # If the key is a VJ session
                    if user_id == object["host"]["id"]: # user is a host
                        return True
        return False


    # Retrieves a session ID from a user currently in a session.
    def getIdFromUser(user_id):
        keys = cache.get_or_set("session_keys", [])
        if not keys:
            return ""

        # Use UUID representation of the user id instead of string (if string passed in).
        if isinstance(user_id, str):
            user_id = uuid.UUID(user_id)

        for key in keys: # For each cache key
            object = cache.get(key)
            if object:
                if "guests" in object: # If the key is a VJ session
                    guest_json = object["guests"]
                    if user_id in guest_json: # user is a guest 
                        return str(key)
                    if user_id == object["host"]["id"]: # user is a host
                        return str(key)
        return ""


    # Returns true if the user in the session has credits > 0.
    def userHasCredits(vj_session_id, user_id):
        session_json = cache.get(vj_session_id) 
        user_json = session_json["guests"]
        if uuid.UUID(user_id) in user_json: # User is guest
            if user_json[uuid.UUID(user_id)]["credits"] > 0:
                return True
        elif uuid.UUID(user_id) == session_json["host"]["id"]: # User is host
            if session_json["host"]["credits"] > 0:
                return True

        return False


    # Returns true if the song is in the queue in the session.
    def songInQueue(vj_session_id, song_id):
        session_json = cache.get(vj_session_id) 
        
        for song in session_json["song_queue"]:
            if song["song_id"] == song_id:
                return True

        return False


    # Returns true if the song is currenlty playing in the session.
    def songPlaying(vj_session_id, song_id):
        session_json = cache.get(vj_session_id) 

        if session_json["playing"]: # A song is playing
            if session_json["playing"]["song_id"] == song_id:
                return True
        
        return False


    # Returns true if the user has already voted for a song in the session queue.
    def hasVoted(vj_session_id, song_id, user_id):
        session_json = cache.get(vj_session_id)

        for song in session_json["song_queue"]:
            if song["song_id"] == song_id:
                if str(user_id) in song["voted_by"]:
                    return True
                break
        return False



    # Returns true if the song is in the playlist.
    def songInPlaylist(vj_session_id, song_id):
        session_json = cache.get(vj_session_id) 

        if song_id in session_json["playlist"]["songs"]:
            return True

        return False


    # Returns true if the location provided is within the connection range of 
    # the provided VJ session.
    def isInRange(vj_session, location):
        session_location = vj_session.session_location
        session_range = vj_session.connection_range

        pointStr = "POINT(" + \
                        str(location.get("coordinates")[0]) + " " + \
                        str(location.get("coordinates")[1]) + ")"
        location = GEOSGeometry(pointStr, srid=4326)

        # Tuples must be reversed for geopy coordinates: (lat, long)
        sphericalDist = distance(session_location.coords[::-1], location.coords[::-1]).meters

        if sphericalDist <= session_range:
            return True

        return False


    # Queues a song by subtracting 1 credit and adding to queue in the cache.
    def queueSong(vj_session, user_id, song_id, time_added):
        session_json = cache.get(vj_session.id)
        guest_json = session_json["guests"]
        if uuid.UUID(user_id) in guest_json: # User is guest
            guest_json[uuid.UUID(user_id)]["credits"] -= 1

        elif uuid.UUID(user_id) == session_json["host"]["id"]: # User is host
            session_json["host"]["credits"] -= 1

        songObj = {
            "song_id": song_id,
            "votes": 1,
            "time_added": time_added,
            "voted_by": [str(user_id)]
        }

        session_json["song_queue"].append(songObj)
        cache.set(vj_session.id, session_json)


    # Ends a song in the session by removing it from the song queue (cache) and
    # returning it. Returns None is the song is not found.
    def dequeueSong(vj_session, song_id):
        session_json = cache.get(vj_session.id)
        song_obj = None

        if len(session_json["song_queue"]) > 0:
            i = 0
            # Iterate through list of songs
            for song_obj in session_json["song_queue"]:
                if song_obj["song_id"] == song_id:
                    song_obj = session_json["song_queue"].pop(i)
                    break
                i += 1

            cache.set(vj_session.id, session_json)

        return song_obj
        

    # Adds a credit (or "token") to a user (host or guest) in a VJ session.
    def addToken(vj_session, user_id):
        session_json = cache.get(vj_session.id)
        guest_json = session_json["guests"]
        if uuid.UUID(user_id) in guest_json: # User is guest
            guest_json[uuid.UUID(user_id)]["credits"] += 1

        elif uuid.UUID(user_id) == session_json["host"]["id"]: # User is host
            session_json["host"]["credits"] += 1

        cache.set(vj_session.id, session_json)


    # Increases the votes of a song in a song queue by 1
    def upvoteSong(vj_session, song_id, user_id):
        session_json = cache.get(vj_session.id)
        for song in session_json["song_queue"]:
            if song["song_id"] == song_id:
                song["votes"] += 1
                song["voted_by"].append(user_id)
                
        cache.set(vj_session.id, session_json)


    # Adds a newly created VJ session to the cache
    def addSessionToCache(vj_session, spotify_playlist_id):
        keys = cache.get_or_set("session_keys", [])
        keys.append(vj_session.id)
        cache.set("session_keys", keys)

        session_json = {
            "name": vj_session.session_name,
            "host": 
                {
                    "id": vj_session.host_id.id,
                    "username": vj_session.host_id.username,
                    "join_time": datetime.now(pytz.utc),
                    "credits": 3, # Hosts will start with three credits
                },
            # Will remove songs with the playsong API, and add songs with the queuesong API. 
            "song_queue": [], 
            "playing": {}, # Stores the currently playing song. Handled by the playsong API.
            "playlist": {
                "spotify_id": spotify_playlist_id,
                "songs": {}
            },
            "guests": {} # Will store guests in format: <guest_id> : {},
        }

        cache.add(vj_session.id, session_json)


    # Adds a spotify playlist to a VJ session given the response from 
    # retrieve playlist songs API call
    # Returns True if > 0  valid songs are present in the response,
    # and False otherwise 
    def addPlaylistToCache(vj_session, response):
        session_json = cache.get(vj_session.id)
        playlist = response.data

        # Keyerror is unexpected as spotify response is expected to be valid
        try:
            items = playlist["items"]
        except KeyError:
            logger.error("Invalid spotify response when retrieving playlist songs" +
                " | For VJ session: " + str(vj_session))
            return

        cleaned_songs = {}

        for song in playlist["items"]: # For each song in the playlist

            if song["track"]["is_local"]: # Skip considering local tracks
                continue

            if not song["track"]["is_playable"]: # Skip considering unplayable tracks
                continue

            if song["track"]["duration_ms"] <= 0: # Skip zero duration tracks
                continue

            if len(song["track"]["album"]["images"]) < 3: # Skip songs without images
                continue

            artists = []
            images = []
            song_json = {}

            for artist in song["track"]["album"]["artists"]: # For each artist of the album
                artists.append(artist["name"])

            album_name = song["track"]["album"]["name"]
            album_uri = song["track"]["album"]["uri"]
            track_num = song["track"]["track_number"]
            name = song["track"]["name"]
            id = song["track"]["id"]
            
            images.append(song["track"]["album"]["images"][0]["url"])
            images.append(song["track"]["album"]["images"][1]["url"])
            images.append(song["track"]["album"]["images"][2]["url"])

            duration_ms = song["track"]["duration_ms"]

            song_json[id] = {
                "album_uri": album_uri,
                "album_name": album_name,
                "artists": artists,
                "track_num": track_num,
                "duration_ms": duration_ms,
                "name": name,
                "images": images,
            }

            cleaned_songs.update(song_json)

        if len(cleaned_songs) == 0:
            return False

        session_json["playlist"]["songs"] = cleaned_songs
        cache.set(vj_session.id, session_json)

        return True


    # Adds a guest to a VJ session in the cache
    def addGuestToCache(vj_session, guest):
        guest_json = {
                        "username": guest.username,
                        "join_time": datetime.now(pytz.utc),
                        "credits": 0,
                     }

        session_json = cache.get(vj_session.id)
        session_json["guests"][guest.id] = guest_json 

        cache.set(vj_session.id, session_json)


    # Removes a guest from a VJ session in the cache
    def removeGuestFromCache(vj_session, guest):
        session_json = cache.get(vj_session.id)
        session_json["guests"].pop(guest.id)

        cache.set(vj_session.id, session_json)


    # Removes VJ session from the cache
    def removeSession(vj_session):
        # Remove session from cache
        cache.delete(vj_session.id)
        keys = cache.get_or_set("session_keys", [])
        keys.remove(vj_session.id)
        cache.set("session_keys", keys)

        # Remove session from db
        vj_session.is_active = False
        vj_session.deleted_at = datetime.now(pytz.utc)
        vj_session.save()
