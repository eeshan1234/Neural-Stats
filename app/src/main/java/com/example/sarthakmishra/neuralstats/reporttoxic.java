package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class reporttoxic extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    String id;
    float anxietyscore, aggressionscore,depressionscore,Gamescore,internetscore,psychosisscore,maleaggscore, panicscore,toxicscore,borderlinescore;
    BarChart barChart;
    static final int check=1111;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private YouTubePlayerView youTubeView2;
    private reporttoxic.MyPlayerStateChangeListener playerStateChangeListener;
    private reporttoxic.MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    DecoView arcView;
    TextView view;
    TextView Testtype,Testdescription;
    private ReportDescriptionResLibrary mDescriptionResLibrary=new ReportDescriptionResLibrary();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(reporttoxic.this,Homepage.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_voice:
                    voice();
                    return true;
                case R.id.navigation_doctor:
                    Intent intent=new Intent(reporttoxic.this,MapsActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporttoxic);

        setupFirebaseListener();
        barChart=(BarChart)findViewById(R.id.graph);
        final ArrayList<BarEntry> barentries=new ArrayList<>();
        Testdescription=findViewById(R.id.TestDescription);
        Testtype=findViewById(R.id.TestType);
        arcView=(DecoView)findViewById(R.id.dynamicArcView);
        view=(TextView)findViewById(R.id.fillview);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view1);

        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        ViewPager viewPager=findViewById(R.id.ViewpagerReport);
        ImageAdapter adapter=new ImageAdapter(this);

        viewPager.setAdapter(adapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("UserLogin");
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        final String curruseremail=user.getEmail();
        final String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  Iterable<DataSnapshot> csnap = dataSnapshot.getChildren();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String email = postSnapshot.child("Email").getValue().toString();
                    if (curruseremail.equals(email)) {
                        anxietyscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("anxiety").getValue().toString());
                        aggressionscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("aggression").getValue().toString());
                        depressionscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("depression").getValue().toString());
                        Gamescore = Float.parseFloat(postSnapshot.child("Score").child(date).child("gaming").getValue().toString());
                        internetscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("internet").getValue().toString());
                        psychosisscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("psychosis").getValue().toString());
                        maleaggscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("maleagg").getValue().toString());
                        panicscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("panic").getValue().toString());
                        toxicscore = Float.parseFloat(postSnapshot.child("Score").child(date).child("toxic").getValue().toString());
                        borderlinescore = Float.parseFloat(postSnapshot.child("Score").child(date).child("borderline").getValue().toString());


                        break;
                    } else
                        continue;
                }
                if (anxietyscore == 0 && aggressionscore == 0 && depressionscore == 0 && Gamescore == 0 && internetscore == 0 && psychosisscore == 0
                        &&maleaggscore==0&&panicscore==0&&toxicscore==0&&borderlinescore==0) {
                    Toast.makeText(reporttoxic.this, "You have not taken any test yet!", Toast.LENGTH_SHORT).show();
                }

//                barentries.add(new BarEntry(depressionscore, 0));
//                barentries.add(new BarEntry(50f, 1));
//
//                barentries.add(new BarEntry(anxietyscore, 2));
//                barentries.add(new BarEntry(50f, 3));
//
//                barentries.add(new BarEntry(internetscore, 4));
//                barentries.add(new BarEntry(50f, 5));
//
//                barentries.add(new BarEntry(Gamescore, 6));
//                barentries.add(new BarEntry(50f, 7));
//
//                barentries.add(new BarEntry(aggressionscore, 8));
//                barentries.add(new BarEntry(50f, 9));
//
//                barentries.add(new BarEntry(psychosisscore, 10));
//                barentries.add(new BarEntry(50f, 11));
//
//                barentries.add(new BarEntry(maleaggscore, 10));
//                barentries.add(new BarEntry(50f, 11));
//
//                barentries.add(new BarEntry(panicscore, 10));
//                barentries.add(new BarEntry(50f, 11));

                barentries.add(new BarEntry(toxicscore, 0));
                barentries.add(new BarEntry(50f, 1));
//
//                barentries.add(new BarEntry(borderlinescore, 10));
//                barentries.add(new BarEntry(50f, 11));

                BarDataSet barDataSet = new BarDataSet(barentries, "Tests");
                barDataSet.setBarSpacePercent(10f);
                barDataSet.setHighlightEnabled(true);
                barDataSet.setColors(new int[]{R.color.white, R.color.colorAccent, R.color.white, R.color.colorAccent,
                        R.color.white, R.color.colorAccent, R.color.white, R.color.colorAccent, R.color.white,
                        R.color.colorAccent, R.color.white, R.color.colorAccent
                });

                ArrayList<String> theTests = new ArrayList<>();
//                theTests.add("Depression");
//                theTests.add("Avg Depression");
//
//                theTests.add("Anxiety");
//                theTests.add("Avg Anxiety");
//
//                theTests.add("Internet");
//                theTests.add("Avg Internet");
//
//                theTests.add("Game");
//                theTests.add("Avg Game");
//
//                theTests.add("Aggression");
//                theTests.add("Avg Aggression");
//
//                theTests.add("Psychosis");
//                theTests.add("Avg Psychosis");
//
//                theTests.add("Male Agg.");
//                theTests.add("Avg Male Agg.");

                theTests.add("Toxic");
                theTests.add("Avg Toxic");

//                theTests.add("Panic");
//                theTests.add("Avg Panic");
//
//                theTests.add("Borderline");
//                theTests.add("Avg Borderline");

                BarData theData = new BarData(theTests, barDataSet);
                barChart.setData(theData);
                barChart.setTouchEnabled(true);
                barChart.setScaleEnabled(true);

//fill chart
                // Create background track
                arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                        .setRange(0, 100, 100)
                        .setInitialVisibility(false)
                        .setLineWidth(30f)
                        .build());

