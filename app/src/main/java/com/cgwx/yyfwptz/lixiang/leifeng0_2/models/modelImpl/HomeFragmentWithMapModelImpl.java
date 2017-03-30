package com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl;


import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.HomeFragmentNormalModelInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.HomeFragmentWithMapModelInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendArrayListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Constants;


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
        listener.sendArray(icons);
    }
}
