from django.contrib import admin

# Register your models here.
from .models import User

# Register VJ user model to admin page
admin.site.register(User)