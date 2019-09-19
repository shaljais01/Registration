package com.example.reg4;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.bluetooth.BluetoothClass.Service.CAPTURE;

public class update2 extends AppCompatActivity implements  View.OnClickListener{

    String email1; String password1;int c;String city1;
    EditText e1,e2;public static MyAppDatabase myAppDatabase; String name3,mobile3,city3,gender3,dob3,photo3;
    Bitmap thumbnail;String photo2;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE = 2;
    private ProgressDialog progressbar;
    private int progressbarStatus;
    private Handler progressBarHandler = new Handler();
    private boolean hasImageChanged = false;
    EditText name5,mobile5,city5,gender5,dob5,pass5;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update2);

        photo2="pp";

        myAppDatabase= Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb3").allowMainThreadQueries().build();

        //List<User>list=login.myAppDatabase.myDao().getuser("shalinijaiswal421@gmail.com","12345");

        email1=getIntent().getStringExtra("Email");

        Button b1=findViewById(R.id.button_photo);
        Button b2=findViewById(R.id.button_update);

        b2.setOnClickListener(this);
        b1.setOnClickListener(this);

        name5=findViewById(R.id.editText_name);
        mobile5=findViewById(R.id.editText_mobile);
        //city5=findViewById(R.id.editText_city);
        gender5=findViewById(R.id.editText_gender);
        dob5=findViewById(R.id.editText_dob);
        pass5=findViewById(R.id.editText_password);

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


        dob5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(update2.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dob5.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.button_photo:
                new MaterialDialog.Builder(this)
                        .title("Set your image")
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                                        break;
                                    case 1:
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, CAPTURE);
                                        break;
                                    case 2:
                                       // profileImageView.setImageResource(R.drawable.ar1);
                                        break;
                                }
                            }

                        })
                        .show();
                break;
            case R.id.button_update:

                if(isvalid()==true) {
                    User user = new User();
                    user.setEmail(email1);
                    user.setName(name5.getText().toString());
                    user.setMobileno(mobile5.getText().toString());
                    user.setPassword(pass5.getText().toString());
                    user.setDob(dob5.getText().toString());
                    user.setCity(city1);
                    user.setGender(gender5.getText().toString());
                    user.setPhoto(photo2);
                    update2.myAppDatabase.myDao().Updateuser(user);
                    Toast.makeText(getApplicationContext(), "User updated sucessfully", Toast.LENGTH_LONG).show();
                    break;
                }
                else
                    Toast.makeText(getApplicationContext(),"Make sure no fields are empty and have valid mobile number and password",Toast.LENGTH_LONG).show();

        }

    }
    public void onRequestPermissionResult(int requestCode , String[] permission, int[]grandResults){
        if(requestCode==0){
            if(grandResults.length>0 && grandResults[0]== PackageManager.PERMISSION_GRANTED
                    && grandResults[1]== PackageManager.PERMISSION_DENIED){
               // profileImageView.setEnabled(true);
            }
        }
    }
    public  void  setProgressBar(){
        progressbar=new ProgressDialog(this);
        progressbar.setCancelable(true);
        progressbar.setMessage("Please Wait...");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setProgress(0);
        progressbar.setMax(100);
        progressbar.show();
        progressbarStatus=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressbarStatus<100)
                {
                    progressbarStatus +=30;
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setProgress(progressbarStatus);
                        }
                    });

                }
                if(progressbarStatus >=100){
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    progressbar.dismiss();
                }
            }
        }).start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_PHOTO){
            if(resultCode==RESULT_OK){
                try{
                    final Uri imageUri=data.getData();
                    final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage= BitmapFactory.decodeStream(imageStream);
                    setProgressBar();
                    //profileImageView.setImageBitmap(selectedImage);
                    Bitmap scaledBitmap = getScaledBitmap(selectedImage, 250, 350);
                    //t1.setText(getBase64String(scaledBitmap));
                    photo2=getBase64String(scaledBitmap);

                }
                catch(FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }else if(requestCode==CAPTURE){
            if(resultCode==RESULT_OK){
                onCaptureImageResult(data);
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    /*@Override
    public boolean onOtionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.action_sort:
                Intent intent=new Intent(this, DetailsActivity.class);
                startActivity(intent);
                return  true;
                default:
                    return  super.onOptionsItemSelected(item);
        }
    }*/
    private void onCaptureImageResult(Intent data){
        thumbnail=(Bitmap)data.getExtras().get("data");
        setProgressBar();
       // profileImageView.setMaxWidth(200);
       // profileImageView.setImageBitmap(thumbnail);
        Bitmap scaledBitmap = getScaledBitmap(thumbnail, 250, 350);
        // t1.setText(getBase64String(scaledBitmap));
        photo2=getBase64String(scaledBitmap);
    }
    private String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }
    private Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight)
    {
        int bWidth = b.getWidth();
        int bHeight = b.getHeight();

        int nWidth = bWidth;
        int nHeight = bHeight;

        if(nWidth > reqWidth)
        {
            int ratio = bWidth / reqWidth;
            if(ratio > 0)
            {
                nWidth = reqWidth;
                nHeight = bHeight / ratio;
            }
        }

        if(nHeight > reqHeight)
        {
            int ratio = bHeight / reqHeight;
            if(ratio > 0)
            {
                nHeight = reqHeight;
                nWidth = bWidth / ratio;
            }
        }

        return Bitmap.createScaledBitmap(b, nWidth, nHeight, true);
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
        if( !isEmpty(name5) && !isEmpty(mobile5) && !isEmpty(pass5)
                && !isEmpty(dob5) && photo2.equals("pp")==false && isValidMObile(mobile5.getText().toString().trim()) &&
                ispassword(pass5.getText().toString(), pass5.getText().toString()) )
            return true;
        else
            return false;
    }
}
