package com.example.user.eggtimer;


import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView timerView;
    SeekBar seekBar;
    Button controlButton;
    boolean counterIsActive = false;
    CountDownTimer countTimer;

    public void updateTimer(int s)
    {
        int minutes = s/60;
        int seconds = s-(minutes*60);
        String minute = Integer.toString(minutes);
        String second = Integer.toString(seconds);
        String smallSecond = second;
        if(seconds<=9)
        {
            smallSecond="0"+second;
        }
        timerView.setText(minute+":"+smallSecond);
    }

    public void controlTimer(View view)
    {
        if(counterIsActive==false)
        {
            counterIsActive=true;
            seekBar.setEnabled(false);
            controlButton.setText("Stop!");
            countTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish()
                {
                    timerView.setText("0:00");
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    timerView.setText("0:10");
                    seekBar.setProgress(10);
                    seekBar.setEnabled(true);
                    controlButton.setText("Go!");
                }
            }.start();

        }
        else
        {
            countTimer.cancel();
            timerView.setText("0:10");
            seekBar.setProgress(10);
            seekBar.setEnabled(true);
            controlButton.setText("Go!");
            counterIsActive=false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlButton= (Button)findViewById(R.id.startButton) ;
        timerView =(TextView) findViewById(R.id.timerView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

    }
}
