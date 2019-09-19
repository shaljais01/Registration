package com.example.reg4;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class signup extends AppCompatActivity  {

    Button signup;Button b2;Button photo;
    public static MyAppDatabase myAppDatabase;
    EditText firstname,lastname,mobile,email,password,dob,repassword;
    RadioGroup rg;
    String Name,mobile1,email1,password1,dob1;
    Spinner city;String city1;String gender;
    String photo1;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        photo=findViewById(R.id.button_photo);

        myAppDatabase= Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb3").allowMainThreadQueries().build();

        firstname=findViewById(R.id.editText_firstname);
         mobile=findViewById(R.id.editText_mobile);
        email=findViewById(R.id.editText_email);
        password=findViewById(R.id.editText_password);
        repassword=findViewById(R.id.editText_repassword);
        dob=findViewById(R.id.editText_dob);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(signup.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



         rg = (RadioGroup) findViewById(R.id.radiogroup_gender);

                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.radioButton_female:
                                gender="Female";
                                break;
                            case R.id.radioButton_male:
                               gender="Male";
                                break;
                        }
                    }
                });

                final ArrayList<String>name=new ArrayList<>();
        name.add("Noida");
        name.add("Greater Noida");
        name.add("Delhi");
        name.add("Patna");
        name.add("Punjab");
        Spinner spinner=(Spinner)findViewById(R.id.spinner_city);
         ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,name);
         spinner.setAdapter(adapter);

         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        city1= parent.getItemAtPosition(position).toString();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
         });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isvalid()==true)
                {
                    Intent i = new Intent(signup.this, signup2.class);
                    i.putExtra("Name", firstname.getText().toString());
                    i.putExtra("Mobile", mobile.getText().toString());
                    i.putExtra("Email", email.getText().toString());
                    i.putExtra("Password", password.getText().toString());
                    if (gender.equals("Female"))
                        i.putExtra("Gender", "Female");
                    else
                        i.putExtra("Gender", "Male");
                    i.putExtra("DOB", dob.getText().toString());
                    i.putExtra("City", city1.toString());

                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Make sure no fields are empty and have valid email address,mobile number and password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    boolean isradioselected(RadioGroup radioGroup)
    {
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            return true;
        }
        else
            return  false;
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
    boolean validemail(String s)
    {
        if(Patterns.EMAIL_ADDRESS.matcher(s).matches()==true)
            return  true;
        else
            return false;
    }
    private boolean isValidMObile(String mobile)
    {
        return (android.util.Patterns.PHONE.matcher(mobile).matches() && mobile.length()>6);
    }
    private boolean ispassword(String p1, String p2)
    {
        int l=p1.length();
        if(l>=10 && p1.equals(p2)==true)
        {
            return true;
        }
        else
            return false;
    }
    boolean isvalid()
    {
        if(!isEmpty(email) && !isEmpty(firstname) && !isEmpty(mobile) && !isEmpty(password) && !isEmpty(repassword)
        && !isradioselected(rg) && !isEmpty(dob) && isValidMObile(mobile.getText().toString().trim()) &&
        ispassword(password.getText().toString(), repassword.getText().toString()) && validemail(email.getText().toString().trim()))
        return true;
        else
            return false;
    }
}
