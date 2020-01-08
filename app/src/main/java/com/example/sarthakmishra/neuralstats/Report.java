package com.example.sarthakmishra.neuralstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Report extends AppCompatActivity {
static String[] topics={"Depression","Anxiety","Aggression","Game Addiction","Internet Addiciton","Psychosis"};
ArrayAdapter<String> adapter;
 Button btn;
 AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

    }
public void opendialog(View v)
{
    AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
    View row=getLayoutInflater().inflate(R.layout.rowlist,null);

    ListView l1=row.findViewById(R.id.rowlistreport);

    adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,topics);
    l1.setAdapter(adapter);
    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String selectedItem=topics[position];
            if(selectedItem.equals("Depression"))
            {
                Intent i1=new Intent(Report.this,reportdep.class);
                startActivity(i1);
            }
            else if(selectedItem.equals("Psychosis"))
            {
                Intent i1=new Intent(Report.this,reportpsycho.class);
                startActivity(i1);
            }
            else if(selectedItem.equals("Anxiety"))
            {
                Intent i1=new Intent(Report.this,reportanxiety.class);
                startActivity(i1);
            }
            else if(selectedItem.equals("Aggression"))
            {
                Intent i1=new Intent(Report.this,reportaggression.class);
                startActivity(i1);
            }
            else if(selectedItem.equals("Game Addiction"))
            {
                Intent i1=new Intent(Report.this,reportGame.class);
                startActivity(i1);
            }
            else if(selectedItem.equals("Internet Addiciton"))
            {
                Intent i1=new Intent(Report.this,reportinternet.class);
                startActivity(i1);
            }
            else
            {
                Toast.makeText(Report.this,"No options selected!",Toast.LENGTH_SHORT).show();
            }
        }
    });

    adapter.notifyDataSetChanged();


    alertdialog.setView(row);
    AlertDialog dialog=alertdialog.create();
    dialog.show();

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}
