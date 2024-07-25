import json
from channels.generic.websocket import AsyncWebsocketConsumer
from better_profanity import profanity


class VJConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        self.room_name = self.scope["url_route"]["kwargs"]["room_name"]
        self.room_group_name = "vjsocket_%s" % self.room_name

        # Join room group
        await self.channel_layer.group_add(
            self.room_group_name,
            self.channel_name
        )

        await self.accept()

    async def disconnect(self, close_code):
        # Leave room group
        await self.channel_layer.group_discard(
            self.room_group_name,
            self.channel_name
        )

    # Receive message from WebSocket
    async def receive(self, text_data):
        text_data_json = json.loads(text_data)

        # Send message to room group
        await self.channel_layer.group_send(
            self.room_group_name,
            {
                "type": text_data_json["type"],
                "data": text_data_json
            }
        )

    # Receive "chat_message" event from room group
    async def chat_message(self, event):
        chat_message = event["data"]["message"]
        name = event["data"]["name"]

        # Profanity Filtering
        filtered_message = profanity.censor(chat_message)

        await self.send(text_data=json.dumps({
            "type": "chat_message",
            "name": name,
            "message": filtered_message
        }))

    # Receive "song_vote" from room group
    async def song_vote(self, event):
        song_id = event["data"]["song_id"]

        await self.send(text_data=json.dumps({
            "type": "song_vote",
            "song_id": song_id
        }))

    # Receive "user_joined" from room group
    async def user_joined(self, event):
        await self.send(text_data=json.dumps({
            "type": "user_joined",
        }))

    # Receive "song_changed" from room group
    async def song_changed(self, event):
        await self.send(text_data=json.dumps({
            "type": "song_changed",
        }))

    # Receive "room_closed" from room group
    async def room_closed(self, event):
        await self.send(text_data=json.dumps({
            "type": "room_closed"
        }))

    # Receive "playback_changed" from room group
    async def playback_changed(self, event):
        song_id = event["data"]["song_id"]
        song_progress = event["data"]["song_progress"]
        song_length = event["data"]["song_length"]
        paused = event["data"]["paused"]

        await self.send(text_data=json.dumps({
            "type": "playback_changed",
            "song_id" : song_id,
            "song_progress": song_progress,
            "song_length": song_length,
            "paused": paused
        }))

    # Receive "song_queued" from room group
    async def song_queued(self, event):
        song_id = event["data"]["song_id"]
        time_added = event["data"]["time_added"]

        await self.send(text_data=json.dumps({
            "type": "song_queued",
            "song_id": song_id,
            "time_added" : time_added
        }))

    async def autoplay_song_queued(self, event):
        song_id = event["data"]["song_id"]

        await self.send(text_data=json.dumps({
            "type": "autoplay_song_queued",
            "song_id": song_id
        }))