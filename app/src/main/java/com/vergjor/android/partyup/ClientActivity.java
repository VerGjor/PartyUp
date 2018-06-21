package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class ClientActivity extends AppCompatActivity {

    static boolean music;
    android.support.v4.view.ViewPager eventSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        eventSlider = (android.support.v4.view.ViewPager) findViewById(R.id.eventSlider);
        EventSliderAdapter eventSliderAdapter = new EventSliderAdapter(this);
        eventSlider.setAdapter(eventSliderAdapter);
        music=true;

        Intent svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (music==true){
            Intent svc=new Intent(this, BackgroundSoundService.class);
            stopService(svc);

            music=false;
        }
        else
        {
            Intent svc=new Intent(this, BackgroundSoundService.class);
            startService(svc);

            music=true;

        }
        return true;
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run(){

            ClientActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run(){
                   if(eventSlider.getCurrentItem() == 0){
                        eventSlider.setCurrentItem(1);
                    }
                    else if(eventSlider.getCurrentItem() == 1) {
                        eventSlider.setCurrentItem(2);
                    }
                    else
                        eventSlider.setCurrentItem(0);
                }
            });
        }
    }


    public void getUserData(View view) {
        Intent getProfile;
        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        if(db.userInfoDao().getUserType() == 1){
            getProfile = new Intent(this, ClientProfileActivity.class);
        }
        else{
            getProfile = new Intent(this, OwnerProfileActivity.class);
        }
        db.close();
        startActivity(getProfile);
    }


    public void getPartyList(View view){
        Intent partyList = new Intent(this, PartyListActivity.class);
        startActivity(partyList);
    }

}
