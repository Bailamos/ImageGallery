package com.example.kaczor.imagegallery.handlers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestHandler {
    private static RequestHandler requestHandler;
    private RequestQueue requestQueue;
    private static Context Ctx;

    private RequestHandler(Context context) {
        Ctx = context;
        requestQueue = GetRequestQueue();
    }

    public static synchronized RequestHandler GetInstance(Context context) {
        if (requestHandler == null) {
            requestHandler = new RequestHandler(context);
        }

        return requestHandler;
    }

    public RequestQueue GetRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(Ctx.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void AddToRequestQueue(Request<T> request) {
        GetRequestQueue().add(request);
    }
}
