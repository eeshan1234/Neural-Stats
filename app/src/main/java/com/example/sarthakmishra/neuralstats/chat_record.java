package com.example.sarthakmishra.neuralstats;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;



public class chat_record extends RecyclerView.ViewHolder  {



    TextView leftText,rightText;

    public chat_record(View itemView){
        super(itemView);

        leftText = (TextView)itemView.findViewById(R.id.leftText);
        rightText = (TextView)itemView.findViewById(R.id.rightText);


    }
}
