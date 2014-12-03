package com.designseisaku.weatherapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class main extends Activity {

    String urlApi = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";
    private RequestQueue mQueue;
//    ArrayAdapter<String> adapter;

    private TextView titleText;
    private TextView dateText;
    private TextView telopText;
    private TextView urlText;

    private ImageLoader mImageLoader;

    private static String tagD = "LogD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView) findViewById(R.id.titleTextV);
        dateText = (TextView) findViewById(R.id.dateTextV);
        telopText = (TextView) findViewById(R.id.telopTextV);
        urlText = (TextView) findViewById(R.id.urlTextV);

        //アプリ起動時の表示f
        titleText.setText("地域");
        dateText.setText("日付");
        telopText.setText("天気");
        urlText.setText(null);

    }

    //クリックしたらURLを変更する

    public void setArea(View v) {
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

                            JSONArray forecastsJson = response.getJSONArray("forecasts");
                            Log.d(tagD, forecastsJson.toString());

//                            ArrayList<String> weather = new ArrayList<String>();
                            ArrayList<Weather> weatherList = new ArrayList<Weather>();

                            for (int i = 0; i < forecastsJson.length(); i++) {
                                JSONObject forecasts = forecastsJson.getJSONObject(i);

                                Weather weather = new Weather();


                                String dateJ = forecasts.getString("date");
                                dateText.setText(dateJ);

                                String telopJ = forecasts.getString("telop");
                                telopText.setText(telopJ);

                                String imageJ = forecasts.getJSONObject("image").getString("url");
                                urlText.setText(imageJ);

                                weather.setDate(dateJ);
                                weather.setTelop(telopJ);
                                weatherList.add(weather);
                                //weathers.add(dateJ + telopJ + imageJ);
                            }
//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(main.this, android.R.layout.simple_list_item_1, weather);
                                WeatherAdapter weatherAdapter = new WeatherAdapter(main.this, 0, weatherList);
                                final ListView listV = (ListView) findViewById(R.id.listView);
                                listV.setEmptyView(findViewById(R.id.empty));

//                                listV.setAdapter(adapter);
                                listV.setAdapter(weatherAdapter);


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
    }


/*
    //高速化の為
    private static class ViewHolder {
        ImageView image;
        TextView date;
        TextView location;

        public ViewHolder(View view) {
            this.image = (ImageView) view.findViewById(R.id.imageV);
            this.date = (TextView) view.findViewById(R.id.dateTextV);
            this.location = (TextView) view.findViewById(R.id.location);
        }
    }
*/

    public class WeatherAdapter extends ArrayAdapter<Weather> {
        private LayoutInflater layoutInflater;
//
        public WeatherAdapter(Context context, int viewResourceId, ArrayList<Weather> weatherList) {
            super(context, viewResourceId, weatherList);
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;

            //再利用できるViewがなかったらLayoutInflaterを使ってlist_row.xmlにViewする
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_row, null);
//                viewHolder = new ViewHolder(convertView);
//                convertView.setTag(viewHolder);
            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
            }

            //Viewにデータをセットする
            Weather weather = (Weather) getItem(position);

            TextView wetherDate = (TextView) convertView.findViewById(R.id.dateTextV);
            wetherDate.setText(weather.getDate());

            TextView wetherTelop = (TextView) convertView.findViewById(R.id.telopTextV);
            wetherTelop.setText(weather.getTelop());

//            viewHolder.image.setImageBitmap(user.getImage());
//            viewHolder.date.setText(weather.getDate());
//            viewHolder.location.setText(user.getlocation());

            //Viewを返す
            return convertView;
        }
    }

    private class Weather {
//        private Bitmap url; //image
        private String date;
        private String telop;

/*
                public Bitmap getImage() {
                    return this.urlJ;
                }

                public void setImage(Bitmap image) {
                    this.urlJ = image;
                }
*/

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTelop() {
            return this.telop;
        }

        public void setTelop(String telop) {
            this.telop = telop;
        }
    }

/* Image
--------------------*/
/* NewworkImageView*/
//    String url = "url"; //とりあえず

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
