from django.urls import path
from .views import CreateUserView, RetrieveUserView, UsernameChangeView, LogoutUserView, UserTakenView

urlpatterns = [
    path("user/create/", CreateUserView.as_view(), name="createuser"),
    path("user/get/<str:id>/", RetrieveUserView.as_view(), name="getuser"),
    path("user/logout/<str:id>/", LogoutUserView.as_view(), name="logoutuser"),
    path("user/change/<str:id>/", UsernameChangeView.as_view(), name="changeusername"),
    path("user/taken/", UserTakenView.as_view(), name="usertaken"),
]