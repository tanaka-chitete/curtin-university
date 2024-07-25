from django.test import TestCase
from rest_framework.test import APITestCase
from rest_framework import status
from django.urls import reverse
from .models import User
import uuid
import logging
from .cron import clearInactiveUsers
from datetime import datetime, timedelta
import pytz

# Set logging level to disable for unit tests (logging.NOTSET will enable all)
DISABLED_LOG_LEVEL = logging.CRITICAL

# Create your tests here.

class TestCreateUserView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_create_user_success(self): # Test valid json provided
        sample_user = {"username": "Alice"}
        response = self.client.post(reverse("createuser"), sample_user)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_create_user_fail(self): # Test wrong json provided
        sample_user = {"user name": "Alice"} # Invalid key
        response = self.client.post(reverse("createuser"), sample_user)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_create_user_conflict(self): # Test username already taken
        sample_user = {"username": "Alice"}
        sample_user2 = {"username": "Alice"}
        self.client.post(reverse("createuser"), sample_user)
        response = self.client.post(reverse("createuser"), sample_user2)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)

    def tearDown(self):
        logging.disable(logging.NOTSET)

class TestRetrieveUserView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

    def test_correct_get_user_fail(self): # Test valid args with nonexistent user
        sample_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        response = self.client.get(reverse("getuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
    
    def test_correct_get_user_found(self): # Valid case
        user = User.objects.get(username="Alice")
        sample_id = user.id
        response = self.client.get(reverse("getuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def tearDown(self):
        logging.disable(logging.NOTSET)

class TestChangeUsernameView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

    def test_valid_username_change(self): # Valid case

        sample_id = User.objects.get(username="Alice").id
        name_change = {"username": "Charlie"}
        response = self.client.patch(reverse("changeusername", args=[sample_id]), name_change)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual((User.objects.get(id=sample_id)).username, "Charlie")

    def test_invalid_username_change_already(self): # Username already taken case

        sample_id = User.objects.get(username="Alice").id
        name_change = {"username": "Bob"}
        response = self.client.patch(reverse("changeusername", args=[sample_id]), name_change)

        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertEqual((User.objects.get(id=sample_id)).username, "Alice")

    def test_invalid_username_change_bad_request(self): # Invalid name and/or payload provided

        sample_id = User.objects.get(username="Alice").id
        name_change = {"username": "Bobbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"} # Too long
        response = self.client.patch(reverse("changeusername", args=[sample_id]), name_change)

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual((User.objects.get(id=sample_id)).username, "Alice")

    def test_invalid_username_change_unknown_user(self): # User id provided not registered

        sample_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        name_change = {"username": "Charlie"} # Too long
        response = self.client.patch(reverse("changeusername", args=[sample_id]), name_change)

        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestLogoutUserView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

    def test_valid_logout(self): # Valid case
        sample_id = User.objects.get(username="Alice").id
        response = self.client.delete(reverse("logoutuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        # User will now no longer be accessible
        response = self.client.get(reverse("getuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_invalid_logout_id(self): # User id provided not registered
        sample_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        response = self.client.delete(reverse("logoutuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        
    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestUserTakenView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

    def test_valid_taken(self): # Valid case - username taken
        json = {"username": "Alice"}
        response = self.client.get(reverse("usertaken"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["name_taken"], True)

    def test_valid_not_taken(self): # Valid case - username not taken
        json = {"username": "Charlie"}
        response = self.client.get(reverse("usertaken"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["name_taken"], False)

    def test_invalid_taken(self): # Invalid case (bad json in header)
        json = {"some-random-value": "Charlie"}
        response = self.client.get(reverse("usertaken"), json)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestClearInactiveUsers(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

    def test_clearing_active_user(self):
        # Set expired timestamp for user
        user = User.objects.filter(is_active=True, username="Alice")
        sample_id = user[0].id
        user.update(updated_at=(datetime.now(pytz.utc) - timedelta(days=1)))
        clearInactiveUsers()

        # User will now no longer be accessible
        response = self.client.get(reverse("getuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_clearing_inactive_user(self):
        user = User.objects.get(username="Alice")
        clearInactiveUsers()

        # User will still be accessible (as their timestamp has not expired)
        sample_id = user.id
        response = self.client.get(reverse("getuser", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def tearDown(self):
        logging.disable(logging.NOTSET)