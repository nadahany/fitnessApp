package com.example.main_fitness_app;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseForReg extends SQLiteOpenHelper {

    public static final String DataBase_Name="FinalFitnessDB.db";

    public static final String Table_Name="Fitness";
    public static final String Col_0="ID";
    public static final String Col_1="UserName";
    public static final String Col_3="Height";
    public static final String Col_2="Weight";
    public static final String Col_4="Password";
    public static final String Col_5="ConfirmPassword";
    public static final String Col_6="Email";
    public static final String Col_7="Age";
    public static final String Col_8="Sex";
    // public static final String Col_9="Typ
    //   public  static  final  String Col_9=

//    public Object Context;
/////



    public DataBaseForReg( Context context) {
        super(context,DataBase_Name,null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + Table_Name +
                        " ( "
                        + Col_0 + " INTEGER PRIMARY KEY,"
                        + Col_1 + " TEXT,"
                        + Col_4 + " TEXT,"
                        + Col_5 + " TEXT,"
                        + Col_6 + " TEXT,"
                        + Col_7 + " TEXT,"
                        + Col_3 + " TEXT,"
                        + Col_2 + " TEXT,"
                        + Col_8 + " TEXT)"
                // + Col_9 + " TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }
    public  long insertData( String username,String password, String cpassword, String email,String age, String weight, String height, String sex){
        ContentValues contentValues =new ContentValues();
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        contentValues.put(DataBaseForReg.Col_1,username);
        contentValues.put(DataBaseForReg.Col_3,height);
        contentValues.put(DataBaseForReg.Col_2,weight);
        //weight

        //height

        contentValues.put(DataBaseForReg.Col_4,password);
        contentValues.put(DataBaseForReg.Col_5,cpassword);
        contentValues.put(DataBaseForReg.Col_6,email);
        contentValues.put(DataBaseForReg.Col_7,age);
        contentValues.put(DataBaseForReg.Col_8,sex);


        long id=db.insert(DataBaseForReg.Table_Name, null,contentValues);
        db.close();

        return id;


    }
    public boolean CheckUser(String UserName,String password){
        String[] Columns={Col_0};
        SQLiteDatabase SDB= getReadableDatabase();
        String Selection =Col_1 + "=?" + " and " + Col_4 + "=?";
        String[] SelectionArgs ={UserName,password};

        Cursor cursor= SDB.query(Table_Name,Columns,Selection,SelectionArgs,null,null,null);
        int count=cursor.getCount();
        cursor.close();
        SDB.close();

        if(count > 0) {
            return true;
        }
        else{
        return  false;
    }

    }

    public  Cursor FetchData(){
        SQLiteDatabase db=getReadableDatabase();
        String[] rowdetails={Col_0,Col_1,Col_4,Col_5,Col_6,Col_7,Col_3,Col_2,Col_8};
        Cursor cursor=db.query(Table_Name,rowdetails,null,null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        db.close();
        return cursor;


    }

    public Cursor getData(String name)
    {
        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " +Table_Name + " where  " + Col_1+ " like?",new String[]{name});
        // Cursor cursor=db.query(Table_Name,new String[]{Col_0,Col_1,Col_4,Col_5,Col_6,Col_7,Col_3,Col_2,Col_8},Col_1 + "?",
        //       new String[]{String.valueOf(name)},null,null,null,null);
  /* if(cursor !=null){
       cursor.moveToFirst();

   }*/
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            cursor.close();
            return  cursor;

        }
        db.close();
        return null;





    }


    public Cursor getus(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor userdeteils=db.query(Table_Name,new String[]{Col_0,Col_1,Col_4,Col_5,Col_6,Col_7,Col_3,Col_2,Col_8},
                "UserName"+Col_1,null,null
                ,null,null,null);
        userdeteils.moveToFirst();
        db.close();
        return  userdeteils;





    }

    public List<String> GetAll(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        List<String> returnList=new ArrayList<>();
        String queryString = "SELECT * From " + Table_Name + " WHERE " +  Col_1 + "= ?" ;
        Cursor cursor = db.rawQuery(queryString,new String[]{name});
        if(cursor.moveToFirst()){

            do{
                String User_Name=cursor.getString(1);
                String User_Email=cursor.getString(4);
                String User_Age=cursor.getString(5);
                String User_Height=cursor.getString(6);
                String User_Weight=cursor.getString(7);
                String User_Sex=cursor.getString(8);

                returnList.add(User_Name);
                returnList.add(User_Email);
                returnList.add(User_Age);
                returnList.add(User_Height);
                returnList.add(User_Weight);
                returnList.add(User_Sex);




            }while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return returnList;
    }

    /*public boolean ifNumberExists(String strNumber)
    { SQLiteDatabase db=getReadableDatabase();

        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + Table_Name + " WHERE  " + ContactNo + " = '" + strNumber +"'";
        cursor= db.rawQuery(selectQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

   */
    public  boolean UpdateData(String name,String newweight,String newheight,String newAge,String newGender){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //height

        contentValues.put("Height",newheight);
        contentValues.put("Weight",newweight);
        contentValues.put("Age",newAge);
        contentValues.put("Sex",newGender);
        db.update(Table_Name,contentValues,   "UserName= ?",new String[]{name});

        db.close();
        return  true;



    }



}
