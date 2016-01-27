package com.wl.kbplayer.kuaibo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public class CustomTableView extends FrameLayout {

    public ListView listView;
    public SlideShowView slideShowView;

    public CustomTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_tableview, this);
    }

    public void setBannerData(ArrayList data){
        if (slideShowView == null) {
            slideShowView = (SlideShowView) findViewById(R.id.slideshowView);
        }
        slideShowView.imageUrls = data;
    }

    public void setListViewData(ArrayList data){
        ArrayList<Row> arrayOfRows = new ArrayList<Row>();

        for(int i = 0;i < data.size(); i ++){
            ArrayList imgs = (ArrayList)data.get(i);
            Row row = new Row((String)imgs.get(0), (String)imgs.get(1), (String)imgs.get(2));
            arrayOfRows.add(row);
        }

        RowsAdapter adapter = new RowsAdapter(getContext(), arrayOfRows);
        if (listView == null) {
            listView = (ListView) findViewById(R.id.lvCustomTableView);
            listView.setDividerHeight(0);
            listView.setAdapter(adapter);
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
        public View getView(int position, View convertView, ViewGroup parent) {

            final Row row = getItem(position);
            final ViewHolder viewHolder;

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
                    }
                });

                imageLoader.loadImage(row.img2, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img2.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img2.setImageBitmap(resize(loadedImage, a));
                    }
                });

                imageLoader.loadImage(row.img3, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.d("WLWL---", row.img3);
                        // Do whatever you want with Bitmap
                        float a = (float) viewHolder.img3.getWidth() / (float) loadedImage.getWidth();
                        viewHolder.img3.setImageBitmap(resize(loadedImage, a));
                    }
                });

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
            imageLoader.loadImage(row.img1, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.d("WLWL", row.img1);
                    // Do whatever you want with Bitmap
                    float a = (float) viewHolder.img1.getWidth() / (float) loadedImage.getWidth();
                    viewHolder.img1.setImageBitmap(resize(loadedImage, a));
                }
            });

            imageLoader.loadImage(row.img2, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // Do whatever you want with Bitmap
                    float a = (float) viewHolder.img2.getWidth() / (float) loadedImage.getWidth();
                    viewHolder.img2.setImageBitmap(resize(loadedImage, a));
                }
            });

            imageLoader.loadImage(row.img3, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.d("WLWL---", row.img3);
                    // Do whatever you want with Bitmap
                    float a = (float) viewHolder.img3.getWidth() / (float) loadedImage.getWidth();
                    viewHolder.img3.setImageBitmap(resize(loadedImage, a));
                }
            });


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
