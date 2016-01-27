package com.wl.kbplayer.kuaibo;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import com.wl.kbplayer.kuaibo.Network.RequestCallback;
import com.wl.kbplayer.kuaibo.Network.HttpRequest;

import com.wl.kbplayer.kuaibo.Tabbar.OnTabClickedListener;
import com.wl.kbplayer.kuaibo.Tabbar.TabBar;
import com.wl.kbplayer.kuaibo.segmentcontrol.SegmentControl;




public class ListViewActivity extends Activity implements RequestCallback {



    static int SCREEN_WIDTH;
    static int SCREEN_HEGHT;
    static String URL       = "http://ssl.panpeili.com/kuaibo/home/jingxuan/home_jingxuan.json";
    static String URL_hanri = "http://ssl.panpeili.com/kuaibo/home/hanri/home_hanri.json";
    static String URL_oumei = "http://ssl.panpeili.com/kuaibo/home/oumei/home_oumei.json";
    static String URL_katong = "http://ssl.panpeili.com/kuaibo/home/katong/home_katong.json";

    public SegmentControl scTop;
    public TabBar tbApplication;


    //RefreshableView refreshableView;

    public HttpRequest hr;
    public ArrayList imagesURLs;
    public ArrayList bannerURLs;
    ListView listView;

    public HttpRequest hr_hanri;
    public ArrayList imagesURLs_hanri;
    public ArrayList bannerURLs_hanri;
    ListView listView_hanri;

    public HttpRequest hr_oumei;
    public ArrayList imagesURLs_oumei;
    public ArrayList bannerURLs_oumei;
    ListView listView_oumei;

    public HttpRequest hr_katong;
    public ArrayList imagesURLs_katong;
    public ArrayList bannerURLs_katong;
    ListView listView_katong;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);



        hr = new HttpRequest();
        hr.setRequestCallback(this);
        hr.setUrl(URL);

        hr_hanri = new HttpRequest();
        hr_hanri.setRequestCallback(this);
        hr_hanri.setUrl(URL_hanri);

        hr_oumei = new HttpRequest();
        hr_oumei.setRequestCallback(this);
        hr_oumei.setUrl(URL_oumei);

        hr_katong = new HttpRequest();
        hr_katong.setRequestCallback(this);
        hr_katong.setUrl(URL_katong);

        listView = (ListView) findViewById(R.id.lv);
        listView_hanri = (ListView) findViewById(R.id.lvHanri);
        listView_oumei = (ListView) findViewById(R.id.lvOumei);
        listView_katong = (ListView) findViewById(R.id.lvKatong);

        WindowManager wm = this.getWindowManager();
        SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
        SCREEN_HEGHT = wm.getDefaultDisplay().getHeight();

        //  1.完成ImageLoaderConfiguration的配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)          // default = device screen dimensions

        .threadPoolSize(3)                          // default
                .threadPriority(Thread.NORM_PRIORITY - 1)   // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)              // default

                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default

                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

        scTop = (SegmentControl) findViewById(R.id.segment_control);
        scTop.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                Log.d("WL", "" + index);
                switch (index) {
                    // 精选
                    case 0:
                        listView.setVisibility(View.VISIBLE);
                        listView_hanri.setVisibility(View.INVISIBLE);
                        listView_oumei.setVisibility(View.INVISIBLE);
                        listView_katong.setVisibility(View.INVISIBLE);

                        break;
                    // 日韩
                    case 1:
                        if (imagesURLs_hanri == null)
                        {
                            // request data
                            hr_hanri.execute(URL_hanri);
                        }

                        listView.setVisibility(View.INVISIBLE);
                        listView_hanri.setVisibility(View.VISIBLE);
                        listView_oumei.setVisibility(View.INVISIBLE);
                        listView_katong.setVisibility(View.INVISIBLE);

//                        RelativeLayout
//                        Re.LayoutParams lp = (LinearLayout.LayoutParams)listView.getLayoutParams();
//                        LinearLayout.LayoutParams lp_hanri = (LinearLayout.LayoutParams)listView_hanri.getLayoutParams();
//                        LinearLayout.LayoutParams lp_oumei = (LinearLayout.LayoutParams)listView_oumei.getLayoutParams();
//                        LinearLayout.LayoutParams lp_katong = (LinearLayout.LayoutParams)listView_katong.getLayoutParams();
//
//                        lp_hanri.width = SCREEN_WIDTH;
//                        lp_hanri.setMargins(0, 0, 0, 0);



                        break;
                    // 欧美
                    case 2:
                        if (imagesURLs_oumei == null)
                        {
                            // request data
                            hr_oumei.execute(URL_oumei);
                        }
                        listView.setVisibility(View.INVISIBLE);
                        listView_hanri.setVisibility(View.INVISIBLE);
                        listView_oumei.setVisibility(View.VISIBLE);
                        listView_katong.setVisibility(View.INVISIBLE);
                        break;
                    // 卡通
                    case 3:
                        if (imagesURLs_katong == null)
                        {
                            // request data
                            hr_katong.execute(URL_katong);
                        }
                        listView.setVisibility(View.INVISIBLE);
                        listView_hanri.setVisibility(View.INVISIBLE);
                        listView_oumei.setVisibility(View.INVISIBLE);
                        listView_katong.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }


            }
        });



        class OnClickedListener implements OnTabClickedListener {
            @Override
            public void onTabClicked(int index) {
                Log.d("WL", "" + index);
            }
        }


        tbApplication = (TabBar) findViewById(R.id.tbApplication);
        tbApplication.addTab(R.drawable.hot_normal, R.drawable.hot_select, "精选", R.color.colorWhite, R.color.colorOrange).setOnTabClickedListener(new OnClickedListener());
        tbApplication.addTab(R.drawable.rank_normal, R.drawable.rank_select, "排行", R.color.colorWhite, R.color.colorOrange).setOnTabClickedListener(new OnClickedListener());
        tbApplication.addTab(R.drawable.game_normal, R.drawable.game_select, "女优", R.color.colorWhite, R.color.colorOrange).setOnTabClickedListener(new OnClickedListener());
        tbApplication.addTab(R.drawable.near_normal, R.drawable.near_select, "附近色友", R.color.colorWhite, R.color.colorOrange).setOnTabClickedListener(new OnClickedListener());
        tbApplication.addTab(R.drawable.my_normal, R.drawable.my_select, "我的", R.color.colorWhite, R.color.colorOrange).setOnTabClickedListener(new OnClickedListener());


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 精选
        if (this.imagesURLs == null)
        {
            // request data
            hr.execute(URL);
        }

