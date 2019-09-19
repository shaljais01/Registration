package com.example.reg4;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class login2 extends AppCompatActivity {

    String email; String password;
    EditText e1,e2;public static MyAppDatabase myAppDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        ImageView i1=(ImageView)findViewById(R.id.imageView3);

      TextView name5=findViewById(R.id.textView5);
      TextView email14=findViewById(R.id.textView14);
        TextView mobile15=findViewById(R.id.textView15);
        TextView city16=findViewById(R.id.textView16);
        TextView gender17=findViewById(R.id.textView17);
        TextView dob18=findViewById(R.id.textView18);

      name5.setText( (getIntent().getStringExtra("Name")));
        email14.setText( (getIntent().getStringExtra("Email")));
        mobile15.setText( (getIntent().getStringExtra("Mobile")));
        city16.setText( (getIntent().getStringExtra("City")));
        gender17.setText( (getIntent().getStringExtra("Gender")));
        dob18.setText( (getIntent().getStringExtra("DOB")));

        byte[] decodedString = Base64.decode((getIntent().getStringExtra("Photo")), Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       i1.setImageBitmap(decodedByte);
    }
}
