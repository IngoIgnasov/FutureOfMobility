package com.example.markusleemet.garage48;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class BussStopActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter adapter;
    private TextView nameOfStation;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buss_stop);
        listView = findViewById(R.id.bussesAtBussStation);
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        nameOfStation = findViewById(R.id.nameOfStation);
        nameOfStation.setText(intent.getStringExtra("nameOfStation"));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        ArrayList<BusInfo> bussesList = new ArrayList<>();
        bussesList.add(new BusInfo("13", "Viimsi", "2"));
        bussesList.add(new BusInfo("2", "Tartu", "7"));
        bussesList.add(new BusInfo("49", "Keila", "12"));


        adapter = new CustomAdapter(bussesList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusInfo busInfo = (BusInfo) parent.getItemAtPosition(position);
                Intent onTheBusIntent = new Intent(BussStopActivity.this, OnTheBusView.class);
                onTheBusIntent.putExtra("bussNumber", busInfo.getNumber());
                startActivity(onTheBusIntent);
            }
        });



    }

}
