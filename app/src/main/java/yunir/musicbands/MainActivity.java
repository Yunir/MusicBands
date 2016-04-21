package yunir.musicbands;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String JSONString;
    JSONObject jsonObject;
    JSONArray jsonArray;

    ListView listview;
    ListViewAdapter adapter; //Объект нашего ListViewAdapter класса
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new DownloadJSON().execute();
    }

    //Выполняем асинхронное скачивание JSON-Данных
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            //Реализуем progressDialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Выгрузка данных из сети");
            mProgressDialog.setMessage("Подождите...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Инициализируем массив хранения данных
                arrayList = new ArrayList<HashMap<String, String>>();

                //Транспортируем данные из JSON-URL в JSONArray
                JSONString = JSONfunctions.getJSONString("http://cache-novosibrt06.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json");
                jsonArray = new JSONArray(JSONString);

                JSONObject iconLinks;

                //Перебираем данные каждого JSON объекта
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonObject = jsonArray.getJSONObject(i);
                    iconLinks = jsonObject.getJSONObject("cover");

                    map.put("smallCover", iconLinks.getString("small"));
                    map.put("bigCover", iconLinks.getString("big"));
                    map.put("id", jsonObject.getString("id"));
                    map.put("name", jsonObject.getString("name"));
                    map.put("genres", JSONfunctions.getJSONGenres(jsonObject.getJSONArray("genres")));
                    map.put("tracks", jsonObject.getString("tracks"));
                    map.put("albums", jsonObject.getString("albums"));
                    try {
                        map.put("link", jsonObject.getString("link"));
                    } catch (Exception e) {
                        Log.w("Warning", "У муз. группы " + jsonObject.getString("name") + " нет своего сайта");
                    }
                    map.put("description", jsonObject.getString("description"));

                    arrayList.add(map);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            listview = (ListView) findViewById(R.id.listview);
            adapter = new ListViewAdapter(MainActivity.this, arrayList);
            listview.setAdapter(adapter);

            //Закрываем progressDialog
            mProgressDialog.dismiss();
        }
    }
}