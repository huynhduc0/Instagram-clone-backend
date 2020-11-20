package com.thduc.instafake.constant;

public class Constant {
    public static final String UPLOAD_PATH = System.getProperty("user.dir") + "/webapps/images/";

    public static final String JPEG_MEDIA_TYPE = "data:image/jpeg;base64";
    public static final String PNG_MEDIA_TYPE = "data:image/png;base64";
    public static final String PDF_MEDIA_TYPE = "data:application/pdf;base64";
    //public static final String PDF_MEDIA_TYPE_SECOND = "data:@file/pdf;base64";
    //Maybe use later
    public static final String DOC_MEDIA_TYPE = "data:application/msword;base64";
    public static final String DOCX_MEDIA_TYPE = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64";

    public static final String NOT_BASE64_STRING = "Request base64 is in correct";
    public static final String INVALID_FILE_EXTENSION = "ACCEPT IMAGE ONLY";
}
