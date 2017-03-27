package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter;

import android.widget.Toast;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.MainActivityModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class MainActivityPresenter extends BasePresenter<MainActivity, MainActivityModelImpl> {

    @Override
    protected MainActivityModelImpl getModel() {
        return new MainActivityModelImpl();
    }

    @Override
    public void performOnClick() {
        Toast.makeText(MainActivity.mainActivity, "show record view", Toast.LENGTH_SHORT).show();
    }

}
