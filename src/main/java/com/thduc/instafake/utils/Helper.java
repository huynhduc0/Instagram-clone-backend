package com.thduc.instafake.utils;

import com.thduc.instafake.entity.HashTags;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static List<String> hashTagsStringFromString(String str){
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(str);
        List<String> strs=new ArrayList<String>();
        while (mat.find()) {
            //System.out.println(mat.group(1));
            strs.add(mat.group(1));
        }
        return strs;
    }
    public static String currentTime(String upload_type){
        Random rand = new Random();
        return upload_type +"_"+ new  SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date())+"_"+String.valueOf(rand.nextInt(1000));
    }
    public static String bcriptPassword(String password){
//        Password p = new Password();
        return new BCryptPasswordEncoder().encode(password);
    }
}
