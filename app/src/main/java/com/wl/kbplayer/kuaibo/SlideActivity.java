package com.wl.kbplayer.kuaibo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class SlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);


    }

    @Override
    protected void onResume() {
        super.onResume();
        SlideShowView ssv = (SlideShowView) findViewById(R.id.slideshowView);
        ArrayList al = new ArrayList<String>();
        al.add("http://ssl.panpeili.com/kuaibo/home/jingxuan/home_jingxuan_banner_3.jpg");
        al.add("http://image.zcool.com.cn/59/54/m_1303967870670.jpg");
        al.add("http://ssl.panpeili.com/kuaibo/home/jingxuan/home_jingxuan_banner_3.jpg");
        al.add("http://image.zcool.com.cn/59/54/m_1303967870670.jpg");
        ssv.imageUrls = al;

    }
}
