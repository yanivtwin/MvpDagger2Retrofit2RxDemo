package com.yantwin.ulike.service;

import com.yantwin.ulike.model.ButtonResponse;

import java.util.List;

public interface FilterService {

    void getDoFilter(List<ButtonResponse> ButtonResponses, boolean internetAvailable);

}
