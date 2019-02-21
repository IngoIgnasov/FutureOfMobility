package com.example.markusleemet.garage48;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class OnTheBusView extends AppCompatActivity {
    private TextView bussNumber;
    private TextView littleText;
    private ListView listView;
    private CustomOnTheBussAdapter onTheBussAdapter;
    ArrayList<OnTheBussInfo> stopsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_the_bus_view);
        bussNumber = findViewById(R.id.bussNumberAtFinalView);

        Log.i("intent", getIntent().getStringExtra("busDestination"));
        Log.i("intent", getIntent().getStringExtra("bussNumber"));

        bussNumber.setText(getIntent().getStringExtra("busDestination"));
        listView = findViewById(R.id.finalList);
        littleText = findViewById(R.id.littleText);
        littleText.setText("bus " + getIntent().getStringExtra("bussNumber"));


        stopsList = new ArrayList<>();
        stopsList.add(new OnTheBussInfo("Sporta akadēmija", 150));
        stopsList.add(new OnTheBussInfo("Meža skola", 65));
        stopsList.add(new OnTheBussInfo("Tirzas iela", 189));


        onTheBussAdapter = new CustomOnTheBussAdapter(stopsList, this);
        listView.setAdapter(onTheBussAdapter);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                myTask();

                handler.postDelayed(this, 1000);
            }
        }, 1000);

    }

    private void myTask() {
        for (OnTheBussInfo businfo : stopsList) {
            businfo.tickDown();
        }
        //sorteerimine
        Collections.sort(stopsList);
        onTheBussAdapter.notifyDataSetChanged();
    }
}
