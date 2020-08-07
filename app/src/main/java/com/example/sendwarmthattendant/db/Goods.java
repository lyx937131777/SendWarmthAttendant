package com.example.sendwarmthattendant.db;

public class Goods
{
    private String name;
    private int picture;
    private GoodsType goodsType;
    private String description;
    private float price;

    public Goods(String name, int picture, GoodsType goodsType, String description, float price)
    {
        this.name = name;
        this.picture = picture;
        this.goodsType = goodsType;
        this.description = description;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPicture()
    {
        return picture;
    }

    public void setPicture(int picture)
    {
        this.picture = picture;
    }

    public GoodsType getGoodsType()
    {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType)
    {
        this.goodsType = goodsType;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }
}
