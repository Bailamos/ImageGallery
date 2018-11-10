package com.example.kaczor.imagegallery.core.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IOnRequestFinished {

    void ActionOK(JSONObject jsonObject);
    void ActionERROR(VolleyError jsonObject);
}
