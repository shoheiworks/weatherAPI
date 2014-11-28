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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class main extends Activity {

    String urlApi = "http://weather.livedoor.com/forecast/webservice/json/v1?city=130010";
    private RequestQueue mQueue;
    ArrayAdapter<String> adapter;

    private TextView titleText;
    private TextView dateText;
    private TextView publicTimeText;

    private static String tagD = "LogD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView)findViewById(R.id.titleTextV);
        dateText = (TextView)findViewById(R.id.dateTextV);
        publicTimeText = (TextView)findViewById(R.id.publicTimeTextV);

//        ListView listForecast = (ListView) findViewById(R.id.listForecast);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        listForecast.setAdapter(adapter);

        mQueue = Volley.newRequestQueue(this);

        mQueue.add(new JsonObjectRequest(Request.Method.GET, urlApi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

//                        Log.d(tagD, mQueue);

//                        String weatherjson = response.

                        try {
                            String title = response.getString("title");
                            titleText.setText(title);

                            String publicTime = response.getString("publicTime");
                            publicTimeText.setText(publicTime);

//                            String forecastsJson = response.getString(response);
        //                    Log.d(tagD, forecastsJson);

                            JSONObject jsRoot = new JSONObject();
                            JSONArray jsDateList = jsRoot.getJSONArray("forecasts");

//                            Log.d(tagD, "jsRoot");

                            for (int i = 0; i < jsDateList.length(); i++) {
                                JSONObject jsDate = jsDateList.getJSONObject(i);

                                String date = jsDate.getString("date");
                                dateText.setText(date);
                            }
//                            date = response.getJSONObject("forecasts").getString("date");



                        } catch (JSONException e) {
//                            Log.e("temakishiki", e.getMessage());
                            e.printStackTrace();
                        }

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
