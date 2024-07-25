## User API Endpoints
### Note: \<uuid\> refers to User ID, invalid body returns 400

| URL    | Request | Description  | Body    | Response | Response Success (JSON Keys)|
| ----------- | ----------- |----------- | ----------- | ----------- | ----------- |
| /user/create/          | POST   | Creates a user given a username<br> |{'username': \<username\>} |<ul><li>201 - created</li><li>409 - username taken</li></ul>|'id', 'created_at', 'updated_at', 'deleted_at', 'is_active', 'username'|
| /user/get/\<uuid\>/    | GET    | Returns an active user<br> |-|<ul><li>200 - success</li><li>404 - user not found</li></ul>|'id', 'created_at', 'updated_at', 'deleted_at', 'is_active', 'username'|
| /user/logout/\<uuid\>/ | DELETE |  Marks a user as inactive in the db |-|<ul><li>200 - success</li><li>404 - user not found</li></ul>|-|
| /user/change/\<uuid\>/ | PATCH  | Changes a user's name | {'username': \<username\>}|<ul><li>200 - success</li><li>409 - username taken</li><li>404 - user not found</li></ul>|-|
| /user/taken/ | GET  | Returns whether or not a username is taken. |(as header)<br>{'username': \<username\>}|<ul><li>200 - success</li></ul>|'name_taken'|
|||||||


## VJ Session API Endpoints
### Note: \<uuid\> refers to VJ Session ID, invalid body returns 400
#### \<VJ_SESSION\> refers to the following JSON keys: 

- 'id', 
- 'host_id', 
- 'created_by', 
- 'updated_by', 
- 'created_by', 
- 'deleted_at', 
- 'is_active', 
- 'is_private', 
- 'session_name', 
- 'session_description',
- 'session_password', 
- 'session_location', 
- 'connection_range'

### Note: \<x\> refers to longitude, and \<y\> refers to latitude

