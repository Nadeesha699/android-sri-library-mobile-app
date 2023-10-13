package com.example.bookstrore;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView txt_reg,txt_about,txt_email,txt_pass,lbl_valuser,lbl_valpass;
    Button Button;

    CardView card_valU, card_valP;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;

    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_reg = findViewById(R.id.txt_reg);
        txt_about = findViewById(R.id.txt_about);
        Button = findViewById(R.id.btn_login);
        txt_email = findViewById(R.id.txt_email);
        txt_pass = findViewById(R.id.txt_pass);
        lbl_valuser = findViewById(R.id.lbl_rvaluser);
        lbl_valpass = findViewById(R.id.lbl_valpass);
        card_valU = findViewById(R.id.card_valu);
        card_valP = findViewById(R.id.card_valp);

        card_valP.setVisibility(View.INVISIBLE);
        card_valU.setVisibility(View.INVISIBLE);

        sqLiteOpenHelper = new SqliteOpenHelperDB(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        txt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_valP.setVisibility(View.INVISIBLE);
                card_valU.setVisibility(View.INVISIBLE);
                Intent i2 = new Intent(LoginActivity.this,AboutActivity.class);
                startActivity(i2);
            }
        });

        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_valP.setVisibility(View.INVISIBLE);
                card_valU.setVisibility(View.INVISIBLE);
                Intent i1 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i1);
            }
        });


        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                if (txt_email.getText().length() == 0){
                    card_valU.setVisibility(View.VISIBLE);
                    lbl_valuser.setText("null email");
                }
                else if (!(email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"))){
                    card_valU.setVisibility(View.VISIBLE);
                    lbl_valuser.setText("invalid email");
                }
                else if(txt_pass.getText().length() < 6){
                    card_valU.setVisibility(View.INVISIBLE);
                    card_valP.setVisibility(View.VISIBLE);
                    lbl_valpass.setText("use password for more than six character");
                }
                else if(txt_pass.getText().length() == 0){
                    card_valU.setVisibility(View.INVISIBLE);
                    card_valP.setVisibility(View.VISIBLE);
                    lbl_valpass.setText("Null password");
                }
                else {
                    cursor = sqLiteDatabase.query("USER",new String[]{"Password","Email"},null,null,null,null,null);
                    while(cursor.moveToNext()){
                        if(Objects.equals(cursor.getString(1), txt_email.getText().toString())){
                            if(Objects.equals(cursor.getString(0), txt_pass.getText().toString())) {

                                SharedPreferences sharedPreferences = getSharedPreferences("CURRENTDATA",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("EUSER",txt_email.getText().toString());
                                editor.commit();

                                txt_email.setText("");
                                txt_pass.setText("");
                                card_valU.setVisibility(View.INVISIBLE);
                                card_valP.setVisibility(View.INVISIBLE);

                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent i3 = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i3);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });


    }


}