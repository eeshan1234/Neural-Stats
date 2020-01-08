package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends AppCompatActivity {
    Button docbtn;
    private TextView textView;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    String bestprovider;
    KenBurnsView kenBurnsView;
    private boolean moving=true;
    static final int check=1111;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(Doctor.this,Homepage.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_voice:
                    voice();
                    return true;
                case R.id.navigation_doctor:
                    Intent in=new Intent(Doctor.this,Doctor.class);
                    startActivity(in);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        kenBurnsView = (KenBurnsView) findViewById(R.id.image);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kenBurnsView.setTransitionGenerator(generator);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupFirebaseListener();

        docbtn=(Button)findViewById(R.id.docbtn);

        kenBurnsView.setTransitionListener(onTransittionListener());

        kenBurnsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moving) {
                    kenBurnsView.pause();
                    moving = false;
                } else {
                    kenBurnsView.resume();
                    moving = true;
                }
            }
        });

        textView=(TextView)findViewById(R.id.idtxt);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            final AlertDialog.Builder builder=new AlertDialog.Builder(Doctor.this);
            builder.setMessage("Permissions not given!\nGive GPS permissions and then Reload.");
            builder.setCancelable(true);

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Reload!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(Doctor.this,Doctor.class);
                    startActivity(i);
                }
            });
            AlertDialog alertdialog=builder.create();
            alertdialog.show();
        }

        Criteria criteria=new Criteria();
        bestprovider=locationManager.getBestProvider(criteria,false);

        final Location location=locationManager.getLastKnownLocation(bestprovider);
        docbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location==null)
                {
                    Toast.makeText(Doctor.this,"Connection Failure!\nMake sure Internet and GPS connection is active",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Doctor.this,MapsActivity.class);
                    startActivity(i);   }
                else
                {
                    Intent i=new Intent(Doctor.this,MapsActivity.class);
                    startActivity(i);
                }
            }
        });
        if(location==null)
        {
            Toast.makeText(Doctor.this,"Location not found",Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder builder=new AlertDialog.Builder(Doctor.this);
            builder.setMessage("Could not connect to your GPS. Check your GPS connection and try again.\n");
            builder.setCancelable(true);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(Doctor.this,Doctor.class);
                    startActivity(i);
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        else{

            onLocationChanged(location);
            loc_func(location);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Doctor.this,Homepage.class);
        startActivity(i);
    }

    private KenBurnsView.TransitionListener onTransittionListener() {
        return new KenBurnsView.TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {

                // Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                // Toast.makeText(MainActivity.this, "end", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void onLocationChanged(Location location)
    {
        longitude=location.getLongitude();
        latitude=location.getLatitude();
    }


    public void onStatusChanged(String s,int i,Bundle bundle){


    }

    public void onProviderEnabled(String s){


    }

    public void onProviderDisabled(String s){

    }


    private void loc_func(Location location){


        try {
            Geocoder geocoder=new Geocoder(this);
            List<Address> addresses=null;
            addresses=geocoder.getFromLocation(latitude,longitude,1);
            String country=addresses.get(0).getCountryName();
            String city=addresses.get(0).getLocality();
            String state=addresses.get(0).getAdminArea();
            textView.setText("Your current location details:\nCountry: "+country+"\nState: "+state+"\nCity: "+city+
            "\nLatitude: "+latitude+"\nLongitude: "+longitude);


        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error:" + e,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }
    private void setupFirebaseListener(){

        mauthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    //signed-in
                    Log.d("AccountManager","onAuthStateChanged: signed_in: "+user.getUid());
                }
                else{
                    //signout
                    Toast.makeText(Doctor.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Doctor.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mauthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mauthStateListener!=null)
            FirebaseAuth.getInstance().removeAuthStateListener(mauthStateListener);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.about){
            Intent i= new Intent(Doctor.this, about.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.signout){
            Toast.makeText(Doctor.this,"Signing out...", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            return true;
        }

        if(id==R.id.contact){
            Toast.makeText(Doctor.this,"Now You can write to developers directly!",Toast.LENGTH_LONG).show();
            Intent i= new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:08726341811"));
            i.putExtra("address", "08726341811");
            startActivity(i);
            return true;
        }

        if(id==R.id.exit){
            onBackPressed1();
            return true;
        }

        if(id==android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed1() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(Doctor.this);
        builder.setMessage("Are you sure, want to exit ?");
        builder.setCancelable(true);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Exit from app!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Doctor.this,"Signing out...", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();

            }
        });
        AlertDialog alertdialog=builder.create();
        alertdialog.show();

    }


    public void voice(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak out where you want to navigate to, in the app among Predictor" +
                ", Report, About, Home and Exit to exit from app. ");
        startActivityForResult(i,check);



    }
    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);

        if(requestcode==check && resultcode==RESULT_OK)
        {
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
            if(results.contains("predictor"))
            {
                Intent i=new Intent(Doctor.this,Predictor.class);
                startActivity(i);

            }
            if(results.contains("report"))
            {
                Intent i=new Intent(Doctor.this,Report.class);
                startActivity(i);

            }
            else if(results.contains("about"))
            {
                Intent i=new Intent(Doctor.this,about.class);
                startActivity(i);
            }
            else if(results.contains("Home"))
            {
                Intent i=new Intent(Doctor.this,Homepage.class);
                startActivity(i);
            }
            else if(results.contains("exit"))
            {
                final AlertDialog.Builder builder=new AlertDialog.Builder(Doctor.this);
                builder.setMessage("Are you sure, want to exit ?");
                builder.setCancelable(true);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Exit from app!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Doctor.this,"Signing out...", Toast.LENGTH_SHORT).show();

                        FirebaseAuth.getInstance().signOut();

                    }
                });
                AlertDialog alertdialog=builder.create();
                alertdialog.show();

            }

            else
                Toast.makeText(Doctor.this,"None of the suitable choices matched! Try again with appropriate choices.!",Toast.LENGTH_SHORT).show();

        }
    }
}
