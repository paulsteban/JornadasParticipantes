package com.edu.epn.jisicv01;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoguinFacebookRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://192.168.1.2:8080/LoginFacebook.php";
    private Map<String, String> params;

    public LoguinFacebookRequest(String facebook_id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("facebook_id", facebook_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
