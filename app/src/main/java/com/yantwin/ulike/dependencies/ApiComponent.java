package com.yantwin.ulike.dependencies;

import com.yantwin.ulike.ui.MainActivity;
import com.yantwin.ulike.ui.SingleButtonActivity;

import dagger.Component;


@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {

    void inject(MainActivity activity);


    void inject(SingleButtonActivity singleButtonActivity);
}
