package yunir.musicbands;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> bandInfo = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //Атрибуты: "id"  "name"  "genres"  "tracks"  "albums"  "link"  "description"  "smallCover"  "bigCover"

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView iv_cover;
        TextView tv_name;
        TextView tv_genres;
        TextView tv_albums_tracks;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.lv_object, parent, false);
        bandInfo = data.get(position);

        //Инициализация виджетов
        iv_cover = (ImageView) itemView.findViewById(R.id.cover);
        tv_name = (TextView) itemView.findViewById(R.id.name);
        tv_genres = (TextView) itemView.findViewById(R.id.genres);
        tv_albums_tracks = (TextView) itemView.findViewById(R.id.albums_tracks);


        //Заполнение lv_object
        tv_name.setText(bandInfo.get("name"));
        tv_genres.setText(bandInfo.get("genres"));
        tv_albums_tracks.setText(bandInfo.get("albums") + " " + wordForms_albums(Integer.parseInt(bandInfo.get("albums"))) + ", " + bandInfo.get("tracks") + " " + wordForms_tracks(Integer.parseInt(bandInfo.get("tracks"))));
        //Вставляем URL-картинку в iv_cover
        Picasso.with(context).load(bandInfo.get("smallCover")).fit().placeholder(R.drawable.defaulticon).into(iv_cover);

        //Захват клика на элемент списка ListView
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Узнаем позицию элемента
                bandInfo = data.get(position);
                Intent intent = new Intent(context, AboutBand.class);
                //Переводим данные в AboutBand
                intent.putExtra("name", bandInfo.get("name"));
                intent.putExtra("cover", bandInfo.get("bigCover"));
                intent.putExtra("genres", bandInfo.get("genres"));
                intent.putExtra("albums_tracks", bandInfo.get("albums") + " " + wordForms_albums(Integer.parseInt(bandInfo.get("albums"))) + "  ·  " + bandInfo.get("tracks") + " " + wordForms_tracks(Integer.parseInt(bandInfo.get("tracks"))));
                intent.putExtra("biography", bandInfo.get("description"));
                intent.putExtra("link", bandInfo.get("link"));

                context.startActivity(intent);

            }
        });
        return itemView;
    }

    //Склонение слова "Песня"
    public String wordForms_tracks(int count) {
        if (count > 0 && count != 11 && count != 12 && count != 13 && count != 14) {
            if (count % 10 == 1) return "песня";
            if ((count % 10 == 2) || (count % 10 == 3) || (count % 10 == 4)) return "песни";
        }
        return "песен";
    }

    //Склонение слова "Альбом"
    public String wordForms_albums(int count) {
        if (count > 0 && count != 11 && count != 12 && count != 13 && count != 14) {
            if (count % 10 == 1) return "альбом";
            if ((count % 10 == 2) || (count % 10 == 3) || (count % 10 == 4)) return "альбома";
        }
        return "альбомов";
    }
}