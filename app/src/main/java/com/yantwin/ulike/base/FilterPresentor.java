package com.yantwin.ulike.base;


import com.yantwin.ulike.model.ButtonResponse;
import com.yantwin.ulike.service.ButtonViewInterface;
import com.yantwin.ulike.service.FilterService;

import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FilterPresentor extends BasePresenter implements Observer<List<ButtonResponse>>, FilterService {
    private ButtonViewInterface mViewInterface;

    public FilterPresentor(ButtonViewInterface viewInterface) {
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
        if(ButtonResponses.size()==0)
            mViewInterface.onNonSelected();
        else{
            String typeSelected="";
            long max =0;
            for (int i = 0; i <ButtonResponses.size() ; i++) {
                if (max<ButtonResponses.get(i).getCool_down())
                {
                    max=ButtonResponses.get(i).getCool_down();
                    typeSelected= ButtonResponses.get(i).getType();
                }
            }
            mViewInterface.onSelected(typeSelected);
        }

    }



    @Override
    public void getDoFilter(List<ButtonResponse> ButtonResponses, final boolean internetAvailable) {

        unSubscribeAll();
        Subscription subscription = Observable.from(ButtonResponses)
                .filter(new Func1<ButtonResponse, Boolean>() {
                    @Override
                    public Boolean call(ButtonResponse o) {
                        return o.getEnabled(); //remove by isEnabled
                    }
                })
                .filter(new Func1<ButtonResponse, Boolean>() {
                    @Override
                    public Boolean call(ButtonResponse o) {
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
                        return o.getValid_days().contains(day); //remove by days of the week
                    }
                })
                .filter(new Func1<ButtonResponse, Boolean>() {
                    @Override
                    public Boolean call(ButtonResponse o) {
                        if(!o.getType().equals("toast"))
                            return true;
                        else
                            return internetAvailable; //remove toast if no internet
                    }
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe( FilterPresentor.this);



    }
}