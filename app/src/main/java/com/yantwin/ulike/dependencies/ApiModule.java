package com.yantwin.ulike.dependencies;

import com.yantwin.ulike.service.ButtonService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
public class ApiModule {

    @Provides
    @CustomScope
    ButtonService provideButtonService(Retrofit retrofit) {
        return retrofit.create(ButtonService.class);
    }
}
