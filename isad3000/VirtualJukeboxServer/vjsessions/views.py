from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from django.contrib.auth.hashers import make_password, check_password
from rest_framework import generics
from django.core.cache import cache
from spotify.views import RetrievePlaylistSongs
from django.http import HttpRequest
from .serializers import CreateSessionSerializer, LeaveSessionSerializer, \
    SessionSerializer, CloseSessionSerializer, JoinSessionSerializer, \
    SongInteractionSerializer, SessionUserSerializer, PlaySongSerializer, \
    QueueSongSerializer
from django.core.exceptions import ValidationError
from .models import Session, SessionHelper, User
from datetime import datetime
from django.contrib.gis.geos import GEOSGeometry
from geopy.distance import distance
import pytz
import logging
import sys

testing = len(sys.argv) > 1 and sys.argv[1] == 'test' # Indicates whether in unit tests
logger = logging.getLogger("django")

# Create your views here.
class SessionView(generics.ListAPIView):
    """
    Retrieves all VJ Sessions that are active and public
    URL (development): 127.0.0.1:8000/vjsession/public/
    """

    serializer_class = SessionSerializer
    queryset = Session.objects.filter(is_active=True, is_private=False)

    def getSessionJson(vj_session):
        session_info = {
            "session" : SessionSerializer(vj_session).data,
            "playlist_songs" : cache.get(vj_session.id)["playlist"]["songs"],
            "queue" :  cache.get(vj_session.id)["song_queue"],
            "playing" : cache.get(vj_session.id)["playing"]
        }
        return session_info

    def getSessionUser(vj_session, user):
        if SessionHelper.isGuest(vj_session.id, str(user.id)): # User is guest
            session_info = {
                "session" : SessionSerializer(vj_session).data,
                "playlist_songs" : cache.get(vj_session.id)["playlist"]["songs"],
                "queue" :  cache.get(vj_session.id)["song_queue"],
                "session_user" : cache.get(vj_session.id)["guests"][user.id],
                "playing" : cache.get(vj_session.id)["playing"]
            }
        else: # Must be host if not guest
            session_info = {
                "session" : SessionSerializer(vj_session).data,
                "playlist_songs" : cache.get(vj_session.id)["playlist"]["songs"],
                "queue" :  cache.get(vj_session.id)["song_queue"],
                "session_user" : cache.get(vj_session.id)["host"],
                "playing" : cache.get(vj_session.id)["playing"]
            }
        return session_info



