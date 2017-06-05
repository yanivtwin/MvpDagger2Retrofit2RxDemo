package com.yantwin.ulike.service;

import com.yantwin.ulike.model.ButtonResponse;

import java.util.List;

import rx.Observable;


public interface ButtonViewInterface {

    void onCompleted();

    void onError(String message);

    void onButtons(List<ButtonResponse> ButtonResponses);

    Observable<List<ButtonResponse>> getButtons();
    void onNonSelected();
    void onSelected(String type);
}
