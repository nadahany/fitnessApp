package com.example.main_fitness_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class WorkOut extends AppCompatActivity {


        String NameTringing[],description[];

        int images[]={};
        int imagesphoto[]={R.drawable.fitness1,R.drawable.fitness2,R.drawable.fitness3};

        private MyAdapter.RecylerViewClickListener listener;

        RecyclerView recyclerView;
        ViewFlipper viewFlipper;
        ImageView imageView;

        void setMyAdapter(){
            setOnClickListener();
            imageView =  (ImageView)findViewById(R.id.imageView7);
            MyAdapter myAdapter = new MyAdapter (this,NameTringing,description,images,listener);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        private void setOnClickListener(){
            listener = new MyAdapter.RecylerViewClickListener(){
                @Override
                public void OnClick(View view, int position) {

                    Intent intent = new Intent(WorkOut.this,WorkOutFull.class);

                    intent.putExtra("position",position);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                }
            };
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void view(int imageee){

            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageee);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(6000);//3 sec
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
            viewFlipper.setInAnimation(this,android.R.anim.slide_out_right);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_work_out);
            Bundle bundle = getIntent().getExtras();

            recyclerView = findViewById(R.id.recyclerview);
            viewFlipper = findViewById(R.id.ViewPhoto);
            recyclerView = findViewById(R.id.recyclerview);
            NameTringing =getResources().getStringArray(R.array.TrainingName);
            description =getResources().getStringArray(R.array.time);

            for(int i=0;i<imagesphoto.length;i++){
                view(imagesphoto[i]);
            }

            setMyAdapter();
        }

}
