package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment;


import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.HomeFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentWithMapbak;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap.baiduMap;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap.bitmapDescriptor;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap.mapView;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap.requestLocButton;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class HomeFragmentWithMapPresenter extends BasePresenter<HomeFragmentNormal, HomeFragmentNormalModelImpl> {

    private FragmentManager fragmentManager;
    private MapStatusUpdate mapStatusUpdate;
    private Button hideButton;
    private Marker markerA;
    private InfoWindow infoWindow;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LocationClient mLocClient;
    private HomeFragmentWithMapPresenter.MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true;





    @Override
    protected HomeFragmentNormalModelImpl getModel() {
        return new HomeFragmentNormalModelImpl();
    }


    public void changeFragment() {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
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

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.mainActivity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            if (MainActivity.mainActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            if (MainActivity.mainActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public void setIcon() {
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(14.0f);
        baiduMap.setMapStatus(mapStatusUpdate);

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                hideButton = new Button(MainActivity.mainActivity.getApplicationContext());
                InfoWindow.OnInfoWindowClickListener listener = null;
                if (marker == markerA) {
                    hideButton.setBackgroundColor(0x000000);
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            Toast.makeText(MainActivity.mainActivity, "dd", Toast.LENGTH_SHORT).show();
                        }
                    };
                    LatLng ll = marker.getPosition();
                    infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(hideButton), ll, -47, listener);
                    baiduMap.showInfoWindow(infoWindow);
                }
                return true;
            }
        });
        initOverlay();
    }

    public void initOverlay() {
        // add marker overlay
        LatLng latLng = new LatLng(43.977607, 125.389826);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptor)
                .zIndex(9)
                .draggable(true);
        markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
        markerA = (Marker) (baiduMap.addOverlay(markerOptions));
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {}
            public void onMarkerDragEnd(Marker marker) {}
            public void onMarkerDragStart(Marker marker) {}
        });
    }

    public void setLocationMode() {
        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, bitmapDescriptor));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, bitmapDescriptor));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, bitmapDescriptor));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);



//        initOverlay();

        mLocClient = new LocationClient(MainActivity.mainActivity);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();


    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            Log.e("location: ", String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()));
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
