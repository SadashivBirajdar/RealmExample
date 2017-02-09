package com.demo.simplerealmandroid.presenter;

import org.greenrobot.eventbus.EventBus;

public class BasePresenter{

    public BasePresenter() {
    }

    public void onStart() {
        EventBus.getDefault().register(this);
    }

    public void onResume() {
        if (!(EventBus.getDefault().isRegistered(this))) {
            EventBus.getDefault().register(this);
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
