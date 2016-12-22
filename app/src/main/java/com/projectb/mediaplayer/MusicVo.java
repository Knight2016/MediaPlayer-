package com.projectb.mediaplayer;

import java.io.Serializable;

/**
 * Created by macbook on 2016/12/21.
 */

public class MusicVo implements Serializable {

    private String name;

    private String artist;

    private String album;

    private String path;

    private long size;

    private long time;

    private int songId;

    private int albumId;

    public MusicVo(String name, String artist, String album, String path, long size, long time, int songId, int albumId) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.path = path;
        this.size = size;
        this.time = time;
        this.songId = songId;
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
