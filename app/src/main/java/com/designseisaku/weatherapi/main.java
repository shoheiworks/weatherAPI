package com.designseisaku.weatherapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    String urlApi = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";
    private RequestQueue mQueue;
    ArrayAdapter<String> adapter;

    private TextView titleText;
    private TextView dateText;
    private TextView telopText;

    private static String tagD = "LogD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView) findViewById(R.id.titleTextV);
        dateText = (TextView) findViewById(R.id.dateTextV);
        telopText = (TextView) findViewById(R.id.telopTextV);

        //アプリ起動時の表示
        titleText.setText("地域");
        dateText.setText("日付");
        telopText.setText("天気");
    }

/* Option : 数日分をリストにして表示する
        ListView listForecast = (ListView) findViewById(R.id.listForecast);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listForecast.setAdapter(adapter);
*/

    //クリックしたらURLを変更する
    public void setArea (View v) {
        switch (v.getId()) {
            case R.id.button1:
                urlApi = urlApi + "400010";
                main();
                break;
            case R.id.button2:
                urlApi = urlApi + "410010";
                main();
                break;
            case R.id.button3:
                urlApi = urlApi + "420010";
                main();
                break;
            case R.id.button4:
                urlApi = urlApi + "430010";
                main();
                break;
            case R.id.button5:
                urlApi = urlApi + "440010";
                main();
                break;
            case R.id.button6:
                urlApi = urlApi + "450010";
                main();
                break;
            case R.id.button7:
                urlApi = urlApi + "460010";
                main();
                break;
            case R.id.button8:
                urlApi = urlApi + "130010";
                main();
                break;
        }
    }

    public void main() {

        mQueue = Volley.newRequestQueue(this);
        mQueue.add(new JsonObjectRequest(Request.Method.GET, urlApi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String title = response.getString("title");
                            titleText.setText(title);

//                            String publicTime = response.getString("publicTime");
//                            publicTimeText.setText(publicTime);
//                            String forecastsJson = response.getString(response);

                            JSONArray forecastsJson = response.getJSONArray("forecasts");
//                          Log.d(tagD, forecastsJson);

                            for (int i = 0; i < 1; i++) { //forecastsJson.length(); i++) {

                                JSONObject forecasts = forecastsJson.getJSONObject(i);

                                String dateJ = forecasts.getString("date");
                                dateText.setText(dateJ);

                                String telopJ = forecasts.getString("telop");
                                telopText.setText(telopJ);

                            }
//                            date = response.getJSONObject("forecasts").getString("date");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // エラー処理 error.networkResponseで確認
                        // エラー表示など
                    }
                }
        ));
        urlApi = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";
//        queue.add(jsonRequest);
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
