package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DescriptionInternet extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_internet);
        setupFirebaseListener();

        tv=findViewById(R.id.nettxt);

        tv.setText("\nIt is a mental condition characterised by excessive use of " +
                "internet, usually to the detriment of the user " +
                "Addiction is generally understood to be a mental disorder " +
                "involving compulsive behavior." +
                "When someone is constantly online, they may be described as being addicted to it.");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    Toast.makeText(DescriptionInternet.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(DescriptionInternet.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    public void testnet(View view) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(DescriptionInternet.this,InternetAddiction.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(DescriptionInternet.this,InternetAddiction.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(DescriptionInternet.this,InternetAddiction.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(DescriptionInternet.this,InternetAddiction.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                    default: Toast.makeText(DescriptionInternet.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();
    }
}
