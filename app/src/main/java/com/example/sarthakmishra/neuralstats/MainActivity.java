package com.example.sarthakmishra.neuralstats;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private SignInButton msigninbtn;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button Login;
    private Button Signup;
    TextView googtxt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("theultimates");

        email = (EditText) findViewById(R.id.etName);
        pass = (EditText) findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();
        googtxt = findViewById(R.id.textView7);
        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("UserLogin");

        msigninbtn = (SignInButton) findViewById(R.id.googlesigninbtn);

        Login = (Button) findViewById(R.id.btnlogin);
        Signup = (Button) findViewById(R.id.signupbtn);

        msigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Signin.class);
                startActivity(i);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent i = new Intent(MainActivity.this, ImageSlider.class);
                    startActivity(i);
                }

            }
        };
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Toast.makeText(MainActivity.this, "Google sign in successfull, authenticating...", Toast.LENGTH_SHORT).show();

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String id=System.currentTimeMillis()+"";
                            final String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());


                            String emailfire = user.getEmail();
                            String namefire =  user.getDisplayName();
                            String phonefire = user.getPhoneNumber();

                            ref.child(id).child("Name").setValue(namefire);
                            ref.child(id).child("Email").setValue(emailfire);
                            ref.child(id).child("Phone number").setValue(phonefire);
                            ref.child(id).child("Key").setValue(id);
                            ref.child(id).child("Score").child(date).child("depression").setValue("0");
                            ref.child(id).child("Score").child(date).child("aggression").setValue("0");
                            ref.child(id).child("Score").child(date).child("gaming").setValue("0");
                            ref.child(id).child("Score").child(date).child("anxiety").setValue("0");
                            ref.child(id).child("Score").child(date).child("internet").setValue("0");
                            ref.child(id).child("Score").child(date).child("psychosis").setValue("0");
                            ref.child(id).child("Score").child(date).child("maleagg").setValue("0");
                            ref.child(id).child("Score").child(date).child("panic").setValue("0");
                            ref.child(id).child("Score").child(date).child("toxic").setValue("0");
                            ref.child(id).child("Score").child(date).child("borderline").setValue("0");

                            Toast.makeText(MainActivity.this, "Google sign in success, Neural Stats welcomes you.", Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(MainActivity.this, "Authentication failed, make sure internet connection is active.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }



    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure, want to exit ?");
        builder.setCancelable(true);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Exit!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    private void login() {
        String memail = email.getText().toString() + "";
        String mpass = pass.getText().toString() + "";

        if (memail.equals("") && mpass.equals("")) {
            Toast.makeText(MainActivity.this, "Both fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            if (memail.equals("")) {
                Toast.makeText(MainActivity.this, "Username can't be left blank", Toast.LENGTH_SHORT).show();

            }
            if (mpass.equals("")) {
                Toast.makeText(MainActivity.this, "Password can't be left blank", Toast.LENGTH_SHORT).show();

            }
        }

        if (!memail.equals("") && !mpass.equals("")) {
            mAuth.signInWithEmailAndPassword(memail, mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "You are not registered. Signup first", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, Signin.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
}
