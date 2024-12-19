package com.example.careerconnect.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyJSONObjectRequests {

    /**
     * Make a Volley JSONObject GET Request
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public static void makeVolleyJSONObjectGETRequest(Context context, String URL, final VolleyJSONObjectCallback callback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        Log.d("Volley JSONObject GET Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onJSONObject(response);
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONObject GET Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONObject GET Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONObject GET Error", "Response Body: " + responseBody);
                        }

                        // Pass failure to the callback
                        callback.onJSONObject(null);
                        callback.onResult(false);
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Make a Volley JSONObject POST Request
     * @param jsonBody Given JSONObject
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyJSONObjectPOSTRequest(JSONObject jsonBody, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY JSONObject POST REQUEST SENT ITEM", String.valueOf(jsonBody));
        final String mRequestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        Log.d("Volley String POST Response", String.valueOf(response));

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
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.d("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Make a Volley JSONObject PUT Request
     * @param jsonBody Given JSONObject
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyJSONObjectPUTRequest(JSONObject jsonBody, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY JSONObject PUT REQUEST SENT ITEM", String.valueOf(jsonBody));
        final String mRequestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                URL,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        Log.d("Volley JSONObject PUT Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONObject PUT Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONObject PUT Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONObject PUT Error", "Response Body: " + responseBody);
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
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.d("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Make a Volley JSONObject DELETE Request
     * @param jsonBody Given JSONObject
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyJSONObjectDELETERequest(JSONObject jsonBody, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY JSONObject DELETE REQUEST SENT ITEM", String.valueOf(jsonBody));
        final String mRequestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                URL,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        Log.d("Volley JSONObject DELETE Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONObject DELETE Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONObject DELETE Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONObject DELETE Error", "Response Body: " + responseBody);
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
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.d("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Callback to account for the asynchronous property of
     * Volley requests
     */
    public interface VolleyCallback {

        void onResult(boolean result);
    }

    public interface VolleyJSONObjectCallback {

        void onResult(boolean result);

        void onJSONObject(JSONObject jsonObject);
    }
}
