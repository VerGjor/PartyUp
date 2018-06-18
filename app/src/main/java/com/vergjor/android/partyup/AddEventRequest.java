package com.vergjor.android.partyup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaltrinI on 17.06.2018.
 */

public class AddEventRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://mpip.000webhostapp.com/AddEvent.php";
    private Map<String,String> params;

    AddEventRequest(String e_name, String tax, String url, String rez, String time,  Response.Listener<String> listener){

        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        try {
            params.put("picture", URLEncoder.encode(url,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("e_name", e_name);
        params.put("b_tax", tax);
        params.put("time", time);
        params.put("reservations",rez);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

