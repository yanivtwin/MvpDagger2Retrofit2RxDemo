package com.yantwin.ulike.service;

import com.yantwin.ulike.model.ButtonResponse;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ButtonService {

    @GET("/butto_to_action_config.json")
    Observable<List<ButtonResponse>> getButtons();

}