class RetrieveSessionView(APIView):
    """
    Retrieves a session that is active via its ID (uuid)
    URL (development): 127.0.0.1:8000/vjsession/get/<uuid>/
    """

    def get(self, request, id, format=None):
        # Check for exception as to whether id is a UUID.
        try:
            queryset = Session.objects.filter(id=id, is_active=True)
        except ValidationError:
            logger.error("404 GET - Request for session not found (non UUID provided: " + 
                str(id) + ").")
            return Response({"Error": "Session id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)

        if queryset.exists(): # If an active session with the provided ID exists
            vj_session = queryset[0] 
            logger.info("200 GET - VJ session successfully retrieved: " + str(vj_session))
            return Response(SessionView.getSessionJson(vj_session), 
                status=status.HTTP_200_OK)
                
        else: # If the provided id isn"t assigned to an active session
            logger.error("404 GET - Request for session not found (UUID provided: " + 
                str(id) + ").")
            return Response({"Error": "Session id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)



class CreateSessionView(APIView):
    """
    Creates a VJ Session and adds it to the database and cache
    URL (development): 127.0.0.1:8000/vjsession/create/
    """

    serializer_class = CreateSessionSerializer

    def post(self, request, format=None):

        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            host_id = serializer.data.get("host_id")
            is_private = serializer.data.get("is_private")
            session_name = serializer.data.get("session_name")
            session_password = serializer.data.get("session_password")
            session_location = serializer.data.get("session_location")
            session_description = serializer.data.get("session_description")
            connection_range = serializer.data.get("connection_range")

            spotify_playlist_id = serializer.data.get("spotify_playlist_id")
            access_token = serializer.data.get("access_token")

            # Get stored host object from host id
            host_obj = User.objects.get(id=host_id)
            queryset_hosted = Session.objects.filter(host_id=host_obj, is_active=True)
            queryset_name = Session.objects.filter(session_name=session_name, is_active=True)

            if queryset_hosted.exists(): # If the host already has an active session
                # Already-created status with the current VJ session returned
                logger.error("409 POST - Create VJ session failed: host '" + 
                    str(host_obj) + "' already hosting.")
                return Response({"Error": "Already hosting a session."}, 
                    status=status.HTTP_409_CONFLICT)
            
            if SessionHelper.isAGuest(host_id): # If the host is a guest in a session.
               # Already a guest status returned. 
                logger.error("409 POST - Create VJ session failed: user '" + 
                    str(host_obj) + "' is a guest in a session.")
                return Response({"Error": "Already a guest."}, 
                    status=status.HTTP_409_CONFLICT)

            if queryset_name.exists(): # If a session already has the provided name
                logger.error("409 POST - Create VJ session failed: name '" + 
                    str(session_name) + "' already taken.")
                return Response({"Error": "Session name already taken."}, 
                    status=status.HTTP_409_CONFLICT)

            if is_private:
                session_password = make_password(session_password, salt=None, hasher="default")

            # Convert type and coordinates to POINT() string readable by 
            # GeometryField()
            location = "POINT(" + \
                        str(session_location.get("coordinates")[0]) + " " + \
                        str(session_location.get("coordinates")[1]) + ")"
                        
            vj_session = Session(
                host_id=host_obj, 
                created_by=host_obj,
                updated_by=host_obj,
                is_private=is_private, 
                session_name=session_name, 
                session_description=session_description,
                session_password=session_password, 
                session_location=location,
                connection_range=connection_range
            )

            songReq = HttpRequest()
            songReq.method = "GET"
            songReq.GET["playlist_id"] = spotify_playlist_id
            songReq.GET["access_token"] = access_token

            response = RetrievePlaylistSongs.as_view()(request=songReq)

            SessionHelper.addSessionToCache(vj_session, spotify_playlist_id)
            validPlaylist = SessionHelper.addPlaylistToCache(vj_session, response)

            # If unit testing. refrain from checking for valid playlists 
            # (as this requires Spotify account tokens)
            if not testing: 
                if not validPlaylist:
                    # Remove session from cache
                    cache.delete(vj_session.id)
                    keys = cache.get_or_set("session_keys", [])
                    keys.remove(vj_session.id)
                    cache.set("session_keys", keys)

                    logger.error("404 POST - Create VJ session failed: '" + 
                        str(session_name) + "' : No valid songs in playlist.")
                    return Response({"Error": "No valid songs in playlist."}, 
                        status=status.HTTP_404_NOT_FOUND)
            
            vj_session.save() 
            host_obj.save()

            logger.info("201 POST - VJ session successfully created: " + str(vj_session))
            return Response(SessionView.getSessionJson(vj_session), 
                status=status.HTTP_201_CREATED)
                
        else:
            logger.error("400 POST - Bad request for create VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)



class CloseUserSessionView(APIView):
    """
    Closes a VJ session given a host ID if they are hosting a session.
    URL (development): 127.0.0.1:8000/vjsession/close/
    """

    serializer_class = CloseSessionSerializer

    def delete(self, request, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format

            host_id = serializer.data.get("host_id")
            queryset_host = User.objects.filter(id=host_id, is_active=True)

            if queryset_host.exists(): # If the host ID maps to a user
                queryset_hosted = Session.objects.filter(host_id=queryset_host[0], is_active=True)

                if queryset_hosted.exists(): # If host is hosting a session
                    vj_session = queryset_hosted[0]
                    host = queryset_host[0]
                    
                    host.save()
                    SessionHelper.removeSession(vj_session)

                    logger.info("200 DELETE - VJ session closed: " + str(vj_session))
                    return Response({"VJ Session ended": str(id)}, 
                        status=status.HTTP_200_OK)

                else:
                    logger.error("404 DELETE - close VJ session failed: user " + 
                        str(host_id) + " is not hosting a session.")
                    return Response({"Error": "Host id provided is not hosting a session."}, 
                        status=status.HTTP_404_NOT_FOUND)

            else:
                logger.error("404 DELETE - close VJ session failed: host id " + 
                    str(host_id) + " not found.")
                return Response({"Error": "Host id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

        else:
            logger.error("400 DELETE - Bad request for close VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)
        


class LeaveUserSessionView(APIView):
    """
    Leaves a VJ session given a guest ID if they are in a session.
    URL (development): 127.0.0.1:8000/vjsession/leave/
    """

    serializer_class = LeaveSessionSerializer
    def put(self, request, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format

            guest_id = serializer.data.get("guest_id")
            queryset_guest = User.objects.filter(id=guest_id, is_active=True)

            if queryset_guest.exists(): # If the guest ID maps to a user

                if SessionHelper.isAGuest(guest_id): # If the user is in a session as a guest
                
                    vj_session = SessionHelper.getSessionFromGuest(guest_id)
                    guest = queryset_guest[0]
                    guest.save()

                    # Remove guest from the cache
                    SessionHelper.removeGuestFromCache(vj_session, guest)

                    logger.info("200 PUT - User '" + str(guest) + 
                        "' Left VJ session: " + str(vj_session))
                    return Response({"Exited VJ session": str(id)}, 
                        status=status.HTTP_200_OK)

                else:
                    logger.error("404 DELETE - leave VJ session failed: user " + 
                        str(guest_id) + " is not a guest in a session.")
                    return Response({"Error": "Guest id provided is not a guest."}, 
                        status=status.HTTP_404_NOT_FOUND)

            else:
                logger.error("404 DELETE - leave VJ session failed: guest id " + 
                    str(guest_id) + " not found.")
                return Response({"Error": "Guest id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

        else:
            logger.error("400 DELETE - Bad request for leave VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)



class CloseSessionView(APIView):
    """
    Closes a VJ Session (as a host), broadcasts it via WebSockets and 
    updates the cache.
    URL (development): 127.0.0.1:8000/vjsession/close/<uuid>/
    """
    
    serializer_class = CloseSessionSerializer

    def delete(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format

            host_id = serializer.data.get("host_id")
            queryset_host = User.objects.filter(id=host_id, is_active=True)
            querysetSession = Session.objects.filter(id=id, is_active=True)

            if querysetSession.exists(): # If the active session ID maps to one in the db

                if queryset_host.exists(): # If the host ID maps to a user
                    input_host_id = queryset_host[0].id
                    actual_host_id = cache.get(id)["host"]["id"]

                    if input_host_id == actual_host_id:
                        vj_session = querysetSession[0]
                        host = queryset_host[0]
                        host.save()

                        SessionHelper.removeSession(vj_session)

                        logger.info("200 DELETE - VJ session closed: " + str(vj_session))
                        return Response({"VJ Session ended": str(id)}, 
                            status=status.HTTP_200_OK)

                    else: # If the provided id isn"t assigned to an active VJ session
                        logger.error("404 DELETE - close VJ session failed: host id " + 
                            str(host_id) + " is not the session's host.")
                        return Response({"Error": 
                            "Host id provided isn\"t the host of the session."}, 
                            status=status.HTTP_404_NOT_FOUND)

                else:
                    logger.error("404 DELETE - close VJ session failed: host id " + 
                        str(host_id) + " not found.")
                    return Response({"Error": "Host id provided does not exist."}, 
                        status=status.HTTP_404_NOT_FOUND)

            else:
                logger.error("404 DELETE - close VJ session failed: vj session id " + 
                        str(id) + " not found.")
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)
                
        else:
            logger.error("400 DELETE - Bad request for close VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)
            


class LeaveSessionView(APIView):
    """
    Leaves a VJ Session (as a guest) and updates the cache
    URL (development): 127.0.0.1:8000/vjsession/leave/<uuid>/
    """

    serializer_class = LeaveSessionSerializer

    def put(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            guest_id = serializer.data.get("guest_id")
            queryset_guest = User.objects.filter(id=guest_id, is_active=True)
            queryset_session = Session.objects.filter(id=id, is_active=True)

            if queryset_session.exists(): # If the active session ID maps to one in the db
                
                if queryset_guest.exists(): # If the guest ID maps to a user

                    if SessionHelper.isGuest(id, guest_id):

                        vj_session = queryset_session[0]
                        guest = queryset_guest[0]
                        guest.save()
                        # Remove guest from the cache
                        SessionHelper.removeGuestFromCache(vj_session, guest)

                        logger.info("200 PUT - User '" + str(guest) + 
                            "' Left VJ session: " + str(vj_session))
                        return Response({"Exited VJ session": str(id)}, 
                            status=status.HTTP_200_OK)

                    else: # If the provided id is already in an active VJ session
                        logger.error("409 PUT - Leave VJ session failed: guest id " + 
                            str(id) + " not in the provided VJ session.")
                        return Response({"Error": "Guest is not in the provided session."}, 
                            status=status.HTTP_409_CONFLICT)

                else:
                    logger.error("404 PUT - Leave VJ session failed: guest id " + 
                        str(guest_id) + " not found.")
                    return Response({"Error": "Guest id provided does not exist."}, 
                        status=status.HTTP_404_NOT_FOUND)

            else:
                logger.error("404 PUT - Leave VJ session failed: vj session id " + 
                    str(id) + " not found.")
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)
                
        else:
            logger.error("400 PUT - Bad request for leave VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)

    
class JoinSessionView(APIView):
    """
    Joins a VJ Session (as a guest) and updates the cache
    URL (development): 127.0.0.1:8000/vjsession/join/<uuid>/
    """

    serializer_class = JoinSessionSerializer  
    # if is_private, check_password. Returns serialized session
    def put(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            guest_id = serializer.data.get("guest_id")
            queryset_guest = User.objects.filter(id=guest_id, is_active=True)
            queryset_session = Session.objects.filter(id=id, is_active=True)

            user_location = serializer.data.get("session_location")

            if queryset_session.exists(): # If the active session ID maps to one in the db

                if queryset_guest.exists(): # If the guest ID maps to a user

                    if not SessionHelper.isAGuest(guest_id):

                        if not SessionHelper.isAHost(guest_id):

                            vj_session = queryset_session[0]

                            # Location check
                            if SessionHelper.isInRange(vj_session, user_location):

                                if (not vj_session.is_private or
                                    check_password(serializer.data.get("session_password"), 
                                    vj_session.session_password)): # If password valid or public

                                    guest = queryset_guest[0]
                                    SessionHelper.addGuestToCache(vj_session, guest)

                                    logging.info("200 PUT - User '" + str(guest) + 
                                        "' Joined VJ session: " + str(vj_session))
                                    return Response(SessionView.getSessionJson(vj_session), 
                                        status=status.HTTP_200_OK)

                                else: # If the password is incorrect
                                    logging.error("401 PUT - Join VJ session failed: guest " + 
                                        str(guest_id) + " provided incorrect password.")
                                    return Response({"Error": "Incorrect password."}, 
                                        status=status.HTTP_401_UNAUTHORIZED)

                            else:
                                logging.error("401 PUT - Join VJ session failed: guest " + 
                                    str(guest_id) + " not in range.")
                                return Response({"Error": "Not in range."}, 
                                    status=status.HTTP_401_UNAUTHORIZED)

                        else: # If the provided user is already hosting a session.
                            logging.error("409 PUT - Join VJ session failed: user " + 
                                str(guest_id) + " already hosting a session.")
                            return Response({"Error": "Already hosting a session."}, 
                                status=status.HTTP_409_CONFLICT)

                    else: # If the provided user is already a guest
                        logging.error("409 PUT - Join VJ session failed: user " + 
                            str(guest_id) + " already in a guest in a session.")
                        return Response({"Error": "Already a guest."}, 
                            status=status.HTTP_409_CONFLICT)

                else:
                    logging.error("404 PUT - Join VJ session failed: guest id " + 
                        str(guest_id) + " not found.")
                    return Response({"Error": "Guest id provided does not exist."}, 
                        status=status.HTTP_404_NOT_FOUND)

            else:
                logging.error("404 PUT - Join VJ session failed: vj session id " + 
                    str(id) + " not found.")
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)
                
        else:
            logging.error("400 PUT - Bad request for join VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)



class RetrieveUserSessionView(APIView):
    """
    Gets the user (tokens, username) in a specific session, as well as the session 
    (to avoid two API calls)
    URL (development): 127.0.0.1:8000/vjsession/get/user/<uuid>/
    """

    def get(self, request, id, format=None):
        if "user_id" not in request.GET:
            logging.error("400 GET - Bad request for retrieve user sessions view (invalid data).")
            return Response({"Bad Request": "Invalid data: user_id not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)

        user_id = request.GET["user_id"]
        queryset_session = Session.objects.filter(id=id, is_active=True)
        queryset_user = User.objects.filter(id=user_id, is_active=True)

        if not queryset_session.exists(): # if the session doesn"t exist
            logging.error("404 GET - Retrieve session user failed: session " + 
                str(id) + " does not exist.")
            return Response({"Error": "VJ session id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)
            
        vj_session = queryset_session[0] 

        if not queryset_user.exists(): # if the user doesn"t exist
            logging.error("404 GET - Retrieve session user failed: user " + 
                str(user_id) + " does not exist.")
            return Response({"Error": "User id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)

        user = queryset_user[0]

        if not SessionHelper.isInSession(vj_session.id, user_id): # if the user isn"t in the session
            logging.error("404 GET - Retrieve session user failed: user " + 
                str(user) + " is not in VJ session: " + str(vj_session) + " .")
            return Response({"Error": "User is not in the provided VJ session."}, 
                status=status.HTTP_404_NOT_FOUND)
            
        logging.info("200 GET - User '" + str(user) + 
            "' retrieved from session: " + str(vj_session))
        return Response(SessionView.getSessionUser(vj_session, user), 
            status=status.HTTP_200_OK)




class UpvoteSongView(APIView):
    """
    Up-votes a song in a VJ Session and updates the cache
    URL (development): 127.0.0.1:8000/vjsession/upvote/<uuid>/
    """

    serializer_class = SongInteractionSerializer
    def put(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            user_id = serializer.data.get("user_id")
            song_id = serializer.data.get("spotify_song_id")
            queryset_user = User.objects.filter(id=user_id, is_active=True)
            queryset_session = Session.objects.filter(id=id, is_active=True)

            if not queryset_session.exists(): # If the active session ID does not map to one in the db
                logging.error("404 PUT - Failed to up-vote song in session id: " + 
                    str(id) + " (VJ Session does not exist)")     
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            vj_session = queryset_session[0]

            if not queryset_user.exists(): # If the user ID does not map to a user
                logging.error("404 PUT - Failed to up-vote song in session: " + 
                    str(vj_session) + " (User id: " + str(user_id) + " does not exist)")     
                return Response({"Error": "User id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            user = queryset_user[0]

            # If the user is not in the provided session
            if not SessionHelper.isInSession(vj_session.id, user_id): 
                logging.error("401 PUT - Failed to up-vote song in session: " + 
                    str(vj_session) + " (User: " + str(user) + " not in VJ session)")       
                return Response({"Error": "User is not in the provided VJ session."}, 
                    status=status.HTTP_401_UNAUTHORIZED)

            # If the song is not in the VJ session queue
            if not SessionHelper.songInQueue(vj_session.id, song_id):
                logging.error("409 PUT - Failed to up-vote song in session: " + 
                    str(vj_session) + " (Song not in queue)")            
                return Response({"Error": "Song is not in the VJ session playlist queue."}, 
                    status=status.HTTP_409_CONFLICT)

            if SessionHelper.hasVoted(vj_session.id, song_id, user_id):
                logging.error("409 PUT - Failed to up-vote song in session: " + 
                    str(vj_session) + " (Song already up-voted by user)")  
                return Response({"Error": "Song already up-voted."}, 
                    status=status.HTTP_401_UNAUTHORIZED)

            SessionHelper.upvoteSong(vj_session, song_id, user_id)

            logging.info("200 PUT - User '" + str(user) + 
                "' up-voted song (" + str(song_id) + ") in session: " + str(vj_session))
            return Response({"success": "song up-voted in queue"}, 
                status=status.HTTP_200_OK)

        else:
            logging.error("400 PUT - Bad request for up-voting song in VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)

        


class InSessionView(APIView):
    """
    Returns whether a user is in a session or not, and the privacy status of 
    the session.
    URL (development): 127.0.0.1:8000/vjsession/insession/<uuid>/
    """
    
    def get(self, request, id, format=None):
        if "user_id" not in request.GET:
            logging.error("400 GET - Bad request for in-session view (invalid data).")
            return Response({"Bad Request": "Invalid data: user_id not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)

        user_id = request.GET["user_id"]
        queryset_session = Session.objects.filter(id=id, is_active=True)
        queryset_user = User.objects.filter(id=user_id, is_active=True)

        if not queryset_session.exists(): # if the session doesn"t exist
            logging.error("404 GET - User session status failed: session " + 
                str(id) + " does not exist.")
            return Response({"Error": "VJ session id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)
            
        vj_session = queryset_session[0] 

        if not queryset_user.exists(): # if the user doesn"t exist
            logging.error("404 GET - User session status failed: user " + 
                str(user_id) + " does not exist.")
            return Response({"Error": "User id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)

        user = queryset_user[0]

        status_info = {
            "in_session" : SessionHelper.isInSession(vj_session.id, user_id),
            "is_private" : vj_session.is_private
        }

        logging.info("200 GET - User '" + str(user) + 
            "' status of session: " + str(vj_session) + " obtained.")
        return Response(status_info, 
            status=status.HTTP_200_OK)



class UserInSessionView(APIView):
    """
    Returns whether a user is in any session (as a guest or host) without the need
    to provide a session ID.
    URL (development): 127.0.0.1:8000/vjsession/user/insession/
    """
    
    def get(self, request, format=None):
        if "user_id" not in request.GET:
            logging.error("400 GET - Bad request for user in-session view (invalid data).")
            return Response({"Bad Request": "Invalid data: user_id not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)

        user_id = request.GET["user_id"]
        queryset_user = User.objects.filter(id=user_id, is_active=True)

        if not queryset_user.exists(): # if the user doesn"t exist
            logging.error("404 GET - User session status failed: user " + 
                str(user_id) + " does not exist.")
            return Response({"Error": "User id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)

        is_host = False
        user = queryset_user[0]
        is_in_session = SessionHelper.alreadyInSession(user_id)

        # Check for whether the user is a host or not 
        if is_in_session:
            if SessionHelper.isAHost(user_id):
                is_host = True

        session_id = SessionHelper.getIdFromUser(user_id)
        status_info = {
            "in_session" : is_in_session,
            "is_host" : is_host,
            "session_id" : session_id
        }

        logging.info("200 GET - User '" + str(user) + 
            "' status of session presence obtained")
        return Response(status_info, 
            status=status.HTTP_200_OK)




class AddTokenView(APIView):
    """
    Increases the tokens/credits by one for a user given their ID and VJ session ID
    URL (development): 127.0.0.1:8000/vjsession/addtoken/<uuid>/
    """

    serializer_class = SessionUserSerializer
    def put(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            user_id = serializer.data.get("user_id")
            queryset_user = User.objects.filter(id=user_id, is_active=True)
            queryset_session = Session.objects.filter(id=id, is_active=True)

            if not queryset_session.exists(): # If the active session ID does not map to one in the db
                logging.error("404 PUT - Failed to give user credit in session: " + 
                    str(id) + " (VJ Session does not exist)")     
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            vj_session = queryset_session[0]

            if not queryset_user.exists(): # If the user ID does not map to a user
                logging.error("404 PUT - Failed to give user credit in session: " + 
                    str(vj_session) + " (User: " + str(user_id) + " does not exist)")     
                return Response({"Error": "User id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            user = queryset_user[0]

            # If the user is not in the provided session
            if not SessionHelper.isInSession(vj_session.id, user_id): 
                logging.error("401 PUT - Failed to give user credit in session: " + 
                    str(vj_session) + " (User: " + str(user) + " not in VJ session)")       
                return Response({"Error": "User is not in the provided VJ session."}, 
                    status=status.HTTP_401_UNAUTHORIZED)

            SessionHelper.addToken(vj_session, user_id)
            
            # Save user to update updated_at field (in case they fail to 
            # successfully call leave/close session) to remain active.
            user.save()

            logging.info("200 PUT - User '" + str(user) + 
                "' credits increased by 1 in session: " + str(vj_session))
            return Response({"success": "credits increased by 1"}, 
                status=status.HTTP_200_OK)

        else:
            logging.error("400 PUT - Bad request for adding credit for user in VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)



class QueueSongView(APIView): # Upvote song in cache, broadcast new queue order
    """
    Queues a song within a VJ Session and updates the cache
    URL (development): 127.0.0.1:8000/vjsession/queue/<uuid>/
    """

    serializer_class = QueueSongSerializer
    
    def put(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            user_id = serializer.data.get("user_id")
            song_id = serializer.data.get("spotify_song_id")
            time_added = serializer.data.get("time_added")

            queryset_user = User.objects.filter(id=user_id, is_active=True)
            queryset_session = Session.objects.filter(id=id, is_active=True)

            if not queryset_session.exists(): # If the active session ID does not map to one in the db
                logging.error("404 PUT - Failed to queue song in session: " + 
                    str(id) + " (VJ Session does not exist)")     
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            vj_session = queryset_session[0]

            if not queryset_user.exists(): # If the user ID does not map to a user
                logging.error("404 PUT - Failed to queue song in session: " + 
                    str(vj_session) + " (User: " + str(user_id) + " does not exist)")     
                return Response({"Error": "User id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            user = queryset_user[0]

            # If the user is not in the provided session
            if not SessionHelper.isInSession(vj_session.id, user_id): 
                logging.error("401 PUT - Failed to queue song in session: " + 
                    str(vj_session) + " (User: " + str(user) + " not in VJ session)")       
                return Response({"Error": "User is not in the provided VJ session."}, 
                    status=status.HTTP_401_UNAUTHORIZED)

            # If the song isn"t in the VJ session playlist
            if not SessionHelper.songInPlaylist(vj_session.id, song_id):
                logging.error("404 PUT - Failed to queue song in session: " + 
                    str(vj_session) + " (Song not in playlist)")          
                return Response({"Error": "Song id provided is not in the VJ session playlist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            # If the song is already in the VJ session queue
            if SessionHelper.songInQueue(vj_session.id, song_id):
                logging.error("409 PUT - Failed to queue song in session: " + 
                    str(vj_session) + " (Already in queue)")            
                return Response({"Error": "Song is already in the VJ session playlist queue."}, 
                    status=status.HTTP_409_CONFLICT)

            # If the song is already is currently playing in the VJ session
            if SessionHelper.songPlaying(vj_session.id, song_id):
                logging.error("409 PUT - Failed to queue song in session: " + 
                    str(vj_session) + " (Already playing)")            
                return Response({"Error": "Song is already playing in the VJ session."}, 
                    status=status.HTTP_409_CONFLICT)

            # If the user doesn"t have credits
            if not SessionHelper.userHasCredits(vj_session.id, user_id):
                logging.error("401 PUT - Failed to queue song in session: " + 
                    str(vj_session) + " (User: " + str(user) + " has not enough credits)")
                return Response({"Error": "Not enough credits to queue song."}, 
                    status=status.HTTP_401_UNAUTHORIZED)

            SessionHelper.queueSong(vj_session, user_id, song_id, time_added)

            logging.info("200 PUT - User '" + str(user) + 
                "' Queued song to session: " + str(vj_session))
            return Response({"success": "song added to queue"}, 
                status=status.HTTP_200_OK)

        else:
            logging.error("400 PUT - Bad request for queuing song in VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)



class PlaySongView(APIView): # Removes the song from a session"s song queue, and adds it to the playing field (in cache)
    """
    Plays a song within a VJ Session by updating the cache (removes song from queue, 
    and add it to currently playing).
    URL (development): 127.0.0.1:8000/vjsession/playsong/<uuid>/
    """

    serializer_class = PlaySongSerializer
    def put(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            song_id = serializer.data.get("spotify_song_id")
            queryset_session = Session.objects.filter(id=id, is_active=True)

            if not queryset_session.exists(): # If the active session ID does not map to one in the db
                logging.error("404 PUT - Failed to end song in session: " + 
                    str(id) + " (VJ Session does not exist)")     
                return Response({"Error": "Session id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            vj_session = queryset_session[0]

            # If the song is not in the VJ session playlist
            if not SessionHelper.songInPlaylist(vj_session.id, song_id):
                logging.error("404 PUT - Failed to play song in session: " + 
                    str(vj_session) + " (Song not in playlist)")            
                return Response({"Error": "Song is not in the playlist."}, 
                    status=status.HTTP_404_NOT_FOUND)

            song_obj = SessionHelper.dequeueSong(vj_session, song_id)
            if song_obj is None:
                song_obj = {
                    "song_id": song_id,
                    "votes": 0,
                    "time_added": datetime.now(pytz.utc),
                    "voted_by": []
                }

            session_json = cache.get(vj_session.id)
            session_json["playing"] = song_obj
            cache.set(vj_session.id, session_json)

            vj_session.save()

            logging.info("200 PUT - " + 
                "Song successfully played in session: " + str(vj_session))
            return Response({"success": "song played"}, 
                status=status.HTTP_200_OK)

        else:
            logging.error("400 PUT - Bad request for playing song in VJ session (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)
                
                
    
class SessionTakenView(APIView):
    """
    Returns whether or not a session name is taken (by an active session).
    URL (development): 127.0.0.1:8000/vjsession/taken/
    """
    
    def get(self, request, format=None):
        if "session_name" not in request.GET:
            logging.error("400 GET - Bad request for session taken view (invalid data).")
            return Response({"Bad Request": "Invalid data: session_name not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)
        
        session_name = str(request.GET["session_name"])
        queryset_name = Session.objects.filter(session_name=session_name, is_active=True)
        isTaken = False

        if queryset_name.exists(): # If a session already has the provided name
            isTaken = True

        status_info = {
            "name_taken" : isTaken,
        }
        logging.info("200 GET - Status of session name taken obtained.")
        return Response(status_info, status=status.HTTP_200_OK)
       

class InRangeSessionsView(APIView):
    """
    Gets all active public sessions in range of a provided point.
    URL (development): 127.0.0.1:8000/vjsession/public/inrange/
    """

    def get(self, request, format=None):
        if "user_location" not in request.GET:
            logging.error("400 GET - Bad request for get in-range sessions (invalid data).")
            return Response({"Bad Request": "Invalid data: user_location not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)

        user_location = str(request.GET["user_location"])
        location = GEOSGeometry(user_location, srid=4326)
        session_list = []

        for session in Session.objects.filter(is_active=True, is_private=False):
            # Tuples must be reversed for geopy coordinates: (lat, long)
            sphericalDist = distance(session.session_location.coords[::-1], location.coords[::-1]).meters
            session_range = session.connection_range

            # Add session to session list if in range
            if sphericalDist <= session_range:
                session_json = {
                    "session" : SessionSerializer(session).data
                }
                session_json["distance_to"] = sphericalDist
                session_list.append(session_json)

        logging.info("200 GET - All public in-range sessions obtained.")
        return Response(session_list, status=status.HTTP_200_OK)


class DistanceSessionsView(APIView):
    """
    Gets all active public sessions and the distance to each session given a coordinate.
    URL (development): 127.0.0.1:8000/vjsession/public/distance/
    """

    def get(self, request, format=None):
        if "user_location" not in request.GET:
            logging.error("400 GET - Bad request for get sessions and distances (invalid data).")
            return Response({"Bad Request": "Invalid data: user_location not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)

        user_location = str(request.GET["user_location"])
        location = GEOSGeometry(user_location, srid=4326)
        session_list = []

        for session in Session.objects.filter(is_active=True, is_private=False):
            # Tuples must be reversed for geopy coordinates: (lat, long)
            sphericalDist = distance(session.session_location.coords[::-1], location.coords[::-1]).meters
            session_range = session.connection_range

            # Add session to session list
            session_json = SessionSerializer(session).data
            session_json["distance_to"] = sphericalDist
            session_list.append(session_json)

        logging.info("200 GET - All public sessions with distance to them obtained.")
        return Response(session_list, status=status.HTTP_200_OK)


class SessionExistsView(APIView):
    """
    Retrieves whether or not a session exists by its ID.
    URL (development): 127.0.0.1:8000/vjsession/exists/<uuid>/
    """

    def get(self, request, id, format=None):
        try:
            queryset = Session.objects.filter(id=id, is_active=True)

        except ValidationError: # Check for exception as to whether id is a UUID.
            logger.info("200 GET - VJ session existence retrieved.")
            return Response({"exists": False}, status=status.HTTP_200_OK)

        if queryset.exists(): # If an active session with the provided ID exists
            vj_session = queryset[0] 
            logger.info("200 GET - VJ session successfully retrieved.")
            return Response({"exists": True}, status=status.HTTP_200_OK)
                
        else: # If the provided id isn"t assigned to an active session
            logger.info("200 GET - VJ session successfully retrieved.")
            return Response({"exists": False}, status=status.HTTP_200_OK)

