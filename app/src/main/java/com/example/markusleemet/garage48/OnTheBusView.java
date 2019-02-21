package com.example.markusleemet.garage48;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OnTheBusView extends AppCompatActivity {
    private TextView bussNumber;
    private TextView littleText;
    private ListView listView;
    private CustomOnTheBussAdapter onTheBussAdapter;

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
        littleText.setText("buss " + getIntent().getStringExtra("bussNumber"));


        ArrayList<OnTheBussInfo> stopsList = new ArrayList<>();
        stopsList.add(new OnTheBussInfo("Viimsi", "2"));
        stopsList.add(new OnTheBussInfo("Tartu", "5"));
        stopsList.add(new OnTheBussInfo("Tallinn", "7"));


        onTheBussAdapter = new CustomOnTheBussAdapter(stopsList, this);
        listView.setAdapter(onTheBussAdapter);

    }
}
