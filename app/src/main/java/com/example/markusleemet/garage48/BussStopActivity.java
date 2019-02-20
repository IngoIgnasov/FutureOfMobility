package com.example.markusleemet.garage48;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class BussStopActivity extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buss_stop);
        listView = findViewById(R.id.bussesAtBussStation);
        listView.setAdapter(adapter);


        ArrayList<BusInfo> bussesList = new ArrayList<>();
        bussesList.add(new BusInfo("1", "Viimsi", "12"));


        adapter = new CustomAdapter(bussesList, this);
        listView.setAdapter(adapter);



    }

}
