package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class description_maleagg extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_maleagg);
        tv=findViewById(R.id.maletxt);

        tv.setText("\nWhen you act out with the intent to cause physical or emotional harm to another person " +
                "or object, this behavior is known as aggression. Aggressive behavior can be a knee-jerk reaction\n" +
                "to a trigger, or it can be planned in advance. Aggression typically falls into two categories:\n" +
                "Physical or hostile aggression\nEmotional or relational aggression " +
                "Hostile aggression happens when you attempt to injure an individual by way of hitting, pinching, biting, pushing, or inflicting any other type of physical harm. Destroying property is also\n" +
                "considered hostile aggression. Relational aggression can involve spreading rumors, teasing someone, or intentionally excluding\n" +
                "person so that he or she feels badly. However, regardless of the type of aggression\n" +
                "that you display, it is likely that this kind of behavior can cause problems within your own\n" +
                "life and in the lives of those you care about.\n" +
                "For many people, aggressive behaviors are\n learned early in life. Some people behave aggressively while under the influence of drugs,\n" +
                "or due to the presence of an untreated mental\n" +
                "health disorder.\nWhat is most important to know is that agreession, no matter the cause, can be treated with " +
                "proper care. With the support of professionals, you can learn to manage your aggression so that you can live a happy and productive life.");


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

    public void testmaleagg(View view) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(description_maleagg.this,maleagg.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(description_maleagg.this,maleagg.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(description_maleagg.this,maleagg.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(description_maleagg.this,maleagg.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                    default: Toast.makeText(description_maleagg.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();
    }
}