//        if (imagesURLs_hanri == null)
//        {
//            // request data
//            hr_hanri.execute(URL_hanri);
//        }

        listView.setVisibility(View.VISIBLE);
        listView_hanri.setVisibility(View.INVISIBLE);
        listView_oumei.setVisibility(View.INVISIBLE);
        listView_katong.setVisibility(View.INVISIBLE);

    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.wl.kbplayer.kuaibo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.wl.kbplayer.kuaibo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void requestCallBack(String url, ArrayList result, ArrayList banners) {

        // 精选
        if (url.equalsIgnoreCase("jingxuan"))
        {
            imagesURLs = result;
            bannerURLs = banners;
            Log.d("WL", imagesURLs.toString() + "\n-------------------------\n" + banners.toString());
            refreshTable();
        } else if(url.equalsIgnoreCase("hanri")){
            imagesURLs_hanri = result;
            bannerURLs_hanri = banners;
            Log.d("WL韩日数据：", imagesURLs_hanri.toString() + "\n-------------------------\n" + banners.toString());
            refreshTable_hanri();
        } else if(url.equalsIgnoreCase("oumei")){
            imagesURLs_oumei = result;
            bannerURLs_oumei = banners;
            Log.d("WL", imagesURLs_oumei.toString() + "\n-------------------------\n" + banners.toString());
            refreshTable_oumei();
        } else if(url.equalsIgnoreCase("katong")){
            imagesURLs_katong = result;
            bannerURLs_katong = banners;
            Log.d("WL", imagesURLs_katong.toString() + "\n-------------------------\n" + banners.toString());
            refreshTable_katong();
        }


    }

    private void refreshTable(){

        ArrayList<Row> arrayOfRows = new ArrayList<Row>();

        Log.d("WL DATA INIT", bannerURLs.toString());
        Row row = new Row("header", "header", "header",  bannerURLs);
        arrayOfRows.add(row);
        for(int i = 0;i < imagesURLs.size(); i ++){
            ArrayList imgs = (ArrayList)imagesURLs.get(i);
            row = new Row((String)imgs.get(0), (String)imgs.get(1), (String)imgs.get(2), bannerURLs);
            arrayOfRows.add(row);
        }

        // last cell
        row = new Row("buttom", "buttom", "buttom", null);
        arrayOfRows.add(row);
        RowsAdapter adapter = new RowsAdapter(this, arrayOfRows);
        listView = (ListView) findViewById(R.id.lv);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);

    }

    private void refreshTable_hanri(){

        ArrayList<Row> arrayOfRows = new ArrayList<Row>();

        Log.d("WL DATA INIT", bannerURLs_hanri.toString());
        Row row = new Row("header", "header", "header",  bannerURLs_hanri);
        arrayOfRows.add(row);
        for(int i = 0;i < imagesURLs_hanri.size(); i ++){
            ArrayList imgs = (ArrayList)imagesURLs_hanri.get(i);
            row = new Row((String)imgs.get(0), (String)imgs.get(1), (String)imgs.get(2), bannerURLs_hanri);
            arrayOfRows.add(row);
        }

        // last cell
        row = new Row("buttom", "buttom", "buttom", null);
        arrayOfRows.add(row);
        RowsAdapter adapter = new RowsAdapter(this, arrayOfRows);
        listView_hanri = (ListView) findViewById(R.id.lvHanri);
        listView_hanri.setDividerHeight(0);
        listView_hanri.setAdapter(adapter);

    }

    private void refreshTable_oumei(){

        ArrayList<Row> arrayOfRows = new ArrayList<Row>();

        Log.d("WL DATA INIT", bannerURLs_oumei.toString());
        Row row = new Row("header", "header", "header",  bannerURLs_oumei);
        arrayOfRows.add(row);
        for(int i = 0;i < imagesURLs_oumei.size(); i ++){
            ArrayList imgs = (ArrayList)imagesURLs_oumei.get(i);
            row = new Row((String)imgs.get(0), (String)imgs.get(1), (String)imgs.get(2), bannerURLs_oumei);
            arrayOfRows.add(row);
        }

        // last cell
        row = new Row("buttom", "buttom", "buttom", null);
        arrayOfRows.add(row);
        RowsAdapter adapter = new RowsAdapter(this, arrayOfRows);
        listView_oumei = (ListView) findViewById(R.id.lvOumei);
        listView_oumei.setDividerHeight(0);
        listView_oumei.setAdapter(adapter);

    }

    private void refreshTable_katong(){

        ArrayList<Row> arrayOfRows = new ArrayList<Row>();

        Log.d("WL DATA INIT", bannerURLs_katong.toString());
        Row row = new Row("header", "header", "header",  bannerURLs_katong);
        arrayOfRows.add(row);
        for(int i = 0;i < imagesURLs_katong.size(); i ++){
            ArrayList imgs = (ArrayList)imagesURLs_katong.get(i);
            row = new Row((String)imgs.get(0), (String)imgs.get(1), (String)imgs.get(2), bannerURLs_katong);
            arrayOfRows.add(row);
        }

        // last cell
        row = new Row("buttom", "buttom", "buttom", null);
        arrayOfRows.add(row);
        RowsAdapter adapter = new RowsAdapter(this, arrayOfRows);
        listView_katong = (ListView) findViewById(R.id.lvKatong);
        listView_katong.setDividerHeight(0);
        listView_katong.setAdapter(adapter);

    }

    public class Row {
        public String img1;
        public String img2;
        public String img3;
        public ArrayList banner;

        public Row(String img1, String img2, String img3, ArrayList banner) {
            this.img1 = img1;
            this.img2 = img2;
            this.img3 = img3;
            this.banner = banner;
        }
    }

    public class RowsAdapter extends ArrayAdapter<Row> {

        // View lookup cache
        private class ViewHolder {
            ImageButton img1;
            ImageButton img2;
            ImageButton img3;
            TextView tvButtom;
            SlideShowView ssv;
        }

        private class ButtomViewHolder {

        }

        public RowsAdapter(Context context, ArrayList<Row> rows) {
            super(context, 0, rows);
        }

        public HttpRequest httpRequest;

        public RowsAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Row row = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            final ViewHolder viewHolder; // view lookup cache stored in tag
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                viewHolder = new ViewHolder();

                if (position == 0) {

                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_slide, parent, false);
                    viewHolder.ssv = (SlideShowView) convertView.findViewById(R.id.slideshowView);
                    Log.d("WL=====:", viewHolder.ssv.toString());
                    viewHolder.ssv.imageUrls = row.banner;

//                    viewHolder.ibBanner = (ImageButton) convertView.findViewById(R.id.ibBanner);
//                    viewHolder.ibBanner.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("WL", row.img2);
//                        }
//                    });

                } else if (row.img1.equalsIgnoreCase("buttom")) {
                        // buttom cell
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell_buttom, parent, false);
                        viewHolder.tvButtom = (TextView) convertView.findViewById(R.id.cellButtom);
                        viewHolder.tvButtom.setText("VIP将获取更多内容");


                } else {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell, parent, false);
                    viewHolder.img1 = (ImageButton) convertView.findViewById(R.id.img1);
                    viewHolder.img2 = (ImageButton) convertView.findViewById(R.id.img2);
                    viewHolder.img3 = (ImageButton) convertView.findViewById(R.id.img3);

                    viewHolder.img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("WL", row.img1);
                        }
                    });

                    viewHolder.img2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("WL", row.img2);
                        }
                    });

                    viewHolder.img3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("WL", row.img3);
                        }
                    });
                }



                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance


            if (position == 0) {
                //Log.d("WL------", row.banner);

                viewHolder.ssv.imageUrls = row.banner;

            } else if (row.img1.equalsIgnoreCase("buttom")) {

            } else {
                imageLoader.loadImage(row.img1, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                        float a = (float)viewHolder.img1.getWidth()/(float)loadedImage.getWidth();
                        viewHolder.img1.setImageBitmap(resize(loadedImage, a));
                    }
                });

                imageLoader.loadImage(row.img2, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                        float a = (float)viewHolder.img2.getWidth()/(float)loadedImage.getWidth();
                        viewHolder.img2.setImageBitmap(resize(loadedImage, a));
                    }
                });

                imageLoader.loadImage(row.img3, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img3.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img3.setImageBitmap(resize(loadedImage, a));
                    }
                });
            }




            // Return the completed view to render on screen
            return convertView;
        }
    }

    /**Bitmap放大的方法*/
    private static Bitmap resize(Bitmap bitmap, float a) {
        Matrix matrix = new Matrix();
        matrix.postScale(a, a); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }




}
