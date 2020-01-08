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

public class DescriptionPanic extends AppCompatActivity {
    private TextView tv,head;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_panic);
        tv=findViewById(R.id.panictext);

        tv.setText("\nMany people experience panic attacks without further episodes or complications. There is little\n" +
                "reason to worry if you've had just one or two panic attacks. However, some people who've" +
                "experienced panic attacks go on to develop panic disorder. Panic disorder is characterized by repeated " +
                "panic attacks, combined with major changes in behavior or persistent anxiety. While a single panic attack may only last\n" +
                "few minutes, for some individuals the effects of the experience can leave a lasting imprint\n" +
                "The memory of the intense fear and terror that you felt during the attacks can negatively impact your self-confidence and cause serious\n" +
                "disruption to your everyday life. Instead offeeling relaxed and like yourself in between\n" +
                "panic attacks, those with panic disorder feel anxious and tense. This \"fear of fear\" is present most of the time, and can be extremely disabling. In some cases, individuals may begin\n" +
                "to avoid certain situations or environments.\n" +
                "SIGNS OF PANIC DISORDER\n" +
                "Expected or unexpected panic attacks\n" +
                "Feeling exhausted\n" +
                "Difficulty sleeping\n" +
                "Shortness of breath\n" +
                "Heart palpitations");


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

    public void testna(View view) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose the language, you are comfortable in.:\n");
        builder.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Intent i=new Intent(DescriptionPanic.this,panic.class);
                        i.putExtra("refvalue","eng");
                        startActivity(i);
                        break;

                    case 1:Intent i1=new Intent(DescriptionPanic.this,panic.class);
                        i1.putExtra("refvalue","hin");
                        startActivity(i1);
                        break;
                    case 2:Intent i2=new Intent(DescriptionPanic.this,panic.class);
                        i2.putExtra("refvalue","kan");
                        startActivity(i2);
                        break;
                    case 3:Intent i3=new Intent(DescriptionPanic.this,panic.class);
                        i3.putExtra("refvalue","tel");
                        startActivity(i3);
                        break;

                    default: Toast.makeText(DescriptionPanic.this,"No language selected!",Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.show();
    }
}


