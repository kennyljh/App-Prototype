package com.example.careerconnect.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyJSONArrayRequests {

    /**
     * Make a Volley JSONArray GET Request
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public static void makeVolleyJSONArrayGETRequest(Context context, String URL, final VolleyJSONArrayCallback callback) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the successful response here
                        Log.d("Volley JSONArray GET Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onJSONArray(response);
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONArray GET Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONArray GET Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONArray GET Error", "Response Body: " + responseBody);

                            callback.onResult(statusCode >= 400 && statusCode < 500);
                        }
                        else {
                            callback.onResult(false);
                        }
                        // Pass failure to the callback
                        callback.onJSONArray(null);
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Make a Volley JSONArray POST Request
     * @param jsonBody Given JSONArray
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyJSONArrayPOSTRequest(JSONArray jsonBody, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY JSONArray POST REQUEST SENT ITEM", String.valueOf(jsonBody));
        final String mRequestBody = jsonBody.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                URL,
                jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the successful response here
                        Log.d("Volley JSONArray POST Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONArray POST Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONArray POST Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONArray POST Error", "Response Body: " + responseBody);
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
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Make a Volley JSONArray PUT Request
     * @param jsonBody Given JSONArray
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyJSONArrayPUTRequest(JSONArray jsonBody, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY JSONArray PUT REQUEST SENT ITEM", String.valueOf(jsonBody));
        final String mRequestBody = jsonBody.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.PUT,
                URL,
                jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the successful response here
                        Log.d("Volley JSONArray PUT Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONArray PUT Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONArray PUT Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONArray PUT Error", "Response Body: " + responseBody);
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
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Make a Volley JSONArray DELETE Request
     * @param jsonBody Given JSONArray
     * @param context Current application context
     * @param URL Given URL
     * @param callback VolleyCallback instance
     */
    public void makeVolleyJSONArrayDELETERequest(JSONArray jsonBody, Context context, String URL, final VolleyCallback callback) {

        Log.d("VOLLEY JSONArray DELETE REQUEST SENT ITEM", String.valueOf(jsonBody));
        final String mRequestBody = jsonBody.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                URL,
                jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the successful response here
                        Log.d("Volley JSONArray DELETE Response", String.valueOf(response));

                        // Pass success to the callback
                        callback.onResult(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley JSONArray DELETE Error", error.toString());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley JSONArray DELETE Error", "Status Code: " + statusCode);

                            // Log the response body (if any)
                            String responseBody = new String(error.networkResponse.data);
                            Log.e("Volley JSONArray DELETE Error", "Response Body: " + responseBody);
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
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Callback to account for the asynchronous property of
     * Volley requests
     */
    public interface VolleyCallback {

        void onResult(boolean result);
    }

    /**
     * JSONArray Callback to account for the asynchronous property
     * of Volley requests
     */
    public interface VolleyJSONArrayCallback {

        void onResult(boolean result);

        void onJSONArray(JSONArray jsonArray);
    }
}
