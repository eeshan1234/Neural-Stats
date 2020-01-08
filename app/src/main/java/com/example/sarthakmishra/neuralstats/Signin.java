package com.example.sarthakmishra.neuralstats;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Signin extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText email,pass,confpass;
    public EditText name;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        name=(EditText)findViewById(R.id.nameet);
        email=(EditText)findViewById(R.id.emailet);
        pass=(EditText)findViewById(R.id.passet);
        confpass=(EditText)findViewById(R.id.confpass);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("UserLogin");
        signup=(Button)findViewById(R.id.signupbtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupfn();
            }
        });
    }


    private void signupfn() {
        final String memail = email.getText().toString()+"";
        final String mpass = pass.getText().toString()+"";
        String mconfpass = confpass.getText().toString()+"";
        final String mname = name.getText().toString()+"";

        boolean checkerror = false;

        if(mname.equals("")&&memail.equals("")&&mpass.equals("")&&mconfpass.equals("")){
            checkerror = true;
            Toast.makeText(Signin.this, "You have not entered any data. Please fill form", Toast.LENGTH_SHORT).show();
        }

        else {
            if (mname.equals("")) {
                checkerror = true;
                Toast.makeText(Signin.this, "Name can't be empty", Toast.LENGTH_SHORT).show();
            }
            if (memail.equals("")) {
                checkerror = true;
                Toast.makeText(Signin.this, "E-mail can't be empty", Toast.LENGTH_SHORT).show();
            }

            if (mpass.equals("") || mconfpass.equals("")) {
                checkerror = true;
                Toast.makeText(Signin.this, "Passwords can't be empty", Toast.LENGTH_SHORT).show();
            }

            if (!mpass.equals(mconfpass)) {
                checkerror = true;
                Toast.makeText(Signin.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
        }
        if(checkerror)
        {
            email.setText("");
            pass.setText("");
            confpass.setText("");
            name.setText("");
        }
        else{
            //save data to firebase

            firebaseAuth.createUserWithEmailAndPassword(memail, mpass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.d("AccountManager", "createUserWithEmail:success");

                                sendEmailVerification() ;

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String id=System.currentTimeMillis()+"";
                                final String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
                        Toast.makeText(Signin.this,"Date: "+date,Toast.LENGTH_SHORT).show();

                                ref.child(id).child("Name").setValue(mname);
                                ref.child(id).child("Email").setValue(memail);
                                ref.child(id).child("Password").setValue(mpass);
                                ref.child(id).child("Key").setValue(id);
                                ref.child(id).child("Score").child(date).child("depression").setValue("0");
                                ref.child(id).child("Score").child(date).child("aggression").setValue("0");
                                ref.child(id).child("Score").child(date).child("gaming").setValue("0");
                                ref.child(id).child("Score").child(date).child("anxiety").setValue("0");
                                ref.child(id).child("Score").child(date).child("internet").setValue("0");
                                ref.child(id).child("Score").child(date).child("psychosis").setValue("0");


                                boolean emailVerified=user.isEmailVerified();
                                if(emailVerified){
                                    Toast.makeText(Signin.this, "EMAIL VERIFIED", Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                }


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("AccountManager", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Signin.this, "Authentication failed. Make sure you are entering correct email address and Password containing" +
                                                " at least 6 characters!",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user)
    {
        if(user!=null)
        {
            Toast.makeText(Signin.this,"You are registered successfully", Toast.LENGTH_SHORT).show();

        }

    }
    private void sendEmailVerification() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Signin.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("AccountManager", "sendEmailVerification", task.getException());

                            Toast.makeText(Signin.this,
                                    "Failed to send verification email. Check email-id.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });
    }
}
