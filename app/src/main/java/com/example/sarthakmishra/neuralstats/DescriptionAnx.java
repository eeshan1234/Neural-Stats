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

public class DescriptionAnx extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_anx);
        tv=findViewById(R.id.anxtxt);

        tv.setText("\nIt is an emotion characterized by an unpleasant state off " +
                "inner turmoil,often accompanied by nervous behaviour such as pacing back " +
                        "and forth,somatic complaints,and rumination.It is the subjecively " +
                        "unpleasant feelings of dread over anticipated " +
                        "events,such as the feelings of imminent death.Anxiety is not same as " +
                        "fear,which is a response to a real or percieved immediate " +
                "threat,Whereas anxiety is the expectation of future threat.");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    public void testanx(View view) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
     builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(DescriptionAnx.this,Anxiety.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(DescriptionAnx.this,Anxiety.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(DescriptionAnx.this,Anxiety.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(DescriptionAnx.this,Anxiety.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                        default: Toast.makeText(DescriptionAnx.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();

    }
}
