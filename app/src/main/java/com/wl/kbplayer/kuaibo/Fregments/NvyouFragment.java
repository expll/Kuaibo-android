package com.wl.kbplayer.kuaibo.Fregments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.wl.kbplayer.kuaibo.CustomTableView;
import com.wl.kbplayer.kuaibo.Network.HttpRequest;
import com.wl.kbplayer.kuaibo.Network.RequestCallback;
import com.wl.kbplayer.kuaibo.PercentLinearLayout;
import com.wl.kbplayer.kuaibo.R;
import com.wl.kbplayer.kuaibo.SlideShowView;
import com.wl.kbplayer.kuaibo.Tabbar.TabBar;
import com.wl.kbplayer.kuaibo.segmentcontrol.SegmentControl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NvyouFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NvyouFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NvyouFragment extends Fragment implements RequestCallback {

    public HashMap<String, Bitmap> bitmaps_jingxuan;
    public HashMap<String, Bitmap> bitmaps_hanri;
    public HashMap<String, Bitmap> bitmaps_oumei;
    public HashMap<String, Bitmap> bitmaps_katong;

    public int whichPage; // 0 Nvyou, 1 hanri, 2 oumei, 3 katong

    static int SCREEN_WIDTH;
    static int SCREEN_HEGHT;


    static String URL       = "http://ssl.panpeili.com/kuaibo/nvyou/cjk/nvyou_cjk.json";
    static String URL_hanri = "http://ssl.panpeili.com/kuaibo/nvyou/bdyjy/nvyou_bdyjy.json";
    static String URL_oumei = "http://ssl.panpeili.com/kuaibo/nvyou/thy/nvyou_thy.json";


    public SegmentControl scTop;

    public HttpRequest hr;
    public ArrayList imagesURLs;
    public ArrayList bannerURLs;
    public ListView listView;
    public SlideShowView headerView;
    public TextView footerView;

    public HttpRequest hr_hanri;
    public ArrayList imagesURLs_hanri;
    public ArrayList bannerURLs_hanri;
    public ListView listView_hanri;
    public SlideShowView headerView_hanri;
    public TextView footerView_hanri;

    public HttpRequest hr_oumei;
    public ArrayList imagesURLs_oumei;
    public ArrayList bannerURLs_oumei;
    public ListView listView_oumei;
    public SlideShowView headerView_oumei;
    public TextView footerView_oumei;

    public HttpRequest hr_katong;
    public ArrayList imagesURLs_katong;
    public ArrayList bannerURLs_katong;
    public ListView listView_katong;
    public SlideShowView headerView_katong;
    public TextView footerView_katong;

    public View V;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NvyouFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NvyouFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NvyouFragment newInstance(String param1, String param2) {
        NvyouFragment fragment = new NvyouFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //listView.addHeaderView(headerView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bitmaps_jingxuan = new HashMap<String, Bitmap>();
        bitmaps_hanri = new HashMap<String, Bitmap>();
        bitmaps_oumei = new HashMap<String, Bitmap>();
        bitmaps_katong = new HashMap<String, Bitmap>();
        whichPage = 0;


        V = inflater.inflate(R.layout.fragment_nvyou, container, false);

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
        //hr_katong.setUrl(URL_katong);



        listView = (ListView) V.findViewById(R.id.lv_jingxuan);
        listView_hanri = (ListView) V.findViewById(R.id.lv_hanri);
        listView_oumei = (ListView) V.findViewById(R.id.lv_oumei);
        listView_katong = (ListView) V.findViewById(R.id.lv_katong);


        View view = inflater.inflate(R.layout.list_cell_buttom, null, false);
        footerView = (TextView) view.findViewById(R.id.cellButtom);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        footerView.setLayoutParams(params);
        listView.addFooterView(footerView);
        listView_hanri.addFooterView(footerView);
        listView_oumei.addFooterView(footerView);
        listView_katong.addFooterView(footerView);


        //listView.addFooterView(footerView);

        WindowManager wm = getActivity().getWindowManager();
        SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
        SCREEN_HEGHT = wm.getDefaultDisplay().getHeight();

        //  1.完成ImageLoaderConfiguration的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.banner_default) // resource or drawable
                        //.showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
                        //.showImageOnFail(R.drawable.ic_error) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                        //.preProcessor(...)
                        //.postProcessor(...)
                        //.extraForDownloader(...)
                        //.considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                        //.decodingOptions(...)
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(getContext());  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                //.memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                .diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
                        //.taskExecutor(...)
                        //.taskExecutorForCachedImages(...)
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .memoryCache(new WeakMemoryCache())
                        //.denyCacheImageMultipleSizesInMemory()
                        //.memoryCache(new UsingFreqLimitedMemoryCache(1024*1024)) //可以通过自己的内存缓存实现
                        //.memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                        // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(getContext())) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(options) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        ImageLoader.getInstance().init(config);


        scTop = (SegmentControl) V.findViewById(R.id.segment_control_jingxuan);
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
                        whichPage = 0;
                        break;
                    // 日韩
                    case 1:
                        if (imagesURLs_hanri == null)
                        {
                            // request data
                            hr_hanri.execute(URL_hanri);
                            Toast toast = Toast.makeText(getContext(),
                                    "开始加载...", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        listView.setVisibility(View.INVISIBLE);
                        listView_hanri.setVisibility(View.VISIBLE);
                        listView_oumei.setVisibility(View.INVISIBLE);
                        listView_katong.setVisibility(View.INVISIBLE);
                        whichPage = 1;
                        break;
                    // 欧美
                    case 2:
                        if (imagesURLs_oumei == null)
                        {
                            // request data
                            hr_oumei.execute(URL_oumei);
                            Toast toast = Toast.makeText(getContext(),
                                    "开始加载...", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        listView.setVisibility(View.INVISIBLE);
                        listView_hanri.setVisibility(View.INVISIBLE);
                        listView_oumei.setVisibility(View.VISIBLE);
                        listView_katong.setVisibility(View.INVISIBLE);
                        whichPage = 2;
                        break;
                    // 卡通
                    case 3:
                        Toast toast = Toast.makeText(getContext(),
                                "暂无数据", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
//                        if (imagesURLs_katong == null)
//                        {
//                            // request data
//                            hr_katong.execute(URL_katong);
//                            Toast toast = Toast.makeText(getContext(),
//                                    "开始加载...", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                        }
                        listView.setVisibility(View.INVISIBLE);
                        listView_hanri.setVisibility(View.INVISIBLE);
                        listView_oumei.setVisibility(View.INVISIBLE);
                        listView_katong.setVisibility(View.VISIBLE);
                        whichPage = 3;
                        break;
                    default:
                        break;
                }


            }
        });

        return  V;

    }

    @Override
    public void onStart() {
        super.onStart();

        // 精选
        if (this.imagesURLs == null)
        {
            // request data
            hr.execute(URL);
            Toast toast = Toast.makeText(getContext(),
                    "开始加载...", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


        listView.setVisibility(View.VISIBLE);
        listView_hanri.setVisibility(View.INVISIBLE);
        listView_oumei.setVisibility(View.INVISIBLE);
        listView_katong.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener = null;
        hr = null;
        imagesURLs= null;
        bannerURLs= null;
        listView= null;

        hr_hanri= null;
        imagesURLs_hanri= null;
        bannerURLs_hanri= null;
        listView_hanri= null;

        hr_oumei= null;
        imagesURLs_oumei= null;
        bannerURLs_oumei= null;
        listView_oumei= null;

        hr_katong= null;
        imagesURLs_katong= null;
        bannerURLs_katong= null;
        listView_katong= null;


        V= null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        hr = null;
        imagesURLs= null;
        bannerURLs= null;
        listView= null;

        hr_hanri= null;
        imagesURLs_hanri= null;
        bannerURLs_hanri= null;
        listView_hanri= null;

        hr_oumei= null;
        imagesURLs_oumei= null;
        bannerURLs_oumei= null;
        listView_oumei= null;

        hr_katong= null;
        imagesURLs_katong= null;
        bannerURLs_katong= null;
        listView_katong= null;


        V= null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void requestCallBack(String url, ArrayList result, ArrayList banners) {

        ArrayList<Row> arrayOfRows = new ArrayList<Row>();

        for(int i = 0;i < result.size(); i ++){
            ArrayList imgs = (ArrayList)result.get(i);
            Row row = new Row((String)imgs.get(0), (String)imgs.get(1), (String)imgs.get(2));
            arrayOfRows.add(row);
        }

        RowsAdapter adapter = new RowsAdapter(getContext(), arrayOfRows);





        // 精选
        if (url.equalsIgnoreCase("jingxuan"))
        {
            if (headerView == null) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_slide, null);
                headerView = (SlideShowView) view.findViewById(R.id.slideshowView);
                headerView.setLayoutParams(new AbsListView.LayoutParams(SCREEN_WIDTH, SCREEN_WIDTH*262/600));
            }
            listView.addHeaderView(headerView);
            headerView.imageUrls = banners;

            imagesURLs = result;
            listView.setAdapter(adapter);

        } else if(url.equalsIgnoreCase("hanri"))
        {
            if (headerView_hanri == null) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_slide, null);
                headerView_hanri = (SlideShowView) view.findViewById(R.id.slideshowView);
                headerView_hanri.setLayoutParams(new AbsListView.LayoutParams(SCREEN_WIDTH, SCREEN_WIDTH*262/600));
            }
            listView_hanri.addHeaderView(headerView_hanri);
            headerView_hanri.imageUrls = banners;

            imagesURLs_hanri = result;
            listView_hanri.setAdapter(adapter);
        } else if(url.equalsIgnoreCase("oumei"))
        {
            if (headerView_oumei == null) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_slide, null);
                headerView_oumei = (SlideShowView) view.findViewById(R.id.slideshowView);
                headerView_oumei.setLayoutParams(new AbsListView.LayoutParams(SCREEN_WIDTH, SCREEN_WIDTH*262/600));
            }
            listView_oumei.addHeaderView(headerView_oumei);
            headerView_oumei.imageUrls = banners;

            imagesURLs_oumei = result;
            listView_oumei.setAdapter(adapter);
        } else if(url.equalsIgnoreCase("katong"))
        {
            if (headerView_katong == null) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_slide, null);
                headerView_katong = (SlideShowView) view.findViewById(R.id.slideshowView);
                headerView_katong.setLayoutParams(new AbsListView.LayoutParams(SCREEN_WIDTH, SCREEN_WIDTH*262/600));
            }
            listView_katong.addHeaderView(headerView_katong);
            headerView_katong.imageUrls = banners;

            imagesURLs_katong = result;
            listView_katong.setAdapter(adapter);
        }


    }

    public class Row {
        public String img1;
        public String img2;
        public String img3;

        public Row(String i1, String i2, String i3) {
            this.img1 = i1;
            this.img2 = i2;
            this.img3 = i3;
        }
    }

    private class ViewHolder {
        ImageButton img1;
        ImageButton img2;
        ImageButton img3;
    }

    public class RowsAdapter extends ArrayAdapter<Row> {

        public RowsAdapter(Context context, ArrayList<Row> rows) {
            super(context, 0, rows);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Row row = getItem(position);
            final ViewHolder viewHolder;
            final HashMap <String, Bitmap>map;
            switch (whichPage)
            {
                case 0:
                    map = bitmaps_jingxuan;
                    break;
                case 1:
                    map = bitmaps_hanri;
                    break;
                case 2:
                    map = bitmaps_oumei;
                    break;
                case 3:
                    map = bitmaps_katong;
                    break;
                default:
                    map = bitmaps_jingxuan;
                    break;
            }

            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell, parent, false);
                viewHolder.img1 = (ImageButton) convertView.findViewById(R.id.img1);
                viewHolder.img2 = (ImageButton) convertView.findViewById(R.id.img2);
                viewHolder.img3 = (ImageButton) convertView.findViewById(R.id.img3);
                viewHolder.img1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.moive));
                viewHolder.img2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.moive));
                viewHolder.img3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.moive));

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

                ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
                imageLoader.loadImage(row.img1, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.d("WLWL", row.img1);
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img1.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img1.setImageBitmap(resize(loadedImage, a));
                        setBackupBitmaps(resize(loadedImage, a), ""+position+"1", map);

                    }
                });

                imageLoader.loadImage(row.img2, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img2.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img2.setImageBitmap(resize(loadedImage, a));
                        setBackupBitmaps(resize(loadedImage, a), "" + position + "2", map);
                    }
                });

                imageLoader.loadImage(row.img3, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.d("WLWL---", row.img3);
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img3.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img3.setImageBitmap(resize(loadedImage, a));
                        setBackupBitmaps(resize(loadedImage, a), "" + position + "3", map);
                    }
                });

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
            if (map.get(""+position+"1") != null)
            {
                viewHolder.img1.setImageBitmap(map.get(""+position+"1"));
            } else {
                viewHolder.img1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.moive));
                imageLoader.loadImage(row.img1, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.d("WLWL", row.img1);
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img1.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img1.setImageBitmap(resize(loadedImage, a));
                        setBackupBitmaps(resize(loadedImage, a), "" + position + "1", map);
                    }
                });
            }

            //
            if (map.get(""+position+"2") != null)
            {
                viewHolder.img2.setImageBitmap(map.get(""+position+"2"));
            } else {
                viewHolder.img2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.moive));
                imageLoader.loadImage(row.img2, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img2.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img2.setImageBitmap(resize(loadedImage, a));
                        setBackupBitmaps(resize(loadedImage, a), "" + position + "2", map);
                    }
                });
            }

            //
            if (map.get(""+position+"3") != null)
            {
                viewHolder.img3.setImageBitmap(map.get(""+position+"3"));
            } else {
                viewHolder.img3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.moive));
                imageLoader.loadImage(row.img3, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.d("WLWL---", row.img3);
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img3.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img3.setImageBitmap(resize(loadedImage, a));
                        setBackupBitmaps(resize(loadedImage, a), "" + position + "3", map);
                    }
                });
            }


            //








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

    private void setBackupBitmaps(Bitmap bitmap, String key, HashMap<String, Bitmap> map){

        if (map.get(key) == null)
        {
            map.put(key, bitmap);
        }
    }



}
