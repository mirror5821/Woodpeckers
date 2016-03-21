package com.mirror.woodpecker.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.AddrAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Address;
import com.mirror.woodpecker.app.util.AppAjaxParam;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/2/23.
 */
public class MapSelectActivity extends BaseActivity {
    EditText mEtSearch;
    private ListView mListView;
    private ImageView mImgLoc;

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;

    private static Context mContext;
    private double mLat,mLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);

        mContext = getApplicationContext();

        mListView = (ListView)findViewById(R.id.list);
        mImgLoc = (ImageView)findViewById(R.id.loc);
        mEtSearch = (EditText)findViewById(R.id.et);

        mMapView = (MapView)findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //设置显示级别
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(18));

        //定位模式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 传入null则，恢复默认图标
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true,null));

        // 开启定位图层  暂时关闭后不会出现定位图层
        //		mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //需设置此参数，否则无地址信息
        option.setAddrType("all");
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                LatLng center = status.target;

                mLat = center.latitude;
                mLng = center.longitude;

                getAddstrs(center);
            }

            @Override
            public void onMapStatusChange(MapStatus status) {

            }
        });


    }


    MyLocationData locData;
    private boolean IS_FIRST_LOC = true;
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            if(IS_FIRST_LOC){
                locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);

                AppContext.Latitude = location.getLatitude();
                AppContext.Longitude = location.getLongitude();

                mLat = location.getLatitude();
                mLng = location.getLongitude();



                AppContext.Address = location.getAddrStr();
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);

                mLocClient.stop();

                getAddstrs(ll);


            }

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private String mCity;
    private String mCountry;
    private String mDistrict;
    private String mProvince;

    private void getAddstrs(LatLng center){

        String url = "http://api.map.baidu.com/geocoder/v2/?ak=Vip0XHEvRRKbs9dPgMpNclzt&callback=renderReverse&location="+
                center.latitude+","+center.longitude+"&output=json&pois=1";

        AppAjaxParam param = new AppAjaxParam(url);

        x.http().get(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String t) {
                t = t.substring(t.indexOf("(") + 1, t.lastIndexOf(")"));

                try {
                    JSONObject jb = new JSONObject(t);
                    String status = jb.getString("status");

                    if (status.equals("0")) {
                        JSONObject result = jb.getJSONObject("result");
                        JSONObject addressComponent = result.getJSONObject("addressComponent");
                        mCity = addressComponent.getString("city");
                        mCountry = addressComponent.getString("country");
                        mDistrict = addressComponent.getString("district");
                        mProvince = addressComponent.getString("province");

                        showToast(mCountry + mProvince + mCity + mDistrict);

                        String formatted_addStr = result.getString("formatted_address");
                        final List<Address> lists = new ArrayList<Address>();
                        //这是返回的推荐地址
                        Address a = new Address();
                        a.setAddr(formatted_addStr.replace(mProvince + mCity + mDistrict, ""));
                        a.setName(" ");
                        lists.add(a);
                        //这是解析的其他列表地址
                        lists.addAll(JsonUtils.parseList(result.getString("pois").replace(mProvince + mCity + mDistrict, ""), Address.class));

                        mListView.setAdapter(new AddrAdapter<>(mContext, lists,1));
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent,
                                                    View view, int position, long id) {
                                Intent i = new Intent(MapSelectActivity.this, RepairAddActivity.class);
                                i.putExtra(LAT, mLat);
                                i.putExtra(LNG, mLng);
                                i.putExtra(ADDRESS, lists.get(position).getAddr());
                                i.putExtra(COUNTRY, mCountry);
                                i.putExtra(PROVINCE, mProvince);
                                i.putExtra(CITY, mCity);
                                i.putExtra(DISTRICT, mDistrict);

                                setResult(RESULT_OK, i);
                                finish();
                            }

                        });
                    } else {

                    }
                } catch (JSONException e) {
//                    loadDefaultAreaForNoLoc();
//					showToast("e------"+e.getLocalizedMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }
}
