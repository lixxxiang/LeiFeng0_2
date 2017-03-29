package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.HomeFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentNormal;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentWithMapbak;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class DetectFragmentWithMapPresenter extends BasePresenter<HomeFragmentNormal, HomeFragmentNormalModelImpl> {

    private FragmentManager fragmentManager;


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
}
