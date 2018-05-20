package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        final EditText txtName=(EditText)findViewById(R.id.txtName);
        final EditText txtPhone=(EditText)findViewById(R.id.txtNumber);
        final EditText txtAddress=(EditText)findViewById(R.id.txtAdress);
        final EditText txtTax=(EditText)findViewById(R.id.txtTax);

        final UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();

        final Button submit = (Button) findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                final String name=txtName.getText().toString();
                final String tax=txtTax.getText().toString();
                final String phone=txtPhone.getText().toString();
                final String addr=txtAddress.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success= jsonResponse.getBoolean("success");
                            if (success){
                                db.userInfoDao().insertUser(new User(name));
                                db.close();
                                Intent intent = new Intent(BusinessRegisterActivity.this,BusinessActivity.class);
                                BusinessRegisterActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegisterActivity.this);
                                builder.setMessage("Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterBusinessRequest registerRequest = new RegisterBusinessRequest(name, tax, phone,addr, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BusinessRegisterActivity.this);
                queue.add(registerRequest);


            }
        });

    }
}
