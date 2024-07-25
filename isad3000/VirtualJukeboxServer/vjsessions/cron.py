# File for scheduled cron jobs regarding Virtual Jukebox sessions.
from django.core.cache import cache
import logging
from vjusers.models import User
from .models import SessionHelper, Session
from datetime import datetime, timedelta
import pytz

logger = logging.getLogger("cron")

# Cron job to run once every minute to mark sessions without active hosts as inactive.
def clearInactiveSessions():
    logger.info(" : ClearInactiveSessions was run.")

    # Get active sessions
    active_sessions = Session.objects.filter(is_active=True)
    three_hours_ago = (datetime.now(pytz.utc) - (timedelta(hours=3))).timestamp()

    if active_sessions.exists():
        for session in active_sessions:
            # If the host of the session is inactive
            if not session.host_id.is_active:
                # Remove the session from the cache and database (set as inactive)
                SessionHelper.removeSession(session)
                logger.info("Session: " + str(session) + " was cleared due to host being inactive.")
                continue
            

            if (int(session.updated_at.timestamp()) <= int(three_hours_ago)):
                # Clear all these sessions
                SessionHelper.removeSession(session)
                logger.info("Session: " + str(session) + " was cleared due to playsong not being called.")

