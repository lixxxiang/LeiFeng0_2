package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentNormal;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentNormal;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.MoreFragment;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.PrivateFragment;


public class MainActivity extends BaseActivity<MainActivityPresenter, MainActivity> implements RadioGroup.OnCheckedChangeListener, BaseViewInterface{


    public static MainActivity mainActivity;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DetectFragmentNormal detectFragment1;
    private PrivateFragment privateFragment;
    private MoreFragment moreFragment;
    private HomeFragmentNormal homeFragmentNormal;
    private FragmentManager fragmentManager;
    private ImageView record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        fragmentManager = getFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab_bar);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton = (RadioButton) findViewById(R.id.rb_home);
        radioButton.setChecked(true);
        record = (ImageView) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.performOnClick();
            }
        });

    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId) {
            case R.id.rb_home:
                if (homeFragmentNormal == null) {
                    homeFragmentNormal = new HomeFragmentNormal();
                    fTransaction.add(R.id.ly_content, homeFragmentNormal);
                } else {
                    fTransaction.show(homeFragmentNormal);
                }
                break;
            case R.id.rb_detect:
                if (detectFragment1 == null) {
                    detectFragment1 = new DetectFragmentNormal();
                    fTransaction.add(R.id.ly_content, detectFragment1);
                } else {
                    fTransaction.show(detectFragment1);
                }
                break;
            case R.id.rb_private:
                if (privateFragment == null) {
                    privateFragment = new PrivateFragment();
                    fTransaction.add(R.id.ly_content, privateFragment);
                } else {
                    fTransaction.show(privateFragment);
                }
                break;
            case R.id.rb_more:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    fTransaction.add(R.id.ly_content, moreFragment);
                } else {
                    fTransaction.show(moreFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragmentNormal != null) fragmentTransaction.hide(homeFragmentNormal);
        if (detectFragment1 != null) fragmentTransaction.hide(detectFragment1);
        if (privateFragment != null) fragmentTransaction.hide(privateFragment);
        if (moreFragment != null) fragmentTransaction.hide(moreFragment);
    }
}
