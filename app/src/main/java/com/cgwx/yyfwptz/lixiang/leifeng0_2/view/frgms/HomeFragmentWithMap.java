package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;


import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment.HomeFragmentWithMapPresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class HomeFragmentWithMap extends BaseFragment<HomeFragmentWithMapPresenter, HomeFragmentWithMap> implements BaseViewInterface{

    private View view;
    public static MapView mapView;
    public static BaiduMap baiduMap;
    private Button changeView;
    private FragmentManager fragmentManager;
    private LocationClient mLocClient;
    public static Button requestLocButton;
    public static BitmapDescriptor bitmapDescriptor;


    public HomeFragmentWithMap() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.home_fragment_with_map, container, false);
        mapView = (MapView) view.findViewById(R.id.bmapView);
        requestLocButton = (Button) view.findViewById(R.id.button1);
        changeView = (Button) view.findViewById(R.id.changeView);
        /**
         * to presenter
         */
        fpresenter.checkPermission();
        fpresenter.setIcon();
        fpresenter.setLocationMode();

        fragmentManager = getFragmentManager();
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
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
        super.onDestroy();
        mLocClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        bitmapDescriptor.recycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


}
