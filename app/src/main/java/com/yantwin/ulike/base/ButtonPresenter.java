package com.yantwin.ulike.base;

import com.yantwin.ulike.model.ButtonResponse;
import com.yantwin.ulike.service.ButtonViewInterface;

import java.util.List;

import rx.Observer;

public class ButtonPresenter extends BasePresenter implements Observer<List<ButtonResponse>> {

    private ButtonViewInterface mViewInterface;

    public ButtonPresenter(ButtonViewInterface viewInterface) {
        mViewInterface = viewInterface;
    }

    @Override
    public void onCompleted() {
        mViewInterface.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mViewInterface.onError(e.getMessage());
    }

    @Override
    public void onNext(List<ButtonResponse> ButtonResponses) {
        mViewInterface.onButtons(ButtonResponses);
    }

    public void fetchButtons() {
        unSubscribeAll();
        subscribe(mViewInterface.getButtons(), ButtonPresenter.this);
    }



}
