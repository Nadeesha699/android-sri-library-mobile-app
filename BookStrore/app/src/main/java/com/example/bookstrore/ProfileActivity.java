package com.example.bookstrore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProfileActivity extends AppCompatActivity {

    ImageView img_add,img_pro,img_home;
    EditText txt_user,txt_email,txt_tel,txt_cur_pass,txt_con_pass;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;
    Cursor cursor;

    String cEmail;

    TextView lbl_cpass,lbl_npass;

    int id;

    Button btn_edit,btn_update;

    private static final int REQUEST_CODE_PERMISSION = 101;
    private static final int REQUEST_CODE_GALLERY = 102;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        img_pro =findViewById(R.id.img_pro_1);
        img_add = findViewById(R.id.img_add);
        img_home = findViewById(R.id.img_home);
        txt_user = findViewById(R.id.txt_user);
        txt_email = findViewById(R.id.txt_pemail);
        txt_tel = findViewById(R.id.txt_tel);
        txt_cur_pass = findViewById(R.id.txt_cur_pass);
        txt_con_pass = findViewById(R.id.txt_con_pass);
        lbl_cpass = findViewById(R.id.lbl_current_pass);
        lbl_npass = findViewById(R.id.lbl_new_pass);
        btn_edit = findViewById(R.id.btn_edits);
        btn_update = findViewById(R.id.btn_update);
        btn_update.setVisibility(View.INVISIBLE);

        loadImageAndDisplay();

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                if(txt_user.getText().length() == 0){
                    Toast.makeText(ProfileActivity.this, "null user", Toast.LENGTH_SHORT).show();
                }
                else if(txt_email.getText().length() == 0){
                    Toast.makeText(ProfileActivity.this, "null email", Toast.LENGTH_SHORT).show();
                }
               /* else if(email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")){
                    Toast.makeText(Profile.this, "invalid email", Toast.LENGTH_SHORT).show();
                }*/
                else if(txt_tel.getText().length() == 0){
                    Toast.makeText(ProfileActivity.this, "null telephone number", Toast.LENGTH_SHORT).show();
                }
                else if(txt_cur_pass.getText().length() == 0){
                    Toast.makeText(ProfileActivity.this, "null new password", Toast.LENGTH_SHORT).show();
                }
                else if(txt_con_pass.getText().length() == 0){
                    Toast.makeText(ProfileActivity.this, "null confirm password", Toast.LENGTH_SHORT).show();
                }
                else if(txt_cur_pass.getText().toString().equals(txt_con_pass.getText().toString())){
                    Toast.makeText(ProfileActivity.this, "miss match password", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Uname", txt_user.getText().toString());
                    contentValues.put("Password", txt_con_pass.getText().toString());
                    contentValues.put("Email", txt_email.getText().toString());
                    contentValues.put("TelephoneNo", Integer.parseInt(txt_tel.getText().toString()));
                    long valid = sqLiteDatabase.update("USER", contentValues, "_id=?", new String[]{String.valueOf(id)});
                    if (valid != 0) {
                        Toast.makeText(ProfileActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                        btn_edit.setVisibility(View.VISIBLE);
                        btn_update.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_user.setEnabled(true);
                txt_email.setEnabled(true);
                txt_cur_pass.setEnabled(true);
                txt_tel.setEnabled(true);
                lbl_npass.setVisibility(View.VISIBLE);
                txt_con_pass.setVisibility(View.VISIBLE);
                lbl_cpass.setText("NEW PASSWORD");
                btn_update.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.INVISIBLE);
            }
        });

        lbl_npass.setVisibility(View.INVISIBLE);
        txt_con_pass.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("CURRENTDATA",MODE_PRIVATE);
        cEmail = sharedPreferences.getString("EUSER",null);

        sqLiteOpenHelper = new SqliteOpenHelperDB(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("USER",new String[]{"UName","Password","Email","TelephoneNo","_id"},"Email=?",new String[]{cEmail},null,null,null);
        cursor.moveToFirst();
        txt_user.setText(cursor.getString(0));
        txt_cur_pass.setText(cursor.getString(1));
        txt_email.setText(cursor.getString(2));
        String tel = String.valueOf(cursor.getInt(3));
        txt_tel.setText(tel);
        id = cursor.getInt(4);

        txt_user.setEnabled(false);
        txt_email.setEnabled(false);
        txt_cur_pass.setEnabled(false);
        txt_tel.setEnabled(false);

    }

    @Override
    public void onBackPressed() {


        new AlertDialog.Builder(ProfileActivity.this).setMessage("Do you want to exit ? ").setCancelable(false).setPositiveButton("Yes"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No",null).show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Save the selected image to a file
            saveImageToFile(selectedImageUri);

            // Load and display the saved image
            loadImageAndDisplay();
        }
    }


    private void saveImageToFile(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File destinationFile = new File(getExternalFilesDir(null), "my_image.jpg");
            OutputStream outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now open the gallery
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied. Cannot open gallery.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}