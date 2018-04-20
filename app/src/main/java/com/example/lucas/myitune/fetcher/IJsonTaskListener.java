package com.example.lucas.myitune.fetcher;

import org.json.JSONObject;

/**
 * Created by Lucas on 20/04/2018.
 */

public interface IJsonTaskListener {

    public void onRequestResult(JSONObject result, Exception e) ;
}
