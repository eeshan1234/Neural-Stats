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
import android.view.Menu;
import android.view.MenuItem;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.google.firebase.auth.FirebaseAuth;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Predictor extends AppCompatActivity {

    KenBurnsView kenBurnsView;
    private boolean moving=true;
    ListView lv;
    static final int check=1111;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(Predictor.this,Homepage.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_voice:
                    voice();
                return true;

                case R.id.navigation_doctor:
                    Intent i1=new Intent(Predictor.this,MapsActivity.class);
                    startActivity(i1);
                    return true;

            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictor);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onBackPressed() {
        Intent i=new Intent(Predictor.this,Homepage.class);
        startActivity(i);
    }

    public void onClick1(View view) {
        Intent i=new Intent(Predictor.this,DescriptionDepression.class);
        startActivity(i);
        }
    public void onClick2(View view) {
        Intent i=new Intent(Predictor.this,DescriptionAnx.class);
        startActivity(i);
    }
    public void onClick3(View view) {
        Intent i=new Intent(Predictor.this,DescriptionInternet.class);
        startActivity(i);
    }
    public void onClick4(View view) {
        Intent i=new Intent(Predictor.this,DescriptionAggression.class);
        startActivity(i);
    }
    public void onClick5(View view) {
        Intent i=new Intent(Predictor.this,DescriptionGaming.class);
        startActivity(i);
    }
    public void onClick6(View view) {
        Intent i=new Intent(Predictor.this,DescriptionPsychosis.class);
        startActivity(i);
    }
    public void onClick7(View view) {
        Intent i=new Intent(Predictor.this,description_maleagg.class);
        startActivity(i);
    }
    public void onClick8(View view) {
        Intent i=new Intent(Predictor.this,description_toxic.class);
        startActivity(i);
    }

    public void onClick9(View view) {
        Intent i=new Intent(Predictor.this,DescriptionBorderline.class);
        startActivity(i);
    }

    public void onClick10(View view) {
        Intent i=new Intent(Predictor.this,DescriptionPanic.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.about){
            Intent i= new Intent(Predictor.this, about.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.signout){
            Intent i= new Intent(Predictor.this,Logout.class);
            startActivity(i);
            return true;
        }

        if(id==R.id.contact){
            Toast.makeText(Predictor.this,"Now You can write to developers directly!",Toast.LENGTH_LONG).show();
            Intent i= new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:08095030481"));
            i.putExtra("address", "08095030481");
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
        final AlertDialog.Builder builder=new AlertDialog.Builder(Predictor.this);
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
                Toast.makeText(Predictor.this,"Signing out...", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();

            }
        });
        AlertDialog alertdialog=builder.create();
        alertdialog.show();

    }

    public void voice(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Just Speak out the domain you want to test yourself. Even short forms, " +
                "like 'net' instead 'Internet addiction' may work!");
        startActivityForResult(i,check);



    }
    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);

        if(requestcode==check && resultcode==RESULT_OK)
        {
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
            if(results.contains("depression"))
            {
                Intent i=new Intent(Predictor.this,DescriptionDepression.class);
                startActivity(i);

            }
            if(results.contains("anxiety"))
            {
                Intent i=new Intent(Predictor.this,DescriptionAnx.class);
                startActivity(i);

            }
           else if(results.contains("internet addiction")||results.contains("internet")||results.contains("net"))
            {
                Intent i=new Intent(Predictor.this,DescriptionInternet.class);
                startActivity(i);
            }
           else if(results.contains("aggression"))
            {
                Intent i=new Intent(Predictor.this,DescriptionAggression.class);
                startActivity(i);
            }
            else if(results.contains("game addiction")||results.contains("game"))
            {
                Intent i=new Intent(Predictor.this,DescriptionGaming.class);
                startActivity(i);

            }

           else if(results.contains("psychosis"))
             {
                 Intent i=new Intent(Predictor.this,DescriptionPsychosis.class);
                 startActivity(i);
              }
            else if(results.contains("male aggression"))
            {
                Intent i1=new Intent(Predictor.this,description_maleagg.class);
                startActivity(i1);
            }
            else if(results.contains("toxic workplace"))
            {
                Intent i1=new Intent(Predictor.this,description_toxic.class);
                startActivity(i1);
            }
            else if((results.contains("borderline personality disorder"))||(results.contains("personality disorder"))||(results.contains("borderline")))
            {
                Intent i1=new Intent(Predictor.this,DescriptionBorderline.class);
                startActivity(i1);
            }
            else if(results.contains("panic attack"))
            {
                Intent i1=new Intent(Predictor.this,DescriptionPanic.class);
                startActivity(i1);
            }

           else
               Toast.makeText(Predictor.this,"None of the options matched! Try again!",Toast.LENGTH_SHORT).show();



        }
    }
    }
