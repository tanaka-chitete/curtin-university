from django.urls import path
from .views import SessionExistsView, SessionView, JoinSessionView, CreateSessionView, CloseSessionView, \
    LeaveSessionView, RetrieveSessionView, QueueSongView, UpvoteSongView, RetrieveUserSessionView, \
    InSessionView, AddTokenView, CloseUserSessionView, LeaveUserSessionView, PlaySongView, UserInSessionView, \
    SessionTakenView, InRangeSessionsView, SessionExistsView, DistanceSessionsView


urlpatterns = [
    path("vjsession/public/", SessionView.as_view(), name="vjsessions"),
    path("vjsession/get/<str:id>/", RetrieveSessionView.as_view(), name="getsession"),
    path("vjsession/create/", CreateSessionView.as_view(), name="createsession"),
    path("vjsession/join/<str:id>/", JoinSessionView.as_view(), name="joinsession"),
    path("vjsession/close/<str:id>/", CloseSessionView.as_view(), name="closesession"),
    path("vjsession/leave/<str:id>/", LeaveSessionView.as_view(), name="leavesession"),
    path("vjsession/queue/<str:id>/", QueueSongView.as_view(), name="queuesong"),
    path("vjsession/upvote/<str:id>/", UpvoteSongView.as_view(), name="upvotesong"),
    path("vjsession/get/user/<str:id>/", RetrieveUserSessionView.as_view(), name="getsessionuser"),
    path("vjsession/insession/<str:id>/", InSessionView.as_view(), name="insession"),
    path("vjsession/addtoken/<str:id>/", AddTokenView.as_view(), name="addtoken"),
    path("vjsession/user/close/", CloseUserSessionView.as_view(), name="closeusersession"),
    path("vjsession/user/leave/", LeaveUserSessionView.as_view(), name="leaveusersession"),
    path("vjsession/user/insession/", UserInSessionView.as_view(), name="userinsession"),
    path("vjsession/taken/", SessionTakenView.as_view(), name="sessiontaken"),
    path("vjsession/playsong/<str:id>/", PlaySongView.as_view(), name="playsong"),
    path("vjsession/public/inrange/", InRangeSessionsView.as_view(), name="sessioninrange"),
    path("vjsession/exists/<str:id>/", SessionExistsView.as_view(), name="sessionexists"),
    path("vjsession/public/distance/", DistanceSessionsView.as_view(), name="sessionsdistance"),
]