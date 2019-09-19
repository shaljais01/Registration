package com.example.reg4;


import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.R.attr.readPermission;
import static com.example.reg4.signup.myAppDatabase;

public class signup2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView profileImageView;
    private Button pickImage;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE = 2;
    private ProgressDialog progressbar;
    private int progressbarStatus;
    private Handler progressBarHandler = new Handler();
    private boolean hasImageChanged = false;
    Bitmap thumbnail;
    String Name2; String Email2; String Mobile2; String Password2;
    String gender2; String city2; String photo2;String dob2;
    public static MyAppDatabase myAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        //t1=findViewById(R.id.textView6);
        myAppDatabase= Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb3").allowMainThreadQueries().build();

photo2="yeee";
        Name2=(getIntent().getStringExtra("Name"));
        Mobile2=(getIntent().getStringExtra("Mobile"));
        Email2=(getIntent().getStringExtra("Email"));
        Password2=(getIntent().getStringExtra("Password"));
        gender2=(getIntent().getStringExtra("Gender"));
        city2=(getIntent().getStringExtra("City"));
        dob2=(getIntent().getStringExtra("DOB"));
        profileImageView = (ImageView) findViewById(R.id.imageView);
        pickImage = (Button) findViewById(R.id.button);
        profileImageView.setImageResource(R.drawable.picture);

        pickImage.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(signup2.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_DENIED){
            profileImageView.setEnabled(false);
            ActivityCompat.requestPermissions(signup2.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},readPermission);
        }else
            profileImageView.setEnabled(true);

        Button b3=findViewById(R.id.button2);
        b3.setOnClickListener(this);
        String ss=Name2;
        //t1.setText(gender2);

    }


    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.button:
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
                                        profileImageView.setImageResource(R.drawable.picture);
                                        break;
                                }
                            }

                        })
                        .show();
                break;
            case R.id.button2:


                if(photo2.equals("yeee")==false) {
                    User user = new User();
                    user.setEmail(Email2);
                    user.setName(Name2);
                    user.setMobileno(Mobile2);
                    user.setPassword(Password2);
                    user.setDob(dob2);
                    user.setCity(city2);
                    user.setGender(gender2);
                    user.setPhoto(photo2);
                    signup2.myAppDatabase.myDao().addUser(user);
                    Toast.makeText(getApplicationContext(), "User added sucessfully", Toast.LENGTH_LONG).show();
                    break;
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Make sure you select the photo",Toast.LENGTH_LONG).show();

                }
                   }

    }

    public void onRequestPermissionResult(int requestCode , String[] permission, int[]grandResults){
        if(requestCode==0){
            if(grandResults.length>0 && grandResults[0]== PackageManager.PERMISSION_GRANTED
                    && grandResults[1]== PackageManager.PERMISSION_DENIED){
                profileImageView.setEnabled(true);
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
                    profileImageView.setImageBitmap(selectedImage);
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
        profileImageView.setMaxWidth(200);
        profileImageView.setImageBitmap(thumbnail);
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
}


