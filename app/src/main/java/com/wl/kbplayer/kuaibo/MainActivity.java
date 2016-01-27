package com.wl.kbplayer.kuaibo;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wl.kbplayer.kuaibo.Fregments.JingxuanFragment;
import com.wl.kbplayer.kuaibo.Fregments.MeFragment;
import com.wl.kbplayer.kuaibo.Fregments.NearFragment;
import com.wl.kbplayer.kuaibo.Fregments.NvyouFragment;
import com.wl.kbplayer.kuaibo.Fregments.PaihangFragment;
import com.wl.kbplayer.kuaibo.Tabbar.OnTabClickedListener;
import com.wl.kbplayer.kuaibo.Tabbar.TabBar;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements
        JingxuanFragment.OnFragmentInteractionListener,
        PaihangFragment.OnFragmentInteractionListener,
        NvyouFragment.OnFragmentInteractionListener,
        NearFragment.OnFragmentInteractionListener,
        MeFragment.OnFragmentInteractionListener {

    private ArrayList<Fragment> fragments;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        ((TabBar) findViewById(R.id.id_tabbar))
                .addTab(R.drawable.hot_normal, R.drawable.hot_select, "精选", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.rank_normal, R.drawable.rank_select, "排行", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.game_normal, R.drawable.game_select, "女优", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.near_normal, R.drawable.near_select, "附近色友", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.my_normal, R.drawable.my_select, "我的", Color.GRAY, Color.rgb(34, 145, 255))
                .setOnTabClickedListener(new OnClickedListener());

        JingxuanFragment f1 = new JingxuanFragment();
        PaihangFragment f2 = new PaihangFragment();
        NvyouFragment f3 = new NvyouFragment();
        NearFragment f4 = new NearFragment();
        MeFragment f5 = new MeFragment();

        fragments = new ArrayList<>();
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        fragments.add(f4);
        fragments.add(f5);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_fragment_container, f1)
                .add(R.id.id_fragment_container, f2).hide(f2)
                .add(R.id.id_fragment_container, f3).hide(f3)
                .add(R.id.id_fragment_container, f4).hide(f4)
                .add(R.id.id_fragment_container, f5).hide(f5)
                .commit();
        currentFragment = f1;


    }

    class OnClickedListener implements OnTabClickedListener {

        @Override
        public void onTabClicked(int index) {
            MainActivity that = MainActivity.this;
            that.getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fragments.get(index)).commit();
            currentFragment = fragments.get(index);

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

