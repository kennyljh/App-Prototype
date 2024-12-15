package com.example.careerconnect.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Make Volley CURDL String requests
 */
public class VolleyStringRequests {

    /**
     * Make a Volley String GET Request
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyStringGETRequest(Context context, String URL, final VolleyCallback callback) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley String GET Response", response);
                        
                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley String GET Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley String GET Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley String GET Error", "Response Body: " + responseBody);
                        }
                        
                        // Pass failure to the callback
                        callback.onResult(false);
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * Make a Volley String POST Request
     * @param message Given String message
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyStringPOSTRequest(String message, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY STRING POST REQUEST SENT ITEM", message);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley String POST Response", response);

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley String POST Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley String POST Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley String POST Error", "Response Body: " + responseBody);
                        }

                        // Pass failure to the callback
                        callback.onResult(false);
                    }
                }
        ) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return message == null ? null : message.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.d("Unsupported Encoding while trying to get the bytes of %s using %s", message, "utf-8");
                    return null;
                }
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * Make a Volley String PUT Request
     * @param message Given String message
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyStringPUTRequest(String message, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY STRING PUT REQUEST SENT ITEM", message);

        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley String PUT Response", response);

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley String PUT Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley String PUT Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley String PUT Error", "Response Body: " + responseBody);
                        }

                        // Pass failure to the callback
                        callback.onResult(false);
                    }
                }
        ) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return message == null ? null : message.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.d("Unsupported Encoding while trying to get the bytes of %s using %s", message, "utf-8");
                    return null;
                }
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * Make a Volley String DELETE Request
     * @param message Given String message
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyStringDELETERequest(String message, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY STRING DELETE REQUEST SENT ITEM", message);

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley String DELETE Response", response);

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley String DELETE Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley String DELETE Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley String DELETE Error", "Response Body: " + responseBody);
                        }

                        // Pass failure to the callback
                        callback.onResult(false);
                    }
                }
        ) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return message == null ? null : message.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.d("Unsupported Encoding while trying to get the bytes of %s using %s", message, "utf-8");
                    return null;
                }
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * Callback to account for the asynchronous property of
     * Volley requests
     */
    public interface VolleyCallback {

        void onResult(boolean result);
    }
}
