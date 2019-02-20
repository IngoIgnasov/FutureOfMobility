package com.example.markusleemet.garage48;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BussStopActivity extends AppCompatActivity {
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



        ArrayList<BusInfo> bussesList = new ArrayList<>();
        bussesList.add(new BusInfo("1", "Viimsi", "5.32"));
        bussesList.add(new BusInfo("2", "Tartu", "12.00"));
        bussesList.add(new BusInfo("3", "Keila", "17.43"));


        adapter = new CustomAdapter(bussesList, this);
        listView.setAdapter(adapter);



    }

}
