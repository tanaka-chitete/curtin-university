from rest_framework import status
from rest_framework.test import APITestCase
from django.urls import reverse
import logging

# Set logging level to disable for unit tests (logging.NOTSET will enable all)
DISABLED_LOG_LEVEL = logging.CRITICAL


class TestGetUrl(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_get_url(self):
        response = self.client.get(reverse("getauthurl"))
        self.assertEqual(response.status_code, 200)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestRetrievePlaylists(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_playlists_invalid(self):
        response = self.client.get(reverse("getuserplaylists"))
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_resume_valid(self):
        response = self.client.put("getuserplaylists", {"access_token": 123})
        self.assertEqual(response.status_code, 404)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestRetrievePlaylistItems(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_retrieve_valid(self):
        data = {
            "access_token": 123,
            "playlist_id": 321,
        }
        response = self.client.get(reverse("getplaylistsongs"), data)
        self.assertEqual(response.status_code, 200)

    def test_retrieve_no_playlist_id(self):
        data = {
            "access_token": 123,
        }
        response = self.client.get(reverse("getplaylistsongs"), data)
        self.assertEqual(response.status_code, 400)

    def test_retrieve_no_access_token(self):
        data = {
            "playlist_id": 321,
        }
        response = self.client.get(reverse("getplaylistsongs"), data)
        self.assertEqual(response.status_code, 400)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestPlaySong(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_play_song_valid(self):
        data = {
            "album_uri": "123",
            "access_token": "123",
            "track_number": 123
        }
        response = self.client.put("playsong", data)
        self.assertEqual(response.status_code, 404)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestGetTokens(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_get_tokens_valid(self):
        data = {
            "code": 123,
        }
        response = self.client.get(reverse("gettokens"), data)
        self.assertEqual(response.status_code, 200)

    def test_get_tokens_invalid(self):
        data = {
            "codeee": 123,
        }
        response = self.client.get(reverse("gettokens"), data)
        self.assertEqual(response.status_code, 400)

    def tearDown(self):
        logging.disable(logging.NOTSET)


class TestRefreshTokens(APITestCase):
    def setUp(self):
        logging.disable(DISABLED_LOG_LEVEL)

    def test_refresh_tokens_valid(self):
        data = {
            "refresh_token": 123,
        }
        response = self.client.post(reverse("refreshtokens"), data)
        self.assertEqual(response.status_code, 200)

    def test_refresh_tokens_invalid(self):
        response = self.client.post(reverse("refreshtokens"))
        self.assertEqual(response.status_code, 400)

    def tearDown(self):
        logging.disable(logging.NOTSET)

