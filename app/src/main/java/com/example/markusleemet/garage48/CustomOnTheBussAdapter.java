package com.example.markusleemet.garage48;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomOnTheBussAdapter extends ArrayAdapter<OnTheBussInfo>{
    private ArrayList<OnTheBussInfo> onTheBussInfo;
    Context mContext;


    public CustomOnTheBussAdapter(ArrayList<OnTheBussInfo> data, Context context) {
        super(context, R.layout.on_the_buss_list, data);
        this.onTheBussInfo = data;
        this.mContext=context;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.on_the_buss_list, parent,false);

        OnTheBussInfo currenBusInfo = onTheBussInfo.get(position);


        TextView stationName = (TextView) listItem.findViewById(R.id.stationNameAtFinalView);
        stationName.setText(currenBusInfo.getStationName());

        TextView time = (TextView) listItem.findViewById(R.id.timeAtFinalView);
        time.setText(currenBusInfo.getTime() + "m");

        return listItem;
    }


}
