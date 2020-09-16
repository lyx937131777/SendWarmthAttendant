package com.example.sendwarmthattendant.db;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Helper implements Serializable
{
    @SerializedName("id")
    private String internetId;
    @SerializedName("helperName")
    private String name;
    @SerializedName("helperIdCard")
    private String idCardNumber;
    @SerializedName("helperTel")
    private String tel;
    private String idCardFront;
    private String idCardBack;
    @SerializedName("workClass1")
    private int workType1;
    @SerializedName("workClass2")
    private int workType2;

    private int storeId;
    private String auditStatus;

    public String getInternetId()
    {
        return internetId;
    }

    public void setInternetId(String internetId)
    {
        this.internetId = internetId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIdCardNumber()
    {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber)
    {
        this.idCardNumber = idCardNumber;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getIdCardFront()
    {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront)
    {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack()
    {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack)
    {
        this.idCardBack = idCardBack;
    }

    public int getWorkType1()
    {
        return workType1;
    }

    public void setWorkType1(int workType1)
    {
        this.workType1 = workType1;
    }

    public int getWorkType2()
    {
        return workType2;
    }

    public void setWorkType2(int workType2)
    {
        this.workType2 = workType2;
    }

    public int getStoreId()
    {
        return storeId;
    }

    public void setStoreId(int storeId)
    {
        this.storeId = storeId;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
    }
}
