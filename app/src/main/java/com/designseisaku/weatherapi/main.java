package com.designseisaku.weatherapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    private ListView listV;

    private static String tagD = "LogD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView) findViewById(R.id.titleTextV);
        dateText = (TextView) findViewById(R.id.dateTextV);
        telopText = (TextView) findViewById(R.id.telopTextV);
        urlText = (TextView) findViewById(R.id.urlTextV);
        listV = (ListView) findViewById(R.id.listView);


        //アプリ起動時の表示
        titleText.setText("地域");
        dateText.setText("日付");
        telopText.setText("天気");
        urlText.setText("URL");

        // Option : 数日分をリストにして表示する
//        String[] ListDate = {"Item1", "Item2"};
        //adapterの作成
/*
        List<String> ListDate = new ArrayList<String>();
        for (int t = 0; t < 7; t++) {
            //    ListDate.add("" + t);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, ListDate);
        //データがなかった場合
        listV.setEmptyView(findViewById(R.id.empty));
        listV.setAdapter(adapter);
*/
        ArrayList<User> users = new ArrayList<User>();
//        List<Map<String, String >> ListDate = new ArrayList<Map<String, String>>();


//        Map<String, String> m = new HashMap<String, String>();
        int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
        String[] names = {"shohei", "soshi", "ryugo"};
        String[] locations = {"meinohama", "hakata", "nisiku"};
/*
        for (int i = 0; i < names.length; i++) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("name", names[i]);
            m.put("location", locations[i]);
            ListDate.add(m);
        }
*/
        for (int i = 0; i < images.length; i++) {
            User user = new User();
            user.setImage(BitmapFactory.decodeResource(getResources(), images[i]));
            user.setName(names[i]);
            user.setlocation(locations[i]);
            users.add(user);
        }

        //Adapterの準備
//        String[] from = {"name", "location"};
//        int[] to = {android.R.id.text1, android.R.id.text2};
//        SimpleAdapter adapter = new SimpleAdapter(this, ListDate, android.R.layout.simple_list_item_2, from, to);

        UserAdapter userAdapter = new UserAdapter(this, 0, users);

        //ListViewにadapterを設置
//        listV.setEmptyView(findViewById(R.id.empty));
//        listV.setAdapter(adapter);
        listV.setAdapter(userAdapter);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
//                String s = tv1.getText().toString();
//                Toast.makeText(main.this, s, Toast.LENGTH_SHORT).show();
                TextView name = (TextView) view.findViewById(R.id.name);
                String s = name.getText().toString();
                Toast.makeText(main.this, s, Toast.LENGTH_SHORT).show();

            }
        });

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
//                          Log.d(tagD, forecastsJson.toString());

                            for (int i = 0; i < forecastsJson.length(); i++) {
//                                ListDate.add(i);

                                JSONObject forecasts = forecastsJson.getJSONObject(i);

                                String dateJ = forecasts.getString("date");
                                dateText.setText(dateJ);

                                String telopJ = forecasts.getString("telop");
                                telopText.setText(telopJ);

                                String imageJ = forecasts.getJSONObject("image").getString("url");
                                urlText.setText(imageJ);

                            }

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

    //高速化の為
    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView location;

        public ViewHolder(View view) {
            this.image = (ImageView) view.findViewById(R.id.imageV);
            this.name = (TextView) view.findViewById(R.id.name);
            this.location = (TextView) view.findViewById(R.id.location);
        }


    }

    public class UserAdapter extends ArrayAdapter<User> {
        private LayoutInflater layoutInflater;

        public UserAdapter(Context context, int viewResourceId, ArrayList<User> users) {
            super(context, viewResourceId, users);
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            //再利用できるViewがなかっらLayoutInflaterを使ってrow.xmlにViewする
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_row, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //Viewにデータをセットする
            User user = (User) getItem(position);

            viewHolder.image.setImageBitmap(user.getImage());
            viewHolder.name.setText(user.getName());
            viewHolder.location.setText(user.getlocation());
/*
            ImageView userImage = (ImageView) convertView.findViewById(R.id.imageV);
            userImage.setImageBitmap(user.getImage());

            TextView userName = (TextView) convertView.findViewById(R.id.name);
            userName.setText(user.getName());

            TextView userLocation = (TextView) convertView.findViewById(R.id.location);
            userLocation.setText(user.getlocation());
*/
            //Viewを返す
            return convertView;
        }
    }

    private class User {
        private Bitmap image;
        private String name;
        private String location;

        public Bitmap getImage() {
            return this.image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getlocation() {
            return this.location;
        }

        public void setlocation(String location) {
            this.location = location;
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
