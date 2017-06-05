package com.yantwin.ulike.application;

import android.app.Application;

import com.yantwin.ulike.dependencies.ApiComponent;
import com.yantwin.ulike.dependencies.DaggerApiComponent;
import com.yantwin.ulike.dependencies.DaggerNetworkComponent;
import com.yantwin.ulike.dependencies.NetworkComponent;
import com.yantwin.ulike.dependencies.NetworkModule;
import com.yantwin.ulike.model.Constant;


public class ButtonApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        resolveDependency();
        super.onCreate();
    }

    private void resolveDependency() {
        mApiComponent = DaggerApiComponent.builder()
                .networkComponent(getNetworkComponent())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(Constant.BASE_URL))

                .build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
