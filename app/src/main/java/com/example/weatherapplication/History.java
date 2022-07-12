package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class History extends AppCompatActivity {
    private TextView h1cityname,h1date,h1maxtemp,h1mintemp,h1avgtemp,h1maxwind,h1minwind,h1condition;
    private ImageView h1img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        h1cityname=findViewById(R.id.history1cityname);
        h1date=findViewById(R.id.history1date);
        h1maxtemp=findViewById(R.id.history1maxtemp);
        h1mintemp=findViewById(R.id.history1mintemp);
        h1avgtemp=findViewById(R.id.history1avgtemp);
        h1maxwind=findViewById(R.id.history1maxwind);
        h1minwind=findViewById(R.id.history1minwind);
        h1condition=findViewById(R.id.history1condition);
        h1img=findViewById(R.id.history1img);
        gethistoryinfo("London","22");
    }
    private void gethistoryinfo(String citynamme,String daate){
        h1cityname.setText(citynamme);
        h1date.setText(daate);
        String url="https://api.weatherapi.com/v1/history.json?key=dca892fc2d534382a1461857211207&q="+citynamme+"&dt=2022-07-11";
        RequestQueue requesttQueue= Volley.newRequestQueue(History.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("hiiiiiii");
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
                    h1maxtemp.setText("Maximum Temp: "+t+"°c");
                    h1mintemp.setText("Minimum Temp: "+mt+"°c");
                    h1avgtemp.setText("Average Temp: "+at+"°c");
                    h1condition.setText("Condition: "+c);
                    h1maxwind.setText("Maximum Wind:"+w+"Kmp");
                    h1minwind.setText("Avg Humidity: "+h);
                    Picasso.get().load("https:".concat(conditionicon)).into(h1img);
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