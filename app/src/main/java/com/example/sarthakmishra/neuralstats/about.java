package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class about extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    static final int check=1111;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(about.this,Homepage.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_voice:
                   voice();
                    return true;
                case R.id.navigation_doctor:
                    Intent intent1=new Intent(about.this,MapsActivity.class);
                    startActivity(intent1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        WebView webView= (WebView) findViewById(R.id.webviewabt);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        setupFirebaseListener();

        String htmlText = " %s ";
        String myData = "<html><body style=\"text-align:justify\">Login page allows the user to sign in/log in one can also sign in through\n" +
                "their google accounts linked to their device.the home page consists of tabs namely  predictor ,report,doctor,reminder and about us.\n" +
                "The predictors page  consists of  set of tests to diagnose the illness. Each test includes a setb of questions with options.options here can \n" +
                "be selected either manually or through voice. After the test is done a report will be generated based on the user input\n" +
                "and the analysis will be displayed accordingly in the graphical representation.a comparison can be made with the previous test results\n" +
                "if any and monitor the progress. Based on the diagnosed mental condition one can approach or seek advice\n" +
                "by linking the google api from the doctor by using doctor's tab.user's current location is located\n" +
                "and the info about the hospitals located nearby  hospitals. Neuro Bot\nAn AI bot embedded to help the user to traverse through the content\n" +
                "of the app.its more than  a eprsonal assistant you would wish for,on whom u can totally rely on.got any query or an issue  dont  u worry,chatbot has got solutions to all of your problems.\n" +
                "things for instance that you can ask a chat bot could be the name of the test you would want to take or the word doctor to open the doctor's tab etc. " +
                "One can also enable voice  input to chat with the bot by clicking on the mic icon. You can set reminders to take test at regular intervals.you can set reminders for your daily activities through our app.you can also \n" +
                "monitor the amount of workout done per day or the mental excercises\n" +
                "done by setting reminders." +
                "For furthur queries, Contact us at : neuralstats@gmail.com<br><br></body></html>";

        myData.replace("\\r\\n", "<br>").replace("\\n", "<br>");
        webView.loadDataWithBaseURL(null,String.format(htmlText, myData), "text/html", "utf-8","utf-8");
        webSettings.setDefaultFontSize(19);
        webView.setBackgroundColor(000000);
        webView.setPaddingRelative(2,2,2,2);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    Toast.makeText(about.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(about.this, MainActivity.class);
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
            Intent i= new Intent(about.this, about.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.signout){
            Toast.makeText(about.this,"Signing out...", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            return true;
        }

        if(id==R.id.contact){
            Toast.makeText(about.this,"Now You can write to developers directly!",Toast.LENGTH_LONG).show();
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
    public void voice(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak out where you want to navigate to, in the app among Predictor" +
                ", Doctor, About, Home and Exit to exit from app. ");
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
                Intent i=new Intent(about.this,Predictor.class);
                startActivity(i);

            }
            if(results.contains("doctor"))
            {
                Intent i=new Intent(about.this,MapsActivity.class);
                startActivity(i);

            }
            else if(results.contains("about"))
            {
                Intent i=new Intent(about.this,about.class);
                startActivity(i);
            }
            else if(results.contains("Home"))
            {
                Intent i=new Intent(about.this,Homepage.class);
                startActivity(i);
            }
            else if(results.contains("exit"))
            {
                final AlertDialog.Builder builder=new AlertDialog.Builder(about.this);
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
                        Toast.makeText(about.this,"Signing out...", Toast.LENGTH_SHORT).show();

                        FirebaseAuth.getInstance().signOut();

                    }
                });
                AlertDialog alertdialog=builder.create();
                alertdialog.show();

            }

            else
                Toast.makeText(about.this,"None of the suitable choices matched! Try again with appropriate choices.!",Toast.LENGTH_SHORT).show();


        }
    }
    public void onBackPressed1() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(about.this);
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
                Toast.makeText(about.this,"Signing out...", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();

            }
        });
        AlertDialog alertdialog=builder.create();
        alertdialog.show();

    }

}
