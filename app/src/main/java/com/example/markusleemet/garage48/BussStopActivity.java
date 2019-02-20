package com.example.markusleemet.garage48;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class BussStopActivity extends AppCompatActivity {
    private ArrayList<BusInfo> bussesList;
    private ListView listView;
    private CustomAdapter adapter;
    private TextView nameOfStation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buss_stop);
        listView = findViewById(R.id.bussesAtBussStation);
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        nameOfStation = findViewById(R.id.nameOfStation);
        nameOfStation.setText(intent.getStringExtra("nameOfStation"));

        bussesList = new ArrayList<>();
        bussesList.add(new BusInfo("1","Jaama", "Viimsi", 300));
        bussesList.add(new BusInfo("2","Viljandi", "Tartu", 400));
        bussesList.add(new BusInfo("3","Põllu", "Keila", 70));

        adapter = new CustomAdapter(bussesList, this);
        listView.setAdapter(adapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                myTask();

                handler.postDelayed(this, 1000);
            }
        }, 1000);

/*        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.i("gpdthread", "lõim tööle");
                myTask();
            }
        }, 0, 1, TimeUnit.SECONDS);*/

    }


    private void myTask() {
        for (BusInfo bus : bussesList) {
            bus.tickDown();
        }
        //sorteerimine
        Collections.sort(bussesList);
        adapter.notifyDataSetChanged();
        Log.i("threadlogi", "jüudsin siia");

    }
}


