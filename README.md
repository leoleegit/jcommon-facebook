jcommon-facebook
================

# 1. FacebookSession 
* postPhoto2Wall(RequestCallback callback, File file, String message, boolean start_upload) ;
* postVideo2Wall(RequestCallback callback, File file, String title, String description, boolean start_upload) ;
* postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description);
* postComment2Wall(RequestCallback callback, String post_id, String message);
* postPhoto2Wall(RequestCallback callback, File file, String message, String access_token, boolean start_upload);
* postVideo2Wall(RequestCallback callback, File file, String title, String description, String access_token, boolean start_upload) ;
* postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description, String access_token);
* postComment2Wall(RequestCallback callback, String post_id, String message, String access_token);
* deletePost4Wall(RequestCallback callback, String post_id);
* deleteComment4Wall(RequestCallback callback, String comment_id);
* replayMessage(RequestCallback callback, String message_id, String message) ;

2.
