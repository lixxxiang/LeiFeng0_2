package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;


import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment.HomeFragmentWithMapPresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.LocationDemo;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentWithMapbak;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class HomeFragmentWithMap extends BaseFragment<HomeFragmentWithMapPresenter, HomeFragmentWithMap> implements BaseViewInterface{

    private View view;
    MapView mMapView;
    BaiduMap mBaiduMap;
    private Button changeView;
    private FragmentManager fragmentManager;
    LocationClient mLocClient;
    public HomeFragmentWithMap.MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    Button requestLocButton;
    boolean isFirstLoc = true;


    public HomeFragmentWithMap() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.home_fragment_with_map, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            if (this.getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            if (this.getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        requestLocButton = (Button) view.findViewById(R.id.button1);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);


        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();


        changeView = (Button) view.findViewById(R.id.changeView);
        fragmentManager = getFragmentManager();
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
                FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                if (homeFragmentWithMapbak != null)
                    fTransaction.hide(homeFragmentWithMapbak);
                if(homeFragmentNormal == null){
                    homeFragmentNormal = new HomeFragmentNormal();
                    fTransaction.replace(R.id.ly_content, homeFragmentNormal);
                } else
                    fTransaction.show(homeFragmentNormal);
                fTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }

    @Override
    protected HomeFragmentWithMapPresenter getPresenter() {
        return new HomeFragmentWithMapPresenter();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            Log.e("location: ", String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()));
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

}
