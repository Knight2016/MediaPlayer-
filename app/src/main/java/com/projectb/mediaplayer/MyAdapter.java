package com.projectb.mediaplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by macbook on 2016/12/21.
 */

public class MyAdapter extends BaseAdapter {

    private ArrayList<MusicVo> musicVos;

    public MyAdapter(ArrayList<MusicVo> musicVos) {
        this.musicVos = musicVos;
    }

    @Override
    public int getCount() {
        return musicVos.size();
    }

    @Override
    public Object getItem(int position) {
        return musicVos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.itemName.setText(musicVos.get(position).getName());
        viewHolder.itemAlbum.setText(musicVos.get(position).getAlbum());
        viewHolder.itemArtist.setText(musicVos.get(position).getArtist());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.item_name)
        TextView itemName;
        @Bind(R.id.item_artist)
        TextView itemArtist;
        @Bind(R.id.item_album)
        TextView itemAlbum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
