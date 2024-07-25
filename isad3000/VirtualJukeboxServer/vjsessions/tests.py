from django.test import TestCase
from rest_framework.test import APITestCase
from rest_framework import status
from django.urls import reverse
from .models import User, Session, SessionHelper
from .cron import clearInactiveSessions
from django.core.cache import cache
import uuid
import logging
from datetime import datetime, timedelta
import pytz


# Set logging level to disable for unit tests (logging.NOTSET will enable all)
DISABLED_LOG_LEVEL = logging.CRITICAL

# Create your tests here.

class TestCreateSessionView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")


    def test_create_session_success_public(self): # User successfully creates a public session
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }

        response = self.client.post(reverse("createsession"), sample_session)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertIsNotNone(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).id))
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_create_session_success_private(self): # User successfully creates a private session
        host_id = User.objects.get(username="Bob").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Bob\"s Vj session",
            "session_description": "Private VJ session for friends.",
            "session_password": "abcd1234",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 30,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }

        response = self.client.post(reverse("createsession"), sample_session)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertIsNotNone(cache.get(Session.objects.get(host_id=User.objects.get(username="Bob"), is_active=True).id))
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Bob"), is_active=True))


    def test_create_session_fail_host_already(self): # User is already hosting a session
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alices\"s Vj session",
            "session_description": "Public VJ session for friends.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 30,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }

        self.client.post(reverse("createsession"), sample_session)
        response = self.client.post(reverse("createsession"), sample_session)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertEqual(response.content, b"{\"Error\":\"Already hosting a session.\"}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
        
    def test_create_session_fail_guest_already(self): # User is already in a session as a guest

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

        # Bob joins Alice"s session
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.857702 -31.952187)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)

        # Bob attempts to create a session
        host_id = User.objects.get(username="Bob").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alices\"s Vj session",
            "session_description": "Public VJ session for friends.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 30,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }

        response = self.client.post(reverse("createsession"), sample_session)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertEqual(response.content, b"{\"Error\":\"Already a guest.\"}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))


    def test_create_session_fail_name_already(self): # Provided name is already taken 
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "my session",
            "session_description": "Public VJ session for friends.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 30,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        host_id2 = User.objects.get(username="Bob").id
        sample_session2 = {
            "host_id": host_id2,
            "is_private": False,
            "session_name": "my session",
            "session_description": "Public VJ session for friends.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 30,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }

        self.client.post(reverse("createsession"), sample_session)
        response = self.client.post(reverse("createsession"), sample_session2)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
        

    def test_create_session_fail_invalid_data(self): # Invalid data provided
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": "Apple", # Invalid
            "session_name": 72, # Invalid
            "session_description": "Public VJ session for friends.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 30,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        response = self.client.post(reverse("createsession"), sample_session)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestCloseSessionView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)


    def test_close_session_valid(self): # Valid closure
        host_id = User.objects.get(username="Alice").id
        host_json = {"host_id": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closesession", args=[session_id]), host_json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_close_session_invalid_id(self): # Invalid session id provided
        host_id = User.objects.get(username="Alice").id
        host_json = {"host_id": host_id}
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        response = self.client.delete(reverse("closesession", args=[session_id]), host_json)

        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))  

    def test_close_session_wrong_host(self): # Host id provided isn"t the session"s host
        host_id = User.objects.get(username="Bob").id
        host_json = {"host_id": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closesession", args=[session_id]), host_json)

        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_close_session_invalid_host(self): # Invalid host id provided
        host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        host_json = {"host_id": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closesession", args=[session_id]), host_json)

        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_close_session_invalid_data(self): # Invalid json data passed
        host_id = User.objects.get(username="Alice").id
        host_json = {"apple": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closesession", args=[session_id]), host_json)

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestCloseUserSessionView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)


    def test_close_session_valid(self): # Valid closure
        host_id = User.objects.get(username="Alice").id
        host_json = {"host_id": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closeusersession"), host_json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_close_session_wrong_host(self): # Host id provided isn"t hosting any session
        host_id = User.objects.get(username="Bob").id
        host_json = {"host_id": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closeusersession"), host_json)

        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_close_session_invalid_host(self): # Invalid host id provided
        host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        host_json = {"host_id": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closeusersession"), host_json)

        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_close_session_invalid_data(self): # Invalid json data passed
        host_id = User.objects.get(username="Alice").id
        host_json = {"apple": host_id}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.delete(reverse("closeusersession"), host_json)

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestJoinSessionView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add three users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        User.objects.create(username="Charlie")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)


    def test_join_session_valid(self): # Valid public session join case 
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.857702 -31.952187)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertIsNotNone(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["guests"][guest_id])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))


    def test_join_session_valid_private(self): # Valid private session join case
        password = "1234"
        host_id = User.objects.get(username="Charlie").id
        sample_session = {
                "host_id": host_id,
                "is_private": True,
                "session_name": "Charlie\"s Vj session",
                "session_description": "Public VJ session for all.",
                "session_password": password,
                "session_location": "POINT(115.85776 -31.95224)",
                "connection_range": 50,
                "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
                "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": password, "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Charlie\"s Vj session").id

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertIsNotNone(cache.get(Session.objects.get(host_id=User.objects.get(username="Charlie"), is_active=True).
            id)["guests"][guest_id])

        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Charlie"), is_active=True))


    def test_join_session_invalid_private(self): # Wrong password for private session
        password = "1234"
        host_id = User.objects.get(username="Charlie").id
        sample_session = {
                "host_id": host_id,
                "is_private": True,
                "session_name": "Charlie\"s Vj session",
                "session_description": "Public VJ session for all.",
                "session_password": password,
                "session_location": "POINT(115.85776 -31.95224)",
                "connection_range": 50,
                "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
                "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "4321", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Charlie\"s Vj session").id

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)

        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Charlie"), is_active=True))


    def test_join_session_invalid_location(self): # Wrong password for private session
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.854735 -31.951576)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)

        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))


    def test_join_session_invalid_in(self): # Already in a session as a guest
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        self.client.put(reverse("joinsession", args=[session_id]), guest_json) 
        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertEqual(response.content, b"{\"Error\":\"Already a guest.\"}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))


    def test_join_session_invalid_hosting(self): # Already in a session as a host
        guest_id = User.objects.get(username="Alice").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertEqual(response.content, b"{\"Error\":\"Already hosting a session.\"}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))


    def test_join_session_invalid_guest(self): # Invalid guest id provided
        guest_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_join_session_invalid_session(self): # Invalid session id provided
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID

        response = self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestRetrieveSessionView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add a user to sample database
        User.objects.create(username="Alice")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_get_session_valid(self): # Valid get
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        response = self.client.get(reverse("getsession", args=[session_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_invalid(self): # Invalid id
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        response = self.client.get(reverse("getsession", args=[session_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestLeaveSessionView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add three users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        User.objects.create(username="Charlie")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_leave_session_valid(self): # Valid session leave case 
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        response = self.client.put(reverse("leavesession", args=[session_id]), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["guests"], {})
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_leave_session_invalid_guest(self): # Invalid guest id
        guest_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        response = self.client.put(reverse("leavesession", args=[session_id]), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_leave_session_invalid_session(self): # Invalid session id
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        response = self.client.put(reverse("leavesession", args=[session_id]), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_leave_session_invalid_presence(self): # Invalid user not in session (as guest)
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("leavesession", args=[session_id]), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
        
    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestLeaveUserSessionView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add three users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        User.objects.create(username="Charlie")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_leave_session_valid(self): # Valid session leave case 
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)

        response = self.client.put(reverse("leaveusersession"), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["guests"], {})
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_leave_session_invalid_guest(self): # Invalid guest id
        guest_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        response = self.client.put(reverse("leaveusersession"), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_leave_session_invalid_presence(self): # Invalid user not in any session.
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("leaveusersession"), guest_json)
        
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
        
    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestQueueView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)
        # Add sample song to cached session playlist
        session = Session.objects.get(session_name="Alice\"s Vj session")
        session_json = cache.get(session.id) 
        song_json = {
                "album_uri": "123456",
                "album_name": "Sample Album",
                "artists": ["artist1", "artist2"],
                "track_num": 2,
                "duration_ms": 10000,
                "name": "The sample song",
                "images": ["sampleUrl1", "sampleUrl2", "sampleUrl3"]
            }

        session_json["playlist"]["songs"]["test_song_id"] = song_json
        cache.set(session.id, session_json)
    
    def giveCredit(self, vj_session_id, user_id):
        session_json = cache.get(vj_session_id) 
        if uuid.UUID(user_id) in session_json["guests"]: # User is guest
           session_json["guests"][uuid.UUID(user_id)]["credits"] += 1

        elif uuid.UUID(user_id) == session_json["host"]["id"]: # User is host
            session_json["host"]["credits"] += 1
    
        cache.set(vj_session_id, session_json)

    def test_queue_song_valid_guest(self): # Valid queue song case (guest)
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        join_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        self.client.put(reverse("joinsession", args=[session_id]), join_json)
        body = {"spotify_song_id": "test_song_id", "user_id": guest_id, "time_added": 1234}
        
        self.giveCredit(session_id, str(guest_id))

        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertNotEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_queue_song_valid_host(self): # Valid queue song case (host)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        
        self.giveCredit(session_id, str(host_id))

        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertNotEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_queue_song_invalid_already_queued(self): # Invalid queue song case (song already queued)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        

        self.client.put(reverse("queuesong", args=[session_id]), body)
        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertNotEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_queue_song_invalid_already_playing(self): # Invalid queue song case (song already playing)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        
        self.client.put(reverse("queuesong", args=[session_id]), body)
        body2 = {"spotify_song_id": "test_song_id"}
        response = self.client.put(reverse("playsong", args=[session_id]), body2)
        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_queue_song_invalid_user(self): # Invalid queue song case (user not in session)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        user_id = User.objects.get(username="Bob").id
        body = {"spotify_song_id": "test_song_id", "user_id": user_id, "time_added": 1234}

        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_queue_song_invalid_song(self): # Invalid queue song case (song not in playlist)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        # No song named "Apple"
        body = {"spotify_song_id": "Apple", "user_id": host_id, "time_added": 1234}
        
        self.giveCredit(session_id, str(host_id))

        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_queue_song_invalid_credits(self): # Invalid queue song case (not enough credits)
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        join_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        self.client.put(reverse("joinsession", args=[session_id]), join_json)
        body = {"spotify_song_id": "test_song_id", "user_id": guest_id, "time_added": 1234}

        response = self.client.put(reverse("queuesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestRetrieveSessionUserView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_get_session_user_valid_guest(self): # Valid get guest
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        
        response = self.client.get(reverse("getsessionuser", args=[session_id]), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_valid_host(self): # Valid get host
        host_id = User.objects.get(username="Alice").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("getsessionuser", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_invalid_1(self): # Invalid user id
        host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("getsessionuser", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_invalid_2(self): # Invalid session id
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        host_id = User.objects.get(username="Alice").id

        response = self.client.get(reverse("getsessionuser", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_invalid_3(self): # Invalid case: user not in session
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": ""}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        
        response = self.client.get(reverse("getsessionuser", args=[session_id]), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
    
    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestUpvoteSongView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)
        # Add sample song to cached session playlist
        session = Session.objects.get(session_name="Alice\"s Vj session")
        session_json = cache.get(session.id) 
        song_json = {
                "album_uri": "123456",
                "album_name": "Sample Album",
                "artists": ["artist1", "artist2"],
                "track_num": 2,
                "duration_ms": 10000,
                "name": "The sample song",
                "images": ["sampleUrl1", "sampleUrl2", "sampleUrl3"]
            }

        session_json["playlist"]["songs"]["test_song_id"] = song_json
        cache.set(session.id, session_json)
    
    def giveCredit(self, vj_session_id, user_id):
        session_json = cache.get(vj_session_id) 
        if uuid.UUID(user_id) in session_json["guests"]: # User is guest
           session_json["guests"][uuid.UUID(user_id)]["credits"] += 1

        elif uuid.UUID(user_id) == session_json["host"]["id"]: # User is host
            session_json["host"]["credits"] += 1
    
        cache.set(vj_session_id, session_json)

    def test_upvote_song_valid_guest(self): # Valid upvote song case (guest)
        guest_id = User.objects.get(username="Bob").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        join_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        self.client.put(reverse("joinsession", args=[session_id]), join_json)
        body = {"spotify_song_id": "test_song_id", "user_id": guest_id, "time_added": 1234}

        # Guest queues a song
        self.giveCredit(session_id, str(guest_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        # Host up-votes the song
        body = {"spotify_song_id": "test_song_id", "user_id": host_id}
        response = self.client.put(reverse("upvotesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["votes"], 2)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["voted_by"], [str(guest_id), str(host_id)])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_upvote_song_valid_host(self): # Valid upvote song case (host)
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        join_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        self.client.put(reverse("joinsession", args=[session_id]), join_json)
        
        # Host queues the song
        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        # Guest up-votes the song
        body = {"spotify_song_id": "test_song_id", "user_id": guest_id}
        response = self.client.put(reverse("upvotesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["votes"], 2)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["voted_by"], [ str(host_id), str(guest_id)])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_upvote_song_invalid_already(self): # Valid upvote song case (guest)
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        join_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        self.client.put(reverse("joinsession", args=[session_id]), join_json)
        body = {"spotify_song_id": "test_song_id", "user_id": guest_id, "time_added": 1234}

        # Guest queues a song (counts as up-voting)
        self.giveCredit(session_id, str(guest_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)
        # Already counts as up-voted
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["voted_by"], [str(guest_id)])
        # Guest up-votes the song
        response = self.client.put(reverse("upvotesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["votes"], 1)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["voted_by"], [str(guest_id)])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_upvote_song_invalid_user1(self): # Invalid upvote song case (user not in session)
        guest_id = User.objects.get(username="Bob").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        body_guest = {"spotify_song_id": "test_song_id", "user_id": guest_id}

        # Guest queues a song
        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)
        # Guest up-votes the song
        response = self.client.put(reverse("upvotesong", args=[session_id]), body_guest)
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["votes"], 1)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_upvote_song_invalid_user2(self): # Invalid upvote song case (invalid user id)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        invalid_host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID

        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        body2 = {"spotify_song_id": "test_song_id", "user_id": invalid_host_id}

        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        response = self.client.put(reverse("upvotesong", args=[session_id]), body2)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["votes"], 1)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_upvote_song_invalid_song(self): # Invalid upvote song case (song not in queue)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id}
    
        response = self.client.put(reverse("upvotesong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_409_CONFLICT)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_upvote_song_invalid_session(self): # Invalid upvote song case (invalid session id)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}
        
        invalid_session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID

        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        response = self.client.put(reverse("upvotesong", args=[invalid_session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"][0]["votes"], 1)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestInSessionView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_get_session_user_valid_guest(self): # Valid case in session (guest)
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        
        response = self.client.get(reverse("insession", args=[session_id]), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["is_private"], False)
        self.assertEqual(response.data["in_session"], True)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_valid_host_session(self): # Valid case in session (host)
        host_id = User.objects.get(username="Alice").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("insession", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["is_private"], False)
        self.assertEqual(response.data["in_session"], True)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_valid_host_not_session(self): # Valid case not in session
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("insession", args=[session_id]), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["is_private"], False)
        self.assertEqual(response.data["in_session"], False)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_invalid_1(self): # Invalid user id
        host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("insession", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_session_user_invalid_2(self): # Invalid session id
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        host_id = User.objects.get(username="Alice").id

        response = self.client.get(reverse("insession", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
    
    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestUserInSessionView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_get_userinsession_valid_guest(self): # Valid case in session (guest)
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        
        response = self.client.get(reverse("userinsession"), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["in_session"], True)
        self.assertEqual(response.data["is_host"], False)
        self.assertEqual(response.data["session_id"], str(session_id))
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_userinsession_valid_host(self): # Valid case in session (host)
        host_id = User.objects.get(username="Alice").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("userinsession"), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["in_session"], True)
        self.assertEqual(response.data["is_host"], True)
        self.assertEqual(response.data["session_id"], str(session_id))
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_userinsession_valid_not(self): # Valid case not in session
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("userinsession"), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["in_session"], False)
        self.assertEqual(response.data["is_host"], False)
        self.assertEqual(response.data["session_id"], "")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_get_userinsession_invalid_user(self): # Invalid user id
        host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.get(reverse("userinsession"), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestAddTokenView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_add_token_valid_guest(self): # Valid case (guest)
        guest_id = User.objects.get(username="Bob").id
        guest_json = {"guest_id": guest_id, "session_password": "", "session_location": "POINT(115.85776 -31.95224)"}
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        self.client.put(reverse("joinsession", args=[session_id]), guest_json)
        
        body = {"user_id": str(guest_id)}

        response = self.client.put(reverse("addtoken", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        userTokens = cache.get(session_id)["guests"][guest_id]["credits"]
        self.assertEqual(userTokens, 1)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_add_token_valid_host(self): # Valid case (host)
        host_id = User.objects.get(username="Alice").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        body = {"user_id": str(host_id)}

        response = self.client.put(reverse("addtoken", args=[session_id]), body)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        userTokens = cache.get(session_id)["host"]["credits"]
        self.assertEqual(userTokens, 4)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_add_token_invalid_user(self): # Invalid add token case (user not in session)
        guest_id = User.objects.get(username="Bob").id
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("addtoken", args=[session_id]), {"user_id": str(guest_id)})
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_add_token_invalid_1(self): # Invalid user id
        host_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id

        response = self.client.put(reverse("addtoken", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        userTokens = cache.get(session_id)["host"]["credits"]
        self.assertEqual(userTokens, 3) # Initial credits is 3.
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_add_token_invalid_2(self): # Invalid session id
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        host_id = User.objects.get(username="Alice").id

        response = self.client.put(reverse("addtoken", args=[session_id]), {"user_id": str(host_id)})
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestSessionTakenView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_valid_taken(self): # Valid case - session name taken
        json = {"session_name": "Alice\"s Vj session"}
        response = self.client.get(reverse("sessiontaken"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["name_taken"], True)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_invalid_taken(self): # Valid case - session name not taken
        json = {"session_name": "Charlie"}
        response = self.client.get(reverse("sessiontaken"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["name_taken"], False)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_invalid_taken(self): # Invalid case (bad json in header)
        json = {"some-random-value": "Charlie"}
        response = self.client.get(reverse("sessiontaken"), json)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)
        

class TestEndSongView(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")
        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)
        # Add sample song to cached session playlist
        session = Session.objects.get(session_name="Alice\"s Vj session")
        session_json = cache.get(session.id) 
        song_json = {
                "album_uri": "123456",
                "album_name": "Sample Album",
                "artists": ["artist1", "artist2"],
                "track_num": 2,
                "duration_ms": 10000,
                "name": "The sample song",
                "images": ["sampleUrl1", "sampleUrl2", "sampleUrl3"]
            }

        session_json["playlist"]["songs"]["test_song_id"] = song_json
        session_json["playlist"]["songs"]["test_song_id2"] = song_json
        cache.set(session.id, session_json)

    def giveCredit(self, vj_session_id, user_id):
        session_json = cache.get(vj_session_id) 
        if uuid.UUID(user_id) in session_json["guests"]: # User is guest
           session_json["guests"][uuid.UUID(user_id)]["credits"] += 1

        elif uuid.UUID(user_id) == session_json["host"]["id"]: # User is host
            session_json["host"]["credits"] += 1
    
        cache.set(vj_session_id, session_json)


    def test_play_song_valid(self): # Valid play song case
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id, "time_added": 1234}

        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        # Queued song case
        body = {"spotify_song_id": "test_song_id"}
        response = self.client.put(reverse("playsong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["song_queue"], [])
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["playing"]["votes"], 1)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["playing"]["song_id"], "test_song_id")

        # Non-queued (auto-played) song case
        body = {"spotify_song_id": "test_song_id2"}
        response = self.client.put(reverse("playsong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["playing"]["song_id"], "test_song_id2")
        self.assertEqual(cache.get(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).
            id)["playing"]["votes"], 0)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))


    def test_play_song_invalid_session(self): # Invalid play song case (invalid session ID)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id}

        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        body = {"spotify_song_id": "test_song_id"}
        invalid_session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # random UUID
        response = self.client.put(reverse("playsong", args=[invalid_session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        self.assertEqual(response.content, b"{\"Error\":\"Session id provided does not exist.\"}")

        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_play_song_invalid_song(self): # Invalid play song case (song not in queue)
        session_id = Session.objects.get(session_name="Alice\"s Vj session").id
        host_id = Session.objects.get(session_name="Alice\"s Vj session").host_id.id
        body = {"spotify_song_id": "test_song_id", "user_id": host_id}

        self.giveCredit(session_id, str(host_id))
        self.client.put(reverse("queuesong", args=[session_id]), body)

        body = {"spotify_song_id": "wrong song id"}
        response = self.client.put(reverse("playsong", args=[session_id]), body)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
        self.assertEqual(response.content, b"{\"Error\":\"Song is not in the playlist.\"}")

        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestClearInactiveSessions(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_clearing_inactive_session_host(self):
        session = Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True)
        host = User.objects.filter(username="Alice", is_active=True)
        host.update(is_active=False)
        clearInactiveSessions()

        # Session will now no longer be accessible
        sample_id = session.id
        response = self.client.get(reverse("getsession", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_clearing_inactive_session_play(self):
        # Set expired timestamp for session.
        session = Session.objects.filter(session_name="Alice\"s Vj session", is_active=True)
        sample_id = session[0].id
        session.update(updated_at=(datetime.now(pytz.utc) - timedelta(hours=3)))
        clearInactiveSessions()

        # Session will now no longer be accessible
        response = self.client.get(reverse("getsession", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_clearing_active_session(self):
        session = Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True)
        clearInactiveSessions()

        # Session will still exist
        sample_id = session.id
        response = self.client.get(reverse("getsession", args=[sample_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)



class TestInRangeSessionsView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_valid_inrange(self): # Valid case - sessions in range
        json = {"user_location": "POINT(115.85776 -31.95224)"}
        response = self.client.get(reverse("sessioninrange"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertNotEqual(len(response.data), 0)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_valid_not_inrange(self): # Valid case - no sessions in range
        json = {"user_location": "POINT(116.85776 -32.95224)"}
        response = self.client.get(reverse("sessioninrange"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data), 0)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
    

    def test_invalid_inrange(self): # Invalid case (bad json in header)
        json = {"user_loc": "POINT(116.85776 -32.95224)"} # Wrong key value
        response = self.client.get(reverse("sessioninrange"), json)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestSessionExistsView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_exist_valid_id(self): # Session exists
        session_id = Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True).id
        response = self.client.get(reverse("sessionexists", args=[session_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.content, b"{\"exists\":true}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_not_exist(self): # Session does not exist (UUID)
        session_id = uuid.UUID("2952a925-0243-4c9a-bd8d-de9729a75457") # Random UUID
        response = self.client.get(reverse("sessionexists", args=[session_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.content, b"{\"exists\":false}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))
    
    def test_exist_invalid_id(self): # Session exists
        session_id = "1234" # Session does not exist (UUID)
        response = self.client.get(reverse("sessionexists", args=[session_id]))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.content, b"{\"exists\":false}")
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestDistanceSessionsView(APITestCase): 
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)
        # Add two users to sample database
        User.objects.create(username="Alice")
        User.objects.create(username="Bob")

        host_id = User.objects.get(username="Alice").id
        sample_session = {
            "host_id": host_id,
            "is_private": False,
            "session_name": "Alice\"s Vj session",
            "session_description": "Public VJ session for all.",
            "session_password": "",
            "session_location": "POINT(115.85776 -31.95224)",
            "connection_range": 50,
            "spotify_playlist_id": "aaaaaaaaaaaaaaaaaaaaaa",
            "access_token": "a"
        }
        self.client.post(reverse("createsession"), sample_session)

    def test_valid_distance_sessions(self): # Valid case - sessions in range
        json = {"user_location": "POINT(115.85776 -31.95224)"}
        response = self.client.get(reverse("sessionsdistance"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertNotEqual(len(response.data), 0)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_valid_distance_sessions_far(self): # Valid case - sessions not in range
        json = {"user_location": "POINT(116.85776 -39.95224)"}
        response = self.client.get(reverse("sessionsdistance"), json)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertNotEqual(len(response.data), 0)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def test_invalid_distance_sessions(self): # Invalid case (bad json in header)
        json = {"user_loc": "POINT(116.85776 -32.95224)"} # Wrong key value
        response = self.client.get(reverse("sessionsdistance"), json)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        SessionHelper.removeSession(Session.objects.get(host_id=User.objects.get(username="Alice"), is_active=True))

    def tearDown(self):
        logging.disable(logging.NOTSET)