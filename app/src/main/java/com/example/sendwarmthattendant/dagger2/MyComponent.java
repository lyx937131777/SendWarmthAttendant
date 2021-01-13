package com.example.sendwarmthattendant.dagger2;


import com.example.sendwarmthattendant.presenter.ChangePasswordPresenter;
import com.example.sendwarmthattendant.presenter.CustomerHistoryOrderPresenter;
import com.example.sendwarmthattendant.presenter.HistoricalOrdersPresenter;
import com.example.sendwarmthattendant.presenter.HomePresenter;
import com.example.sendwarmthattendant.presenter.LoginPresenter;
import com.example.sendwarmthattendant.presenter.MainPresenter;
import com.example.sendwarmthattendant.presenter.MapPresenter;
import com.example.sendwarmthattendant.presenter.OrderDetailPresenter;
import com.example.sendwarmthattendant.presenter.OrderPresenter;
import com.example.sendwarmthattendant.presenter.RegisterPresenter;
import com.example.sendwarmthattendant.presenter.SetNewPasswordPresenter;
import com.example.sendwarmthattendant.presenter.SettingPresenter;

import dagger.Component;

@Component(modules = {MyModule.class})
public interface MyComponent
{
    LoginPresenter loginPresenter();

    RegisterPresenter registerPresenter();

    HomePresenter homePresenter();

    HistoricalOrdersPresenter historicalOrdersPresenter();

    OrderDetailPresenter orderDetailPresenter();

    SettingPresenter settingPresenter();

    MapPresenter mapPresenter();

    OrderPresenter orderPresenter();

    CustomerHistoryOrderPresenter customerHistoryOrderPresenter();

    ChangePasswordPresenter changePasswordPresenter();

    SetNewPasswordPresenter setNewPasswordPresenter();

    MainPresenter mainPresenter();
}
