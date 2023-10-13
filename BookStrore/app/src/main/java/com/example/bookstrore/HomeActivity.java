package com.example.bookstrore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    ImageView img_pro,img_log,img_mail,img_store,img_whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        img_pro = findViewById(R.id.img_prof);
        img_store =findViewById(R.id.img_stores);
        img_mail = findViewById(R.id.img_mails);
        img_whatsapp = findViewById(R.id.img_what);
        img_log = findViewById(R.id.img_logs);

        loadImageAndDisplay();

        img_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        img_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,LibraryActivity.class);
                startActivity(intent);
            }
        });

        img_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] TO = {"sama@gmail.com"};
                String [] CC ={"samanaas@gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.putExtra(Intent.EXTRA_CC,CC);
                intent.putExtra(Intent.EXTRA_SUBJECT, "SEND FEEDBACK OR REPORT");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm user,");
                startActivity(Intent.createChooser(intent, "Send Email"));


            }
        });

        img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+94 710466087"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                //try {
                    /*PackageManager pm = context.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);*/
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                /*} catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(home_base_two.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }*/

            }
        });

        img_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this,R.style.Alers).setCancelable(false).setTitle("BOOKSHO").setMessage("Do you want to logout ? ").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("no",null).show();
            }
        });
    }

    private void loadImageAndDisplay() {
        File imageFile = new File(getExternalFilesDir(null), "my_image.jpg");
        if (imageFile.exists()) {
            Uri imageUri = Uri.fromFile(imageFile);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                img_pro.setImageBitmap(bitmap);
                img_pro.setVisibility(ImageView.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
