import json
import requests
import logging
import os

from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from requests import Request, post

BASE_URL = "https://api.spotify.com/v1/me/"
CLIENT_ID = os.environ.get("SPOTIFY_CLIENT_ID")
CLIENT_SECRET = os.environ.get("SPOTIFY_CLIENT_SECRET")
REDIRECT_URI = os.environ.get("SPOTIFY_REDIRECT_URI")

logger = logging.getLogger("django")


class AuthURL(APIView):
    """
    Generates and returns the Spotify authentication url
    """

    def get(self, request, format=None):
        scopes = "streaming " \
                 "user-read-email " \
                 "user-read-private " \
                 "user-read-playback-state " \
                 "user-modify-playback-state " \
                 "playlist-read-private " \
                 "playlist-read-collaborative"
        url = Request("GET", "https://accounts.spotify.com/authorize", params={
            "scope": scopes,
            "response_type": "code",
            "redirect_uri": REDIRECT_URI,
            "client_id": CLIENT_ID
        }).prepare().url

        logger.info("200 GET - Spotify Authentication Url Successfully Retrieved")
        return Response({"url": url}, status=status.HTTP_200_OK)



class RetrievePlaylists(APIView):
    """
    Retrieves all of a user's playlists
    """

    def get(self, request, format=None):
        if "access_token" not in request.GET:
            logger.error("400 GET - Bad Request for retrieve playlists (missing access token)")
            return Response({"message": "missing access token"}, status=status.HTTP_400_BAD_REQUEST)

        headers = {"Authorization": "Bearer " + request.GET["access_token"]}
        message = requests.get(BASE_URL + "playlists?limit=50", headers=headers)
        playlist_data = []

        if "items" not in message.json():
            return Response(message.json())
        else:
            all_playlists = message.json()["items"]
            playlist_offset = 0

            while len(message.json()["items"]) == 50:
                playlist_offset += 50
                message = requests.get(BASE_URL + f"playlists?limit=50&offset={playlist_offset}", headers=headers)
                all_playlists += message.json()["items"]

            for playlist in all_playlists:
                playlist_data.append(
                    {
                        "name": playlist["name"],
                        "playlist_id": playlist["id"],
                        "total_tracks": playlist["tracks"]["total"]
                    }
                )
            return Response({"playlists": playlist_data})


class RetrievePlaylistSongs(APIView):
    """
    Retrieves all the songs for a given playlist ID
    """

    def get(self, request, format=None):
        if "playlist_id" not in request.GET or "access_token" not in request.GET:
            logger.error("400 GET - Bad Request for retrieve playlist songs (missing parameters)")
            return Response({"message": "missing required parameters"}, status=status.HTTP_400_BAD_REQUEST)

        headers = {
            "playlist_id": request.GET["playlist_id"],
            "Authorization": "Bearer " + request.GET["access_token"],
        }

        playlist_id = request.GET["playlist_id"]
        market = "AU"
        url = f"https://api.spotify.com/v1/playlists/{playlist_id}/tracks?market={market}&" + \
              "fields=items(track(is_playable,name,track_number,id,is_local,duration_ms,album(name,images,uri," \
              + "artists(name))))"
        song_list = requests.get(url, headers=headers)
        offset = 0
        # Playlist with more than 100 songs requires more get requests
        if "items" not in song_list.json():
            return Response(song_list.json())
        else:
            all_songs = song_list.json()
            while len(song_list.json()["items"]) == 100:
                offset += 100
                song_list = requests.get(url + f"&offset={offset}", headers=headers)
                all_songs["items"] += song_list.json()["items"]
            return Response(all_songs)


class PlaySong(APIView):
    """
    Plays the song specified by the Spotify track uri
    """

    def put(self, request, format=None):
        required_keys = {"album_uri", "access_token", "track_number", "device_id"}
        if not required_keys.issubset(request.data.keys()):
            logger.error("400 PUT - Bad Request for Play Song (missing parameters)")
            return Response({"message": "missing required parameters"}, status=status.HTTP_400_BAD_REQUEST)

        track_number = (request.data["track_number"] - 1)
        data = {
            "context_uri": request.data["album_uri"],
            "offset": {
                "position": track_number
            },
            "position_ms": 0
        }
        headers = {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + str(request.data["access_token"])
        }

        url = Request("PUT", BASE_URL + "player/play", params={
            "device_id": request.data["device_id"],
        }).prepare().url

        message = requests.put(url, headers=headers, data=json.dumps(data))

        return Response({"message: ": message})


class GetUserTokens(APIView):
    """
    Retrieves the access and refresh tokens for the Spotify API
    """

    def get(self, request, format=None):
        if "code" not in request.GET:
            logger.error("400 GET - Bad Request for Get User Tokens (missing code)")
            return Response({"message": "no code provided"}, status=status.HTTP_400_BAD_REQUEST)

        response = post("https://accounts.spotify.com/api/token", data={
            "grant_type": "authorization_code",
            "code": request.GET["code"],
            "redirect_uri": REDIRECT_URI,
            "client_id": CLIENT_ID,
            "client_secret": CLIENT_SECRET
        })
        return Response(response.json())


class RefreshUserTokens(APIView):
    """
    Retrieves a new access token for the Spotify API
    """

    def post(self, request, format=None):
        if "refresh_token" not in request.data:
            logger.error("400 POST - Bad Request for Refresh User Tokens (missing refresh token)")
            return Response({"message": "no token provided"}, status=status.HTTP_400_BAD_REQUEST)

        response = post("https://accounts.spotify.com/api/token", data={
            "grant_type": "refresh_token",
            "refresh_token": request.data["refresh_token"],
            "client_id": CLIENT_ID,
            "client_secret": CLIENT_SECRET
        })
        return Response(response.json())


class AccountStatus(APIView):
    """
    Retrieves the status of the user's account
    """

    def get(self, request, format=None):
        if "access_token" not in request.GET:
            logger.error("400 GET - Bad Request for get account status (missing access token)")
            return Response({"message": "missing access token"}, status=status.HTTP_400_BAD_REQUEST)

        headers = {"Authorization": "Bearer " + request.GET["access_token"]}
        profile_info = requests.get(BASE_URL, headers=headers)

        if "product" in profile_info.json():  # Successful request
            is_premium = profile_info.json()["product"] == "premium"
            market = profile_info.json()["country"]
            return Response({
                "is_premium": is_premium,
                "market": market
            }, status=status.HTTP_200_OK)
        return Response(profile_info.json())
