package com.example.allen.student.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import android.widget.TextView;
import android.content.ContentResolver;

import com.example.allen.student.R;
import com.example.allen.student.sqlite.SecureDataSource;


public class MainActivity extends AppCompatActivity {

    private Button SnpBtn;
    EditText etUserName,etPw,eRePw;
    private SecureDataSource secureDataSource;
    boolean isRegistered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secureDataSource = new SecureDataSource(this);
        setContentView(R.layout.activity_main);
        isRegistered = secureDataSource.isRegistered();

        if (isRegistered) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();

        } else {
            etUserName = (EditText) findViewById(R.id.editText);
            etPw = (EditText) findViewById(R.id.editText2);
            eRePw = (EditText) findViewById(R.id.editText3);
            SnpBtn = (Button) findViewById(R.id.button);
            SnpBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String sUname = etUserName.getText().toString();
                    String sPw = etPw.getText().toString();
                    String sRePw = eRePw.getText().toString();
                    ContentValues values = new ContentValues();
                    values.put("username", sUname);
                    values.put("keyword", sPw);

                    if (sUname != null && sUname.trim().length() > 0) {
                        if (sPw != null && sPw.trim().length() > 0) {
                            if (sRePw != null && sRePw.trim().length() > 0) {
                                if (sPw.equals(sRePw)) {
                                    secureDataSource.insertdata(values, "user");
                                    showAlertDialog(MainActivity.this, "Success", "Registered Success", false);
                                    Intent intent = new Intent(MainActivity.this, login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    showAlertDialog(MainActivity.this, "Alert", "Both Password must be same....", false);
                                }
                            } else {
                                showAlertDialog(MainActivity.this, "Alert", "Enter Re-Type Password", false);
                            }
                        } else {
                            showAlertDialog(MainActivity.this, "Alert", "Enter Password", false);
                        }
                    } else {
                        showAlertDialog(MainActivity.this, "Alert", "Enter User Name.", false);
                    }
                }

            });
        }
    }
    public static void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        // Setting alert dialog icon
        /*alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);*/
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    /* private void loadContacts(){
         StringBuilder builder = new StringBuilder();
         ContentResolver contentResolver = getContentResolver();
         Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
         if (cursor.getCount() > 0){
             String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
             String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
             int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
             if(hasPhoneNumber > 0){
                 Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                         ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " ?",new String[] {id},null );
                 while (cursor2.moveToNext())  {
                     String PhoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                     builder.append("Contact :").append(name).append(",PhoneNumber :").append(PhoneNumber).append("\n\n");
                 }
                 cursor2.close();
             }

         }
         cursor.close();
         listContacts.setText(builder.toString());
     }
 }*/
  /*  private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

}*/
    private void getAllContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (phoneCursor != null) {
                        if (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                             Toast.makeText(getApplicationContext(),"Contact Readed",Toast.LENGTH_LONG).show();


                            //At here You can add phoneNUmber and Name to you listView ,ModelClass,Recyclerview
                            phoneCursor.close();
                        }


                    }
                }
            }
        }
    }
}