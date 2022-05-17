package com.example.main_fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class WorkOutFull extends AppCompatActivity {

    private static final long Start_time = 31000;
    private static final long Start_timesec = 5000;

    private TextView textViewtimeall;
    private TextView textViewtimesec;

    private CountDownTimer timerall;
    private CountDownTimer timersec;

    private long mtimeleft = Start_time;
    private long mtimeleftsec = Start_timesec;

    int position=0;

    boolean runinng = false;

    WebView webVieww;
    TextView textViewDescription;

    ImageView imageViewBack;
    ImageView imageViewStart;

    String description[]=null;
    String Time[]=null;
    private void starttimer1(){
        timerall = new CountDownTimer(mtimeleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mtimeleft = millisUntilFinished;
                updatecounterdowntext1();
            }
            @Override
            public void onFinish() {

            }
        }.start();
        runinng =true;
    }
    private void starttimer2() {
        timersec = new CountDownTimer(mtimeleftsec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mtimeleftsec = millisUntilFinished;

                updatecounterdowntext2();
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void updatecounterdowntext1(){
        int seconds = (int)(mtimeleft/1000%60-1);
        String time = String.format(Locale.getDefault(),"%02d",seconds);
        textViewtimeall.setText(time);
        if(textViewtimeall.getText().toString().contains("03")){
            textViewtimesec.setVisibility(View.VISIBLE);
            starttimer2();
        }
    }
    private void updatecounterdowntext2() {
        int seconds = (int) (mtimeleftsec / 1000 % 60 - 1);
        String time = String.format(Locale.getDefault(), "%01d", seconds);
        textViewtimesec.setText(time);
        if (textViewtimesec.getText().toString().contains("0")) {
            textViewtimesec.setVisibility(View.INVISIBLE);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out_full);
        textViewtimeall = findViewById(R.id.textViewtimer);
        textViewtimesec = findViewById(R.id.textViewtimerend);

        Time =getResources().getStringArray(R.array.time);
        description =getResources().getStringArray(R.array.Description);
        textViewDescription = findViewById(R.id.texdescription);


        imageViewBack =  (ImageView)findViewById(R.id.imageViewback);
        imageViewStart =  (ImageView)findViewById(R.id.imageViewStart);
        webVieww = findViewById(R.id.webViewfull);

        position = getIntent().getIntExtra("position",0);
        textViewDescription.setText(description[position]);
        textViewtimeall.setText(Time[position]);
        WebSettings websettings =webVieww.getSettings();
        String PathGif = "file:android_asset/" + "t" +  position+position + ".gif";

        webVieww.loadUrl(PathGif);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(WorkOutFull.this,WorkOut.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

            }
        });
        imageViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Time[position].contains("Sec")){
                    if(!runinng){
                        starttimer1();
                    }
                }
                else{
                    textViewtimeall.setText(Time[position]);
                }
            }
        });
    }
    }
