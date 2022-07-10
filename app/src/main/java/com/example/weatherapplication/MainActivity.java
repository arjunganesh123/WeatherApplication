package com.example.weatherapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout homeRL;
    private ProgressBar londingPB;
    private TextView cityname,temperature,condition;
    private RecyclerView weather;
    private TextInputEditText editcity;
    private ImageView backtheme,climateicon,searchicon;
    private ArrayList<WeatherModel> weatherModelArrayList;
    private WeatherAdapter weatherAdapter;
    private LocationManager locationManager;
    private int Permissioncode=1;
    private String Citynameee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        homeRL = findViewById(R.id.RLHome);
        londingPB = findViewById(R.id.PBLoading);
        cityname = findViewById(R.id.CityName);
        temperature = findViewById(R.id.Temperature);
        condition = findViewById(R.id.TextWeather);
        weather = findViewById(R.id.RVWeather);
        editcity = findViewById(R.id.EditCityName);
        backtheme = findViewById(R.id.backtheme);
        climateicon = findViewById(R.id.ClimateImage);
        searchicon = findViewById(R.id.Searchicon);
        weatherModelArrayList=new ArrayList<>();
        weatherAdapter=new WeatherAdapter(this,weatherModelArrayList);
        weather.setAdapter(weatherAdapter);

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},Permissioncode);
        }

        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Citynameee=getcityname(location.getLongitude(),location.getLatitude());
        getweatherinfo(Citynameee);

        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=editcity.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter city name",Toast.LENGTH_SHORT);
                }
                else{
                    cityname.setText(city);
                    getweatherinfo(city);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==Permissioncode){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Please Give Permission",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getcityname(double longitude, double latitude){
        String citynaame="not found";
        Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
        try{
            List<Address> addressList=gcd.getFromLocation(latitude,longitude,10);
            for(Address adr:addressList){
                if(adr!=null){
                    String city=adr.getLocality();
                    if(city!=null && !city.equals("")){
                        citynaame=city;
                    }
                    else{
                        Log.d("TAG","City not found");
                        Toast.makeText(this,"User City Not Found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return citynaame;
    }
    private void getweatherinfo(String citynamee){
        String url="https://api.weatherapi.com/v1/forecast.json?key=dca892fc2d534382a1461857211207&q="+citynamee+"&days=1&aqi=yes&alerts=yes";
        cityname.setText(citynamee);
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                  londingPB.setVisibility(View.GONE);
                  homeRL.setVisibility(View.VISIBLE);
                  weatherModelArrayList.clear();
                  try{
                      String temperaturee=response.getJSONObject("current").getString("temp_c");
                      temperature.setText(temperaturee+"°c");
                      int isday=response.getJSONObject("current").getInt("is_day");
                      String conditionn=response.getJSONObject("current").getJSONObject("condition").getString("text");
                      String conditionicon=response.getJSONObject("current").getJSONObject("condition").getString("icon");
                      Picasso.get().load("https:".concat(conditionicon)).into(climateicon);
                      condition.setText(conditionn);
                      if(isday==1){
                          backtheme.setImageResource(R.drawable.morningimg);
//                          Picasso.get().load("https://www.4freephotos.com/medium/batch/Morning-sky1536.jpg").into(backtheme);
                      }
                      else {
                          Picasso.get().load("https://i.pinimg.com/564x/21/b0/0e/21b00eba1a11332ce952964225d621a1.jpg").into(backtheme);
                      }
                      JSONObject forecastobject=response.getJSONObject("forecast");
                      JSONObject forecast0=forecastobject.getJSONArray("forecastday").getJSONObject(0);
                      JSONArray hourarray=forecast0.getJSONArray("hour");
                      for(int i=0;i<hourarray.length();i++){
                          JSONObject hourobj=hourarray.getJSONObject(i);
                          String time=hourobj.getString("time");
                          String temmp=hourobj.getString("temp_c");
                          String immg=hourobj.getJSONObject("condition").getString("icon");
                          String wwind=hourobj.getString("wind_kph");
                          weatherModelArrayList.add(new WeatherModel(time,temmp,immg,wwind));
                      }
                      weatherAdapter.notifyDataSetChanged();
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Please enter valid city name",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}