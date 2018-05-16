package com.vergjor.android.partyup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        final Button owner = (Button) findViewById(R.id.btnOwner);
        owner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i=new Intent(PreLoginActivity.this,BusinessRegisterActivity.class);
                startActivity(i);
            }
        });

        final Button client = (Button) findViewById(R.id.btnClient);
        client.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i=new Intent(PreLoginActivity.this,ClientRegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
