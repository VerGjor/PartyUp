package com.vergjor.android.partyup;

/**
 * Created by KaltrinI on 09.05.2018.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterBusinessRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://mpip.000webhostapp.com/RegisterBusiness.php";
    private Map<String,String> params;
    public RegisterBusinessRequest(String bname,String tax,String phone,String addr,  Response.Listener<String> listener){

        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", bname);
        params.put("taxNR", tax);
        params.put("mob_tel", phone);
        params.put("location",addr);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
