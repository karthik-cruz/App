package com.example.allen.student.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.allen.student.R;
import com.example.allen.student.sqlite.SecureDataSource;
import com.example.allen.student.util.SMSBroadcastReceiver;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class appwindow extends AppCompatActivity {
    private Button SnpBtn;
    private Button encrBtn;
    private SecureDataSource secureDataSource;
    String db_keywords[]=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appwindow);
        SnpBtn = (Button) findViewById(R.id.button3);
        encrBtn = (Button) findViewById(R.id.button4);
        secureDataSource = new SecureDataSource(this);
        db_keywords = secureDataSource.getkeyword();

        SnpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                secureDataSource.deletedata("user");
                Intent intent = new Intent(appwindow.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        encrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Encode into Base64 format
                byte[] data = new byte[0];
                try {
                    data = db_keywords[1].getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String base64 = Base64.encodeToString(data, Base64.DEFAULT);

             /*// Receiving side
                byte[] dataD = Base64.decode(base64, Base64.DEFAULT);
                String text = null;
                try {
                    text = new String(dataD, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                    */
                //String BasicBase64format = Base64.encodeToString(db_keywords[1].getBytes(), 0);
                //String strpass = new String(BasicBase64format);
                Toast.makeText(getApplicationContext(), base64 ,Toast.LENGTH_LONG).show();
            }
        });

}

}

