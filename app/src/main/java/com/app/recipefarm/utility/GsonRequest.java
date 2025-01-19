package com.app.recipefarm.utility;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<RequestType, ResponseType> extends Request<ResponseType> {
    private final Class<ResponseType> responseClass;
    private final Map<String, String> headers;
    private final RequestType requestBody;
    private final Gson gson = new Gson();
    private final Response.Listener<ResponseType> listener;

    public GsonRequest(int method, String url, RequestType requestBody, Class<ResponseType> responseClass,
                       Map<String, String> headers, Response.Listener<ResponseType> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.responseClass = responseClass;
        this.headers = headers;
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(ResponseType response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<ResponseType> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, responseClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new com.android.volley.ParseError(e));
        }
    }

    @Override
    public byte[] getBody() {
        if (requestBody != null) {
            return gson.toJson(requestBody).getBytes();
        }
        return null;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }
}