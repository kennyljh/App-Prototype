package com.example.careerconnect.Global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageConverter {

    /**
     * Converts image URI to a bitmap representing the original image data
     *
     * @param imageUri The URI of the image to be converted. This should be a content URI that point
     *                 to an image resource accessible through the content resolver.
     * @param context given application context
     * @return  A bitmap representing the image data, or null if the conversion fails.
     * @throws IOException If an I/O error occurs while reading from the InputStream.
     */
    public static Bitmap imageUriToBitmap(Uri imageUri, Context context){

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts a bitmap representing some image to a Base 64 encoded byte array in string
     * @param bitmap given bitmap
     * @return base 64 encoded byte array in string
     */
    public static String bitmapToBase64EncodedString(Bitmap bitmap){

        if (bitmap == null){
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * Converts a base 64 encoded byte array representing some image to a Bitmap
     * @param base64Encode given base 64 encoded byte array
     * @return Bitmap of image
     */
    public static Bitmap base64EncodedStringToBitmap(String base64Encode){

        if (base64Encode == null){
            return null;
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64Encode);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}
