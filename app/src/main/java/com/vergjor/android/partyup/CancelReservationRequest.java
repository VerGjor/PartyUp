package com.vergjor.android.partyup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaltrinI on 17.06.2018.
 */

public class CancelReservationRequest extends StringRequest {

    private static final String CANCEL_REQUEST_URL = "https://mpip.000webhostapp.com/deleteReservation.php";
    private Map<String,String> params;

    CancelReservationRequest(String clname, String ename, String btax, Response.Listener<String> listener){

        super(Method.DELETE, CANCEL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("cl_name", clname);
        params.put("e_name", ename);
        params.put("b_tax", btax);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

