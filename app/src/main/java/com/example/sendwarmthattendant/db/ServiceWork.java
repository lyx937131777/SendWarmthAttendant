package com.example.sendwarmthattendant.db;

import java.io.Serializable;

public class ServiceWork implements Serializable
{
    private String name;
    private String type;
    private String description;
    private float price;
    private String unit;
    private int picture;

    public ServiceWork(String name, String type, String description, float price, String unit,
                       int picture)
    {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.picture = picture;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public int getPicture()
    {
        return picture;
    }

    public void setPicture(int picture)
    {
        this.picture = picture;
    }
}
