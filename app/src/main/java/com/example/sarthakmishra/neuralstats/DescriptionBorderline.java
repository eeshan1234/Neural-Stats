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

public class DescriptionBorderline extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_borderline);
        tv=findViewById(R.id.bordertxt);

        tv.setText("\nBorderline personality disorder (BPD) is\n" +
                "disorder of mood and how a person interacts\n" +
                "with others. It's the most commonly recognized personality disorder.\nIn general, someone with a personality disorder will differ significantly from an average person\n" +
                "in terms of how he or she thinks, perceives, feels\n" +
                "or relates to others.\n" +
                "The symptoms of BPD can be grouped into four\n" +
                "main areas:\n" +
                "1)Emotional instability the psychological term for this is \"affective dysregulation\"\n" +
                "2)Disturbed patterns of thinking or perception (\"cognitive distortions\" or\"perceptual distortions\")\n" +
                "3)Impulsive behavior Intense but unstable relationships with others." +
                "\nIf you have BPD, you may experience a range of\n" +
                "often intense negative emotions, such as:\n" +
                "Rage\n" +
                "Sorrow\n" +
                "Shame\n" +
                "Panic\n" +
                "Terror\n" +
                "\n" +
                "Long-term feelings of emptiness and\n" +
                "loneliness You may have severe mood swings over a short\n" +
                "space of time.");


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

    public void testborder(View view) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(DescriptionBorderline.this,borderline.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(DescriptionBorderline.this,borderline.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(DescriptionBorderline.this,borderline.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(DescriptionBorderline.this,borderline.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                    default: Toast.makeText(DescriptionBorderline.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();
    }
}