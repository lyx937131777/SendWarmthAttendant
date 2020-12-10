package com.example.sendwarmthattendant.dagger2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sendwarmthattendant.presenter.ChangePasswordPresenter;
import com.example.sendwarmthattendant.presenter.CustomerHistoryOrderPresenter;
import com.example.sendwarmthattendant.presenter.HistoricalOrdersPresenter;
import com.example.sendwarmthattendant.presenter.HomePresenter;
import com.example.sendwarmthattendant.presenter.LoginPresenter;
import com.example.sendwarmthattendant.presenter.MapPresenter;
import com.example.sendwarmthattendant.presenter.OrderDetailPresenter;
import com.example.sendwarmthattendant.presenter.OrderPresenter;
import com.example.sendwarmthattendant.presenter.RegisterPresenter;
import com.example.sendwarmthattendant.presenter.SetNewPasswordPresenter;
import com.example.sendwarmthattendant.presenter.SettingPresenter;
import com.example.sendwarmthattendant.util.CheckUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule
{
    private Context context;

    public MyModule(Context context)
    {
        this.context = context;
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public CheckUtil provideCheckUtil(Context context)
    {
        return new CheckUtil(context);
    }

    @Provides
    public Context provideContext()
    {
        return context;
    }

    @Provides
    public LoginPresenter provideLoginPresenter(Context context, SharedPreferences pref, CheckUtil checkUtil)
    {
        return new LoginPresenter(context,pref,checkUtil);
    }

    @Provides
    public RegisterPresenter provideRegisterPresenter(Context context, CheckUtil checkUtil, SharedPreferences pref)
    {
        return new RegisterPresenter(context,checkUtil,pref);
    }

    @Provides
    public HomePresenter provideHomePresenter(Context context, SharedPreferences pref){
        return new HomePresenter(context, pref);
    }

    @Provides
    public HistoricalOrdersPresenter provideHistoricalOrdersPresenter(Context context, SharedPreferences pref){
        return new HistoricalOrdersPresenter(context, pref);
    }

    @Provides
    public OrderDetailPresenter provideOrderDetailPresenter(Context context, SharedPreferences pref){
        return new OrderDetailPresenter(context,pref);
    }

    @Provides
    public SettingPresenter provideSettingPresenter(Context context, SharedPreferences pref){
        return new SettingPresenter(context,pref);
    }

    @Provides
    public MapPresenter provideMapPresenter(Context context, SharedPreferences pref){
        return new MapPresenter(context, pref);
    }

    @Provides
    public OrderPresenter provideOrderPresenter(Context context, SharedPreferences pref){
        return new OrderPresenter(context,pref);
    }

    @Provides
    public CustomerHistoryOrderPresenter provideCustomerHistoryOrderPresenter(Context context, SharedPreferences pref){
        return new CustomerHistoryOrderPresenter(context,pref);
    }

    @Provides
    ChangePasswordPresenter provideChangePasswordPresenter(Context context, SharedPreferences pref, CheckUtil checkUtil){
        return new ChangePasswordPresenter(context,pref,checkUtil);
    }

    @Provides
    SetNewPasswordPresenter provideSetNewPasswordPresenter(Context context, SharedPreferences pref, CheckUtil checkUtil){
        return new SetNewPasswordPresenter(context,pref,checkUtil);
    }
}
