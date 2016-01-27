package com.wl.kbplayer.kuaibo;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    private Context context;                        //运行上下文
    private List<Map<String, Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器


    public final class ListItemView{                //自定义控件集合
        public ImageView img1;
        public ImageView img2;
        public ImageView img3;
    }


    public ListViewAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        //listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        listContainer = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listItems = listItems;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return listItems.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView  listItemView;

        if (convertView == null) {
            System.out.println("Cell 不存在， 创建Cell");
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_cell, null);
            //获取控件对象
            listItemView.img1 = (ImageView)convertView.findViewById(R.id.img1);
            listItemView.img2 = (ImageView)convertView.findViewById(R.id.img2);
            listItemView.img3 = (ImageView)convertView.findViewById(R.id.img3);


            listItemView.img1.setImageResource((Integer) this.listItems.get(position).get("img1"));
            listItemView.img2.setImageResource((Integer) this.listItems.get(position).get("img2"));
            listItemView.img3.setImageResource((Integer) this.listItems.get(position).get("img3"));

            //设置控件集到convertView
            convertView.setTag(listItemView);
        }else {
            listItemView = (ListItemView)convertView.getTag();
        }


        return convertView;
    }
}