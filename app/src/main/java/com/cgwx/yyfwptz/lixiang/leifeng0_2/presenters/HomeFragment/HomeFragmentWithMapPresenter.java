package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.HomeFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentNormal;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentWithMap;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class HomeFragmentWithMapPresenter extends BasePresenter<HomeFragmentNormal, HomeFragmentNormalModelImpl> {

    private FragmentManager fragmentManager;


    @Override
    protected HomeFragmentNormalModelImpl getModel() {
        return new HomeFragmentNormalModelImpl();
    }


    public void changeFragment() {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        if (homeFragmentWithMap != null)
            fTransaction.hide(homeFragmentWithMap);
        if(homeFragmentNormal == null){
            homeFragmentNormal = new HomeFragmentNormal();
            fTransaction.replace(R.id.ly_content, homeFragmentNormal);
        } else
            fTransaction.show(homeFragmentNormal);
        fTransaction.commit();
    }
}
