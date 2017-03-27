package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class HomeFragmentWithMap extends Fragment {

    private View view;
    private MapView mapView = null;
    private Button changeView;
    private HomeFragmentNormal homeFragmentNormal;
    private FragmentManager fragmentManager;
    public HomeFragmentWithMap() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.home_fragment2, container, false);
        mapView = (MapView) view.findViewById(R.id.id_map_view);
        changeView = (Button) view.findViewById(R.id.changeView);
        fragmentManager = getFragmentManager();
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                homeFragmentNormal = new HomeFragmentNormal();
                fTransaction.replace(R.id.ly_content, homeFragmentNormal);
                fTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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
