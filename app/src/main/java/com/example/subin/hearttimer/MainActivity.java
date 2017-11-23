package com.example.subin.hearttimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView timerText;
    SeekBar seekTimer;
    Button timerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){  //this is to reset the time to intial value

        timerText.setText("0:30"); //settint he text back
        seekTimer.setProgress(30);//so that if Go is pressed the timer ll start from 30
        countDownTimer.cancel(); //so that countdown doest execute
        timerButton.setText("Go!"); //setting the button text back to go
        seekTimer.setEnabled(true); //since we disabled , it is to enable the seekbar
        counterIsActive = false; //so that the counter is active
    }

    public void updateTimer(int secondsLeft){ //to update time in the text for every second, the value is from the control time function

        int minutes = (int) secondsLeft / 60 ; //to update the minutes tab,
        int seconds = secondsLeft - minutes * 60; //to update the seconds tab

        String secondString = Integer.toString(seconds); //this is th eseconds tab that is changed to string


        if (seconds <= 9){ //or th eright side if the value i sin single digit

            secondString = "0" + secondString; //add a 0 to the left
        }
        timerText.setText(Integer.toString(minutes)+ ":" + secondString); //present it on the timer text
    }

    public void controlTimer(View view){
        if (counterIsActive == false) { //if false then enter in
            counterIsActive = true; //get the counter to be true so whne button clicked nbo action is performed
            seekTimer.setEnabled(false); //seekbar is disabled
            timerButton.setText("Stop!"); //time button text to be set to stop

            countDownTimer = new CountDownTimer(seekTimer.getProgress() * 1000, 1000) { //the progess in the seekbar and the value muliplied by 100 to generate millisecs, interval is every 1s=1000ms
                @Override
                public void onTick(long l) { //the progress number is presented as long integer
                    updateTimer((int) l / 1000); // this value divide by 1000 to get it in secs now sent to update timer
                }

                @Override
                public void onFinish() {//once the timer is finished

                    resetTimer(); //go to this function
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ipl1); //play this song
                    mediaPlayer.start(); //start the music

                }
            }.start(); //start the countdown timer function

        } else {

            resetTimer(); //if stop button is pressed this has to be shown

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekTimer = (SeekBar) findViewById(R.id.timerSeek); //get the seekbar
        timerText = (TextView) findViewById(R.id.timerText);// get the text
        timerButton = (Button) findViewById(R.id.timerButton);//get the button

        seekTimer.setMax(600); //setting the timer max limit to 10 minutes...which is expressed in seconds
        seekTimer.setProgress(30); //progress is to tell the seekbar to be intially in 30

        seekTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //when the seekbar is changed
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { //on the progress is changed

                updateTimer(i); //go to this function and update the timer to the value

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
}
}
