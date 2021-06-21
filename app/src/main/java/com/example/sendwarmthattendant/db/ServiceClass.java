package com.example.sendwarmthattendant.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class ServiceClass extends LitePalSupport implements Serializable
{
    @SerializedName("id")
    private String internetId;
    @SerializedName("className")
    private String name;
    private String orderWorkType;
    private String des;
    private String image;
    private String remarkImg;

    private int clickCount;

    public String getRemarkImg() {
        return remarkImg;
    }

    public void setRemarkImg(String remarkImg) {
        this.remarkImg = remarkImg;
    }

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

    public String getOrderWorkType()
    {
        return orderWorkType;
    }

    public void setOrderWorkType(String orderWorkType)
    {
        this.orderWorkType = orderWorkType;
    }

    public String getDes()
    {
        return des;
    }

    public void setDes(String des)
    {
        this.des = des;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public int getClickCount()
    {
        return clickCount;
    }

    public void setClickCount(int clickCount)
    {
        this.clickCount = clickCount;
    }
}
