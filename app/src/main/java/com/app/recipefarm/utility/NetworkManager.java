package com.app.recipefarm.utility;import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class NetworkManager {
    private static NetworkManager instance;
    private RequestQueue requestQueue;
    private static Context context;

    private NetworkManager(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkManager shared(Context ctx) {
        if (instance == null) {
            instance = new NetworkManager(ctx.getApplicationContext());
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public <T> void post(String url, Map<String, String> headers, T body, Class<T> responseClass,
                         final ResponseCallback<T> callback) {
        GsonRequest<T> gsonRequest = new GsonRequest<>(
                Request.Method.POST,
                url,
                body,
                responseClass,
                headers,
                callback::onSuccess,
                error -> callback.onError(error.getMessage())
        );
        addToRequestQueue(gsonRequest);
    }

    public <T> void get(String url, Map<String, String> headers, Class<T> responseClass,
                        final ResponseCallback<T> callback) {
        GsonRequest<T> gsonRequest = new GsonRequest<>(
                Request.Method.GET,
                url,
                null,
                responseClass,
                headers,
                callback::onSuccess,
                error -> callback.onError(error.getMessage())
        );
        addToRequestQueue(gsonRequest);
    }

    public interface ResponseCallback<T> {
        void onSuccess(T response);
        void onError(String error);
    }
}
