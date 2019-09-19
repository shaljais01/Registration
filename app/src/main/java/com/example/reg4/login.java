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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {

   String email1; String password1;int c;
   EditText e1,e2,emaile,passworde;public static MyAppDatabase myAppDatabase; String name3,mobile3,city3,gender3,dob3,photo3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myAppDatabase= Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb3").allowMainThreadQueries().build();

        emaile=(EditText)findViewById(R.id.editText_1);
         passworde=(EditText)findViewById(R.id.editText_2);
        ImageView imageView=findViewById(R.id.imageView4);
        imageView.setImageResource(R.drawable.tree);


        Button b1=findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                List<User>list=login.myAppDatabase.myDao().getuser(emaile.getText().toString(),passworde.getText().toString());

                name3="pp";
                c=0;
                for(User user:list)
                {
                    email1=user.getEmail();
                    name3=user.getName();
                    mobile3=user.getMobileno();
                    city3=user.getCity();
                    gender3=user.getGender();
                    dob3=user.getDob();
                    photo3=user.getPhoto();
                    c++;
                }
                if(c>0)
                {
                    Intent i = new Intent(login.this, login2.class);
                    i.putExtra("Name", name3);
                    i.putExtra("Mobile", mobile3);
                    i.putExtra("Email", email1);
                    i.putExtra("City", city3);
                    i.putExtra("Gender", gender3);
                    i.putExtra("DOB",dob3);
                    i.putExtra("Photo", photo3);
                    startActivity(i);
                }

            }
        });
    }
}
