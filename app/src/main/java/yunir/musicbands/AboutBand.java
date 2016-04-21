package yunir.musicbands;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class AboutBand extends AppCompatActivity {
    String name, cover, genres, albums_tracks, biography, link;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_band);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        //Изменяем title каждого из музыкантов
        getSupportActionBar().setTitle(i.getStringExtra("name"));

        //Собираем данные
        name = i.getStringExtra("name");
        cover = i.getStringExtra("cover");
        genres = i.getStringExtra("genres");
        albums_tracks = i.getStringExtra("albums_tracks");
        biography = i.getStringExtra("biography");
        link = i.getStringExtra("link");

        //Инициализация виджетов
        ImageView iv_cover = (ImageView) findViewById(R.id.cover);
        TextView tv_genres = (TextView) findViewById(R.id.genres);
        TextView tv_albums_tracks = (TextView) findViewById(R.id.albums_tracks);
        TextView tv_biography = (TextView) findViewById(R.id.biography);

        //Заполняем нашу XML'ку
        tv_genres.setText(genres);
        tv_albums_tracks.setText(albums_tracks);
        tv_biography.setText(biography);
        //Выгружаем картинку в iv_cover
        Picasso.with(AboutBand.this)
                .load(cover)
                .placeholder(R.drawable.defaultbigicon)
                .into(iv_cover);
    }

    //Реализация Up button'а
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }