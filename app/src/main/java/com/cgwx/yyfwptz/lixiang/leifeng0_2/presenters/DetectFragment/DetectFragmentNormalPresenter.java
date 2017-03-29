package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment;



import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.DetectFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentNormal;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class DetectFragmentNormalPresenter extends BasePresenter<DetectFragmentNormal, DetectFragmentNormalModelImpl>{


    @Override
    protected DetectFragmentNormalModelImpl getModel() {
        return new DetectFragmentNormalModelImpl();
    }

    public void getURLRequest(String detectFragmentNormal) {
        model.geturl(detectFragmentNormal, new OnSendStringListener() {
            @Override
            public void sendurl(String string) {
                getView().getURL(string);
            }
        });
    }

    public void changeFragment() {

    }
}
