package com.projectb.mediaplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 2016/12/21.
 */

public class PalyMusicActivity extends Activity implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {

    @Bind(R.id.music_name)
    TextView musicName;
    @Bind(R.id.music_pro)
    SeekBar musicPro;
    @Bind(R.id.tiem_l)
    TextView tiemL;
    @Bind(R.id.tiem_r)
    TextView tiemR;
    @Bind(R.id.iv_paly)
    ImageView ivPaly;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.pic)
    CircleImageView pic;

    private List<MusicVo> musicVos;
    private MusicVo musicVo;
    private int currPosition;
    private MediaPlayer mp;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        musicVos = (List<MusicVo>) getIntent().getSerializableExtra("VOS");
        currPosition = getIntent().getIntExtra("POSITION", 0);
        musicVo = musicVos.get(currPosition);
        animation = AnimationUtils.loadAnimation(this, R.anim.roate);
        animation.setInterpolator(new LinearInterpolator());

        musicPro.setOnSeekBarChangeListener(this);

        // # 1 初始化MediaPlayer
        mp = new MediaPlayer();
        playMusic();

    }

    @Override
    public void finish() {
        super.finish();
        mp.stop();
        mp.release();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    try {
                        if (mp.getCurrentPosition() <= mp.getDuration()) {

                            musicPro.setMax(mp.getDuration());
                            musicPro.setProgress(mp.getCurrentPosition());
                            tiemL.setText(getTime(mp.getCurrentPosition()));
                            tiemR.setText(getTime(mp.getDuration()));

                            handler.sendEmptyMessageDelayed(1, 1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        //手指松开屏幕
        mp.seekTo(seekBar.getProgress());

    }

    /**
     * 时间转化方法
     */
    public String getTime(long time) {


        long s = time / 1000;
        long m = s / 60;
        s = s % 60;

        String sStr, mStr;

        if (s < 10) {
            sStr = "0" + s;
        } else {
            sStr = "" + s;
        }

        if (m < 10) {
            mStr = "0" + m;
        } else {
            mStr = "" + m;
        }

        return mStr + ":" + sStr;
    }

    @OnClick(R.id.iv_paly)
    public void onClick() {

        if (mp.isPlaying()) {
            mp.pause();
            ivPaly.setImageResource(R.drawable.ic_play);
            pic.clearAnimation();
        } else {
            mp.start();
            ivPaly.setImageResource(R.drawable.ic_pause);
            pic.startAnimation(animation);
        }
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                //上一曲
                if (currPosition == 0) {
                    currPosition = musicVos.size() - 1;
                } else {
                    currPosition--;
                }
                break;
            case R.id.iv_right:
                //下一曲
                if (currPosition == musicVos.size() - 1) {
                    currPosition = 0;
                } else {
                    currPosition++;
                }
                break;
        }

        //共同部分
        musicVo = musicVos.get(currPosition);
        mp.stop();
        playMusic();

    }

    /**
     * 播放歌曲
     */
    public void playMusic() {

        //设置名字
        musicName.setText(musicVo.getName());
        //设置歌曲封面
        pic.setImageBitmap(MusicGetPic.getArtwork(this, musicVo.getSongId(), musicVo.getAlbumId(), true));

        // # 2 设置资源
        mp.reset();
        try {
            mp.setDataSource(musicVo.getPath());
            //mp.setDataSource("http://153.37.234.17/mp32.9ku.com/upload/128/2016/11/08/855515.mp3");

            // # 3 准备资源
            //mp.prepare();
            mp.prepareAsync();
        } catch (IOException e) {
            Log.e("TAL", "准备资源错误：" + e.getMessage());
            e.printStackTrace();
        }

        // #  设置监听（非必需）
        mp.setOnCompletionListener(this);//完成监听
        mp.setOnErrorListener(this);//错误监听
        mp.setOnPreparedListener(this);//准备好的监听
        mp.setOnBufferingUpdateListener(this);//缓冲监听

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        if (currPosition == musicVos.size() - 1) {
            currPosition = 0;
        } else {
            currPosition++;
        }
        musicVo = musicVos.get(currPosition);
        mp.stop();
        playMusic();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Log.e("TAL", "播放错误：" + what);

        if (currPosition == musicVos.size() - 1) {
            currPosition = 0;
        } else {
            currPosition++;
        }
        musicVo = musicVos.get(currPosition);
        mp.stop();
        playMusic();

        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        // # 4 播放
        mp.start();
        pic.startAnimation(animation);

        // 更新进度
        handler.sendEmptyMessage(1);

        mp.getDuration();//总时长
        mp.getCurrentPosition();//当前位置
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }
}
