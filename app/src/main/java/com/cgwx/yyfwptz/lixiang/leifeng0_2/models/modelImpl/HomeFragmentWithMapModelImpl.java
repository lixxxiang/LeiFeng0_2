package com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl;


import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Location;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.HomeFragmentNormalModelInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.HomeFragmentWithMapModelInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendArrayListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Constants;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.getLocationDemo;


/**
 * Created by yyfwptz on 2017/3/27.
 */

public class HomeFragmentWithMapModelImpl implements HomeFragmentWithMapModelInterface {
    /**
     * 入参为定位信息 查库获取一定范围内的所有点 用数组表示
     */
    Icon icon1 = new Icon();
    Icon icon2 = new Icon();
    Icon icon3 = new Icon();
    Icon icon4 = new Icon();
    Icon icon5 = new Icon();
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;


    public void insert(){
        icon1.setLangitude(44.000000);
        icon1.setLatitude(125.410000);

        icon2.setLangitude(44.030000);
        icon2.setLatitude(125.440000);

        icon3.setLangitude(44.060000);
        icon3.setLatitude(125.470000);

        icon4.setLangitude(44.090000);
        icon4.setLatitude(125.500000);

        icon5.setLangitude(44.120000);
        icon5.setLatitude(125.530000);
    }

    Icon icons[] = {icon1, icon2, icon3, icon4, icon5};



    @Override
    public void getIcons(OnSendArrayListener listener) {
        mLocationClient = new LocationClient(MainActivity.mainActivity.getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();
        insert();
        listener.sendArray(icons);
    }


    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();
                Log.i("MainActivity", "address:" + address + " latitude:" + latitude
                        + " longitude:" + longitude + "---");
                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
            }
        }
    }
}
