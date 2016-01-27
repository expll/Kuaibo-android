package com.wl.kbplayer.kuaibo.Network;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public interface RequestCallback {

    void requestCallBack(String url, ArrayList result, ArrayList banners);
}