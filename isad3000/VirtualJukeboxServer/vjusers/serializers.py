from rest_framework import serializers

from .models import User

class UserSerializer(serializers.ModelSerializer):
    """
    For returning users in responses.
    """

    class Meta:
        model = User
        fields = ("id", "created_at", "updated_at", "deleted_at", "is_active", 
            "username",)


class CreateUserSerializer(serializers.ModelSerializer):
    """
    For creating users via requests.
    """

    class Meta:
        model = User
        fields = ("id", "username",)


class ChangeUsernameSerializer(serializers.ModelSerializer):
    """
    For changing usernames via requests.
    """

    class Meta:
        model = User
        fields = ("id", "username",)