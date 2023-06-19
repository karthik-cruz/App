package com.example.allen.student.activity;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.pm.PackageManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;


import com.example.allen.student.R;
import com.example.allen.student.sqlite.SecureDataSource;

    public class login extends AppCompatActivity implements  LocationListener {
    public int cp=0;
    String db_keywords[]= null;
    EditText etUserName,etPw;
    private Button logBtn;
    private Button SnpBtn;
    private SecureDataSource secureDataSource;

    String provider;
        protected Context context;
        protected LocationManager locationManager;
        protected LocationListener listener;
        private static final int LOCATION_INTERVAL = 180000;
        private static final float LOCATION_DISTANCE = 0.0f;
        double lati,longi;
        String loc_name;

        Location location;

        double latitude;
        double longitude;

        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

     /*   if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, listener);
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
*/




        secureDataSource = new SecureDataSource(this);

        etUserName = (EditText) findViewById(R.id.editText4);
        etPw = (EditText) findViewById(R.id.editText5);
        logBtn = (Button) findViewById(R.id.button2);
        SnpBtn = (Button) findViewById(R.id.button5);

        SnpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                secureDataSource.deletedata("user");
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ////


                ///

                String sUname = etUserName.getText().toString();
                db_keywords =secureDataSource.getkeyword();

               // showAlertDialog(login.this, "Success", db_keywords, false);
                String sPw = etPw.getText().toString();
                if (sUname != null && sUname.trim().length() > 0) {
                    if (sPw != null && sPw.trim().length() > 0) {
                        if (db_keywords[0].equals(sUname) && db_keywords[1].equals(sPw)) {

                            showAlertDialog(login.this, "Success", "Valid User", false);
                            Intent intent = new Intent(login.this, appwindow.class);
                            startActivity(intent);
                            finish();
                        } else {
                           showAlertDialog(login.this, "Alert", "UserName Or Password Wrong....", false);
                      }

                    }else {
                        showAlertDialog(login.this, "Alert", "Enter Password", false);
                    }
                } else {
                    showAlertDialog(login.this, "Alert", "Enter User Name.", false);
                }

            }
        });

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

    @Override
    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        Toast.makeText(context, ""+location.getLatitude(), 7000).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(context, ""+location.getLatitude(), 7000).show();
       // Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
       // Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      //  Log.d("Latitude","status");
    }

}
