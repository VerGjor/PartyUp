package com.vergjor.android.partyup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_GET = 1;
    final EditText e_name=findViewById(R.id.txteventname);
    final EditText nr_R=findViewById(R.id.txtReservInt);
    final Button subm=findViewById(R.id.btnSubmitAdd);
    final Button imagepick=findViewById(R.id.btnImagePick);
    String url;
    final UserDatabase db = Room.databaseBuilder(getApplicationContext(),
            UserDatabase.class, "user-database").allowMainThreadQueries().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);



        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success= jsonResponse.getBoolean("success");
                            if (success){
                                Intent intent = new Intent(AddEventActivity.this, OwnerProfileActivity.class);
                                AddEventActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
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

                AddEventRequest registerRequest = new AddEventRequest(e_name.getText().toString(),"111111111111" ,url ,nr_R.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddEventActivity.this);
                queue.add(registerRequest);



            }
        });


        imagepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }

            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
                    Uri fullPhotoUri = data.getData();

                    Cloudinary cloudinary = new Cloudinary();
                    try {
                        Map result=cloudinary.uploader().upload(fullPhotoUri, ObjectUtils.emptyMap());

                        url=result.get("url").toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}

