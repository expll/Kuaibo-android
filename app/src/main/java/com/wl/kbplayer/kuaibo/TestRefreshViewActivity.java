//package com.wl.kbplayer.kuaibo;
//
//import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Window;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//public class TestRefreshViewActivity extends Activity {
//
//    RefreshableView refreshableView;
//    ListView listView;
//    ArrayAdapter<String> adapter;
//    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N" };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_test_refresh_view);
//        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
//        listView = (ListView) findViewById(R.id.list_view);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(adapter);
//        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
//            @Override
//            public void onRefresh() {
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                refreshableView.finishRefreshing();
//            }
//        }, 0);
//    }
//}
