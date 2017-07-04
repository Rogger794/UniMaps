package com.example.moviles.proyectomoviles;


import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminSQLite extends SQLiteOpenHelper{

    public static final String TAG = AdminSQLite.class.getSimpleName();


    public static final String DB_nombre = "db_unimaps.db";
    public static final int DB_version = 1;

    public static final String Users_table = "users";
    public static final String column_name = "names";
    public static final String column_lastname = "lastname";
    public static final String column_pass = "pass";
    public static final String column_email = "email";


//   Query para la tabla de usuarios
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + Users_table  + " ( "
            + column_name + " TEXT, "
            + column_lastname + " TEXT, "
            + column_email + " TEXT PRIMARY KEY, "
            + column_pass + " TEXT "
            + ");";


    public AdminSQLite(Context context) {
        super(context, DB_nombre, null, DB_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL("INSERT INTO "+ Users_table+ " (names,lastname,pass,email) VALUES ('UniMaps','UNI','12345678','unimaps@gmail.com'); ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte,
                          int versionNue) {
        db.execSQL("drop table if exists " + Users_table);
        onCreate(db);
    }

    public void addUser (String name, String lastname, String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues tupla = new ContentValues();
        tupla.put(column_name,name);
        tupla.put(column_lastname,lastname);
        tupla.put(column_email,email);
        tupla.put(column_pass,pass);

        long id = db.insert(Users_table,null, tupla);
        db.close();
        Log.d(TAG,"Usuario Registrado "+id);


    }

    public boolean getUserLogin(String email, String pass){

        String selectQuery = "select * from " + Users_table + " where " +
                column_email + " = " + " '"+email+"' " + " and " + column_pass + " = " + " '"+pass+"' ;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            return true;
        }
        cursor.close();
        db.close();
        return false;

    }

    public void updateUserPass (String newpass,String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + Users_table + " set pass = '" + newpass + "' where email = '"+correo +"';"  );
        db.close();
        //Log.d(TAG,"Password Actualizado ");

    }


    public String [] getUserValues (String email){
        String[] datos= null;
//        String[] campos = new String[] {column_name, column_lastname,column_email};
//        String[] args = new String[] {email};
        String selectQuery = "select names, lastname, email from " + Users_table + " where " +
                column_email + " = " + " '"+email+"'  ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
                datos = new String[] {c.getString(0),c.getString(1),c.getString(2)};
        }
        return datos;
    }



}