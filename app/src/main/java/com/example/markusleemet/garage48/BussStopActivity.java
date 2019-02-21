package com.example.markusleemet.garage48;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;


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
        bussesList.add(new BusInfo("1", "Jaama", "Viimsi", 300));
        bussesList.add(new BusInfo("2", "Viljandi", "Tartu", 400));
        bussesList.add(new BusInfo("3", "Põllu", "Keila", 70));


        adapter = new CustomAdapter(bussesList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusInfo busInfo = (BusInfo) parent.getItemAtPosition(position);
                Intent onTheBusIntent = new Intent(BussStopActivity.this, OnTheBusView.class);
                onTheBusIntent.putExtra("bussNumber", busInfo.getNumber());
                onTheBusIntent.putExtra("busDestination", busInfo.getDestination());
                startActivity(onTheBusIntent);
            }
        });

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
    }

}
