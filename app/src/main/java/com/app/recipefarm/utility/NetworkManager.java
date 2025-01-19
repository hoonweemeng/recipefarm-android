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

    public static synchronized NetworkManager getInstance(Context ctx) {
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

    public <RequestType, ResponseType> void post(
            String url,
            Map<String, String> headers,
            RequestType requestBody,
            Class<ResponseType> responseClass,
            final ResponseCallback<ResponseType> callback
    ) {
        GsonRequest<RequestType, ResponseType> gsonRequest = new GsonRequest<>(
                Request.Method.POST,
                url,
                requestBody,
                responseClass,
                headers,
                callback::onSuccess,
                error -> callback.onError(error.getMessage())
        );
        getRequestQueue().add(gsonRequest);
    }

    public interface ResponseCallback<T> {
        void onSuccess(T response);
        void onError(String error);
    }
}
