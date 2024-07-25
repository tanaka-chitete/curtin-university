# File for scheduled cron jobs regarding Virtual Jukebox users.
from django.core.cache import cache
import logging
from .models import User
from vjsessions.models import SessionHelper
from datetime import datetime, timedelta
import pytz

logger = logging.getLogger("cron")

# Cron job to run once every hour to mark inactive users as inactive in the db.
# Inactive is defined as not calling join/create or leave/close session endpoints 
# for a day, while not being in a Virtual Jukebox session.
def clearInactiveUsers():
    logger.info(" : ClearInactiveUsers was run.")

    # Get active users that have not made session actions (join/create and leave/close) 
    # actions for a day
    yesterday = datetime.now(pytz.utc) - timedelta(days=1)
    active_users = User.objects.filter(is_active=True, updated_at__lte=yesterday)
    
    if active_users.exists():
        for user in active_users:
            # If the user is not in a session
            if not SessionHelper.alreadyInSession(user.id):
                # Set user as inactive in db
                user.is_active = False
                user.deleted_at = datetime.now(pytz.utc)
                user.save()

                logger.info("User: " + str(user) + " was cleared due to inactivity.")