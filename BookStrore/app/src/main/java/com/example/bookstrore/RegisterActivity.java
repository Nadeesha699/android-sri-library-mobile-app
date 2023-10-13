package com.example.bookstrore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btn_sign,btn_clear;
    TextView txt_user,txt_pass,txt_email,txt_num,txt_cpass,txt_vuser,txt_vpass,txt_vemail,txt_vnum,txt_vcpass,txt_gender;

    SqliteOpenHelperDB sqliteOpenHelperDB;
    SQLiteDatabase sqLiteDatabase;

    CardView card_valru,card_valrp,card_valrcp,card_valre,card_valrt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_sign = findViewById(R.id.btn_sign);
        btn_clear = findViewById(R.id.btn_clear);
        txt_user = findViewById(R.id.txt_ruser);
        txt_pass = findViewById(R.id.txt_rpass);
        txt_email = findViewById(R.id.txt_remail);
        txt_num = findViewById(R.id.txt_num);
        txt_cpass = findViewById(R.id.txt_rconpass);
        txt_vuser = findViewById(R.id.lbl_rvaluser);
        txt_vpass = findViewById(R.id.lbl_rvalpass);
        txt_vemail = findViewById(R.id.lbl_rvalemail);
        txt_vnum = findViewById(R.id.lbl_valnum);
        txt_vcpass = findViewById(R.id.lbl_rvalconpass);
        card_valru = findViewById(R.id.card_valru);
        card_valrp = findViewById(R.id.card_valrp);
        card_valrcp = findViewById(R.id.card_valcpu);
        card_valrt = findViewById(R.id.card_valrt);
        card_valre = findViewById(R.id.card_valre);

        card_valru.setVisibility(View.INVISIBLE);
        card_valrp.setVisibility(View.INVISIBLE);
        card_valrcp.setVisibility(View.INVISIBLE);
        card_valrt.setVisibility(View.INVISIBLE);
        card_valre.setVisibility(View.INVISIBLE);

        sqliteOpenHelperDB = new SqliteOpenHelperDB(this);
        sqLiteDatabase = sqliteOpenHelperDB.getReadableDatabase();

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =txt_email.getText().toString();
                if(txt_user.getText().length() == 0){
                    card_valru.setVisibility(View.VISIBLE);
                    txt_vuser.setText("null username");
                }
                else if(txt_pass.getText().length() < 6){
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.VISIBLE);
                    txt_vuser.setText("");
                    txt_vpass.setText("password for more than 6 character");
                }
                else if(txt_pass.getText().length() == 0){
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.VISIBLE);
                    txt_vuser.setText("");
                    txt_vpass.setText("null password");
                }
                else if(!txt_pass.getText().toString().equals(txt_cpass.getText().toString())){
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.INVISIBLE);
                    card_valrcp.setVisibility(View.VISIBLE);
                    txt_vuser.setText("");
                    txt_vpass.setText("");
                    txt_vcpass.setText("not match confirm password");
                }
                else if(txt_email.getText().length() == 0){
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.INVISIBLE);
                    card_valrcp.setVisibility(View.INVISIBLE);
                    card_valre.setVisibility(View.VISIBLE);
                    txt_vuser.setText("");
                    txt_vpass.setText("");
                    txt_vcpass.setText("");
                    txt_vemail.setText("null email");
                }
                else if(txt_num.getText().length() != 10){
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.INVISIBLE);
                    card_valrcp.setVisibility(View.INVISIBLE);
                    card_valre.setVisibility(View.INVISIBLE);
                    card_valrt.setVisibility(View.VISIBLE);
                    txt_vuser.setText("");
                    txt_vpass.setText("");
                    txt_vcpass.setText("");
                    txt_vemail.setText("");
                    txt_vnum.setText("invalid phone no");
                }
                else if(txt_num.getText().length() == 0){
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.INVISIBLE);
                    card_valrcp.setVisibility(View.INVISIBLE);
                    card_valre.setVisibility(View.INVISIBLE);
                    card_valrt.setVisibility(View.VISIBLE);
                    txt_vuser.setText("");
                    txt_vpass.setText("");
                    txt_vcpass.setText("");
                    txt_vemail.setText("");
                    txt_vnum.setText("null phone no");
                }
                else{
                    String user = txt_user.getText().toString();
                    String password = txt_pass.getText().toString();
                    String email1 =txt_email.getText().toString();
                    int num =Integer.parseInt(txt_num.getText().toString());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("UName",user);
                    contentValues.put("Password",password);
                    contentValues.put("Email",email1);
                    contentValues.put("TelephoneNo",num);
                    long valid= sqLiteDatabase.insert("USER",null,contentValues);
                    if(valid == 0){
                        Toast.makeText(RegisterActivity.this,"unsuccessful",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Register success",Toast.LENGTH_SHORT).show();
                    }
                    txt_vuser.setText("");
                    txt_vpass.setText("");
                    txt_vcpass.setText("");
                    txt_vemail.setText("");
                    txt_vnum.setText("");
                    txt_user.setText("");
                    txt_pass.setText("");
                    txt_cpass.setText("");
                    txt_email.setText("");
                    txt_num.setText("");
                    card_valru.setVisibility(View.INVISIBLE);
                    card_valrp.setVisibility(View.INVISIBLE);
                    card_valrcp.setVisibility(View.INVISIBLE);
                    card_valre.setVisibility(View.INVISIBLE);
                    card_valrt.setVisibility(View.INVISIBLE);

                }

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_vuser.setText("");
                txt_vpass.setText("");
                txt_vcpass.setText("");
                txt_vemail.setText("");
                txt_vnum.setText("");
                txt_user.setText("");
                txt_pass.setText("");
                txt_cpass.setText("");
                txt_email.setText("");
                txt_num.setText("");
                card_valru.setVisibility(View.INVISIBLE);
                card_valrp.setVisibility(View.INVISIBLE);
                card_valrcp.setVisibility(View.INVISIBLE);
                card_valre.setVisibility(View.INVISIBLE);
                card_valrt.setVisibility(View.INVISIBLE);
            }
        });
    }





    @Override

    public  void  onBackPressed(){

        new AlertDialog.Builder(this,R.style.Alers).setMessage("Do you want to exit ? ").setCancelable(false).setPositiveButton("Yes"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No",null).show();




    }
}




