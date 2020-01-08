package com.example.sarthakmishra.neuralstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logout extends AppCompatActivity {
Button logout,homebtn;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        logout=(Button)findViewById(R.id.logoutbtn);
        homebtn=(Button)findViewById(R.id.homebtn);
        setupFirebaseListener();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.d("AccountFragment","onClick(attempt to sign out user");
              FirebaseAuth.getInstance().signOut();

            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Logout.this,Homepage.class);
                startActivity(i);
            }
        });
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
                    Toast.makeText(Logout.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Logout.this, MainActivity.class);
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
}