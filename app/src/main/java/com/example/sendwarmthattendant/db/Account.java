package com.example.sendwarmthattendant.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Account extends LitePalSupport implements Serializable
{
    @SerializedName("id")
    private String internetId;
    private boolean activated;

    private String profile;

    private String loginName;
    private String password;
    private String roleType;

    private String customerId;
    private Customer customerInfo;

    private String workerId;
    private Worker workerInfo;

    private String helperId;
    private Helper helperInfo;

    public String getName(){
        if(customerInfo != null){
            return customerInfo.getName();
        }
        if(workerInfo != null){
            return workerInfo.getWorkerName();
        }
        if(helperInfo != null){
            return helperInfo.getName();
        }
        return "未知账户";
    }

    public String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public Worker getWorkerInfo() {
        return workerInfo;
    }

    public void setWorkerInfo(Worker workerInfo) {
        this.workerInfo = workerInfo;
    }

    public String getHelperId() {
        return helperId;
    }

    public void setHelperId(String helperId) {
        this.helperId = helperId;
    }

    public Helper getHelperInfo() {
        return helperInfo;
    }

    public void setHelperInfo(Helper helperInfo) {
        this.helperInfo = helperInfo;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}