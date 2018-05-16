package com.vergjor.android.partyup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class ClientActivity extends AppCompatActivity {


    android.support.v4.view.ViewPager eventSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        eventSlider = (android.support.v4.view.ViewPager) findViewById(R.id.eventSlider);
        EventSliderAdapter eventSliderAdapter = new EventSliderAdapter(this);
        eventSlider.setAdapter(eventSliderAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
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



    public void getPartyList(View view){
        Intent partyList = new Intent(this, PartyListActivity.class);
        startActivity(partyList);
    }

}
