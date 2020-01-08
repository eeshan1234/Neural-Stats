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

public class description_toxic extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_toxic);
        tv=findViewById(R.id.toxictxt);

        tv.setText("\n\n" +
                "Toxic workplaces are generally characterized by\n" +
                "culture of dysfunctional interpersonal dynamic\n" +
                "ics. These dynamics can play out in a number\n" +
                "of ways: inequity, unaddressed conflict, harass\n" +
                "ment, lack of communication, bullying, being\n" +
                "talked down to, setting people up for failure or\n" +
                "mismanagement. It can come from your boss,\n" +
                "your peers, your juniors and even your clients.\n" +
                "SIGNS OF A TOXIC WORKPLACE\n" +
                "Recent statistics tell us that most people spend more of their waking hours in their place of work " +
                "than at home with their families. Spending 8+ hours a day in an environment where you feel undervalued, under appreciated, undermined, or are perhaps even being bullied can make work\n" +
                "life even more challenging. Here are some signs that you may be working in a toxic workplace:\n" +
                "\n" +
                "Management playing favorites Managers who play favorites don't just reward good performance, they give undeserving employees perks just because they like them.\n" +
                "Poor leadership skills Sometimes toxic work environments are derived from how the boss treats their employees. Supervisors can take\n" +
                "advantage of their power, and set out to make their staff feel inferior.");


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

    public void testtoxic(View view) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(description_toxic.this,toxic.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(description_toxic.this,toxic.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(description_toxic.this,toxic.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(description_toxic.this,toxic.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                    default: Toast.makeText(description_toxic.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();
    }
}