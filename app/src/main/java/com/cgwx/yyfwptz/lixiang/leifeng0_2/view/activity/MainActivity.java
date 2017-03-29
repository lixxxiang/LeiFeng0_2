package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;


public class MainActivity extends BaseActivity<MainActivityPresenter, MainActivity> implements RadioGroup.OnCheckedChangeListener, BaseViewInterface{


    public static MainActivity mainActivity;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ImageView record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab_bar);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton = (RadioButton) findViewById(R.id.rb_home);
        radioButton.setChecked(true);
        record = (ImageView) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.performOnClick();//test
                Intent intent = new Intent(MainActivity.this, LocationDemo.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        presenter.createFragment(checkedId);
    }


}
