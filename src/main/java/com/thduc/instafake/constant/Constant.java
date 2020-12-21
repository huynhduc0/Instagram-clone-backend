package com.thduc.instafake.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Component
public class Constant {

    public static final String JPEG_MEDIA_TYPE = "data:image/jpeg;base64";
    public static final String JPG_MEDIA_TYPE = "data:image/jpg;base64";
    public static final String PNG_MEDIA_TYPE = "data:image/png;base64";
    public static final String PDF_MEDIA_TYPE = "data:application/pdf;base64";
    //public static final String PDF_MEDIA_TYPE_SECOND = "data:@file/pdf;base64";
    //Maybe use later
    public static final String DOC_MEDIA_TYPE = "data:application/msword;base64";
    public static final String DOCX_MEDIA_TYPE = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64";

    public static final String NOT_BASE64_STRING = "Request base64 is in correct";
    public static final String INVALID_FILE_EXTENSION = "ACCEPT IMAGE ONLY";

    public static final String TBL_POSTS = "posts";
    public static final String TBL_HASHTAGS = "hagtags";

    public static final String TBL_USER = "user";
    public static final String TBL_USER_FOLLOW = "follows";

    public static final String COL_USER_FOLLOWINGS = "followings";
    public static final String COL_USER_FOLLOWERS = "followers";

    public static final String COL_ID = "id";
    public static final String COL_POSTS_ID = "postid";
    public static final String COL_HASHTAGS_ID = "hagtagid";

    public static final String TBL_POSTS_FILTER = "postsFilter";
    public static final String TBL_HASHTAGS_FILTER = "hashtagsFilter";
    public static final String TBL_USER_FILTER = "userFilter";
//    public static final String TBL_ROLES_FILTER = "rolesFilter";
    public static final String TBL_FOLLOWS_FILTER = "followsFilter";

    public static final String POSTS_HASHTAGS="hashtags";
    public static final String HASHTAGS_POSTS="posts";

    public static final String USER_FOLLOWERS="userfollower";
    public static final String USER_FOLLOWING="posts";

    public static final String FOLLOW_FROM = "from";
    public static final String FOLLOW_TO = "to";

    public static final int NUM_STEP_DATA = 10;

    public static final String FOLLOW_NOTI_MESSAGE = " has follow you";
    public static final String LIKE_NOTI_MESSAGE = " has like your post";
    public static final String COMMENT_NOTI_MESSAGE = " has comment on your post";
}
