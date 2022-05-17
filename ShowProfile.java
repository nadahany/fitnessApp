package com.example.main_fitness_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ShowProfile extends AppCompatActivity {
    TextView usernamee;
    TextView userEmail;
    TextView userage;
    TextView userheight;
    TextView userweight;
    TextView usersex;
    String tHeight;
    String tWeight;
    String tAge;
    String tGender;
    ListView userdata;
    ArrayAdapter<String> arrayAdapter;
    DataBaseForReg db;
    MainActivity maain;
    Register register;
    Bundle bundle;
    ImageView image;
    public Uri imageUri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageTask uploadtask;
    FirebaseAuth firebaseAuth;
    FirebaseUser user ;
    Button reset;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);


        //  userdata = (ListView) findViewById(R.id.list);

        db = new DataBaseForReg(getApplicationContext());
        image=(ImageView)findViewById(R.id.imageView3);

        back=(ImageView)findViewById(R.id.imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bodycheck=new Intent(ShowProfile.this,BodyCheck.class);
                bodycheck.putExtras(bundle);
                startActivity(bodycheck);
            }
        });

        // image.setImageURI(imageUri);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                chooseImage();

            }
        });

        BodyCheck b=new BodyCheck();

        bundle=getIntent().getExtras();
        System.out.println("Use is"+b.use);
        String use=bundle.getString("UserName");

        List<String> list=db.GetAll(use);
        register = new Register();
        Cursor cursor=db.getData(use);
        Cursor cursor1 = db.FetchData();



        for(int i=0;i<list.size();i++){
            System.out.println(i);
            System.out.println(list.get(0));
            System.out.println(list.get(1));
            System.out.println(list.get(2));
            System.out.println(list.get(3));
            System.out.println(list.get(4));
        }

        usernamee=((TextView)findViewById(R.id.x));
        usernamee.setText("Name:"+ list.get(0));

        userEmail=((TextView)findViewById(R.id.t));
        userEmail.setText("Email:"+ list.get(1));
        userage=((TextView)findViewById(R.id.e));
        userage.setText("Age:"+ list.get(2)+"Years");
        tAge=userage.getText().toString().trim();
        userheight=((TextView)findViewById(R.id.te));

        userheight.setText("Height:"+list.get(3)+"cm");
        tHeight=userheight.getText().toString().trim();
        userweight=((TextView)findViewById(R.id.v));
        userweight.setText("Weight:"+ list.get(4)+"kg");
        tWeight=userweight.getText().toString().trim();
        usersex=((TextView)findViewById(R.id.se));
        usersex.setText("Gender:"+ list.get(5));
        tGender=usersex.getText().toString().trim();




        if(list.isEmpty()){
            list = db.GetAll(use);

            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
            userdata.setAdapter(arrayAdapter);
        }
        reset=(Button)findViewById(R.id.resetprofile);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("Height",tHeight);
                bundle.putString("Weight",tWeight);
                bundle.putString("Age",tAge);
                bundle.putString("Gender",tGender);

                Intent ResetActivity= new Intent(ShowProfile.this,ResetProfile.class);
                ResetActivity.putExtras(bundle);
                startActivity(ResetActivity);
            }
        });
    }

    public  void  chooseImage(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 &&resultCode==RESULT_OK && data!=null&&data.getData()!=null){
            imageUri=data.getData();
            image.setImageURI(imageUri);
            uploadimage();

        }
    }

    public  void uploadimage(){


        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Upload Image ...");
        progressDialog.show();
        String randomkey= UUID.randomUUID().toString();
        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageReference.child("images/*").child(random() + ".jpg" );

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Object fileRef;
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(imageUri).into(image);
                            }
                        });
                        progressDialog.dismiss();
                        // Snakbar.make(findViewById(android.R.id.content),"Image Uploaded ",Snakbar.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Faild Upload",Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent =(100.00 *  taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Percentage : " + (int)progressPercent + "%" );
            }
        });

    }

    public  static String random(){
        Random generator=new Random();
        StringBuilder randomstringBuilder=new StringBuilder();
        int randomLength=generator.nextInt(10);
        char temChar;
        for (int i=0;i<randomLength;i++){
            temChar=(char)(generator.nextInt(96)+32);
            randomstringBuilder.append(temChar);
        }
        return randomstringBuilder.toString();

    }

}

