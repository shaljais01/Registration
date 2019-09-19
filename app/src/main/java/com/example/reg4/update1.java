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

public class update1 extends AppCompatActivity {

    String email1; String password1;int c;String ee;
    EditText mail,pass;
    EditText e1,e2;public static MyAppDatabase myAppDatabase; String name3,mobile3,city3,gender3,dob3,photo3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update1);
        mail=findViewById(R.id.editText_email);
       pass=findViewById(R.id.editText_password);

ImageView im=findViewById(R.id.imageView2);
im.setImageResource(R.drawable.tree);

        myAppDatabase= Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb3").allowMainThreadQueries().build();




          Button b1=findViewById(R.id.button_update);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                List<User>list=update1.myAppDatabase.myDao().getuser1(mail.getText().toString(),pass.getText().toString());
                c=0;
                for(User user:list)
                {
                    c++;
                }

                if(c>0)
                {
                    Intent i = new Intent(update1.this, update2.class);

                    i.putExtra("Email", mail.getText().toString());

                    startActivity(i);
                }
            }
        });
    }
}
