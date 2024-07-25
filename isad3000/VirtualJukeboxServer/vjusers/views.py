from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from django.core.exceptions import ValidationError
from .serializers import ChangeUsernameSerializer, CreateUserSerializer, UserSerializer
from .models import User
from datetime import datetime
import pytz
import logging

logger = logging.getLogger("django")

# Create your views here.
class RetrieveUserView(APIView):
    """
    Retrieves a user that is active via their ID (uuid)
    URL (development): 127.0.0.1:8000/user/get/<uuid>/
    """

    def get(self, request, id, format=None):
        # Check for exception as to whether id is a UUID.
        try:
            queryset = User.objects.filter(id=id, is_active=True)
        except ValidationError:
            logger.error("404 GET - Request for user not found (non UUID provided: " + 
                str(id) + ").")
            return Response({"Error": "User id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)

        if queryset.exists(): # If an active user with the provided ID exists
            user = queryset[0] 
            logger.info("200 GET - User successfully retrieved: " + str(user))
            return Response(UserSerializer(user).data, 
                status=status.HTTP_200_OK)

        else: # If the provided id isn"t assigned to an active user
            logger.error("404 GET - Request for user not found (UUID provided: " + 
                str(id) + ").")
            return Response({"Error": "User id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)


class CreateUserView(APIView):
    """
    Creates a user given a username (in body), and adds them to the database.
    URL (development): 127.0.0.1:8000/user/create/
    """

    serializer_class = CreateUserSerializer
    def post(self, request, format=None):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            username = serializer.data.get("username")
            queryset = User.objects.filter(is_active=True, username=username)

            if queryset.exists(): # If an active user with the provided username already exists
                logger.error("409 POST - Create user failed: username '" + 
                    str(username) + "' already taken.")
                return Response({"Error": "Username is already in use."}, 
                    status=status.HTTP_409_CONFLICT)
                    
            else: # Create the new user
                user = User(username=username)
                user.save()
                logger.info("201 POST - User successfully created: " + str(user))
                return Response(UserSerializer(user).data, 
                    status=status.HTTP_201_CREATED)
        else:
            logger.error("400 POST - Bad request for create user (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)


class UsernameChangeView(APIView):
    """
    Changes the username of a User object in the database, 
    given a user id and new username in body.
    URL (development): 127.0.0.1:8000/user/change/<uuid>/
    """

    serializer_class = ChangeUsernameSerializer
    def patch(self, request, id, format=None):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid(): # If the provided JSON follows the Serializer format
            newUsername = serializer.data.get("username")
            querysetCurrent = User.objects.filter(is_active=True, id=id)
            querysetNew = User.objects.filter(is_active=True, username=newUsername)

            if querysetCurrent.exists(): # The user id provided is valid
            
                if querysetNew.exists(): # The new username is already taken
                    logger.error("409 PATCH - Change username failed: username '" + 
                        str(newUsername) + "' already taken.")
                    return Response({"Error": "Username is already in use."}, 
                        status=status.HTTP_409_CONFLICT)

                else: # All valid, make the change

                    user = querysetCurrent[0] 
                    user.username = newUsername
                    user.save()
                    logger.info("200 PATCH - User successfully changed username: " + str(user))
                    return Response(UserSerializer(user).data, 
                        status=status.HTTP_200_OK)

            else:
                logger.error("404 PATCH - change username failed: user id " + 
                    str(id) + " not found.")
                return Response({"Error": "User id provided does not exist."}, 
                    status=status.HTTP_404_NOT_FOUND)
        else:
            logger.error("400 PATCH - Bad request for change username (invalid data).")
            return Response({"Bad Request": "Invalid data..."}, 
                status=status.HTTP_400_BAD_REQUEST)



class LogoutUserView(APIView):
    """
    Marks a User object as inactive in the database, given a user id.
    URL (development): 127.0.0.1:8000/user/logout/<uuid>/
    """

    def delete(self, request, id, format=None):
        queryset = User.objects.filter(id=id, is_active=True)
        if queryset.exists(): # If an active user with the provided ID exists
            user = queryset[0] 
            
            user.is_active = False
            user.deleted_at = datetime.now(pytz.utc)
            user.save()
            logger.info("200 DELETE - User successfully logged out: " + str(user))
            return Response({"User logged out": str(id)}, 
                status=status.HTTP_200_OK)
        else: # If the provided id isn"t assigned to an active user
            logger.error("404 DELETE - Logout user failed: user id " + 
                str(id) + " not found.")
            return Response({"Error": "User id provided does not exist."}, 
                status=status.HTTP_404_NOT_FOUND)


class UserTakenView(APIView):
    """
    Returns whether or not a username is taken (by an active user).
    URL (development): 127.0.0.1:8000/user/taken/
    """
    
    def get(self, request, format=None):
        if "username" not in request.GET:
            return Response({"Bad Request": "Invalid data: username not in GET header"}, 
                status=status.HTTP_400_BAD_REQUEST)
        
        username = str(request.GET["username"])
        queryset_name = User.objects.filter(username=username, is_active=True)
        isTaken = False

        if queryset_name.exists(): # If a user already has the provided username
            isTaken = True

        status_info = {
            "name_taken" : isTaken,
        }
        logging.info("200 GET - Status of username taken obtained.")
        return Response(status_info, status=status.HTTP_200_OK)