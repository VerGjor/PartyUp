package com.vergjor.android.partyup;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;

import org.json.JSONException;
import org.json.JSONObject;

public class AddEventActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMG = 1;
    @SuppressLint("StaticFieldLeak")
    static EditText e_name;
    @SuppressLint("StaticFieldLeak")
    static EditText nr_R;
    @SuppressLint("StaticFieldLeak")
    static Button subm;
    @SuppressLint("StaticFieldLeak")
    static Button imagepick;
    static String url;
    static String eventDate;
    static String eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        e_name = findViewById(R.id.txteventname);
        nr_R=findViewById(R.id.txtReservInt);
        subm=findViewById(R.id.btnSubmitAdd);
        imagepick=findViewById(R.id.btnImagePick);
        eventDate = "24.06.2018";
        eventTime = "00:00:00";

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
                                finish();
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

                UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                        UserDatabase.class, "user-database").allowMainThreadQueries().build();

                AddEventRequest registerRequest = new AddEventRequest(e_name.getText().toString(), db.userInfoDao().getTaxOfOwner() ,url ,nr_R.getText().toString(), responseListener);
                db.userInfoDao().insertNewCreatedEvent(new OwnerCreatedEvents(e_name.getText().toString(),eventDate, eventTime, db.userInfoDao().getTaxOfOwner(), nr_R.getText().toString()));
                db.close();

                RequestQueue queue = Volley.newRequestQueue(AddEventActivity.this);
                queue.add(registerRequest);




            }
        });


        imagepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, RESULT_LOAD_IMG);
                }

            }


            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                AddEventActivity.super.onActivityResult(requestCode,resultCode,data);


                if (resultCode == RESULT_OK) {
                    Uri fullPhotoUri = data.getData();

                    url = MediaManager.get().upload(fullPhotoUri).dispatch();


                }
            }
        });
    }
}

