package yunir.musicbands;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONfunctions {

    public static String getJSONString(String url) {
        InputStream is = null;
        String result = "";
        JSONArray jArray = null;

        // Скачка JSON данных из URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("Error", "Ошибка http соединения " + e.toString());
        }

        //Преобразование ответа в строку
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("Error", "Ошибка преобразования ответа " + e.toString());
        }

        return result;
    }

    //Преобразование массива жанров в строку
    public static String getJSONGenres(JSONArray array) {
        String result = "";
        try {
            if (array.length() != 0)
                result = array.getString(0);
            for (int i = 1; i < array.length(); i++) {
                result += ", " + array.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}