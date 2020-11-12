package com.example.sendwarmthattendant.dagger2;


import com.example.sendwarmthattendant.presenter.HistoricalOrdersPresenter;
import com.example.sendwarmthattendant.presenter.HomePresenter;
import com.example.sendwarmthattendant.presenter.LoginPresenter;
import com.example.sendwarmthattendant.presenter.OrderDetailPresenter;
import com.example.sendwarmthattendant.presenter.RegisterPresenter;

import dagger.Component;

@Component(modules = {MyModule.class})
public interface MyComponent
{
    LoginPresenter loginPresenter();

    RegisterPresenter registerPresenter();

    HomePresenter homePresenter();

    HistoricalOrdersPresenter historicalOrdersPresenter();

    OrderDetailPresenter orderDetailPresenter();
}
