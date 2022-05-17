package com.example.main_fitness_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class BodyCheck extends AppCompatActivity {
    ShowProfile showProfile;
    inbody_check inbodyCheck;
    Button bmi;
    Button Logout;
    Button profile;
    String use;
    Button calcalo;
    Bundle bundle;
    DataBaseForReg db;
    List<String> list;
    ImageView notificationImage;
    Button WorkOut;
    Button FootStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_check);
        showProfile=new ShowProfile();
        inbodyCheck=new inbody_check();
        db=new DataBaseForReg(this);
        calcalo=(Button)findViewById(R.id.Calo);
        bundle=getIntent().getExtras();
        use=bundle.getString("UserName");
        System.out.println(use);
        list=db.GetAll(use);
        //notificationImage=(ImageView)findViewById(R.id.notification);
        bmi=(Button)findViewById(R.id.Bmi);
        profile=(Button)findViewById(R.id.profile);

        String height=list.get(3);
        String weight=list.get(4);

        String Age=list.get(2);
        String Gender=list.get(5);






        System.out.println(height);
        System.out.println(showProfile.tAge);
        System.out.println(showProfile.usersex);
        float Height=  Float.parseFloat(height);
        float Weight=   Float.parseFloat(weight);
        int age=  Integer.parseInt(Age);
        // String  gender=showProfile.usersex.getText().toString();


        double calories=  inbodyCheck.Calc_Calories(Gender,Weight,Height,age);
        calcalo.setText("Your Daily calories: "+calories);
        fileAdapter.totalcal= calories ;
        calcalo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent = new Intent(BodyCheck.this,foodmain.class);
        startActivity(intent);
        }
        });
        double BMI= inbodyCheck.BMI_Calc(Weight,Height);
        bmi.setText("BMI: "+BMI);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShowActivity= new Intent(BodyCheck.this,ShowProfile.class);
                ShowActivity.putExtras(bundle);
                startActivity(ShowActivity);
            }
        });
        Logout=(Button)findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GOTOMainActivity=new Intent(BodyCheck.this,MainActivity.class);
                startActivity(GOTOMainActivity);
            }
        });

        WorkOut=(Button)findViewById(R.id.WorkOut);

        WorkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(BodyCheck.this,WorkOut.class);

                    startActivity(intent);
            }
        });

        FootStep=(Button)findViewById(R.id.FootStep);
        FootStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyCheck.this,stepcounter.class);

                startActivity(intent);
            }
        });



    }


}
