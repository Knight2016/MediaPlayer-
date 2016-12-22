package com.projectb.mediaplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.lv)
    ListView lv;
    private ArrayList<MusicVo> musicVos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getAudio();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, PalyMusicActivity.class);
                intent.putExtra("VOS", musicVos);
                intent.putExtra("POSITION", position);
                startActivity(intent);

            }
        });
    }

    /**
     * 获取手机所有音频资源
     */
    public void getAudio() {

        musicVos = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//资源位置
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {

            //歌曲名
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            //歌手
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            //专辑
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            //路径
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            //大小
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            //时间
            long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            //歌曲Id
            int songId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            //专辑Id
            int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));


            Log.e("TAL", "name:" + name);
            Log.e("TAL", "artist:" + artist);
            Log.e("TAL", "album:" + album);
            Log.e("TAL", "path:" + path);

            musicVos.add(new MusicVo(name, artist, album, path, size, time, songId, albumId));
        }

        lv.setAdapter(new MyAdapter(musicVos));
    }
}