//Create data series track
                final SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                        .setRange(0, 100, 0)
                        .setInitialVisibility(false)
                        .setLineWidth(30f)
                        .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_OUTER, Color.parseColor("#22000000"), 0.2f))
                        .setSeriesLabel(new SeriesLabel.Builder("Percent %.0f%%").setVisible(false).build())
                        //.setInterpolator(new OvershootInterpolator())
                        .setShowPointWhenEmpty(false)
                        .setCapRounded(false)
                        .setInset(new PointF(32f, 32f))
                        .setDrawAsPoint(false)
                        .setSpinClockwise(true)
                        .setSpinDuration(2000)
                        .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                        .build();

                int series1Index = arcView.addSeries(seriesItem1);
                arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                        .setDelay(1000)
                        .setDuration(1000)
                        .build());


                //Entered percent of each test will be here
                arcView.addEvent(new DecoEvent.Builder(toxicscore).setIndex(series1Index).setDelay(2000).build());

                arcView.configureAngles(360, 0);
                arcView.setHorizGravity(DecoView.HorizGravity.GRAVITY_HORIZONTAL_FILL);
                arcView.setVertGravity(DecoView.VertGravity.GRAVITY_VERTICAL_BOTTOM);


                final String format = "%.0f%%";

                seriesItem1.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
                    @Override
                    public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                        if (format.contains("%%")) {
                            float percentFilled = ((currentPosition - seriesItem1.getMinValue()) / (seriesItem1.getMaxValue() - seriesItem1.getMinValue()));
                            view.setText(String.format(format, percentFilled * 100f));
                        } else {
                            view.setText(String.format(format, currentPosition));
                        }
                    }

                    @Override
                    public void onSeriesItemDisplayProgress(float percentComplete) {

                    }

                });

                if (toxicscore >= 0 && toxicscore <= 25) {
                    Testtype.setText(mDescriptionResLibrary.getTypetoxic(0));
                    Testdescription.setText(mDescriptionResLibrary.getDescriptiontoxic(0));

                } else if (toxicscore >= 25 && toxicscore <= 50) {
                    Testtype.setText(mDescriptionResLibrary.getTypetoxic(1));
                    Testdescription.setText(mDescriptionResLibrary.getDescriptiontoxic(1));
                } else if (toxicscore >= 50 && toxicscore <= 75) {
                    Testtype.setText(mDescriptionResLibrary.getTypetoxic(2));
                    Testdescription.setText(mDescriptionResLibrary.getDescriptiontoxic(2));

                } else {
                    Testtype.setText(mDescriptionResLibrary.getTypetoxic(3));
                    Testdescription.setText(mDescriptionResLibrary.getDescriptiontoxic(3));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//videos


        playerStateChangeListener = new reporttoxic.MyPlayerStateChangeListener();
        playbackEventListener = new reporttoxic.MyPlaybackEventListener();

        final EditText seekToText = (EditText) findViewById(R.id.seek_to_text);
        Button seekToButton = (Button) findViewById(R.id.seek_to_button);
        seekToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int skipToSecs = Integer.valueOf(seekToText.getText().toString());
                player.seekToMillis(skipToSecs * 1000);
            }
        });

    }  @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {
            player.cueVideo("26U_seo0a1g"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }

        if(requestCode==check && resultCode==RESULT_OK)
        {
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
            if(results.contains("predictor"))
            {
                Intent i=new Intent(reporttoxic.this,Predictor.class);
                startActivity(i);

            }
            if(results.contains("doctor"))
            {
                Intent i=new Intent(reporttoxic.this,MapsActivity.class);
                startActivity(i);

            }
            else if(results.contains("about"))
            {
                Intent i=new Intent(reporttoxic.this,about.class);
                startActivity(i);
            }
            else if(results.contains("Home"))
            {
                Intent i=new Intent(reporttoxic.this,Homepage.class);
                startActivity(i);
            }
            else if(results.contains("exit"))
            {
                final AlertDialog.Builder builder=new AlertDialog.Builder(reporttoxic.this);
                builder.setMessage("Are you sure, want to exit ?");
                builder.setCancelable(true);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Exit from app!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(reporttoxic.this,"Signing out...", Toast.LENGTH_SHORT).show();

                        FirebaseAuth.getInstance().signOut();

                    }
                });
                AlertDialog alertdialog=builder.create();
                alertdialog.show();

            }

            else
                Toast.makeText(reporttoxic.this,"None of the suitable choices matched! Try again with appropriate choices.!",Toast.LENGTH_SHORT).show();

        }

    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
            // showMessage("Playing");
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
            // showMessage("Paused");
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
            // showMessage("Stopped");
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

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
                    Toast.makeText(reporttoxic.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(reporttoxic.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(reporttoxic.this,Homepage.class);
        startActivity(i);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.about){
            Intent i= new Intent(reporttoxic.this, about.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.signout){
            Toast.makeText(reporttoxic.this,"Signing out...", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            return true;
        }

        if(id==R.id.contact){
            Toast.makeText(reporttoxic.this,"Now You can write to developers directly!",Toast.LENGTH_LONG).show();
            Intent i= new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:08095030481"));
            i.putExtra("address", "08095030481");
            startActivity(i);
            return true;
        }

        if(id==R.id.exit){
            onBackPressed1();
            return true;
        }
        if(id==android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed1() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(reporttoxic.this);
        builder.setMessage("Are you sure, want to exit ?");
        builder.setCancelable(true);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Exit from app!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(reporttoxic.this,"Signing out...", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();

            }
        });
        AlertDialog alertdialog=builder.create();
        alertdialog.show();

    }

    public void voice(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak out where you want to navigate to, in the app among Predictor" +
                ", Doctor, About, Home and Exit to exit from app. ");
        startActivityForResult(i,check);



    }

}
