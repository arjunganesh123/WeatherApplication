package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout homeRL;
    private ProgressBar londingPB;
    private TextView cityname,temperature,condition;
    private RecyclerView weather;
    private TextInputEditText editcity;
    private ImageView backtheme,climateicon,searchicon;
    private ArrayList<WeatherModel> weatherModelArrayList;
    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeRL = findViewById(R.id.RLHome);
        londingPB = findViewById(R.id.PBLoading);
        cityname = findViewById(R.id.CityName);
        temperature = findViewById(R.id.Temperature);
        condition = findViewById(R.id.TextWeather);
        weather = findViewById(R.id.RVWeather);
        editcity = findViewById(R.id.SCityName);
        backtheme = findViewById(R.id.backtheme);
        climateicon = findViewById(R.id.ClimateImage);
        searchicon = findViewById(R.id.Searchicon);
        weatherModelArrayList=new ArrayList<>();
        weatherAdapter=new WeatherAdapter(this,weatherModelArrayList);
        weather.setAdapter(weatherAdapter);
    }
}