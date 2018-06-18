package com.vergjor.android.partyup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetAllEventsRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://mpip.000webhostapp.com/getReservations.php";
    private Map<String,String> params;
    public GetAllEventsRequest(String name, String tax, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("ename", name);
        params.put("btax", tax);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}