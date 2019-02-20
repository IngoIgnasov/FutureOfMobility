package com.example.markusleemet.garage48;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class BussStopActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buss_stop);
        listView = findViewById(R.id.bussesAtBussStation);
        adapter = new ArrayAdapter<>(this, R.layout.busitem, R.id.textView, new ArrayList<>(Arrays.asList("buss nr1", "buss nr12", "buss nr13", "buss nr14", "buss nr15")));
        listView.setAdapter(adapter);

    }

}
