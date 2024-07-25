from rest_framework import serializers

from .models import Session

class SessionSerializer(serializers.ModelSerializer):
    """
    For returning VJ sessions in responses.
    """
    
    class Meta:
        model = Session
        fields = ("id", "host_id", "created_by", "updated_by", "created_by", 
            "deleted_at", "is_active", "is_private", "session_name", "session_description",
            "session_password", "session_location", "connection_range")


class CreateSessionSerializer(serializers.ModelSerializer):
    """
    For creating VJ sessions via requests.
    """

    spotify_playlist_id = serializers.CharField(max_length=22)
    access_token = serializers.CharField()
    session_password = serializers.CharField(allow_blank=True, allow_null=True)

    class Meta:
        model = Session
        fields = ("host_id", "is_private", "session_name", 
            "session_description", "session_password", "session_location", 
            "connection_range", "spotify_playlist_id", "access_token")


class JoinSessionSerializer(serializers.ModelSerializer): 
    """
    For joining VJ sessions via requests.
    """

    guest_id = serializers.UUIDField()
    session_password = serializers.CharField(allow_blank=True, allow_null=True)

    class Meta:
        model = Session
        fields = ("id", "guest_id", "session_password", "session_location")


class CloseSessionSerializer(serializers.ModelSerializer):
    """
    For closing VJ sessions via requests.
    """

    host_id = serializers.UUIDField()

    class Meta:
        model = Session
        fields = ("id", "host_id") # Requires both host id and session id


class LeaveSessionSerializer(serializers.ModelSerializer):
    """
    For leaving VJ sessions via requests.
    """

    guest_id = serializers.UUIDField()

    class Meta:
        model = Session
        fields = ("id", "guest_id") # Requires both guest ID and session ID


class SongInteractionSerializer(serializers.ModelSerializer):
    """
    For up-voting songs in VJ sessions via requests.
    """

    spotify_song_id = serializers.CharField(max_length=22)
    user_id = serializers.UUIDField()

    class Meta:
        model = Session
        fields = ("id", "spotify_song_id", "user_id") # Requires both user ID and session ID

        
class QueueSongSerializer(serializers.ModelSerializer):
    """
    For queuing songs in VJ sessions via requests.
    """

    spotify_song_id = serializers.CharField(max_length=22)
    user_id = serializers.UUIDField()
    time_added = serializers.IntegerField()

    class Meta:
        model = Session
        fields = ("id", "spotify_song_id", "user_id", "time_added") # Requires both user ID and session ID


class PlaySongSerializer(serializers.ModelSerializer):
    """
    For playing songs in VJ sessions via PUT requests (to also remove from queue).
    """

    spotify_song_id = serializers.CharField(max_length=22)
    class Meta:
        model = Session
        fields = ("id", "spotify_song_id") # Requires both session ID and song ID


class SessionUserSerializer(serializers.ModelSerializer):
    """
    For querying users in a session with non GET requests.
    """
    user_id = serializers.UUIDField()

    class Meta:
        model = Session
        fields = ("id", "user_id") # Requires both user ID and session ID
    
    