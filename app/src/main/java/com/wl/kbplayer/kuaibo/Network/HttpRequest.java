package com.wl.kbplayer.kuaibo.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/14 0014.
 */


public class HttpRequest extends AsyncTask<Object, Object, Object> {


    static String URL_jingxuan       = "http://ssl.panpeili.com/kuaibo/home/jingxuan/home_jingxuan.json";
    static String URL_jingxuan_hanri = "http://ssl.panpeili.com/kuaibo/home/hanri/home_hanri.json";
    static String URL_jingxuan_oumei = "http://ssl.panpeili.com/kuaibo/home/oumei/home_oumei.json";
    static String URL_jingxuan_katong = "http://ssl.panpeili.com/kuaibo/home/katong/home_katong.json";

    static String URL_paihang_hanri = "http://ssl.panpeili.com/kuaibo/paihang/hanri/paihang_hanri.json";
    static String URL_paihang_oumei = "http://ssl.panpeili.com/kuaibo/paihang/oumei/paihang_oumei.json";
    static String URL_paihang_katong = "http://ssl.panpeili.com/kuaibo/paihang/katong/paihang_katong.json";

    static String URL_nvyou_cjk = "http://ssl.panpeili.com/kuaibo/nvyou/cjk/nvyou_cjk.json";
    static String URL_nvyou_bdyjy = "http://ssl.panpeili.com/kuaibo/nvyou/bdyjy/nvyou_bdyjy.json";
    static String URL_nvyou_thy = "http://ssl.panpeili.com/kuaibo/nvyou/thy/nvyou_thy.json";



    private RequestCallback delegate;
    private String url;

    public void setRequestCallback(RequestCallback d)
    {
        this.delegate = d;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }



