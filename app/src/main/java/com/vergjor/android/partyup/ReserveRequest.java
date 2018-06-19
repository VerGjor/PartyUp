package com.vergjor.android.partyup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaltrinI on 17.06.2018.
 */

public class ReserveRequest extends StringRequest {


    private static final String REGISTER_REQUEST_URL = "https://mpip.000webhostapp.com/ReserveEvent.php";
    private Map<String, String> params;

    ReserveRequest(String cl_name, String event, String b_tax, Response.Listener<String> listener) {

        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("cl_name", cl_name);
        params.put("event", event);
        params.put("b_tax", b_tax);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
