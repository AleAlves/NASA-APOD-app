package com.aleson.example.nasaapodapp.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by GAMER on 28/10/2017.
 */

public class HashUtils {

    public static String makeSHA1Hash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
            return hexStr;
        }catch (Exception e){
            return null;
        }
    }
}