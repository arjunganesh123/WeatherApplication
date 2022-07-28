package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    ProgressBar p;
    RelativeLayout r;
    TextView datecityname,datedate,datemaxtemp,datemintemp,dateavgtemp,datecondition,datemaxwind,dateminwind;
    ImageView dateimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        p=findViewById(R.id.PBLoadingdate);
        r=findViewById(R.id.relative11);
        datecityname=findViewById(R.id.Date1cityname);
        datedate=findViewById(R.id.Date1date);
        datemaxtemp=findViewById(R.id.date1maxtemp);
        datemintemp=findViewById(R.id.date1mintemp);
        dateavgtemp=findViewById(R.id.date1avgtemp);
        datecondition=findViewById(R.id.date1condition);
        datemaxwind=findViewById(R.id.date1maxwind);
        dateminwind=findViewById(R.id.date1minwind);
        dateimg=findViewById(R.id.Date1img);
        Bundle b=getIntent().getExtras();
        String datee=b.getString("getdate");
        String cname=b.getString("cname");
        gethistoryinfo(cname,datee);

    }
    private void gethistoryinfo(String citynamme,String daate){
        String url="https://api.weatherapi.com/v1/history.json?key=dca892fc2d534382a1461857211207&q="+citynamme+"&dt="+daate;
        RequestQueue requesttQueue= Volley.newRequestQueue(MainActivity2.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                p.setVisibility(View.GONE);
                r.setVisibility(View.VISIBLE);
                try {
                    JSONObject forecastobject = response.getJSONObject("forecast");
                    JSONObject forecast0=forecastobject.getJSONArray("forecastday").getJSONObject(0);
                    String t=forecast0.getJSONObject("day").getString("maxtemp_c");
                    String mt=forecast0.getJSONObject("day").getString("mintemp_c");
                    String at=forecast0.getJSONObject("day").getString("avgtemp_c");
                    String c=forecast0.getJSONObject("day").getJSONObject("condition").getString("text");
                    String w=forecast0.getJSONObject("day").getString("maxwind_kph");
                    String h=forecast0.getJSONObject("day").getString("avghumidity");
                    String conditionicon=forecast0.getJSONObject("day").getJSONObject("condition").getString("icon");
                        datecityname.setText(citynamme);
                        datedate.setText(daate);
                        datemaxtemp.setText("Maximum Temp: "+t+"°c");
                        datemintemp.setText("Minimum Temp: "+mt+"°c");
                        dateavgtemp.setText("Average Temp: "+at+"°c");
                        datecondition.setText("Condition: "+c);
                        datemaxwind.setText("Maximum Wind:"+w+"Kmp");
                        dateminwind.setText("Avg Humidity: "+h);
                        Picasso.get().load("https:".concat(conditionicon)).into(dateimg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,"Please enter valid city name",Toast.LENGTH_SHORT).show();
            }
        });
        requesttQueue.add(jsonObjectRequest);
    }
}