    @Override
    protected Object doInBackground(Object... params) {
        // TODO Auto-generated method stub
        Log.d("WL", params[0].toString());
        HttpGet httpRequest = new HttpGet(params[0].toString());
        Log.d("WL", params[0].toString());
        try
        {
            Log.d("WL", "OK1");
            HttpClient httpClient = new DefaultHttpClient();
            Log.d("WL", "OK2");
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            Log.d("WL", "OK3");
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String strResult = EntityUtils.toString(httpResponse.getEntity());
                return strResult;
            } else
            {
                return "请求出错";
            }

        } catch (ClientProtocolException e)
        {

        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


    @Override

    protected void onCancelled(Object result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }


    @Override

    protected void onPostExecute(Object result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        try
        {
            //创建一个JSON对象
            String msg = result.toString();
            String str = new String(msg.getBytes("ISO-8859-1"),"UTF-8");
            JSONObject jsonObject = new JSONObject(str).getJSONObject("data");

            // Home
            if (url.indexOf("http://ssl.panpeili.com/kuaibo/home") != -1)
            {
                //获取某个对象的JSON数组
                JSONArray applist = jsonObject.getJSONArray("applist");
                ArrayList alRet = new ArrayList();
                //StringBuilder builder = new StringBuilder();

                for (int i = 0; i < applist.length(); i++)
                {
                    //新建一个JSON对象，该对象是某个数组里的其中一个对象
                    JSONObject jsonObject2 = (JSONObject) applist.opt(i);
                    String icon = jsonObject2.getString("icon");
                    String title = jsonObject2.getString("title");
                    String bundleid = jsonObject2.getString("bundleid");
                    Log.d("WL", icon + title + bundleid);

                    ArrayList al = new ArrayList();
                    al.add(icon);
                    al.add(title);
                    al.add(bundleid);
                    alRet.add(al);

                }


                JSONArray banners = jsonObject.getJSONArray("banner");
                ArrayList aBanner = new ArrayList();
                for (int i = 0; i < banners.length(); i++)
                {
                    //新建一个JSON对象，该对象是某个数组里的其中一个对象
                    JSONObject jsonObject2 = (JSONObject) banners.opt(i);
                    String img = jsonObject2.getString("img");

                    aBanner.add(img);
                }


                // jing xuan
                if (url.equalsIgnoreCase(URL_jingxuan)) {
                    delegate.requestCallBack("jingxuan", alRet, aBanner);
                } else if(url.equalsIgnoreCase(URL_jingxuan_hanri)) {
//                Log.d("韩日URL:", url);
//                Log.d("韩日请求返回字符串：", msg);
                    delegate.requestCallBack("hanri", alRet, aBanner);
                } else if(url.equalsIgnoreCase(URL_jingxuan_oumei)) {
                    delegate.requestCallBack("oumei", alRet, aBanner);
                } else if(url.equalsIgnoreCase(URL_jingxuan_katong)) {
                    delegate.requestCallBack("katong", alRet, aBanner);

                }

                // Paihang
            } else if (url.indexOf("http://ssl.panpeili.com/kuaibo/paihang") != -1)
            {
                //获取某个对象的JSON数组
                JSONArray applist = jsonObject.getJSONArray("result");
                ArrayList alRet = new ArrayList();
                //StringBuilder builder = new StringBuilder();

                for (int i = 0; i < applist.length(); i++)
                {
                    //新建一个JSON对象，该对象是某个数组里的其中一个对象
                    JSONObject jsonObject2 = (JSONObject) applist.opt(i);
                    String icon = jsonObject2.getString("icon");
                    String title = jsonObject2.getString("title");
                    String bundleid = jsonObject2.getString("bundleid");
                    Log.d("WL", icon + title + bundleid);

                    ArrayList al = new ArrayList();
                    al.add(icon);
                    al.add(title);
                    al.add(bundleid);
                    alRet.add(al);

                }

                Log.d("#####", alRet.toString());
                if (url.equalsIgnoreCase(URL_paihang_hanri)) {
                    delegate.requestCallBack("hanri", alRet, null);
                } else if (url.equalsIgnoreCase(URL_paihang_oumei)) {
                    delegate.requestCallBack("oumei", alRet, null);
                } else if (url.equalsIgnoreCase(URL_paihang_katong)) {
                    delegate.requestCallBack("katong", alRet, null);
                }
            } else if(url.indexOf("http://ssl.panpeili.com/kuaibo/nvyou") != -1) {
                //获取某个对象的JSON数组
                JSONArray applist = jsonObject.getJSONArray("result");
                ArrayList alRet = new ArrayList();
                //StringBuilder builder = new StringBuilder();

                for (int i = 0; i < applist.length(); i++)
                {
                    //新建一个JSON对象，该对象是某个数组里的其中一个对象
                    JSONObject jsonObject2 = (JSONObject) applist.opt(i);
                    String icon = jsonObject2.getString("icon");
                    String title = jsonObject2.getString("title");
                    String bundleid = jsonObject2.getString("bundleid");
                    Log.d("WL", icon + title + bundleid);

                    ArrayList al = new ArrayList();
                    al.add(icon);
                    al.add(title);
                    al.add(bundleid);
                    alRet.add(al);

                }



                JSONObject banners = jsonObject.getJSONObject("recommend");
                ArrayList aBanner = new ArrayList();
                aBanner.add(banners.get("img"));
//                for (int i = 0; i < banners.length(); i++)
//                {
//                    //新建一个JSON对象，该对象是某个数组里的其中一个对象
//                    JSONObject jsonObject2 = (JSONObject) banners.opt(i);
//                    String img = jsonObject2.getString("img");
//
//                    aBanner.add(img);
//                }


                // jing xuan
                if (url.equalsIgnoreCase(URL_nvyou_cjk)) {
                    delegate.requestCallBack("jingxuan", alRet, aBanner);
                } else if(url.equalsIgnoreCase(URL_nvyou_bdyjy)) {
                    delegate.requestCallBack("hanri", alRet, aBanner);
                } else if(url.equalsIgnoreCase(URL_nvyou_thy)) {
                    delegate.requestCallBack("oumei", alRet, aBanner);
                }
            }







        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        //super.onPreExecute();
        //Toast.makeText(getApplicationContext(), "开始HTTP GET请求", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }


}

