package com.example.main_fitness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class stepcounter extends AppCompatActivity {
    private TextView textView;
    private  double magnitudePrevious=0;
    public Integer stepCount=0;
    ImageView back;
    Button stepfoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepcounter);
        textView= findViewById(R.id.counter);
        back=(ImageView)findViewById(R.id.imageView);
        // bundle=new Bundle();
        stepfoot=(Button)findViewById(R.id.Foot);
        SensorManager sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepDetecor= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!=null)
                {
                    float X_acceleration = sensorEvent.values[0];
                    float Y_acceleration = sensorEvent.values[1];
                    float Z_acceleration = sensorEvent.values[2];
                    double magnitude=Math.sqrt(X_acceleration*X_acceleration+Y_acceleration*Y_acceleration+Z_acceleration*Z_acceleration);
                    double magnitudeDelta= magnitude-magnitudePrevious;
                    magnitudePrevious=magnitude;
                    if(magnitudeDelta>6)
                    {
                        stepCount++;
                    }
                    textView.setText(stepCount.toString());
                    System.out.println("Step foot is"+textView.getText().toString());
                    stepfoot.setText("Foot Step :"+textView.getText().toString());
                    // BodyCheck fotbutton=new BodyCheck();
                    // fotbutton.FootStep.setText("Foot Step"+textView.getText().toString());



                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(stepDetecor,sensor,sensorManager.SENSOR_DELAY_NORMAL);


    }
    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepcount",stepCount);
        editor.apply();

    }

    protected void onStop()
    {
        super.onStop();
        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepcount",stepCount);
        editor.apply();

    }

    protected void onResume()
    {
        super.onResume();
        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        stepCount=sharedPreferences.getInt("step count",0);

    }
}