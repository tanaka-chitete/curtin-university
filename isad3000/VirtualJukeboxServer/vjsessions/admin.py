from django.contrib import admin

# Register your models here.
from .models import Session

# Register VJ session model to admin page
admin.site.register(Session)