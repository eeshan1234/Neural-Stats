package com.example.sarthakmishra.neuralstats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Homepage extends AppCompatActivity {
    private CardView predictorcard,reportcard,aboutcard,doctorcard;
    TextView userinfo,studytxt,infotxt,upltxt,abouttxt;
    static int check=1;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    static String[] topics={"Choose the topic:","DEPRESSION","ANXIETY","AGGRESSION","GAME ADDICTION","INTERNET ADDICTION","PSYCHOSIS",
    "MALE AGGRESSION","TOXIC WORKPLACE","BORDERLINE PERSONALITY DISORDER","PANIC ATTACK"};
    ArrayAdapter<String> adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    Double anxietyscore, aggressionscore,depressionscore,Gamescore,internetscore,psychosisscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        setupFirebaseListener();

        ViewPager viewPager=findViewById(R.id.Viewpager);
        ImageAdapter adapter=new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase= FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference();

        if (user != null&&check==1) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            if (name != null){
                Toast.makeText(Homepage.this,"Welcome " + name+" to Neural Stats!",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(Homepage.this,"Welcome " + email+" to Neural Stats!",Toast.LENGTH_SHORT).show();

            check++;
        }
    }

    public void onClick1(View view) {
        Intent i;
        i = new Intent(this, Predictor.class);
        startActivity(i);
    }
    public void onClick2(View view) {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        View row=getLayoutInflater().inflate(R.layout.rowlist,null);

        ListView l1=row.findViewById(R.id.rowlistreport);

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,topics);
        l1.setAdapter(adapter);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem=topics[position];
                if(selectedItem.equals("DEPRESSION"))
                {
                    Intent i1=new Intent(Homepage.this,reportdep.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("PSYCHOSIS"))
                {
                    Intent i1=new Intent(Homepage.this,reportpsycho.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("ANXIETY"))
                {
                    Intent i1=new Intent(Homepage.this,reportanxiety.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("AGGRESSION"))
                {
                    Intent i1=new Intent(Homepage.this,reportaggression.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("GAME ADDICTION"))
                {
                    Intent i1=new Intent(Homepage.this,reportGame.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("INTERNET ADDICTION"))
                {
                    Intent i1=new Intent(Homepage.this,reportinternet.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("MALE AGGRESSION"))
                {
                    Intent i1=new Intent(Homepage.this,reportmaleaggress.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("TOXIC WORKPLACE"))
                {
                    Intent i1=new Intent(Homepage.this,reporttoxic.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("BORDERLINE PERSONALITY DISORDER"))
                {
                    Intent i1=new Intent(Homepage.this,reportborderline.class);
                    startActivity(i1);
                }
                else if(selectedItem.equals("PANIC ATTACK"))
                {
                    Intent i1=new Intent(Homepage.this,reportpanic.class);
                    startActivity(i1);
                }

            }
        });

        adapter.notifyDataSetChanged();
        alertdialog.setView(row);
        AlertDialog dialog=alertdialog.create();
        dialog.show();

    }
    public void onClick3(View view) {
        Intent i;
        i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    public void onClick4(View view) {
        Intent i;
        i = new Intent(this, about.class);
        startActivity(i);
    }
    public void onClick5(View view) {
        Intent i;
        i = new Intent(this, reminder.class);
        startActivity(i);
    }
    public void onClick6(View view) {
        Intent i;
        i = new Intent(this, SARMbot.class);
        startActivity(i);
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
                    Toast.makeText(Homepage.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Homepage.this, MainActivity.class);
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
            Intent i= new Intent(Homepage.this, about.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.signout){
            Toast.makeText(Homepage.this,"Signing out...", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            return true;
        }
        if(id==R.id.StudySIT){
            String appName = "StudySIT";
            String packageName = "com.example.sarthakmishra.checking";
            openApp(Homepage.this, appName, packageName);
            return true;
        }
        if(id==R.id.PlacementSIT){
            String appName = "PlacementSIT";
            String packageName = "com.sark.android.placement_sit1";
            openApp(Homepage.this, appName, packageName);
            return true;
        }
        if(id==R.id.contact){
            Toast.makeText(Homepage.this,"Now You can write to developers directly!",Toast.LENGTH_LONG).show();
            Intent i= new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:08095030481"));
            i.putExtra("address", "08095030481");
            startActivity(i);
            return true;
        }

        if(id==R.id.exit){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static void openApp(Context context, String appName, String packageName) {
        if (isAppInstalled(context, packageName))
            if (isAppEnabled(context, packageName))
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
            else Toast.makeText(context, appName + " app is not enabled.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, appName + " app is not installed.", Toast.LENGTH_SHORT).show();
    }

    private static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return false;
    }

    private static boolean isAppEnabled(Context context, String packageName) {
        boolean appStatus = false;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (ai != null) {
                appStatus = ai.enabled;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appStatus;
    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(Homepage.this);
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
                Toast.makeText(Homepage.this,"Signing out...", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();

            }
        });
        AlertDialog alertdialog=builder.create();
        alertdialog.show();
    }
}