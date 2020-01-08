package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Anxiety extends AppCompatActivity {
    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    private TextView mQuestionView;
    private RadioButton Choice1;
    private RadioButton Choice2;
    private RadioButton Choice3;
    private RadioButton Choice4;
    RadioGroup rg;
    private Button Next;
    int[] value = new int[100];
    private int mQuestionNumber = 0;
    int i = 0, j = 1, sum = 0, k;
    Boolean b = true;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    DatabaseReference refques,refans,refval;
    private FirebaseAuth firebaseAuth;
    int r;
    int count = 0;int check=0;
    String id; String lang;
    int radioselected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anxiety);

        firebaseDatabase= FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("UserLogin");
        Next=findViewById(R.id.AxNextButton1);

       lang=getIntent().getStringExtra("refvalue");
        refval=firebaseDatabase.getReference("Userquesval").child("Anxiety");

        if(lang.equals("tel"))
        {
            refques=firebaseDatabase.getReference("Userquestelugu").child("Anxiety");
            refans=firebaseDatabase.getReference("Useranstelugu").child("Anxiety");
            Next.setText("తరువాత");
        }

        else if(lang.equals("hin"))
        {
            refques=firebaseDatabase.getReference("Userqueshindi").child("Anxiety");
            refans=firebaseDatabase.getReference("Useranshindi").child("Anxiety");
            Next.setText("आगामी");
        }
        else if(lang.equals("kan"))
        {
            refques=firebaseDatabase.getReference("Userqueskannada").child("Anxiety");
            refans=firebaseDatabase.getReference("Useranskannada").child("Anxiety");
            Next.setText("ಮುಂದೆ");
        }

        else
        {
            refques=firebaseDatabase.getReference("Userques").child("Anxiety");
            refans=firebaseDatabase.getReference("Userans").child("Anxiety");
            Next.setText("Next");
        }

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String curruseremail=user.getEmail();
        final String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());


        mQuestionView = (TextView) findViewById(R.id.AxQues1);
        Choice1 = (RadioButton) findViewById(R.id.AxQ1Opt1);
        Choice2 = (RadioButton) findViewById(R.id.AxQ1Opt2);
        Choice3 = (RadioButton) findViewById(R.id.AxQ1Opt3);
        Choice4 = (RadioButton) findViewById(R.id.AxQ1Opt4);
        rg=(RadioGroup)findViewById(R.id.radioGroup);

        refques.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String ques1= postSnapshot.child(0+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques1,0);
                    String ques2= postSnapshot.child(1+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques2,1);
                    String ques3= postSnapshot.child(2+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques3,2);
                    String ques4= postSnapshot.child(3+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques4,3);
                    String ques5= postSnapshot.child(4+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques5,4);
                    String ques6= postSnapshot.child(5+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques6,5);
                    String ques7= postSnapshot.child(6+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques7,6);
                    String ques8= postSnapshot.child(7+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques8,7);
                    String ques9= postSnapshot.child(8+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques9,8);
                    String ques10= postSnapshot.child(9+"").getValue().toString();
                    mQuestionLibrary.setQuestionAx(ques10,9);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    for(int x=0;x<=9;x++)
                    {
                        String choice1= postSnapshot.child(0+"").getValue().toString();
                        mQuestionLibrary.setChoice1Ax(choice1,x);
                        String choice2= postSnapshot.child(1+"").getValue().toString();
                        mQuestionLibrary.setChoice2Ax(choice2,x);
                        String choice3= postSnapshot.child(2+"").getValue().toString();
                        mQuestionLibrary.setChoice3Ax(choice3,x);
                        String choice4= postSnapshot.child(3+"").getValue().toString();
                        mQuestionLibrary.setChoice4Ax(choice4,x);

                    }
                }
                mQuestionView.setText(mQuestionLibrary.getQuestionAx(0));
                Choice1.setText(mQuestionLibrary.getChoice1Ax(0));
                Choice2.setText(mQuestionLibrary.getChoice2Ax(0));
                Choice3.setText(mQuestionLibrary.getChoice3Ax(0));
                Choice4.setText(mQuestionLibrary.getChoice4Ax(0));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radiofnclick(view))
                        {  if (Choice1.isChecked())
                        {
                            radioselected=1;
                            //value[i] = 1;
                        }


                        else if (Choice2.isChecked())
                        {
                            radioselected=2;
                            // value[i] = 2;

                        }

                        else if (Choice3.isChecked())
                        {
                            radioselected=3;
                            //value[i] = 3;
                        }

                        else if (Choice4.isChecked())
                        {
                            radioselected=4;
                            // value[i] = 4;
                        }

                            refval.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                        if(((i)+"").equals(postSnapshot.child("qno").getValue().toString()))
                                        {
                                            String val=postSnapshot.child(radioselected+"").getValue().toString();
                                            value[i]=Integer.parseInt(val);
                                            //  Toast.makeText(Aggression.this,"Value of option selected: "+value[i]+" nd i= "+i,Toast.LENGTH_LONG).show();
                                        }


                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });





                            if(i==9)
                            {
                                int j,sum=0;
                               final double percent;
                                for(j=0;j<=9;j++){
                                    sum=sum+value[j];
                                }
                                percent=((sum/40.0)*100);
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            String email= postSnapshot.child("Email").getValue().toString();

                                            if(curruseremail.equals(email))
                                            {
                                                id=postSnapshot.child("Key").getValue().toString();
                                                break;
                                            }
                                            else
                                                continue;
                                        }

                                        ref.child(id).child("Score").child(date).child("anxiety").setValue(percent);

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Anxiety.this);
                                        builder.setMessage("Do you want to see report?");
                                        builder.setCancelable(true);

                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i=new Intent(Anxiety.this,Homepage.class);
                                                startActivity(i);
                                            }
                                        });

                                        builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i=new Intent(Anxiety.this,reportanxiety.class);
                                                startActivity(i);
                                            }
                                        });
                                        AlertDialog alertdialog = builder.create();
                                        alertdialog.show();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                               else if (i <= 8) {
                                mQuestionView.setText(mQuestionLibrary.getQuestionAx(i + 1));

                                Choice1.setText(mQuestionLibrary.getChoice1Ax(i + 1));
                                Choice2.setText(mQuestionLibrary.getChoice2Ax(i + 1));
                                Choice3.setText(mQuestionLibrary.getChoice3Ax(i + 1));
                                Choice4.setText(mQuestionLibrary.getChoice4Ax(i + 1));

                                rg.clearCheck();

                            }
                        }
                        else{
                            i--;
                            }
                         i++;
                }


        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Anxiety.this,Homepage.class);
        startActivity(i);
    }

    public boolean radiofnclick(View view)
    {
        if(Choice1.isChecked()||Choice2.isChecked()||Choice3.isChecked()||Choice4.isChecked())
            return true;

        return false;

    }

    public void save(final String curruseremail, final Double score){
        final String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String email= postSnapshot.child("Email").getValue().toString();

                    if(curruseremail.equals(email))
                    {
                        id=postSnapshot.child("Key").getValue().toString();
                        break;
                    }
                    else
                        continue;
                }

                ref.child(id).child("Score").child(date).child("anxiety").setValue(score);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}