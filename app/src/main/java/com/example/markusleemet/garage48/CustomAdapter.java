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

public class CustomAdapter extends ArrayAdapter<BusInfo> implements View.OnClickListener{
    private ArrayList<BusInfo> busInfo;
    Context mContext;


    public CustomAdapter(ArrayList<BusInfo> data, Context context) {
        super(context, R.layout.buss_information, data);
        this.busInfo = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.buss_information, parent,false);

        BusInfo currenBusInfo = busInfo.get(position);


        TextView number = (TextView) listItem.findViewById(R.id.number);
        number.setText(currenBusInfo.getNumber());

        TextView destination = (TextView) listItem.findViewById(R.id.destination);
        destination.setText(currenBusInfo.getDestination());

        TextView arrival = (TextView) listItem.findViewById(R.id.arrival);
        arrival.setText(currenBusInfo.getArriveingTime());

        return listItem;
    }
}
