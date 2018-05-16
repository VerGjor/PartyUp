package com.vergjor.android.partyup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.iv);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splashtransition);
        imageView.startAnimation(anim);
        final Intent client = new Intent(this, ClientActivity.class);
        final Intent register = new Intent(this, PreLoginActivity.class);
        Thread timer = new Thread(){
          public void run(){
              try{
                  sleep(4000);
              }
              catch (InterruptedException e){
                  e.printStackTrace();
              }
              finally {
                  SharedPreferences pref;
                  SharedPreferences.Editor editor;
                  pref = getSharedPreferences("testapp", MODE_PRIVATE);
                  editor = pref.edit();

                  if(pref.contains("register"))
                  {
                      String getStatus=pref.getString("register", "nil");
                      if(getStatus.equals("true")){
                          startActivity(client);
                      }else{
                          //first time
                          editor.putString("register","true");
                          editor.commit();
                          ///  show registration page again
                          startActivity(register);
                      }
                  }
                  else{ //first time
                      editor = pref.edit();
                      editor.putString("register","true");
                      editor.commit();
                      ///  show registration page again
                      startActivity(register);
                  }
                  finish();
              }
          }
        };

        timer.start();
    }


}
