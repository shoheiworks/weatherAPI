package com.designseisaku.weatherapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class main extends Activity {

    String urlApi = "http://weather.livedoor.com/forecast/webservice/json/v1?city=130010";
    private RequestQueue mQueue;
    ArrayAdapter<String> adapter;

    private TextView titleText;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView)findViewById(R.id.titleTextV);
//        dateText = (TextView)findViewById(R.id.dateTextV);

//        ListView listForecast = (ListView) findViewById(R.id.listForecast);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        listForecast.setAdapter(adapter);

        mQueue = Volley.newRequestQueue(this);
        mQueue.add(new JsonObjectRequest(Request.Method.GET, urlApi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // JSONObjectのパース、List、Viewへの追加等

//                        Log.d("temakishiki", "response : " + response.toString());

                        String title = null;
//                        String date = null;

                        try {
                            title = response.getString("title");
/*
                            JSONArray forecasts = JSONObject.getJSONArray("forecasts");
                            for (int i=0; i<forecasts.length(); i++) {
                                JSONObject forecast = forecasts.getJSONObject(i);
                                date = forecast.getStrings("date");
                            }
*/
//                            date = response.getJSONObject("forecasts").getString("date");


                        } catch (JSONException e) {
//                            Log.e("temakishiki", e.getMessage());
                            e.printStackTrace();
                        }

                        titleText.setText(title);
//                        dateText.setText(date);

//                        Toast.makeText(main.this, date, Toast.LENGTH_SHORT).show();


                    }
                },

                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        // エラー処理 error.networkResponseで確認
                        // エラー表示など
                    }
                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
