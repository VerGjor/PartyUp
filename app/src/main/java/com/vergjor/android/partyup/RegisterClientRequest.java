package com.vergjor.android.partyup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterClientRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://mpip.000webhostapp.com/RegisterClient.php";
    private Map<String,String> params;
     public RegisterClientRequest(String name, String phone,  Response.Listener<String> listener){

         super(Method.POST, REGISTER_REQUEST_URL, listener, null);
         params = new HashMap<>();
         params.put("name", name);
         params.put("mob_tel", phone);
     }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

