from django.urls import path
from .views import *

urlpatterns = [
    path("get-auth-url", AuthURL.as_view(), name="getauthurl"),
    path("get-user-playlists", RetrievePlaylists.as_view(), name="getuserplaylists"),
    path("get-playlist-songs", RetrievePlaylistSongs.as_view(), name="getplaylistsongs"),
    path("play-song", PlaySong.as_view(), name="playsong"),
    path("get-tokens", GetUserTokens.as_view(), name="gettokens"),
    path("refresh-tokens", RefreshUserTokens.as_view(), name="refreshtokens"),
    path("account-status", AccountStatus.as_view(), name="accountstatus"),
]
