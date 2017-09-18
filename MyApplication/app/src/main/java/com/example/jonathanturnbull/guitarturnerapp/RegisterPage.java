package com.example.jonathanturnbull.guitarturnerapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static com.example.jonathanturnbull.guitarturnerapp.DBHelper.TABLE_USERDATA;

public class RegisterPage extends AppCompatActivity {

    public static final String TABLE_USERDATA = "userdata";
    String hasher = "";
    EditText username;
    EditText passWord;
    EditText passWordc;
    Button register;
    DBHelper myDBHelper;

    String[] allColumns = new String[] { "_userid", "username", "password" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        myDBHelper = new DBHelper(this);
        username = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        passWordc = (EditText) findViewById(R.id.passWordc);
        register = (Button) findViewById(R.id.Register);


        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username.length() == 8 && passWord.length() == 8) {
                    // get Text from form elements
                    username.getText();
                    passWord.getText();

                    // Hash password to a 32 bit string
                    hasher = md5(passWord.toString());

                    // insert into my database
                    insert(username.toString(), hasher);

                    Log.d("HASHED", "HASHED PASSWORD = " + hasher);
                    Context context = getApplicationContext();
                    CharSequence text = "Username and Password Saved!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "No Hashing Error!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    public void goHome(MenuItem item) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void logOut(MenuItem item) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    // https://stackoverflow.com/questions/3934331/how-to-hash-a-string-in-android
    // Author: Yuriy Lisenkov
    public static String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }
    // End of borrowed code //

    public void insert(String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.insert(TABLE_USERDATA, null, values);
    }


}
