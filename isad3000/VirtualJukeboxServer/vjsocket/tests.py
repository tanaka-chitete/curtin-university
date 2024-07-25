import asyncio
import json

from channels.routing import URLRouter
from channels.testing import WebsocketCommunicator
from django.test import TestCase

from vjsocket.routing import websocket_urlpatterns


class TestWebSocket(TestCase):

    async def test_websocket_connect(self):
        communicator = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")

        connected = await communicator.connect()
        assert connected

        await communicator.disconnect()

    async def test_websocket_session(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_three = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/different_session/")

        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected
        connected, _ = await user_three.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "song_queued",
            "song_id": "123123"
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "song_queued",
            "song_id": "123123"
        })

        with self.assertRaises(asyncio.TimeoutError):
            await user_three.receive_from()

        await user_one.disconnect()
        await user_two.disconnect()
        await user_three.disconnect()


class TestEvents(TestCase):
    async def test_chat_message(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        # No profanity message test
        await user_one.send_to(json.dumps({
            "type": "chat_message",
            "name": "user_one",
            "message": "test_message"
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "chat_message",
            "name": "user_one",
            "message": "test_message"
        })

        # Profanity filter test
        profanity_words = ["arse", "4rse", "4rs3", "ars3", "aRsE", "ar se"]
        
        for word in profanity_words:
            await user_one.send_to(json.dumps({
                "type": "chat_message",
                "name": "user_one",
                "message": "this song is " + str(word)
            }))
            response = await user_two.receive_from()
            assert response == json.dumps({
                "type": "chat_message",
                "name": "user_one",
                "message": "this song is ****"
            })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_user_joined(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "user_joined",
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "user_joined",
        })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_song_vote(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "song_vote",
            "song_id": 12345
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "song_vote",
            "song_id": 12345
        })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_song_changed(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "song_changed",
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "song_changed",
        })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_room_closed(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "room_closed",
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "room_closed",
        })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_playback_changed(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "playback_changed",
            "song_id" : "abc",
            "song_progress": 100,
            "song_length": 200,
            "paused": True
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "playback_changed",
            "song_id" : "abc",
            "song_progress": 100,
            "song_length": 200,
            "paused": True
        })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_song_queued(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "song_queued",
            "song_id": 123,
            "time_added" : 123
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "song_queued",
            "song_id": 123,
            "time_added" : 123
        })

        await user_two.disconnect()
        await user_one.disconnect()

    async def test_autoplay_song_queued(self):
        user_one = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        user_two = WebsocketCommunicator(URLRouter(websocket_urlpatterns), "/ws/session/test_session/")
        connected, _ = await user_one.connect()
        assert connected
        connected, _ = await user_two.connect()
        assert connected

        await user_one.send_to(json.dumps({
            "type": "autoplay_song_queued",
            "song_id": 123
        }))

        response = await user_two.receive_from()
        assert response == json.dumps({
            "type": "autoplay_song_queued",
            "song_id": 123
        })

        await user_two.disconnect()
        await user_one.disconnect()
