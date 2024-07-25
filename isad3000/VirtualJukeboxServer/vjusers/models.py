from django.db import models
import uuid

# default: null=False (NOT NULL)

# Create your models here.
class User(models.Model):
    # Primary Key - UUID used to avoid leaking data such as total users and 
    # user id guessing
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(null=True)
    
    is_active = models.BooleanField(default=True)

    username = models.CharField(max_length=50)

    # VJ User as a string
    def __str__(self):
        return "ID: " + str(self.id) + " | VJ_USER: " + str(self.username)