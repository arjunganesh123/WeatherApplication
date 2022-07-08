package com.example.weatherapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherModel> weatherModellist;
    public WeatherAdapter(Context context, ArrayList<WeatherModel> weatherModellist) {
        this.context = context;
        this.weatherModellist = weatherModellist;
    }
    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.weatheritems,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        WeatherModel model=weatherModellist.get(position);
        Picasso.get().load("https:".concat(model.getIcon())).into(holder.conditions);
        holder.temp.setText(model.getTemperature()+"°c");
        holder.wind.setText(model.getWindspeed()+"Km/hr");
        SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output=new SimpleDateFormat("hh:mm aa");
        try{
            Date t=input.parse(model.getTime());
            holder.time.setText(output.format(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wind,temp,time;
        private ImageView conditions;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind=itemView.findViewById(R.id.Windspeed);
            temp=itemView.findViewById(R.id.Temp);
            time=itemView.findViewById(R.id.Time);
            conditions=itemView.findViewById(R.id.Conditions);
        }
    }
}
