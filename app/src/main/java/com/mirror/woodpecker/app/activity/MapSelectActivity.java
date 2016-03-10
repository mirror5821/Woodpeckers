package com.mirror.woodpecker.app.activity;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.mirror.woodpecker.app.R;

/**
 * Created by 王沛栋 on 2016/2/23.
 */
public class MapSelectActivity extends BaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);

        mMapView = (MapView)findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //设置显示级别
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(18));

    }
}