| URL    | Request | Description  | Body    | Response | Response Success (JSON Keys)|
| ----------- | ----------- |----------- | ----------- | ----------- | ----------- |
| /vjsession/public/          | GET   |Returns all public active VJ sessions<br>|-|<ul><li>200 - success</li></ul>|List of \<VJ_SESSION\>|
| /vjsession/public/inrange/  | GET   |Returns all public active VJ sessions in range of given coordinates, and the distance to them<br>|{'user_location':'POINT(\<x\> \<y\>)'}|<ul><li>200 - success</li></ul>|List of <br>{'session': \<VJ_SESSION\>,<br>'distance_to': \<metres\>|
| /vjsession/public/distance/  | GET   |Returns all public active VJ sessions and the distance to each of them<br>|{'user_location':'POINT(\<x\> \<y\>)'}|<ul><li>200 - success</li></ul>|List of <br>\<VJ_SESSION\>, 'distance_to': \<metres\>|
| /vjsession/get/\<uuid\>/    | GET   |Returns an active VJ session<br> |-|<ul><li>200 - success</li><li>404 - session not found</li></ul>|'session': \<VJ_SESSION\>,<br>'playlist_songs',<br>'queue',<br>'playing'|
| /vjsession/get/user/\<uuid\>/    | GET   |Returns user (incl. credits) and session info of a user in a session.<br>|(as header)<br>{'user_id':\<user_id\>}|<ul><li>200 - success</li><li>404 - session or user not found, or user not in session</li></ul>|'session': \<VJ_SESSION\>,<br>'playlist_songs',<br>'queue',<br>'session_user': {'credits', 'join_time', 'username'},<br>'playing'|
| /vjsession/insession/\<uuid\>/    | GET   |Returns status of whether a user is in a session, and the session's privacy.<br>|(as header)<br>{'user_id':\<user_id\>}|<ul><li>200 - success</li><li>404 - session or user not found</li></ul>|'in_session',<br>'is_private'|
| /vjsession/user/insession/    | GET   |Returns status of whether a user is in any session (either as guest or host) without the need of a session ID.<br>|(as header)<br>{'user_id':\<user_id\>}|<ul><li>200 - success</li><li>404 - User not found (i.e. marked as inactive or doesn't exist) </li></ul>|'in_session',<br>'is_host',<br>'session_id'|
| /vjsession/join/\<uuid\>/   | PUT  |Joins a VJ session as a guest|{'guest_id':\<guest_id\>,<br>'session_password':\<password\>,<br>'session_location':'POINT(\<x\> \<y\>)'}|<ul><li>200 - success</li><li>401 - incorrect password, or not in range (check error message)</li><li>404 - session or guest id not found</li><li>409 - already a guest or a host (check error message)</li></ul>|'session': \<VJ_SESSION\>,<br>'playlist_songs',<br>'queue'|
| /vjsession/create/          | POST |Creates a VJ session from the body |{'host_id':\<host_id\>,<br>'is_private':\<True/False\>,<br>'session_name':\<session_name\>,<br>'session_description':\<session_desc\>,<br>'session_password':\<password\>,<br>'session_location':'POINT(\<x\> \<y\>)',<br>'connection_range':\<connection_range\>,<br>'spotify_playlist_id':\<spotify_playlist_id\>,<br>'access_token':\<access_token\>}|<ul><li>201 - created</li><li>409 - already hosting, already a guest, or session name taken (check error message)</li><li>404 - No valid songs exist in the playlist.</li></ul>|'session': \<VJ_SESSION\>,<br>'playlist_songs',<br>'queue',<br>'playing'|
| /vjsession/join/\<uuid\>/   | PUT  |Joins a VJ session as a guest|{'guest_id':\<guest_id\>,<br>'session_password':\<password\>,<br>'session_location':'POINT(\<x\> \<y\>)'}|<ul><li>200 - success</li><li>401 - incorrect password, or not in range (check error message)</li><li>404 - session or guest id not found</li><li>409 - already a guest or a host (check error message)</li></ul>|'session': \<VJ_SESSION\>,<br>'playlist_songs',<br>'queue',<br>'playing'|
| /vjsession/close/\<uuid\>/  | DELETE  |Closes a VJ session as a host|{'host_id':\<host_id\>}|<ul><li>200 - success</li><li>404 - wrong host id or session id</li></ul>|-|
| /vjsession/leave/\<uuid\>/  | PUT |Leaves a VJ session as a guest|{'guest_id':\<guest_id\>}|<ul><li>200 - success</li><li>404 - wrong guest or session id</li><li>409 - guest is not in the provided session</li></ul>|-|
| /vjsession/user/close/  | DELETE  |Closes a VJ session as a host without providing the session.|{'host_id':\<host_id\>}|<ul><li>200 - success</li><li>404 - host id doesn't exist, or the user isn't hosting any session (check error message).</li></ul>|-|
| /vjsession/user/leave/  | PUT |Leaves a VJ session as a guest without providing the session.|{'guest_id':\<guest_id\>}|<ul><li>200 - success</li><li>404 - guest id doesn't exist, or the user isn't a guest in any session  (check error message).</li></ul>|-|
| /vjsession/queue/\<uuid\>/  | PUT |Adds a song to the queue given the user has enough credits|{'user_id':\<user_id\>,<br>'spotify_song_id': \<song_id\>,<br>'time_added': \<int\>}|<ul><li>200 - success</li><li>404 - user id or session id does not exist, or invalid song id</li><li>401 - host or guest does not have enough credits, or not in session</li><li>409 - song already in the queue</li></ul>|(success message)<br>'success'|
| /vjsession/upvote/\<uuid\>/  | PUT |Up-votes a song in a VJ session song queue.|{'user_id':\<user_id\>,<br>'spotify_song_id': \<song_id\>}|<ul><li>200 - success</li><li>404 - wrong user or session id, or invalid song id</li><li>401 - user not in session</li><li>409 - song is not in the queue</li></ul>|(success message)<br>'success'|
| /vjsession/addtoken/\<uuid\>/  | PUT |Increases the credit count of a user by 1 in a VJ session.|{'user_id':\<user_id\>}|<ul><li>200 - success</li><li>404 - wrong user or session id</li><li>401 - user not in session</li></ul>|(success message)<br>'success'|
| /vjsession/taken/  | GET |Returns whether or not a session name is taken.|(as header)<br>{'session_name':\<session_name\>}|<ul><li>200 - success</li></ul>|'name_taken'|
| /vjsession/playsong/\<uuid\>/  | PUT |Plays a song in a session by removing it from the cache queue, and settings it as playing.|{'spotify_song_id': \<song_id\>}|<ul><li>200 - success</li><li>404 - session doesn't exist, or song not in playlist (check error message)</li></ul>|(success message)<br>'success'|
| /vjsession/exists/\<uuid\>/  | GET |Returns whether or not a sesssion exists by its ID.|-|<ul><li>200 - success</li></ul>|{'exists':<true/false>}|
|||||||
