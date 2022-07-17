package com.example.weatherapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

public class History extends AppCompatActivity {
    private TextView h1cityname,h1date,h1maxtemp,h1mintemp,h1avgtemp,h1maxwind,h1minwind,h1condition;
    private ImageView h1img;
    private TextView h2cityname,h2date,h2maxtemp,h2mintemp,h2avgtemp,h2maxwind,h2minwind,h2condition;
    private ImageView h2img;
    private TextView h3cityname,h3date,h3maxtemp,h3mintemp,h3avgtemp,h3maxwind,h3minwind,h3condition;
    private ImageView h3img;
    private TextView h4cityname,h4date,h4maxtemp,h4mintemp,h4avgtemp,h4maxwind,h4minwind,h4condition;
    private ImageView h4img;
    private TextView h5cityname,h5date,h5maxtemp,h5mintemp,h5avgtemp,h5maxwind,h5minwind,h5condition;
    private ImageView h5img;
    private ProgressBar londingPBB;
    private RelativeLayout r1,r2,r3,r4,r5;
    String cname;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Weather History");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        r1=findViewById(R.id.relative1);
        r2=findViewById(R.id.relative2);
        r3=findViewById(R.id.relative3);
        r4=findViewById(R.id.relative4);
        r5=findViewById(R.id.relative5);
        londingPBB=findViewById(R.id.PBLoadinghistory);
        h1cityname=findViewById(R.id.history1cityname);
        h1date=findViewById(R.id.history1date);
        h1maxtemp=findViewById(R.id.history1maxtemp);
        h1mintemp=findViewById(R.id.history1mintemp);
        h1avgtemp=findViewById(R.id.history1avgtemp);
        h1maxwind=findViewById(R.id.history1maxwind);
        h1minwind=findViewById(R.id.history1minwind);
        h1condition=findViewById(R.id.history1condition);
        h1img=findViewById(R.id.history1img);
        h2cityname=findViewById(R.id.history2cityname);
        h2date=findViewById(R.id.history2date);
        h2maxtemp=findViewById(R.id.history2maxtemp);
        h2mintemp=findViewById(R.id.history2mintemp);
        h2avgtemp=findViewById(R.id.history2avgtemp);
        h2maxwind=findViewById(R.id.history2maxwind);
        h2minwind=findViewById(R.id.history2minwind);
        h2condition=findViewById(R.id.history2condition);
        h2img=findViewById(R.id.history2img);
        h3cityname=findViewById(R.id.history3cityname);
        h3date=findViewById(R.id.history3date);
        h3maxtemp=findViewById(R.id.history3maxtemp);
        h3mintemp=findViewById(R.id.history3mintemp);
        h3avgtemp=findViewById(R.id.history3avgtemp);
        h3maxwind=findViewById(R.id.history3maxwind);
        h3minwind=findViewById(R.id.history3minwind);
        h3condition=findViewById(R.id.history3condition);
        h3img=findViewById(R.id.history3img);
        h4cityname=findViewById(R.id.history4cityname);
        h4date=findViewById(R.id.history4date);
        h4maxtemp=findViewById(R.id.history4maxtemp);
        h4mintemp=findViewById(R.id.history4mintemp);
        h4avgtemp=findViewById(R.id.history4avgtemp);
        h4maxwind=findViewById(R.id.history4maxwind);
        h4minwind=findViewById(R.id.history4minwind);
        h4condition=findViewById(R.id.history4condition);
        h4img=findViewById(R.id.history4img);
        h5cityname=findViewById(R.id.history5cityname);
        h5date=findViewById(R.id.history5date);
        h5maxtemp=findViewById(R.id.history5maxtemp);
        h5mintemp=findViewById(R.id.history5mintemp);
        h5avgtemp=findViewById(R.id.history5avgtemp);
        h5maxwind=findViewById(R.id.history5maxwind);
        h5minwind=findViewById(R.id.history5minwind);
        h5condition=findViewById(R.id.history5condition);
        h5img=findViewById(R.id.history5img);
        Bundle b=new Bundle();
        b=getIntent().getExtras();
        cname=b.getString("cname");
        gethistoryinfo(cname,java.time.LocalDate.now().minusDays(1).toString(),1);
        gethistoryinfo(cname,java.time.LocalDate.now().minusDays(2).toString(),2);
        gethistoryinfo(cname,java.time.LocalDate.now().minusDays(3).toString(),3);
        gethistoryinfo(cname,java.time.LocalDate.now().minusDays(4).toString(),4);
        gethistoryinfo(cname,java.time.LocalDate.now().minusDays(5).toString(),5);
    }
    private void gethistoryinfo(String citynamme,String daate,int x){
        String url="https://api.weatherapi.com/v1/history.json?key=dca892fc2d534382a1461857211207&q="+citynamme+"&dt="+daate;
        RequestQueue requesttQueue= Volley.newRequestQueue(History.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                londingPBB.setVisibility(View.GONE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
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
                    if(x==1){
                        h1cityname.setText(citynamme);
                        h1date.setText(daate);
                        h1maxtemp.setText("Maximum Temp: "+t+"°c");
                        h1mintemp.setText("Minimum Temp: "+mt+"°c");
                        h1avgtemp.setText("Average Temp: "+at+"°c");
                        h1condition.setText("Condition: "+c);
                        h1maxwind.setText("Maximum Wind:"+w+"Kmp");
                        h1minwind.setText("Avg Humidity: "+h);
                        Picasso.get().load("https:".concat(conditionicon)).into(h1img);
                    }
                    if(x==2){
                        h2cityname.setText(citynamme);
                        h2date.setText(daate);
                        h2maxtemp.setText("Maximum Temp: "+t+"°c");
                        h2mintemp.setText("Minimum Temp: "+mt+"°c");
                        h2avgtemp.setText("Average Temp: "+at+"°c");
                        h2condition.setText("Condition: "+c);
                        h2maxwind.setText("Maximum Wind:"+w+"Kmp");
                        h2minwind.setText("Avg Humidity: "+h);
                        Picasso.get().load("https:".concat(conditionicon)).into(h2img);
                    }
                    if(x==3){
                        h3cityname.setText(citynamme);
                        h3date.setText(daate);
                        h3maxtemp.setText("Maximum Temp: "+t+"°c");
                        h3mintemp.setText("Minimum Temp: "+mt+"°c");
                        h3avgtemp.setText("Average Temp: "+at+"°c");
                        h3condition.setText("Condition: "+c);
                        h3maxwind.setText("Maximum Wind:"+w+"Kmp");
                        h3minwind.setText("Avg Humidity: "+h);
                        Picasso.get().load("https:".concat(conditionicon)).into(h3img);
                    }
                    if(x==4){
                        h4cityname.setText(citynamme);
                        h4date.setText(daate);
                        h4maxtemp.setText("Maximum Temp: "+t+"°c");
                        h4mintemp.setText("Minimum Temp: "+mt+"°c");
                        h4avgtemp.setText("Average Temp: "+at+"°c");
                        h4condition.setText("Condition: "+c);
                        h4maxwind.setText("Maximum Wind:"+w+"Kmp");
                        h4minwind.setText("Avg Humidity: "+h);
                        Picasso.get().load("https:".concat(conditionicon)).into(h4img);
                    }
                    if(x==5){
                        h5cityname.setText(citynamme);
                        h5date.setText(daate);
                        h5maxtemp.setText("Maximum Temp: "+t+"°c");
                        h5mintemp.setText("Minimum Temp: "+mt+"°c");
                        h5avgtemp.setText("Average Temp: "+at+"°c");
                        h5condition.setText("Condition: "+c);
                        h5maxwind.setText("Maximum Wind:"+w+"Kmp");
                        h5minwind.setText("Avg Humidity: "+h);
                        Picasso.get().load("https:".concat(conditionicon)).into(h5img);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this,"Please enter valid city name",Toast.LENGTH_SHORT).show();
            }
        });
        requesttQueue.add(jsonObjectRequest);
    }
}