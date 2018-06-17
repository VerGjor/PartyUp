package com.vergjor.android.partyup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaltrinI on 17.06.2018.
 */

public class AddEventRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://mpip.000webhostapp.com/AddEvent.php";
    private Map<String,String> params;
    public AddEventRequest(String e_name, String tax, String url, String rez, Response.Listener<String> listener){

        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("e_name", e_name);
        params.put("b_tax", tax);
        params.put("picture", url);
        params.put("reservations",rez);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

