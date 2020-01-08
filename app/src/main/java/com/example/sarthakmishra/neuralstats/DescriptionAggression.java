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

public class DescriptionAggression extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_aggression);

        tv=findViewById(R.id.aggtxt);

        tv.setText("\nIn psychology and other social and behavioral sciences, aggression refers to behavior that " +
                "is intended to cause harm or pain, property damage and other destructive behaviour may also fall under the definition of aggression.");

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

    public void testagg(View view) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(DescriptionAggression.this,Aggression.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(DescriptionAggression.this,Aggression.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(DescriptionAggression.this,Aggression.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(DescriptionAggression.this,Aggression.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                    default: Toast.makeText(DescriptionAggression.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();

    }
}
