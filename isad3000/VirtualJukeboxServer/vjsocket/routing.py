from django.urls import re_path

from . import consumers

websocket_urlpatterns = [
    re_path(r'ws/session/(?P<room_name>[-\w]+)/$', consumers.VJConsumer.as_asgi()),
]
