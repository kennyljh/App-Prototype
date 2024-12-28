package com.careerconnect.app.Global;

import java.util.Base64;

/**
 * Allows for the conversion of image data from byte array to
 * base 64 string, and vice versa
 */
public class ImageDataConverter {

    /**
     * Converts byte[] to Base 64 String
     * @param byteArray given byte[]
     * @return Base 64 String
     */
    public static String byteArrayToBase64(byte[] byteArray){

        if (byteArray == null){
            return null;
        }
        else {
            return Base64.getEncoder().encodeToString(byteArray);
        }
    }

    /**
     * Converts Base 64 String to byte[]
     * @param base64 given Base 64 String
     * @return byte[]
     */
    public static byte[] base64ToByteArray(String base64){

        if (base64 == null){
            return null;
        }
        else {
            return Base64.getDecoder().decode(base64);
        }
    }
}
